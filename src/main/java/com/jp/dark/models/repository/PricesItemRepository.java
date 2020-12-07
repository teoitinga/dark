package com.jp.dark.models.repository;

import com.jp.dark.models.entities.PricesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesItemRepository extends JpaRepository<PricesItem, Integer> {
}
