package Model;

import java.util.*;

/**
 * stores information about a recipe such as its name and ingredients
 */
public class Recipe {

  private int id;

  private String name;

  private String owner;

  private final Map<String, Amount> ingredients = new HashMap<>();

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
  public Recipe(String name) { this.name = name; }

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

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Map<String, Amount> getIngredients() {
    return ingredients;
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

  public void addIngredient(String ingredientName, Amount amount) { ingredients.put(ingredientName, amount); }

}

