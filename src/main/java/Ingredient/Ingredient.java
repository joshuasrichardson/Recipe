package Ingredient;

import java.util.ArrayList;
import java.util.Date;

/**
 * stores information about ingredients including their name, average price, sale price, most recent price,
 * the amount that comes with that price, what unit that amount is measured in, and where to buy it for a
 * good price.
 */
public class Ingredient {
  private int ingredientID;
  private String name;
  private double averagePrice = -1;
  private double salePrice = -1;
  private double mostRecentPrice = -1;
  private double amount = -1;
  private String unit;
  private int number = -1;
  private String container;
  private String cheapestStore;
  private String mostRecentStore;
  private Date expirationDate;
  private String brand;
  private String city;
  private String foodGroup;
  private ArrayList<String> allergens;

  public Ingredient() {}

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
   * @param unit
   */
  public Ingredient(String name, double amount, String unit) {
    this.name = name;
    this.amount = amount;
    this.unit = unit;
  }

  /**
   * constructs an ingredient with commonly known variables
   *
   * @param name
   * @param mostRecentPrice
   * @param amount
   * @param unit
   * @param expirationDate
   */
  public Ingredient(String name, double mostRecentPrice, double amount, String unit, Date expirationDate) {
    this.name = name;
    this.mostRecentPrice = mostRecentPrice;
    this.amount = amount;
    this.unit = unit;
    this.expirationDate = expirationDate;
  }

  /**
   * constructs an ingredient with many variables
   *
   * @param name
   * @param mostRecentPrice
   * @param amount
   * @param unit
   * @param mostRecentStore
   * @param expirationDate
   */
  public Ingredient(String name, double mostRecentPrice, double amount, String unit, int number, String container,
                    String mostRecentStore, Date expirationDate, String brand, String city, String foodGroup,
                    ArrayList<String> allergens) {
    this.name = name;
    this.mostRecentPrice = mostRecentPrice;
    this.amount = amount;
    this.unit = unit;
    this.number = number;
    this.container = container;
    this.expirationDate = expirationDate;
    this.mostRecentStore = mostRecentStore;
    this.brand = brand;
    this.city = city;
    this.foodGroup = foodGroup;
    this.allergens = allergens;
  }

  public int getIngredientID() { return ingredientID; }

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

  public String getUnit() {
    return unit;
  }

  public int getNumber() { return number; }

  public String getContainer() { return container; }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public String getBrand() { return brand; }

  public String getCity() { return city; }

  public String getFoodGroup() { return foodGroup; }

  public ArrayList<String> getAllergens() { return allergens; }

  public void setIngredientID(int ingredientID) { this.ingredientID = ingredientID; }

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

  public void setUnit(String unit) {
    this.unit = unit;
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

  public void setNumber(int number) { this.number = number; }

  public void setContainer(String container) { this.container = container; }

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
            "Average Price: $" + averagePrice + " for " + amount + " " + unit + '\n' +
            "Sale Price: $" + salePrice + " for " + amount + " " + unit + '\n' +
            "Most Recent Price: $" + mostRecentPrice + " for " + amount + " " + unit + '\n' +
            "Cheapest at " + cheapestStore + '\n' +
            "Expires: " + expirationDate + '\n' +
            '}';
  }
}
