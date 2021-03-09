package Database;

import Model.Tax;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpensesDAO {

  private final Connection connection;

  /**
   * creates a connection to the user table and updates and retrieves information.
   * @param connection the connection to the database.
   */
  public ExpensesDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * creates a table that stores information about taxes.
   * @return whether a table was created for the taxes.
   * @throws DatabaseAccessException if there was a problem adding to the database or something.
   */
  public boolean createTaxesTable() throws DatabaseAccessException {
    PreparedStatement stmt = null;
    try {
      String sql = "CREATE TABLE IF NOT EXISTS Taxes (\n" +
              "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
              "username VARCHAR(255) NOT NULL, \n" +
              "store VARCHAR(255) NOT NULL, \n" +
              "taxes DOUBLE NOT NULL, \n" +
              "date varchar(255) NOT NULL);";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseAccessException("SQL Error encountered while creating Taxes table");
    }
    return true;
  }

  /**
   * adds the store, date, and amount of the tax to the table.
   * @param tax the tax to add to the table.
   * @return whether the tax was added to the table.
   * @throws DatabaseAccessException
   */
  public boolean addTaxesToTable(Tax tax) throws DatabaseAccessException {
    PreparedStatement stmt = null;
    try {
      String sql = "INSERT INTO Taxes (username, store, taxes, date) " +
              "values (?, ?, ?, ?)";
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, tax.getUsername());
      stmt.setString(2, tax.getStore());
      stmt.setDouble(3, tax.getTaxes());
      stmt.setString(4, tax.getDate().toString());
      stmt.execute();
    } catch (SQLException e) {
      throw new DatabaseAccessException("Couldn't add taxes.");
    }
    return true;
  }

  /**
   * accesses sales taxes from the table.
   * @param date the day the taxes were added to the table.
   * @return the Taxes with information about the amount and store.
   * @throws DatabaseAccessException
   */
  public ArrayList<Tax> accessTaxesFromTable(String username, LocalDate date) throws DatabaseAccessException {
    ArrayList<Tax> taxes = new ArrayList<>();
    PreparedStatement stmt;
    try {
      String sql = "SELECT * FROM Taxes WHERE date = '" + date.toString() + "' AND username = '" + username + "';";
      stmt = connection.prepareStatement(sql);
      ResultSet keyRS = stmt.executeQuery();
      while(keyRS.next()) {
        Tax tax = new Tax();
        tax.setUsername(keyRS.getString(2));
        tax.setStore(keyRS.getString(3));
        tax.setTaxes(keyRS.getDouble(4));
        tax.setDate(date);
        taxes.add(tax);
      }
    } catch (SQLException e) {
      throw new DatabaseAccessException("No taxes recorded that day.");
    }
    return taxes;
  }
}
