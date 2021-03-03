package Request;

public class GetIngredientRequest {

  private final String name;
  private final String authtoken;

  public GetIngredientRequest(String name, String authtoken) {
    this.name = name;
    this.authtoken = authtoken;
  }

  public String getName() {
    return name;
  }

  public String getAuthtoken() {
    return authtoken;
  }
}
