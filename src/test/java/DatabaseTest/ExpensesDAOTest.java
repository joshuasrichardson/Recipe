package DatabaseTest;

import Database.Database;
import Database.ExpensesDAO;
import Database.DatabaseAccessException;
import Model.Tax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static test.ServerTest.TEST_CONNECTION_URL;

public class ExpensesDAOTest {
  Database database;
  ExpensesDAO expensesDAO;

  @BeforeEach
  public void connect() {
    try {
      database = new Database();
      database.openConnection(TEST_CONNECTION_URL);
      database.clearAllTables();
      database.closeConnection(true);
      expensesDAO = new ExpensesDAO(database.getConnection(TEST_CONNECTION_URL));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @AfterEach
  public void disconnect() {
    try {
      database.closeConnection(true);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Test
  public void createTaxesTableTest() {
    try {
      expensesDAO.createTaxesTable();
    } catch (DatabaseAccessException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void addTaxesTest() {
    LocalDate date = LocalDate.now();
    try {
      expensesDAO.addTaxesToTable(new Tax("Test", "Walmart", 2.50, date));
      expensesDAO.addTaxesToTable(new Tax("Test", "Costco", 2.50, date));
      expensesDAO.addTaxesToTable(new Tax("Test", "Smith's", 2.50, date));
      expensesDAO.addTaxesToTable(new Tax("Test", "Asian Market", 2.50, date));

      ArrayList<Tax> taxes = expensesDAO.accessTaxesFromTable("Test", date);

      assertEquals(new Tax("Test", "Walmart", 2.50, date), taxes.get(0));
      assertEquals(new Tax ("Test","Costco", 2.50, date), taxes.get(1));
      assertEquals(new Tax("Test", "Smith's", 2.50, date), taxes.get(2));
      assertEquals(new Tax("Test", "Asian Market", 2.50, date), taxes.get(3));
    } catch (DatabaseAccessException e) {
      fail(e.getMessage());
    }
  }
}
