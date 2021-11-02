package io.github.wellvergton.dotheshopping.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.wellvergton.dotheshopping.enums.Measurement;
import io.github.wellvergton.dotheshopping.model.Item;
import io.github.wellvergton.dotheshopping.model.ItemList;

import java.io.IOException;

public class ItemDeserializer extends StdDeserializer<Item> {
  public ItemDeserializer(Class<Item> valueClass) {
    super(valueClass);
  }

  public ItemDeserializer() {
    this(null);
  }

  @Override
  public Item deserialize(
    JsonParser parser, DeserializationContext context
  ) throws IOException, JsonProcessingException {
    JsonNode node = parser.getCodec().readTree(parser);
    ItemList list = new ItemList();
    Item item = new Item();

    list.setId(node.get("listId").asLong());

    item.setName(node.get("name").asText());
    item.setMeasurement(Measurement.translate(node.get("measurement").asText()));
    item.setAmount(node.get("amount").asInt());
    item.setOptional(node.get("isOptional").asBoolean());
    item.setItemList(list);

    return item;
  }
}
