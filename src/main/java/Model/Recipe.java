package Model;

import Model.Ingredient;

import java.util.*;

/**
 * stores information about a recipe such as its name and ingredients
 */
public class Recipe {

  private int id;

  private String name;

  private String owner;

  private final Map<Ingredient, Double> ingredients = new HashMap<>();

  private String description;

  private String instructions;

  private String appliance;

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

  public Map<Ingredient, Double> getIngredients() {
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

  public String getAppliance() {
    return appliance;
  }

  public void setAppliance(String appliance) {
    this.appliance = appliance;
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
}

