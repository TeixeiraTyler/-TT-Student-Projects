import java.util.Scanner;

public class fruit
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int stands = scan.nextInt();
		int[] dailyOrder = new int[stands];
		int[] maxStored = new int[stands];
		int count = 0;
		
		for (int i = 0; i < stands; i++)
		{
			int min = -1;
			int days = scan.nextInt();
			int[] data = new int[days];
			
			for (int j = 0; j < days; j++)
			{
				data[j] = scan.nextInt();
				
				if (min == -1 || min > data[j])
					min = data[j];
			}
			
			int remainder = 0;
			int n = min;
			while (n <= 1000)
			{
				int max = -1;
				boolean success = true;
				remainder = 0;
				
				for (int k = 0; k < days; k++)
				{
					success = true;
					
					if (n - data[k] + remainder < 0)
					{
						success = false;
						break;
					}
					
					remainder += n - data[k];
					
					if (max == -1 || max < remainder)
						max = remainder;
				}
				
				if (success)
				{
					dailyOrder[count] = n;
					maxStored[count] = max;
					count++;
					break;
				}
				
				n++;
			}
		}
		
		for (int i = 0; i < stands; i++)
			System.out.println(dailyOrder[i] + " " + maxStored[i]);
	}
}