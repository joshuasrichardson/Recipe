package Recipe;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * stores information about a recipe such as its name and ingredients
 */
public class Recipe {

  private int id;

  private String name;

  private ArrayList<String> ingredients;

  /**
   * constructs a recipe with provided name and ingredients that the user can refer to later
   *
   * @param name name of the recipe
   * @param ingredients a list of ingredients that is used in the recipe
   */
  public Recipe(String name, ArrayList<String> ingredients) {
    this.name = name;
    this.ingredients = new ArrayList<>(ingredients);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Recipe{'\n'" +
            "Name: " + name + '\n' +
            "Ingredients: " + ingredients + '\n' +
            '}';
  }
}

