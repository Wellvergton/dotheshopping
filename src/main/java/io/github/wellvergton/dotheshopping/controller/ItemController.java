package io.github.wellvergton.dotheshopping.controller;

import io.github.wellvergton.dotheshopping.model.Item;
import io.github.wellvergton.dotheshopping.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {
  @Autowired
  private ItemService itemService;

  @GetMapping(path = "/{id}")
  public ResponseEntity<Item> findItem(@PathVariable Long id) {
    Optional<Item> item = itemService.findItem(id);

    return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Item> saveItem(
    @RequestBody Item item, HttpServletRequest request
  ) {
    Item savedItem = itemService.saveItem(item);
    String url = request.getRequestURL().toString() + "/" + savedItem.getId();

    return ResponseEntity.created(URI.create(url)).body(savedItem);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
    return ResponseEntity.ok(itemService.updateItem(id, item));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Item> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);

    return ResponseEntity.noContent().build();
  }
}
