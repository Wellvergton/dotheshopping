package io.github.wellvergton.dotheshopping.service;

import io.github.wellvergton.dotheshopping.model.Item;
import io.github.wellvergton.dotheshopping.repository.ItemListRepository;
import io.github.wellvergton.dotheshopping.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemListRepository itemListRepository;

  public Optional<Item> findItem(Long id) {
    return itemRepository.findById(id);
  }

  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  public Item updateItem(Long id, Item item) {
    item.setId(id);

    return itemRepository.save(item);
  }

  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }
}
