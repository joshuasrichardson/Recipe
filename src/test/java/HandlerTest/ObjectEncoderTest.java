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
  public void addAuthToJsonFailTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    assertThrows(IllegalArgumentException.class, () -> {
      encoder.addAuthTokenToJson("auth", "");
    });
  }
}
