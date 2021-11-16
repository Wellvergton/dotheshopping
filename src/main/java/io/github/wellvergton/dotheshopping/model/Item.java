package io.github.wellvergton.dotheshopping.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.wellvergton.dotheshopping.deserializer.ItemDeserializer;
import io.github.wellvergton.dotheshopping.enums.Measurement;
import io.github.wellvergton.dotheshopping.serializer.ItemSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
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
