package io.github.wellvergton.dotheshopping.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.wellvergton.dotheshopping.deserializer.ItemListDeserializer;
import io.github.wellvergton.dotheshopping.serializer.ItemListSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonSerialize(using = ItemListSerializer.class)
@JsonDeserialize(using = ItemListDeserializer.class)
@Entity(name = "lists")
public class ItemList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToMany(mappedBy = "itemList", orphanRemoval = true, cascade = CascadeType.ALL)
  @OrderBy
  private List<Item> items = new ArrayList<>();
}
