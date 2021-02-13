package Database;

import Database.Database;

public class ArgumentException  extends Exception {
  ArgumentException() {
    super("Not enough or too many arguments.");
  }

  public ArgumentException(String message) {
    super(message);
  }
}
