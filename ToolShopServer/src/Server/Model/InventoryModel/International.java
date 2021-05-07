package Server.Model.InventoryModel;

public class International extends Supplier {

	//international suppliers have an import tax field
	private double importTax;
	
	//instantiates an international type supplier
	public International(int id, String company, String address, String contact, double tax) {
		super(id, company, address, contact);
		supplierType = "International";
		importTax = tax;
	}
	
	public String getSupplierType() {
		return supplierType;
	}

	public double getImportTax() {
		return importTax;
	}

	public void setImportTax(double importTax) {
		this.importTax = importTax;
	}

	@Override
	public String toString() {
		String s = "";
		s += supplierId+", "+companyName+", "+address+", "+salesContact+", "+supplierType+", "+importTax;
		return s;
	}

}
