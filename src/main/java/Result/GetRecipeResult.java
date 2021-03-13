package Result;

import Model.Recipe;

public class GetRecipeResult extends Result {

  private Recipe recipe;

  public GetRecipeResult(boolean success) {
    super(success);
  }

  public GetRecipeResult(boolean success, String message) {
    super(success, message);
  }

  public GetRecipeResult(boolean success, Recipe recipe) {
    super(success);
    this.recipe = recipe;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }
}
