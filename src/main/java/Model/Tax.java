package Model;

import java.time.LocalDate;
import java.util.Objects;

public class Tax {
  private String username;
  private String store;
  private double taxes;
  private LocalDate date;

  public Tax() {
  }

  public Tax(String username, String store, double taxes, LocalDate date) {
    this.username = username;
    this.store = store;
    this.taxes = taxes;
    this.date = date;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public double getTaxes() {
    return taxes;
  }

  public void setTaxes(double taxes) {
    this.taxes = taxes;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tax tax = (Tax) o;
    return Double.compare(tax.taxes, taxes) == 0 && Objects.equals(username, tax.username) && Objects.equals(store, tax.store) && Objects.equals(date, tax.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, store, taxes, date);
  }
}
