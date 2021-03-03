package Result;

import Ingredient.Ingredient;

public class GetIngredientResult extends Result {
  Ingredient ingredient;

  public GetIngredientResult(boolean success, String message) {
    super(success, message);
  }

  public GetIngredientResult(boolean success, Ingredient ingredient) {
    super(success);
    this.ingredient = ingredient;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }
}
