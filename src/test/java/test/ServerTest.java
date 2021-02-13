package test;

import Main.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {

  @Test
  public void findUsernameTest() {
    String arg = "jdbc:sqlite:Name.sqlite";
    String name = Server.findUsername(arg);
    assertEquals(name, "Name");
  }
}
