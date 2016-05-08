import java.io.*;

public class Product implements Serializable {

	private String product_id;
	private String productname;
	private int price;
	private String producttype;
	private String explanation;
	private int inventory;
	private int salesInventory;
	
	public int getSalesInventory() {
		return salesInventory;
	}

	public void setSalesInventory(int salesInventory) {
		this.salesInventory = salesInventory;
	}

	public String getProductid() {
		return product_id;
	}

	public void setProductid(String productid) {
		this.product_id = productid;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public void setProduct(String productid, String productname, int price, String producttype, String explanation,
			int inventory, int salesInventory) {
		this.product_id = productid;
		this.productname = productname;
		this.price = price;
		this.producttype = producttype;
		this.explanation = explanation;
		this.inventory = inventory;
		this.salesInventory = salesInventory;
	}
	
}
