package Model;

import java.util.Objects;

public class RecipeIngredient {

  private String ingredient;

  private Double amount;

  private String unit;

  public RecipeIngredient() {
  }

  public RecipeIngredient(String ingredient, Double amount, String unit) {
    this.ingredient = ingredient;
    this.amount = amount;
    this.unit = unit;
  }

  public String getIngredient() {
    return ingredient;
  }

  public void setIngredient(String ingredient) {
    this.ingredient = ingredient;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RecipeIngredient recipeIngredient1 = (RecipeIngredient) o;
    return Objects.equals(ingredient, recipeIngredient1.ingredient) &&
            Objects.equals(amount, recipeIngredient1.amount) &&
            Objects.equals(unit, recipeIngredient1.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ingredient, amount, unit);
  }
}
