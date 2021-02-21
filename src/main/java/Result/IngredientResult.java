package Result;

public class IngredientResult extends Result {

  public IngredientResult(boolean success) {
    super(success);
  }

  public IngredientResult(boolean success, String message) {
    super(success, message);
  }
}
