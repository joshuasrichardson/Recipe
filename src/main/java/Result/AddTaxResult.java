package Result;

public class AddTaxResult extends Result{

  public AddTaxResult(boolean success) {
    super(success);
  }

  public AddTaxResult(boolean success, String message) {
    super(success, message);
  }
}
