package Model;

import Model.Ingredient;
import Model.Recipe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * stores information about recipes added and accessed by the user by adding them and accessing from their book
 */
public class RecipeBook {
/*
  private String author;

  ArrayList<Recipe> recipes = new ArrayList<>();

  public RecipeBook() { }

  public RecipeBook(String author) {
    this.author = author;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * lets a user insert new recipes into their recipe book
   *
   * @param input a scanner that takes in user input from the keyboard as they list new ingredients and amounts
   *              for their recipe
   *
  public Recipe addRecipe(Scanner input) {

    String userRecipeName;

    String nameNumberAndUnit;

    Ingredient ingredient;

    String ingredientName;

    double ingredientNumber;

    String ingredientUnit;

    ArrayList<Ingredient> userIngredients = new ArrayList<>();

    System.out.println("What is the name of the recipe?");
    userRecipeName = input.nextLine();
    System.out.println("Please enter all the ingredients one at a time.");
    System.out.println("(Name number unit) Example: butter 1 pound");
    System.out.println("Type \"done\" when you are finished.");
    nameNumberAndUnit = input.nextLine();
    while (!"done".equals(nameNumberAndUnit)) {
      ingredientName = Ingredient.findIngredientName(nameNumberAndUnit);
      ingredientNumber = Ingredient.findIngredientNumber(nameNumberAndUnit);
      ingredientUnit = Ingredient.findIngredientUnit(nameNumberAndUnit);
      ingredient = new Ingredient(ingredientName, ingredientNumber, ingredientUnit);
      userIngredients.add(ingredient);
      nameNumberAndUnit = input.nextLine();
    }
    Recipe recipe = new Recipe(userRecipeName, userIngredients);
    System.out.println(recipe.toString());

    recipes.add(recipe);

    return recipe;
  }*/
}
