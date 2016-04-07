package project_exercise;

public class Product {
	
	String productname;
	String explanation;
	String producttype;
	int price;
	int product_id;
	int inventory;
	int productid;

	
	 public int getProductid() {
	        return product_id;
	    }
	    
	    public void setProductid(int productid) {
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
	    
	    Product()
	    {
	    
	    }

	    public Product(int productid, String productname, int price, String producttype, String explanation, int inventory) {
	        this.product_id = productid;
	        this.productname = productname;
	        this.price = price;
	        this.producttype = producttype;
	        this.explanation = explanation;
	        this.inventory = inventory;
	    }
	}
	
	
}
