package HandlerTest;

import Handler.ObjectEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectEncoderTest {

  @Test
  public void addAuthToJsonTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    String authorizedJson = encoder.addAuthTokenToJson("auth", "{\n\"name\":\"name\"\n}");
    assertEquals("{\n\"authToken\":\"auth\",\n\"name\":\"name\"\n}", authorizedJson);
  }

  @Test
  public void addAuthToIngredientJsonTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    String authorizedJson = encoder.addAuthTokenToJson("auth", "{\n" +
            "\t\"ingredientName\":\"name\",\n" +
            "\t\"mostRecentPrice\":\"mostRecentPrice\",\n" +
            "\t\"number\":\"1\",\n" +
            "\t\"container\":\"box\",\n" +
            "\t\"amount\":\"1\",\n" +
            "\t\"unit\":\"ounces\",\n" +
            "\t\"expirationDate\":\"2021 12 31\",\n" +
            "\t\"brand\":\"Kroger\",\n" +
            "\t\"foodGroup\":\"vegetable\",\n" +
            "\t\"mostRecentStore\":\"Smith's\",\n" +
            "\t\"city\":\"Provo\",\n" +
            "\t\"allergens\":[\n" +
            "\t\t\n" +
            "\t]\n" +
            "}");
    assertEquals("{\n\"authToken\":\"auth\",\n" +
            "\t\"ingredientName\":\"name\",\n" +
            "\t\"mostRecentPrice\":\"mostRecentPrice\",\n" +
            "\t\"number\":\"1\",\n" +
            "\t\"container\":\"box\",\n" +
            "\t\"amount\":\"1\",\n" +
            "\t\"unit\":\"ounces\",\n" +
            "\t\"expirationDate\":\"2021 12 31\",\n" +
            "\t\"brand\":\"Kroger\",\n" +
            "\t\"foodGroup\":\"vegetable\",\n" +
            "\t\"mostRecentStore\":\"Smith's\",\n" +
            "\t\"city\":\"Provo\",\n" +
            "\t\"allergens\":[\n" +
            "\t\t\n" +
            "\t]\n" +
            "}", authorizedJson);
  }

  @Test
  public void addAuthToJsonFailTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    assertThrows(IllegalArgumentException.class, () -> {
      encoder.addAuthTokenToJson("auth", "");
    });
  }
}
