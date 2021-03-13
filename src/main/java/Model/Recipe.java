package Model;

import java.util.*;

/**
 * stores information about a recipe such as its name and ingredients
 */
public class Recipe {

  private String name;

  private String owner;

  private ArrayList<RecipeIngredient> ingredients;

  private int servings;

  private String description;

  private String instructions;

  private int minutes;

  private ArrayList<String> tools;

  private ArrayList<String> appliances;

  private int temperature;

  private int calories;

  /**
   * constructs a recipe with provided name.
   *
   * @param name name of the recipe
   */
  public Recipe(String name) {
    this.name = name;
    this.ingredients = new ArrayList<>();
    this.appliances = new ArrayList<>();
    this.tools = new ArrayList<>();
  }

  /**
   *
   * @param name
   * @param owner
   * @param servings
   * @param description
   * @param instructions
   * @param minutes
   * @param tools
   * @param appliances
   * @param temperature
   * @param calories
   * @param ingredients
   */
  public Recipe(String name, String owner, int servings, String description, String instructions, int minutes,
                ArrayList<String> tools, ArrayList<String> appliances, int temperature, int calories,
                ArrayList<RecipeIngredient> ingredients) {
    this.name = name;
    this.owner = owner;
    this.servings = servings;
    this.description = description;
    this.instructions = instructions;
    this.minutes = minutes;
    this.tools = tools;
    this.appliances = appliances;
    this.temperature = temperature;
    this.calories = calories;
    this.ingredients = ingredients;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public ArrayList<String> getAppliances() {
    return appliances;
  }

  public void setAppliances(ArrayList<String> appliances) {
    this.appliances = appliances;
  }

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int temperature) {
    this.temperature = temperature;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public int getMinutes() { return minutes; }

  public void setMinutes(int minutes) { this.minutes = minutes; }

  public ArrayList<String> getTools() { return tools; }

  public void setTools(ArrayList<String> tools) { this.tools = tools; }

  public int getServings() { return servings; }

  public void setServings(int servings) { this.servings = servings; }

  public ArrayList<RecipeIngredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
    this.ingredients = ingredients;
  }

  public void addIngredient(RecipeIngredient ingredient) {
    this.ingredients.add(ingredient);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Recipe recipe = (Recipe) o;
    return servings == recipe.servings &&
            minutes == recipe.minutes &&
            temperature == recipe.temperature &&
            calories == recipe.calories &&
            Objects.equals(name, recipe.name) &&
            Objects.equals(owner, recipe.owner) &&
            Objects.equals(description, recipe.description) &&
            Objects.equals(instructions, recipe.instructions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, owner, servings, description, instructions, minutes, temperature, calories);
  }
}

