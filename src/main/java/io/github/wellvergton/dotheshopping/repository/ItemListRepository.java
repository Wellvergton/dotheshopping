package io.github.wellvergton.dotheshopping.repository;

import io.github.wellvergton.dotheshopping.model.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemListRepository extends JpaRepository<ItemList, Long> {
}
