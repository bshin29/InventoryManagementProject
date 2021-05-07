package Server.Model.InventoryModel;

public class NonElectrical extends Item {

	//instantiates a nonelectrical type object
	public NonElectrical(int itemId, String itemName, int quantity, double price, Supplier supplier) {
		super(itemId, itemName, quantity, price, supplier);
		itemType = "Non-Electrical";
	}

	@Override
	public String toString() {
		String s="";
		s += itemId+", "+itemName+", "+itemQuantity+", "+itemPrice+", "+itemSupplier.getSupplierId()+", "+itemType;
		return s;
	}
}
