package Service;

import Database.*;
import Model.Tax;
import Request.AddTaxRequest;
import Result.AddTaxResult;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;

import static Server.Server.CONNECTION_URL;
import static Server.Server.logger;

public class TaxService {

  private final String connection;

  private final Database db = new Database();

  public TaxService() {
    this.connection = CONNECTION_URL;
  }

  public TaxService(String connection) {
    this.connection = connection;
  }

  public AddTaxResult addTax(String authToken, AddTaxRequest request) {
    logger.log(Level.INFO, "Adding taxes.");

    try {
      Tax tax = new Tax(new Service(connection).findOwner(authToken).getUsername(),
              request.getStore(), request.getTaxes(), LocalDate.now());

      ExpensesDAO expensesDAO = new ExpensesDAO(db.getConnection("jdbc:sqlite:storage.sqlite"));
      expensesDAO.createTaxesTable();
      db.closeConnection(true);

      expensesDAO = new ExpensesDAO(db.getConnection("jdbc:sqlite:storage.sqlite"));
      expensesDAO.addTaxesToTable(tax);

      return new AddTaxResult(true, tax.getStore() + "'s taxes were successfully added.");
    } catch (SQLException | DatabaseAccessException e) {
      return new AddTaxResult(false, "Error: (while adding " + request.getStore() + "'s taxes) " + e.getMessage());
    } finally {
      try {
        db.closeConnection(true);
      }
      catch (SQLException e) {
        logger.log(Level.WARNING, "Couldn't close the database connection.");
        e.printStackTrace();
      }
    }
  }

}
