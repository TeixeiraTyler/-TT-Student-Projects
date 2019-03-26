import java.util.ArrayList;
import java.util.Scanner;

public class profits
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		while (true)
		{
			int days = scan.nextInt();
			
			if (days == 0)
				break;
			
			ArrayList<Integer> profit = new ArrayList<Integer>();
			for (int day = 0; day < days; day++)
			{
				profit.add(scan.nextInt());
			}
			
			//ArrayList<Integer> subset = new ArrayList<Integer>();
				
			//subset.add(profit.get(0));
			//for (int i = 1; i < profit.size(); i++)
				//subset.add(subset.get(i-1) + profit.get(i));
			
			int max = 0, sum = 0;
			for (int x = 0, y = 0; y < profit.size(); y++)
			{
				sum += profit.get(y);
				
				if (sum > max)
					max = sum;
				else if (sum < 0)
				{
					x = y + 1;
					sum = 0;
				}
			}
			
			if (max == 0)
			{
				max = Integer.MIN_VALUE;
				for (int z : profit)
				{
					if (z > max)
						max = z;
				}
			}

			System.out.println(max);
		}
	}
}