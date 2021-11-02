package io.github.wellvergton.dotheshopping.repository;

import io.github.wellvergton.dotheshopping.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
