package ServiceTest;

import Database.*;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

  Database db;

  @BeforeEach
  @AfterEach
  public void setup() {
    try {
      db = new Database();
      db.getConnection("jdbc:sqlite:username.sqlite");
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
    RegisterService service = new RegisterService(true);
    RegisterResult result = service.register(request);
    RegisterResult expectedResult = new RegisterResult("username" + LocalDate.now().toString(), "username", "lastNameusername", true);
    assertEquals(expectedResult, result);
  }

  @Test
  public void registerUsernameFailTest() {
    RegisterRequest request = new RegisterRequest(null, "password",
            "email", "firstName", "lastName");
    RegisterService service = new RegisterService(false);
    RegisterResult result = service.register(request);
    RegisterResult expectedResult = new RegisterResult(false, "Error: Something is wrong with the user's username.");
    assertEquals(expectedResult, result);
  }

  @Test
  public void registerUserTwiceFailTest() {
    RegisterRequest request = new RegisterRequest("username", "password",
            "email", "firstName", "lastName");
    RegisterService service = new RegisterService(true);
    service.register(request);
    RegisterResult registerTwiceResult = service.register(request);
    RegisterResult expectedResult = new RegisterResult(false, "Error: [SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed " +
            "(UNIQUE constraint failed: User.person_id)");
    assertEquals(expectedResult, registerTwiceResult);
  }
}
