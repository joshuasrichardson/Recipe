package Recipe;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Represents the ingredients in the user's fridge, freezer, pantry, etc.
 */
public class Storage {

  private HashSet<Ingredient> ingredients = new HashSet<>();

  public Ingredient addIngredientToStorage(Scanner input) {

    Ingredient newIngredient;

    String name;

    String amountWithUnit;

    int amount = 0;

    String unit;

    double cost;

    String store;

    int year;

    int month;

    int day;

    Date expirationDate;

    System.out.println("What ingredient would you like to add?");
    name = input.next();
    newIngredient = new Ingredient(name);

    System.out.println("How much/many? (number then unit) To skip, type \"skip.\"");
    input.nextLine();
    amountWithUnit = input.nextLine();
    if (!"skip".equals(amountWithUnit)) {
      int i = 0;
      while (i < amountWithUnit.length() && Character.isDigit(amountWithUnit.charAt(i))) {
        amount *= 10;
        amount += (amountWithUnit.charAt(i) - 48);
        ++i;
      }
      newIngredient.setAmount(amount);
      if (i > amountWithUnit.length() + 1) {
        unit = amountWithUnit.substring(i + 1);
        newIngredient.setMeasurement(unit);
      }
    }

    System.out.println("How much did that cost? To skip, type \"skip.\"");
    if (input.hasNextDouble()) {
      cost = input.nextDouble();
      newIngredient.setMostRecentPrice(cost);
    }
    else {
      input.nextLine();
    }

    System.out.println("Where did you buy that? To skip, type \"skip.\"");
    store = input.next();
    if (!"skip".equals(input)) {
      newIngredient.setMostRecentStore(store);
    }

    System.out.println("When will it expire? (yyyy mm dd) To skip, type \"skip.\"");
    if (input.hasNextInt()) {
      year = input.nextInt();
      month = input.nextInt();
      day = input.nextInt();
      expirationDate = new Date(year, month, day);
      newIngredient.setExpirationDate(expirationDate);
    }
    input.next();

    System.out.println(newIngredient.toString());

    return newIngredient;
  }

}
