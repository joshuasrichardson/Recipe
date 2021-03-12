package Model;

import Model.Ingredient;

import java.util.*;

/**
 * stores information about a recipe such as its name and ingredients
 */
public class Recipe {

  private int id;

  private String name;

  private ArrayList<Ingredient> ingredients;

  /**
   * constructs a recipe with provided name and ingredients that the user can refer to later
   *
   * @param name name of the recipe
   * @param ingredients a list of ingredients that is used in the recipe
   */
  public Recipe(String name, ArrayList<Ingredient> ingredients) {
    this.name = name;
    this.ingredients = new ArrayList<>(ingredients);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Ingredient> getIngredients() {
    return ingredients;
  }

}

