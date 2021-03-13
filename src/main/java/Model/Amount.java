package Model;

public class Amount {

  private Double amount;

  private String unit;

  public Amount() {
  }

  public Amount(Double amount, String unit) {
    this.amount = amount;
    this.unit = unit;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
