package Result;

public class AddIngredientResult extends Result {

  public AddIngredientResult(boolean success) {
    super(success);
  }

  public AddIngredientResult(boolean success, String message) {
    super(success, message);
  }
}
