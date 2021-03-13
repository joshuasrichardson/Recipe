package HandlerTest;

import Model.Amount;
import Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetRecipeHandlerTest {
  @Test
  public void recipeToJson() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Recipe recipe = new Recipe("chocolate chip cookies");
    Amount butterAmount = new Amount(1.0, "pounds");
    Amount brownSugarAmount = new Amount(2.0, "cups");
    Amount whiteSugarAmount = new Amount(1.5, "cups");
    Amount eggAmount = new Amount(3.0, "eggs");
    Amount saltAmount = new Amount(1.5, "teaspoons");
    Amount bakingSodaAmount = new Amount(1.5, "teaspoons");
    Amount vanillaAmount = new Amount(1.5, "tablespoons");
    Amount flourAmount = new Amount(6.0, "cups");
    Amount chocolateChipAmount = new Amount(4.0, "cups");
    recipe.addIngredient("butter", butterAmount);
    recipe.addIngredient("brown sugar", brownSugarAmount);
    recipe.addIngredient("white sugar", whiteSugarAmount);
    recipe.addIngredient("eggs", eggAmount);
    recipe.addIngredient("salt", saltAmount);
    recipe.addIngredient("baking soda", bakingSodaAmount);
    recipe.addIngredient("vanilla", vanillaAmount);
    recipe.addIngredient("flour", flourAmount);
    recipe.addIngredient("chocolate chips", chocolateChipAmount);
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
