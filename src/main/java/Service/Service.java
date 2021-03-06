package Service;

import Database.AuthTokenDAO;
import Database.Database;
import Database.DatabaseAccessException;
import Database.UserDAO;
import Model.AuthToken;
import Model.User;
import java.sql.SQLException;

public class Service {
  Database db = new Database();
  String connection;

  Service(String connection) {
    this.connection = connection;
  }

  public User findOwner(String authToken) throws SQLException, DatabaseAccessException {
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection(connection));
    AuthToken authTokenObject = authTokenDAO.accessAuthTokenFromTable(authToken);
    UserDAO userDAO = new UserDAO(db.getConnection(connection));
    User user = userDAO.accessUserFromTable(authTokenObject.getUsername());
    db.closeConnection(true);
    return user;
  }
}
