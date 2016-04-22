
public class GoodsExamp {
	
	public static void main(String args[])
	{
		class GoodsStock
		{
			String goodsCode;
			int stockNum;
			
			void addStock (int amount)
			{stockNum += amount;
				
			}
			int substractStock(int amount)
			{
				if (stockNum < amount)
					return 0;
				stockNum -= amount;
				return amount;
			}
		}
		
	}

}
