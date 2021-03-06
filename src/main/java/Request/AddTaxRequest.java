package Request;

public class AddTaxRequest {
  private String store;
  private Double taxes;

  public AddTaxRequest() {
  }

  public AddTaxRequest(String store, Double taxes) {
    this.store = store;
    this.taxes = taxes;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public Double getTaxes() {
    return taxes;
  }

  public void setTaxes(Double taxes) {
    this.taxes = taxes;
  }
}
