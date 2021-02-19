package test;

import Main.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {

  @Test
  public void findUsernameTest() {
    String arg = "jdbc:sqlite:Name.sqlite";
    String name = Main.findUsername(arg);
    assertEquals("Name", name);
  }
}
