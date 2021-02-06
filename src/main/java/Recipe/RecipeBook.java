package Recipe;

import java.util.ArrayList;
import java.util.Scanner;

public class RecipeBook {

  ArrayList<Recipe> recipes = new ArrayList<>();
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
