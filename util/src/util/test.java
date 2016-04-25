// test.java
// test BarcodeCreator

package util;

import java.util.UUID;

public class test {
	
	// Create unique id( UUID )
	public static String createUUID() {		  
		  return UUID.randomUUID().toString();		  
		 }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BarcodeCreator barcodeCreator = new BarcodeCreator();
		
		// Create image file
		barcodeCreator.makeImageFile(createUUID(), "test");
	}

}
