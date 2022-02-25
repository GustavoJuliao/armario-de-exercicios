package entities;

public class ImportedProduct extends Product{

	private Double customsFee;
	
	public Double getcustomsFee() {
		return customsFee;
	}

	public ImportedProduct(String name, Double price, Double customsFee) {
		super(name, price);
		this.customsFee = customsFee;
	}

	// Metodos
	public double totalPrice() {		
		return customsFee + getPrice();
	}
	
	public String priceTag() {
		return getName() + " $ " + String.format("%.2f", totalPrice())+ " (Customs fee: $" + String.format("%.2f", customsFee)+ ")";
	}
	
}
