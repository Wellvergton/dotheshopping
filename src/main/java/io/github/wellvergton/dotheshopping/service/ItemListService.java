package io.github.wellvergton.dotheshopping.service;

import io.github.wellvergton.dotheshopping.model.ItemList;
import io.github.wellvergton.dotheshopping.repository.ItemListRepository;
import io.github.wellvergton.dotheshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemListService {
  @Autowired
  private ItemListRepository itemListRepository;

  @Autowired
  private UserRepository userRepository;

  public Optional<ItemList> findList(Long id) {
    return itemListRepository.findById(id);
  }

  public ItemList saveList(ItemList list) {
    return itemListRepository.save(list);
  }

  public ItemList updateList(Long id, ItemList list) {
    ItemList dbList = itemListRepository.getById(id);

    dbList.setName(list.getName());

    return itemListRepository.save(dbList);
  }

  public void deleteList(Long id) {
    itemListRepository.deleteById(id);
  }
}
