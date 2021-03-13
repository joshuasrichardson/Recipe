package Result;

public class AddRecipeResult extends Result {

  public AddRecipeResult(boolean success) {
    super(success);
  }

  public AddRecipeResult(boolean success, String message) {
    super(success, message);
  }
}
