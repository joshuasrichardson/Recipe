package Recipe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * stores information about recipes added and accessed by the user by adding them and accessing from their book
 */
public class RecipeBook {

  ArrayList<Recipe> recipes = new ArrayList<>();

  /**
   * lets a user insert new recipes into their recipe book
   *
   * @param input a scanner that takes in user input from the keyboard as they list new ingredients and amounts
   *              for their recipe
   */
  public void addRecipe(Scanner input) {

    String userRecipeName;

    String ingredient;

    ArrayList<String> userIngredients = new ArrayList<>();

    System.out.println("What is the name of the recipe?");
    userRecipeName = input.next();
    System.out.println("Please enter all the ingredients one at a time,");
    System.out.println("and type \"done\" when you are finished.");
    ingredient = input.next();
    while (!"done".equals(ingredient)) {
      userIngredients.add(ingredient);
      ingredient = input.next();
    }
    Recipe recipe = new Recipe(userRecipeName, userIngredients);
    System.out.println(recipe.toString());

    recipes.add(recipe);
  }
}
