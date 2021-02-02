package Recipe;

public class DatabaseException extends Exception {
  DatabaseException() {
    super("There was a problem with the database");
  }

  DatabaseException(String message) {
    super(message);
  }
}
