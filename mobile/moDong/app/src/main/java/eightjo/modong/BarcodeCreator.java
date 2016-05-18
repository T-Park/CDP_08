// BarcodeCreator.java
// It create Code128 1D barcode png file
// It uses barbeque open source library

package eightjo.modong;

import java.io.File;
import java.util.UUID;



import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

public class BarcodeCreator {
	
	
	// params
	// barcodeData - using UUID package to get unique id
    // 		
	// fileName - unique file name it will be user id
	//  		  it is different from barcodeData	
	
	public boolean makeImageFile( String barcodeData, String fileName )
	{
        try {
        	Barcode barcode = BarcodeFactory.createCode128B(barcodeData);
        	//barcode.setDrawingText(false);
        	// resize barheight

        	//barcode.setBarHeight(300); // pixel entity
        	//barcode.setBarWidth(5);
        	
            File f = new File(fileName + ".png");
            BarcodeImageHandler.savePNG(barcode, f);
            
            return true;
        } catch (Exception e) {
            // Error handling here
        	e.printStackTrace();
        	return false;
        }
	}
}
