package com.jp.dark.models.repository;

import com.jp.dark.models.entities.PricesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceItemRepository extends JpaRepository<PricesItem, Integer> {
}
