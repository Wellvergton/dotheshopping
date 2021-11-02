package io.github.wellvergton.dotheshopping.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.wellvergton.dotheshopping.model.Item;
import io.github.wellvergton.dotheshopping.model.ItemList;

import java.io.IOException;

public class ItemListSerializer extends StdSerializer<ItemList> {
  public ItemListSerializer(Class<ItemList> type) {
    super(type);
  }

  public ItemListSerializer() {
    this(null);
  }

  @Override
  public void serialize(
    ItemList list, JsonGenerator generator, SerializerProvider provider
  ) throws IOException, JsonProcessingException {
    generator.writeStartObject();
    generator.writeNumberField("id", list.getId());
    generator.writeStringField("name", list.getName());
    generator.writeNumberField("ownerId", list.getUser().getId());
    generator.writeArrayFieldStart("items");

    for (Item item : list.getItems()) {
      generator.writeObject(item);
    }

    generator.writeEndArray();
    generator.writeEndObject();;
  }
}
