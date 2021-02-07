package Recipe;

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

  /**
   * constructs a new ingredient with only a name
   *
   * @param name of the ingredient
   */
  public Ingredient(String name) {
    this.name = name;
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

  public String getCheapestStore() {
    return cheapestStore;
  }

  public String getMostRecentStore() {
    return mostRecentStore;
  }

  public String getMeasurement() {
    return measurement;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public boolean addIngredient(Scanner input) {

    return true;
  }

  @Override
  public String toString() {
    return "Ingredient{" +
            "Name: " + name + '\n' +
            "Average Price: $" + averagePrice + " for " + amount + " " + measurement + '\n' +
            "Sale Price: $" + salePrice + " for " + amount + " " + measurement + '\n' +
            "Most Recent Price: " + mostRecentPrice + " for " + amount + " " + measurement + '\n' +
            "Cheapest at " + cheapestStore + '\n' +
            "Expires: " + expirationDate + '\n' +
            '}';
  }
}
