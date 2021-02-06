package Recipe;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Recipe {

  private int id;

  private String name;

  private ArrayList<String> ingredients;

  public Recipe(String name, ArrayList<String> ingredients) {
    this.name = name;
    this.ingredients = new ArrayList<>(ingredients);
  }

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

  @Override
  public String toString() {
    return "Recipe{" +
            "name='" + name + '\'' +
            ", ingredients=" + ingredients +
            '}';
  }
}

