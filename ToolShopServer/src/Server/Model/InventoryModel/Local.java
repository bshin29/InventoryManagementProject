package Server.Model.InventoryModel;

public class Local extends Supplier {

	//instantiates a local type supplier
	public Local(int id, String company, String address, String contact) {
		super(id, company, address, contact);
		supplierType = "Local";
	}

	public String getSupplierType() {
		return supplierType;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += supplierId+", "+companyName+", "+address+", "+salesContact+", "+supplierType;
		return s;
	}

}
