package DatabaseTest;

import Database.Database;
import Database.RecipeDAO;
import Model.RecipeIngredient;
import Model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static DatabaseTest.DatabaseTest.TEST_CONNECTION_URL;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeDAOTest {
  Database database;
  RecipeDAO recipeDAO;
  Recipe recipe;
  Recipe recipe1;
  Recipe recipe2;

  @BeforeEach
  public void setup() {
    database = new Database();

    ArrayList<RecipeIngredient> ingredients = new ArrayList<>(List.of(
            new RecipeIngredient("cereal", 10.0, "pounds"),
            new RecipeIngredient("milk", 2.0, "gallons")));
    recipe = new Recipe("food", "me", 10, "edible", "just do it",
            15, new ArrayList<>(List.of("spoon", "bowl")), new ArrayList<>(List.of("oven")),
            350, 100, ingredients);

    ArrayList<RecipeIngredient> ingredients1 = new ArrayList<>(List.of(
            new RecipeIngredient("cereal", 10.0, "pounds"),
            new RecipeIngredient("eggs", 2.0, "dozen")));
    recipe1 = new Recipe("food", "me", 10, "same recipe name but different description",
            "and instructions", 10, new ArrayList<>(List.of("fork", "bowl")),
            new ArrayList<>(List.of("microwave")), 350, 1000, ingredients1);

    ArrayList<RecipeIngredient> ingredients2 = new ArrayList<>(List.of(
            new RecipeIngredient("flour", 6.0, "cups"),
            new RecipeIngredient("eggs", 3.0, "eggs")));
    String description = "a description that is more than just one word and might actually include some valuable information.";
    String instructions = "1. some instructions\n2. that have multiple steps\n3. and multiple newlines.";
    recipe2 = new Recipe("other food", "someone else", 5, description, instructions,
            10, new ArrayList<>(List.of("spoon", "mixing bowl")), new ArrayList<>(List.of("oven")),
            350, 100, ingredients2);

    try {
      database.openConnection(TEST_CONNECTION_URL);
      database.clearAllTables();
      recipeDAO = new RecipeDAO(database.getConnection(TEST_CONNECTION_URL));
      recipeDAO.createTables();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @AfterEach
  public void disconnect() {
    try {
      database.closeConnection(true);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Test
  public void accessRecipeTest() {
    try {
      recipeDAO.addRecipe(recipe);
      recipeDAO.addRecipe(recipe2);
      Recipe sameRecipe = recipeDAO.accessRecipe("food");
      Recipe sameRecipe2 = recipeDAO.accessRecipe("other food");
      assertEquals(recipe, sameRecipe);
      assertEquals(recipe2, sameRecipe2);
    }
    catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void accessRecipeNotExistTest() {
    assertNull(recipeDAO.accessRecipe("not yet added recipe"));
  }

  @Test
  public void accessRecipeIngredientsTest() {
    try {
      for (RecipeIngredient ingredient : recipe.getIngredients()) {
        recipeDAO.addRecipeIngredient(recipe.getName(), ingredient);
      }
      ArrayList<RecipeIngredient> sameRecipeIngredients = recipeDAO.accessRecipeIngredients(recipe.getName());
      assertEquals(recipe.getIngredients(), sameRecipeIngredients);
    }
    catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void accessRecipeIngredientsNotExistTest() {
    assertEquals(0, recipeDAO.accessRecipeIngredients("doesn't exist").size());
  }

  @Test
  public void accessRecipeToolsTest() {
    try {
      for (String tool : recipe.getTools()) {
        recipeDAO.addRecipeTool(recipe.getName(), tool);
      }
      ArrayList<String> sameRecipeTools = recipeDAO.accessRecipeTools(recipe.getName());
      assertEquals(recipe.getTools(), sameRecipeTools);
    }
    catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void accessRecipeAppliancesTest() {
    try {
      for (String appliance : recipe.getAppliances()) {
        recipeDAO.addRecipeAppliance(recipe.getName(), appliance);
      }
      ArrayList<String> sameRecipeAppliances = recipeDAO.accessRecipeAppliances(recipe.getName());
      assertEquals(recipe.getAppliances(), sameRecipeAppliances);
    }
    catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

}
