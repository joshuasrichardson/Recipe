package Database;

/**
 * Exception thrown when things go wrong with the database. Used to help with generating error messages.
 */
public class DatabaseAccessException extends Exception {
  DatabaseAccessException(String message)
  {
    super(message);
  }
}

