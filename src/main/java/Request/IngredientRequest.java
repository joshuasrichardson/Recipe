package Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class IngredientRequest {

  private String authToken;
  private String name;
  private Double mostRecentPrice;
  private Double amount;
  private String unit;
  private Integer number;
  private String container;
  private String mostRecentStore;
  private Date expirationDate;
  private String brand;
  private String city;
  private String foodGroup;
  private ArrayList<String> allergens;

  public IngredientRequest() {

  }

  public IngredientRequest(String authToken, String name, double mostRecentPrice, double amount, String unit, int number,
                           String container, String mostRecentStore, Date expirationDate, String brand, String city,
                           String foodGroup, ArrayList<String> allergens) {
    this.authToken = authToken;
    this.name = name;
    this.mostRecentPrice = mostRecentPrice;
    this.amount = amount;
    this.unit = unit;
    this.number = number;
    this.container = container;
    this.mostRecentStore = mostRecentStore;
    this.expirationDate = expirationDate;
    this.brand = brand;
    this.city = city;
    this.foodGroup = foodGroup;
    this.allergens = allergens;
  }

  public String hasAllNecessaryFields() {
    if (this.authToken == null) return "authorization token";
    if (this.name == null) return "name";
    if (this.mostRecentPrice == null) return "most recent price";
    if (this.amount == null) return "amount";
    if (this.unit == null) return "unit";
    if (this.number == null) return "number";
    if (this.container == null) return "container";
    if (this.mostRecentStore == null) return "most recent store";
    if (this.expirationDate == null) return "expiration date";
    if (this.brand == null) return "brand";
    if (this.city == null) return "city";
    if (this.foodGroup == null) return "food group";
    return "大丈夫です";
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getName() {
    return name;
  }

  public double getMostRecentPrice() {
    return mostRecentPrice;
  }

  public double getAmount() {
    return amount;
  }

  public String getUnit() {
    return unit;
  }

  public int getNumber() {
    return number;
  }

  public String getContainer() {
    return container;
  }

  public String getMostRecentStore() {
    return mostRecentStore;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public String getBrand() {
    return brand;
  }

  public String getCity() {
    return city;
  }

  public String getFoodGroup() {
    return foodGroup;
  }

  public ArrayList<String> getAllergens() {
    return allergens;
  }

  public void setName(String name) {
    this.name = name;
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

  public void setNumber(int number) {
    this.number = number;
  }

  public void setContainer(String container) {
    this.container = container;
  }

  public void setMostRecentStore(String mostRecentStore) {
    this.mostRecentStore = mostRecentStore;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setFoodGroup(String foodGroup) {
    this.foodGroup = foodGroup;
  }

  public void setAllergens(ArrayList<String> allergens) {
    this.allergens = allergens;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IngredientRequest that = (IngredientRequest) o;
    return Double.compare(that.mostRecentPrice, mostRecentPrice) == 0 && Double.compare(that.amount, amount) == 0 && number == that.number && Objects.equals(name, that.name) && Objects.equals(unit, that.unit) && Objects.equals(container, that.container) && Objects.equals(mostRecentStore, that.mostRecentStore) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(brand, that.brand) && Objects.equals(city, that.city) && Objects.equals(foodGroup, that.foodGroup) && Objects.equals(allergens, that.allergens);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, mostRecentPrice, amount, unit, number, container, mostRecentStore, expirationDate, brand, city, foodGroup, allergens);
  }
}
