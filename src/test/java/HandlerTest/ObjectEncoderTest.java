package HandlerTest;

import Handler.ObjectEncoder;
import Model.RecipeIngredient;
import Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectEncoderTest {

  @Test
  public void addAuthToJsonTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    String authorizedJson = encoder.addAuthTokenToJson("auth", "{\n\"name\":\"name\"\n}");
    assertEquals("{\n\"authToken\":\"auth\",\n\"name\":\"name\"\n}", authorizedJson);
  }

  @Test
  public void addAuthToIngredientJsonTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    String authorizedJson = encoder.addAuthTokenToJson("auth", "{\n" +
            "\t\"ingredientName\":\"name\",\n" +
            "\t\"mostRecentPrice\":\"mostRecentPrice\",\n" +
            "\t\"number\":\"1\",\n" +
            "\t\"container\":\"box\",\n" +
            "\t\"amount\":\"1\",\n" +
            "\t\"unit\":\"ounces\",\n" +
            "\t\"expirationDate\":\"2021 12 31\",\n" +
            "\t\"brand\":\"Kroger\",\n" +
            "\t\"foodGroup\":\"vegetable\",\n" +
            "\t\"mostRecentStore\":\"Smith's\",\n" +
            "\t\"city\":\"Provo\",\n" +
            "\t\"allergens\":[\n" +
            "\t\t\n" +
            "\t]\n" +
            "}");
    assertEquals("{\n\"authToken\":\"auth\",\n" +
            "\t\"ingredientName\":\"name\",\n" +
            "\t\"mostRecentPrice\":\"mostRecentPrice\",\n" +
            "\t\"number\":\"1\",\n" +
            "\t\"container\":\"box\",\n" +
            "\t\"amount\":\"1\",\n" +
            "\t\"unit\":\"ounces\",\n" +
            "\t\"expirationDate\":\"2021 12 31\",\n" +
            "\t\"brand\":\"Kroger\",\n" +
            "\t\"foodGroup\":\"vegetable\",\n" +
            "\t\"mostRecentStore\":\"Smith's\",\n" +
            "\t\"city\":\"Provo\",\n" +
            "\t\"allergens\":[\n" +
            "\t\t\n" +
            "\t]\n" +
            "}", authorizedJson);
  }

  @Test
  public void addAuthToJsonFailTest() {
    ObjectEncoder encoder = new ObjectEncoder();
    assertThrows(IllegalArgumentException.class, () -> encoder.addAuthTokenToJson("auth", ""));
  }

  @Test
  public void recipeToJson() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Recipe recipe = new Recipe("chocolate chip cookies");
    RecipeIngredient butterRecipeIngredient = new RecipeIngredient("butter", 1.0, "pounds");
    RecipeIngredient brownSugarRecipeIngredient = new RecipeIngredient("brown sugar", 2.0, "cups");
    RecipeIngredient whiteSugarRecipeIngredient = new RecipeIngredient("white sugar", 1.5, "cups");
    RecipeIngredient eggRecipeIngredient = new RecipeIngredient("eggs", 3.0, "eggs");
    RecipeIngredient saltRecipeIngredient = new RecipeIngredient("salt", 1.5, "teaspoons");
    RecipeIngredient bakingSodaRecipeIngredient = new RecipeIngredient("baking soda", 1.5, "teaspoons");
    RecipeIngredient vanillaRecipeIngredient = new RecipeIngredient("vanilla", 1.5, "tablespoons");
    RecipeIngredient flourRecipeIngredient = new RecipeIngredient("flour", 6.0, "cups");
    RecipeIngredient chocolateChipRecipeIngredient = new RecipeIngredient("chocolate chips", 4.0, "cups");
    recipe.addIngredient(butterRecipeIngredient);
    recipe.addIngredient(brownSugarRecipeIngredient);
    recipe.addIngredient(whiteSugarRecipeIngredient);
    recipe.addIngredient(eggRecipeIngredient);
    recipe.addIngredient(saltRecipeIngredient);
    recipe.addIngredient(bakingSodaRecipeIngredient);
    recipe.addIngredient(vanillaRecipeIngredient);
    recipe.addIngredient(flourRecipeIngredient);
    recipe.addIngredient(chocolateChipRecipeIngredient);
    recipe.setAppliances(new ArrayList<>(List.of("oven")));
    recipe.setMinutes(30);
    recipe.setTools(new ArrayList<>(List.of("mixing bowl", "spoon", "cookie sheets", "oven mitts")));
    recipe.setCalories(300);
    recipe.setServings(15);
    recipe.setDescription("Super delicious chocolate chip cookies. Contains wheat.");
    recipe.setInstructions("1. Mix ingredients\n2. bake");
    recipe.setOwner("Joshua");
    recipe.setTemperature(350);
    System.out.println(gson.toJson(recipe));
  }
}
