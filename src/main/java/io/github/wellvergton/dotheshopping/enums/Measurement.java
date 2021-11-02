package io.github.wellvergton.dotheshopping.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Measurement {
  UNITY("unity"),
  GRAMS("grams"),
  KILOGRAMS("kilograms"),
  MILLILITERS("milliliters"),
  LITERS("liters");

  private final String value;

  Measurement(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }

  @JsonCreator
  public static Measurement translate(final String term) {
    return Stream.of(Measurement.values())
      .filter(target -> target.value.equals(term))
      .findFirst()
      .orElse(null);
  }
}
