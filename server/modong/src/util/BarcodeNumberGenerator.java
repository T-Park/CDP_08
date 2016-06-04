/*
 * BarcodeNumberGenerator.java
 * 
 * Generate barcode number
 * leftmost digit : group(0:non, 1:group)
 * 1~(15) : sec echo from 1970 1 1
 * else : padding to make 15 digit
*/
package util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class BarcodeNumberGenerator {
	static public String generateBarcodeNum(boolean flag)
	{
		final int barcodeLength = 15;
		Random rand = new Random();
		String number = "";
		
		// set seed
		rand.setSeed(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		
		// barcode flag
		if (flag)
			number += "1";
		else
			number += "0";
		
		// add time epoch
		number += (new Long(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))).toString();
		
		for ( int i = number.length(); i <= barcodeLength; i++ )
		{
			number += rand.nextInt(9);
		}
		
		System.out.println(number);
		return number;
	}
	
	static public void main(String args[])
	{
		generateBarcodeNum(true);
	}
}
