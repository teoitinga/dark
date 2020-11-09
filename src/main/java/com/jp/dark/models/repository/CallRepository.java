package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, String> {
}
