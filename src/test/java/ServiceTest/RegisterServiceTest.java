package ServiceTest;

import Database.*;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static test.ServerTest.TEST_CONNECTION_URL;

public class RegisterServiceTest {

  Database db;

  @BeforeEach
  @AfterEach
  public void setup() {
    try {
      db = new Database();
      db.getConnection(TEST_CONNECTION_URL);
      db.createTables();
      db.clearAllTables();
      db.closeConnection(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void registerUserTest() {
    RegisterRequest request = new RegisterRequest( "username", "password",
            "email", "firstName", "lastName");
    RegisterService service = new RegisterService(TEST_CONNECTION_URL);
    RegisterResult result = service.register(request);
    RegisterResult expectedResult = new RegisterResult("username".hashCode() + LocalDateTime.now().toString(), "username", "lastNameusername", true);
    assertEquals(expectedResult.getUsername(), result.getUsername());
  }

  @Test
  public void registerUsernameFailTest() {
    RegisterRequest request = new RegisterRequest(null, "password",
            "email", "firstName", "lastName");
    RegisterService service = new RegisterService(TEST_CONNECTION_URL);
    RegisterResult result = service.register(request);
    RegisterResult expectedResult = new RegisterResult(false, "Error: Something is wrong with the user's username.");
    assertEquals(expectedResult, result);
  }

  @Test
  public void registerUserTwiceFailTest() {
    RegisterRequest request = new RegisterRequest("username", "password",
            "email", "firstName", "lastName");
    RegisterService service = new RegisterService(TEST_CONNECTION_URL);
    service.register(request);
    RegisterResult registerTwiceResult = service.register(request);
    RegisterResult expectedResult = new RegisterResult(false, "Error: User already exists.");
    assertEquals(expectedResult, registerTwiceResult);
  }
}
