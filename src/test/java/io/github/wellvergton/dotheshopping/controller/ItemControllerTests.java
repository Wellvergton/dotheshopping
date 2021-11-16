package io.github.wellvergton.dotheshopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wellvergton.dotheshopping.enums.Measurement;
import io.github.wellvergton.dotheshopping.model.Item;
import io.github.wellvergton.dotheshopping.model.ItemList;
import io.github.wellvergton.dotheshopping.model.User;
import io.github.wellvergton.dotheshopping.service.ItemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
  value = ItemController.class,
  excludeAutoConfiguration = SecurityAutoConfiguration.class
)
public class ItemControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ItemController itemController;

  @MockBean
  private ItemService itemService;

  private static Item item;
  private static ItemList list;
  private final ObjectMapper mapper = new ObjectMapper();

  @BeforeAll
  static void setup() {
    list = new ItemList(1L, "List", new User(), null);
    item = new Item(1L, "Test", Measurement.UNITY, 1, false, list);
  }

  @Test
  public void shouldFindAnItemById() throws Exception {
    when(itemService.findItem(item.getId())).thenReturn(Optional.of(item));

    this.mockMvc.perform(get("/item/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(item.getId()));
  }

  @Test
  public void GivenWrongIdShouldReturnNotFound() throws Exception {
    when(itemService.findItem(2L)).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/item/2"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnTheCreatedItem() throws Exception {
    Item newItem = new Item(0L, "Test", Measurement.UNITY, 1, false, list);

    when(itemService.saveItem(any(Item.class))).thenReturn(item);

    this.mockMvc.perform(post("/item")
        .content(mapper.writeValueAsString(newItem))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  public void shouldReturnTheUpdatedItem() throws Exception {
    Item updatedItem = new Item(1L, "Same Item", Measurement.UNITY, 1, false, list);

    when(itemService.updateItem(eq(updatedItem.getId()), any(Item.class)))
      .thenReturn(updatedItem);

    this.mockMvc.perform(put("/item/1")
        .content(mapper.writeValueAsString(updatedItem))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(updatedItem.getId()))
      .andExpect(jsonPath("$.name").value(updatedItem.getName()));
  }
}
