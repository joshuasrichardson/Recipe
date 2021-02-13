package Ingredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * stores information about ingredients including their name, average price, sale price, most recent price,
 * the amount that comes with that price, what unit that amount is measured in, and where to buy it for a
 * good price.
 */
public class Ingredient {
  private String name;
  private double averagePrice;
  private double salePrice;
  private double mostRecentPrice;
  private double amount;
  private String measurement;
  private String cheapestStore;
  private String mostRecentStore;
  private Date expirationDate;
  private String brand;
  private String city;
  private String foodGroup;
  private ArrayList<String> allergens;

  /**
   * constructs a new ingredient with only a name
   *
   * @param name of the ingredient
   */
  public Ingredient(String name) {
    this.name = name;
  }

  /**
   * constructs an ingredient to be used in a recipe
   *
   * @param name
   * @param amount
   * @param measurement
   */
  public Ingredient(String name, double amount, String measurement) {
    this.name = name;
    this.amount = amount;
    this.measurement = measurement;
  }

  /**
   * constructs an ingredient with commonly known variables
   *
   * @param name
   * @param mostRecentPrice
   * @param amount
   * @param measurement
   * @param expirationDate
   */
  public Ingredient(String name, double mostRecentPrice, double amount, String measurement, Date expirationDate) {
    this.name = name;
    this.mostRecentPrice = mostRecentPrice;
    this.amount = amount;
    this.measurement = measurement;
    this.expirationDate = expirationDate;
  }

  /**
   * constructs an ingredient with many variables
   *
   * @param name
   * @param averagePrice
   * @param salePrice
   * @param mostRecentPrice
   * @param amount
   * @param measurement
   * @param cheapestStore
   * @param mostRecentStore
   * @param expirationDate
   */
  public Ingredient(String name, double averagePrice, double salePrice, double mostRecentPrice,
                    double amount, String measurement, String cheapestStore, String mostRecentStore, Date expirationDate) {
    this.name = name;
    this.averagePrice = averagePrice;
    this.salePrice = salePrice;
    this.mostRecentPrice = mostRecentPrice;
    this.amount = amount;
    this.measurement = measurement;
    this.cheapestStore = cheapestStore;
    this.expirationDate = expirationDate;
    this.mostRecentStore = mostRecentStore;
  }

  public String getName() {
    return name;
  }

  public double getAveragePrice() {
    return averagePrice;
  }

  public double getSalePrice() {
    return salePrice;
  }

  public double getMostRecentPrice() {
    return mostRecentPrice;
  }

  public double getAmount() {
    return amount;
  }

  public String getCheapestStore() { return cheapestStore; }

  public String getMostRecentStore() {
    return mostRecentStore;
  }

  public String getMeasurement() {
    return measurement;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public String getBrand() { return brand; }

  public String getCity() { return city; }

  public String getFoodGroup() { return foodGroup; }

  public ArrayList<String> getAllergens() { return allergens; }

  public void setName(String name) { this.name = name; }

  public void setAveragePrice(double averagePrice) {
    this.averagePrice = averagePrice;
  }

  public void setSalePrice(double salePrice) {
    this.salePrice = salePrice;
  }

  public void setMostRecentPrice(double mostRecentPrice) {
    this.mostRecentPrice = mostRecentPrice;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void setMeasurement(String measurement) {
    this.measurement = measurement;
  }

  public void setCheapestStore(String cheapestStore) {
    this.cheapestStore = cheapestStore;
  }

  public void setMostRecentStore(String mostRecentStore) {
    this.mostRecentStore = mostRecentStore;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setBrand(String brand) { this.brand = brand; }

  public void setCity(String city) { this.city = city; }

  public void setFoodGroup(String foodGroup) { this.foodGroup = foodGroup; }

  public void setAllergens(ArrayList<String> allergens) { this.allergens = allergens; }

  public static String findIngredientName(String input) {
    StringBuilder ingredientName = new StringBuilder();

    for (int i = 0; i < input.length(); ++i) {
      if (Character.isDigit(input.charAt(i))) {
        break;
      }
      else {
        ingredientName.append(input.charAt(i));
      }
    }

    if (ingredientName.length() > 0 && ingredientName.charAt(ingredientName.length() - 1) == ' ') {
      ingredientName.deleteCharAt(ingredientName.length() - 1);
    }

    return ingredientName.toString();
  }

  public static double findIngredientNumber(String input) {
    StringBuilder ingredientName = new StringBuilder();

    for (int i = 0; i < input.length(); ++i) {
      if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
        ingredientName.append(input.charAt(i));
      }
    }

    double number = Double.parseDouble(ingredientName.toString());

    return number;
  }

  public static String findIngredientUnit(String input) {
    StringBuilder ingredientName = new StringBuilder();

    int i = 0;

    while (i < input.length() && !Character.isDigit(input.charAt(i))) {
      ++i;
    }
    while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
      ++i;
    }
    while (i < input.length()) {
      ingredientName.append(input.charAt(i));
      ++i;
    }

    if (ingredientName.length() != 0 && ingredientName.charAt(0) == ' ') {
      ingredientName.deleteCharAt(0);
    }

    return ingredientName.toString();
  }

  @Override
  public String toString() {
    return "Ingredient{" +
            "Name: " + name + '\n' +
            "Average Price: $" + averagePrice + " for " + amount + " " + measurement + '\n' +
            "Sale Price: $" + salePrice + " for " + amount + " " + measurement + '\n' +
            "Most Recent Price: $" + mostRecentPrice + " for " + amount + " " + measurement + '\n' +
            "Cheapest at " + cheapestStore + '\n' +
            "Expires: " + expirationDate + '\n' +
            '}';
  }
}
