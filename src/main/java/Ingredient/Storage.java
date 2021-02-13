package Ingredient;

import Ingredient.Ingredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Represents the ingredients in the user's fridge, freezer, pantry, etc.
 */
public class Storage {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private HashSet<Ingredient> ingredients = new HashSet<>();

  public Ingredient addIngredientToStorage(Scanner input) {

    Ingredient newIngredient;
    String ingredientName;
    String amountWithUnit;
    int amount = 0;
    String unit;
    double cost;
    String store;
    int year;
    int month;
    int day;
    Date expirationDate;
    String brand;
    String foodGroup;
    String city;
    String allergen;
    ArrayList<String> allergens = new ArrayList<>();

    System.out.println("What ingredient would you like to add?");
    ingredientName = input.nextLine();
    newIngredient = new Ingredient(ingredientName);

    System.out.println("How much/many? (number then unit)");
    amountWithUnit = input.nextLine();
    int i = 0;
    while (i < amountWithUnit.length() && Character.isDigit(amountWithUnit.charAt(i))) {
      amount *= 10;
      amount += (amountWithUnit.charAt(i) - 48);
      ++i;
    }
    newIngredient.setAmount(amount);
    if (i + 1 < amountWithUnit.length()) {
      unit = amountWithUnit.substring(i + 1);
    }
    else {
      unit = ingredientName;
    }
    newIngredient.setMeasurement(unit);

    System.out.println("How much did that cost?");
    if (input.hasNextDouble()) {
      cost = input.nextDouble();
      newIngredient.setMostRecentPrice(cost);
    }
    input.nextLine();

    System.out.println("Where did you buy that?");
    store = input.nextLine();
    newIngredient.setMostRecentStore(store);

    System.out.println("When will it expire? (yyyy mm dd)");
    if (input.hasNextInt()) {
      year = input.nextInt();
      if (input.hasNextInt()) {
        month = input.nextInt();
        if (input.hasNextInt()) {
          day = input.nextInt();
          expirationDate = new Date(year, month, day);
          newIngredient.setExpirationDate(expirationDate);
        }
      }
    }
    input.nextLine();

    System.out.println("What brand is it?");
    brand = input.nextLine();
    newIngredient.setBrand(brand);

    System.out.println("What food group is it?");
    foodGroup = input.nextLine();
    newIngredient.setFoodGroup(foodGroup);

    System.out.println("What city did you buy it in?");
    city = input.nextLine();
    newIngredient.setCity(city);

    System.out.println("What allergens does it have?");
    while (input.hasNextLine()) {
      allergen = input.nextLine();
      if (allergen.equals("done")) {
        break;
      }
      allergens.add(allergen);
    }
    newIngredient.setAllergens(allergens);

    System.out.println(newIngredient.toString());

    ingredients.add(newIngredient);

    return newIngredient;
  }

}
