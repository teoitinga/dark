package com.jp.dark.security.jwt;

import com.jp.dark.DarkApplication;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.security.dto.AutheticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.expiration}")
    private String EXPIRATION_TIME;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_USER = "name";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_EXPIRATION = "expiration";
    static final String CLAIM_KEY_CREATED = "created";
    /**
     * Gera um novo token JWT a partir de dados do usuario.
     *
     * @param usuario
     * @return String
     */
    public String geraToken(Persona usuario){

        long expiracao = Long.valueOf(EXPIRATION_TIME);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracao);
        //converte LocalDateTime para Date para configurar o token
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        Date dataGeracao = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        //Configurando Claims
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, usuario.getCpf());
        claims.put(CLAIM_KEY_USER, usuario.getNome());
        claims.put(CLAIM_KEY_ROLE, usuario.getPermissao());
        claims.put(CLAIM_KEY_EXPIRATION, data);
        claims.put(CLAIM_KEY_CREATED, dataGeracao);
        if (usuario != null) {
            claims.put(CLAIM_KEY_USER, usuario.getNome());

        }
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public String geraToken(AutheticatedUser usuario) {
        long expiracao = Long.valueOf(EXPIRATION_TIME);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracao);
        //converte LocalDateTime para Date para configurar o token
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        Date dataGeracao = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        //Configurando Claims
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, usuario.getUsername());
        claims.put(CLAIM_KEY_USER, usuario.getUser());
        claims.put(CLAIM_KEY_ROLE, usuario.getAuthorities());
        claims.put(CLAIM_KEY_EXPIRATION, data);
        claims.put(CLAIM_KEY_CREATED, dataGeracao);
        if (usuario != null) {
            claims.put(CLAIM_KEY_USER, usuario.getUsername());

        }
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    /**
     * Gera um novo token JWT contendo os dados (claims) fornecidos.
     *
     * @param claims
     * @return String
     */
    private String geraToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(gerarDataExpiracao())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public String refreshToken(String token) {
        String refreshedToken;
        long expString = Long.valueOf(this.EXPIRATION_TIME);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
        Date dataGeracao = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        try {
            final Claims claims = this.getClaims(token);
            claims.put(CLAIM_KEY_CREATED, dataGeracao);

            refreshedToken = this.doGenerateToken(claims);

        }catch(Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    private String doGenerateToken(Map<String, Object> claims) {

        Date dataGera = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(Long.valueOf(this.EXPIRATION_TIME));
        Date dataExpira = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        claims.put(CLAIM_KEY_CREATED, dataGera);
        claims.put(CLAIM_KEY_EXPIRATION, dataExpira);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(dataExpira)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Verifica se um token JTW está expirado.
     *
     * @param token
     * @return boolean
     */
    private boolean tokenExpirado(String token) {
        Date dataExpiracao = this.getExpirationDateFromToken(token);
        if (dataExpiracao == null) {
            return false;
        }
        return dataExpiracao.before(new Date());
    }
    public boolean tokenValido(String token){

        try {
            Claims claims = this.getClaims(token);

            Long expiration = (Long) claims.get("expiration");

            LocalDateTime exp = Instant.ofEpochMilli(expiration).atZone(ZoneId.systemDefault()).toLocalDateTime();

            if(!LocalDateTime.now().isAfter(exp)){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }


    }

    public String getLoginUsuario(String token) throws ExpiredJwtException {
        return (String) this.getClaims(token).get("login");
    }

    public static void main(String args[]){
        ConfigurableApplicationContext context = SpringApplication.run(DarkApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Persona usuario = Persona.builder()
                .cpf("04459471604")
                .nome("João Paulo")
                .permissao(EnumPermissao.ADMINISTRADOR)
                .build();
        String token = service.geraToken(usuario);
        log.info("Token: {}", token);
        boolean tokenValido = service.tokenValido(token);
        log.info("tokenValido? {}", tokenValido);

        log.info("Token usuario: {}", service.getLoginUsuario(token));
    }

    /**
     * Retorna um novo token JWT com base nos dados do usuários.
     *
     * @param user
     * @return String
     */
    public String getToken(Persona user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, user.getCpf());
        claims.put(CLAIM_KEY_ROLE, user.getPermissao());
        claims.put(CLAIM_KEY_CREATED, new Date());
        if (user != null) {
            claims.put(CLAIM_KEY_USER, user.getNome());

        }

        return geraToken(claims);
    }


    /**
     * Retorna a data de expiração de um token JWT.
     *
     * @param token
     * @return Date
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }
    /**
     * Retorna a data de expiração com base na data atual.
     *
     * @return Date
     */
    private Date gerarDataExpiracao() {
        long expString = Long.valueOf(this.EXPIRATION_TIME);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date dataExpira = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
        return dataExpira;
    }

    private Claims obterClaims(String token)  throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
    }
    public boolean canTokenBeRefreshed(String token) {
        return this.tokenValido(token);
    }
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(Exception e) {
            claims = null;
        }
        return claims;
    }

    public String obterLogin(String token){
        return obterClaims(token).getSubject();
    }

}
