package com.jp.dark.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumStatus {

    INICIADA("iniciada"),
    FINALIZADA("finalizada"),
    EXPIRADA("expirada"),
    CANCELADA("cancelada");
    private String description;
}
