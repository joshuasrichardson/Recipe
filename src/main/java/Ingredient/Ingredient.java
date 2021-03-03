package Ingredient;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * stores information about ingredients including their name, average price, sale price, most recent price,
 * the amount that comes with that price, what unit that amount is measured in, and where to buy it for a
 * good price.
 */
public class Ingredient {
  private int ingredientID;
  private String name;
  private String owner;
  private String storageContainer;
  private double averagePricePerUnit;
  private double salePricePerUnit;
  private double mostRecentPricePerUnit;
  private double mostRecentPrice;
  private double amount;
  private double totalAmountBought;
  private String unit;
  private int number;
  private String container;
  private String cheapestStore;
  private String mostRecentStore;
  private LocalDate purchaseDate;
  private LocalDate expirationDate;
  private String brand;
  private String city;
  private String foodGroup;
  private ArrayList<String> allergens;

  public Ingredient() {}

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
                    String mostRecentStore, LocalDate expirationDate, String brand, String city, String foodGroup,
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

  /**
   * constructs an Ingredient object with the information needed for the ingredientInformation table.
   * @param name
   * @param brand
   * @param totalAmountBought
   * @param averagePricePerUnit
   * @param salePricePerUnit
   * @param mostRecentPricePerUnit
   * @param amount
   * @param unit
   * @param foodGroup
   * @param cheapestStore
   * @param city
   */
  public Ingredient(String name, String brand, double totalAmountBought, double averagePricePerUnit,
                    double salePricePerUnit, double mostRecentPricePerUnit, double amount, String unit,
                    String foodGroup, String cheapestStore, String city) {
    this.name = name;
    this.averagePricePerUnit = averagePricePerUnit;
    this.salePricePerUnit = salePricePerUnit;
    this.mostRecentPricePerUnit = mostRecentPricePerUnit;
    this.amount = amount;
    this.totalAmountBought = totalAmountBought;
    this.unit = unit;
    this.cheapestStore = cheapestStore;
    this.brand = brand;
    this.city = city;
    this.foodGroup = foodGroup;
  }

  /**
   * constructs an Ingredient object with the information needed for the ingredientInventory table.
   * @param name
   * @param brand
   * @param owner
   * @param storageContainer
   * @param mostRecentPrice
   * @param number
   * @param container
   * @param amount
   * @param unit
   * @param expirationDate
   */
  public Ingredient(String name, String brand, String owner, String storageContainer, double mostRecentPrice,
                    int number, String container, double amount, String unit, LocalDate purchaseDate, LocalDate expirationDate) {
    this.name = name;
    this.owner = owner;
    this.storageContainer = storageContainer;
    this.mostRecentPrice = mostRecentPrice;
    this.amount = amount;
    this.unit = unit;
    this.number = number;
    this.container = container;
    this.purchaseDate = purchaseDate;
    this.expirationDate = expirationDate;
    this.brand = brand;
  }

  public int getIngredientID() { return ingredientID; }

  public String getName() {
    return name;
  }

  public String getOwner() {
    return owner;
  }

  public String getStorageContainer() {
    return storageContainer;
  }

  public double getAveragePricePerUnit() {
    return averagePricePerUnit;
  }

  public double getSalePricePerUnit() {
    return salePricePerUnit;
  }

  public double getMostRecentPricePerUnit() { return mostRecentPricePerUnit; }

  public double getMostRecentPrice() {
    return mostRecentPrice;
  }

  public double getAmount() {
    return amount;
  }

  public double getTotalAmountBought() { return totalAmountBought; }

  public String getCheapestStore() { return cheapestStore; }

  public String getMostRecentStore() {
    return mostRecentStore;
  }

  public String getUnit() {
    return unit;
  }

  public int getNumber() { return number; }

  public String getContainer() { return container; }

  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public String getBrand() { return brand; }

  public String getCity() { return city; }

  public String getFoodGroup() { return foodGroup; }

  public ArrayList<String> getAllergens() { return allergens; }

  public void setIngredientID(int ingredientID) { this.ingredientID = ingredientID; }

  public void setName(String name) { this.name = name; }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setStorageContainer(String storageContainer) {
    this.storageContainer = storageContainer;
  }

  public void setAveragePricePerUnit(double averagePricePerUnit) {
    this.averagePricePerUnit = averagePricePerUnit;
  }

  public void setSalePricePerUnit(double salePricePerUnit) {
    this.salePricePerUnit = salePricePerUnit;
  }

  public void setMostRecentPricePerUnit(double mostRecentPricePerUnit) { this.mostRecentPricePerUnit = mostRecentPricePerUnit; }

  public void setMostRecentPrice(double mostRecentPrice) {
    this.mostRecentPrice = mostRecentPrice;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void setTotalAmountBought(double totalAmountBought) {
    this.totalAmountBought = totalAmountBought;
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

  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ingredient that = (Ingredient) o;
    return Objects.equals(name, that.name) && Objects.equals(brand, that.brand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, brand, foodGroup);
  }

  @Override
  public String toString() {
    return "Ingredient{" +
            "Name: " + name + '\n' +
            "Average Price: $" + averagePricePerUnit + " for " + amount + " " + unit + '\n' +
            "Sale Price: $" + salePricePerUnit + " for " + amount + " " + unit + '\n' +
            "Most Recent Price: $" + mostRecentPrice + " for " + amount + " " + unit + '\n' +
            "Cheapest at " + cheapestStore + '\n' +
            "Expires: " + expirationDate + '\n' +
            '}';
  }
}
