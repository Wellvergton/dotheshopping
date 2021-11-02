package io.github.wellvergton.dotheshopping.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.wellvergton.dotheshopping.deserializer.ItemDeserializer;
import io.github.wellvergton.dotheshopping.enums.Measurement;
import io.github.wellvergton.dotheshopping.serializer.ItemSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@JsonSerialize(using = ItemSerializer.class)
@JsonDeserialize(using = ItemDeserializer.class)
@Entity(name = "items")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Measurement measurement;
  private Integer amount;
  private boolean isOptional;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_list_id")
  private ItemList itemList;
}
