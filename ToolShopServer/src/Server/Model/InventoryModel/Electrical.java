package Server.Model.InventoryModel;

public class Electrical extends Item {

	//electrical object will have a power-type attribute
	private String powerType;
	
	//instantiates a electrical type object
	public Electrical(int itemId, String itemName, int quantity, double price, Supplier supplier, String pType) {
		super(itemId, itemName, quantity, price, supplier);
		itemType = "Electrical";
		powerType = pType;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	
	@Override
	public String toString() {
		String s="";
		s += itemId+", "+itemName+", "+itemQuantity+", "+itemPrice+", "+itemSupplier.getSupplierId()+", "+itemType+", "+powerType;
		return s;
	}

}
