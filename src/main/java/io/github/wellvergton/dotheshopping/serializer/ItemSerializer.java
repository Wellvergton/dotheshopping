package io.github.wellvergton.dotheshopping.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.wellvergton.dotheshopping.model.Item;

import java.io.IOException;

public class ItemSerializer extends StdSerializer<Item> {
  public ItemSerializer(Class<Item> type) {
    super(type);
  }

  public ItemSerializer() {
    this(null);
  }

  @Override
  public void serialize(
    Item item, JsonGenerator generator, SerializerProvider provider
  ) throws IOException, JsonProcessingException {
    generator.writeStartObject();
    generator.writeNumberField("id", item.getId());
    generator.writeStringField("name", item.getName());
    generator.writeStringField("measurement", item.getMeasurement().getValue());
    generator.writeNumberField("amount", item.getAmount());
    generator.writeBooleanField("isOptional", item.isOptional());
    generator.writeNumberField("listId", item.getItemList().getId());
    generator.writeEndObject();
  }
}
