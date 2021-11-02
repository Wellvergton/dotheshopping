package io.github.wellvergton.dotheshopping.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.wellvergton.dotheshopping.model.ItemList;
import io.github.wellvergton.dotheshopping.model.User;

import java.io.IOException;

public class ItemListDeserializer extends StdDeserializer<ItemList> {
  public ItemListDeserializer(Class<ItemList> valueClass) {
    super(valueClass);
  }

  public ItemListDeserializer() {
    this(null);
  }

  @Override
  public ItemList deserialize(
    JsonParser parser, DeserializationContext context
  ) throws IOException, JsonProcessingException {
    JsonNode node = parser.getCodec().readTree(parser);
    User user = new User();
    ItemList list = new ItemList();

    user.setId(node.get("ownerId").asLong());

    list.setName(node.get("name").asText());
    list.setUser(user);

    return list;
  }
}
