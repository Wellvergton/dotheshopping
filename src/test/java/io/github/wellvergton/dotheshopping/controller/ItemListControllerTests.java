package io.github.wellvergton.dotheshopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wellvergton.dotheshopping.model.Item;
import io.github.wellvergton.dotheshopping.model.ItemList;
import io.github.wellvergton.dotheshopping.model.User;
import io.github.wellvergton.dotheshopping.service.ItemListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
  value = ItemListController.class,
  excludeAutoConfiguration = SecurityAutoConfiguration.class
)
class ItemListControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ItemListController itemListController;

  @MockBean
  private ItemListService itemListService;

  private final User user = new User(1L, "", "", new ArrayList<ItemList>());
  private final ItemList list = new ItemList(1L, "List", user, new ArrayList<Item>());
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldFindAnListById() throws Exception {
    when(itemListService.findList(1L)).thenReturn(Optional.of(list));

    this.mockMvc.perform(get("/list/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(list.getId()));
  }

  @Test
  void givenWrongIdShouldReturnNotFound() throws Exception {
    when(itemListService.findList(2L)).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/list/2"))
      .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnTheCreatedList() throws Exception {
    ItemList newList = new ItemList(0L, "List", user, new ArrayList<Item>());

    when(itemListService.saveList(any(ItemList.class))).thenReturn(list);

    this.mockMvc.perform(post("/list")
        .content(mapper.writeValueAsString(newList))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(list.getId()));
  }

  @Test
  void shouldReturnTheUpdatedItem() throws Exception {
    ItemList updatedList = new ItemList(1L, "Same List", user, new ArrayList<Item>());

    when(itemListService.updateList(eq(updatedList.getId()), any(ItemList.class)))
      .thenReturn(updatedList);

    this.mockMvc.perform(put("/list/1")
        .content(mapper.writeValueAsString(updatedList))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(updatedList.getId()))
      .andExpect(jsonPath("$.name").value(updatedList.getName()));
  }

  @Test
  void shouldReturnNoContentWhenDeleteList() throws Exception {
    this.mockMvc.perform(delete("/list/1"))
      .andExpect(status().isNoContent());
  }
}
