package io.github.wellvergton.dotheshopping.controller;

import io.github.wellvergton.dotheshopping.model.ItemList;
import io.github.wellvergton.dotheshopping.service.ItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/list")
public class ItemListController {
  @Autowired
  private ItemListService itemListService;

  @GetMapping(path = "/{id}")
  public ResponseEntity<ItemList> findList(@PathVariable Long id) {
    Optional<ItemList> list = itemListService.findList(id);

    return list.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ItemList> saveList(
    @RequestBody ItemList itemList, HttpServletRequest request
  ) {
    ItemList savedList = itemListService.saveList(itemList);
    String url = request.getRequestURL().toString() + "/" + savedList.getId();

    return ResponseEntity.created(URI.create(url)).body(savedList);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ItemList> updateList(@PathVariable Long id, @RequestBody ItemList list) {
    itemListService.updateList(id, list);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<ItemList> deleteList(@PathVariable Long id) {
    itemListService.deleteList(id);

    return ResponseEntity.noContent().build();
  }
}
