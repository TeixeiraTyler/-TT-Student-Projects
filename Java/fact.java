import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class fact 
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		
		for (int c = 0; c < cases; c++)
		{
			int num = scan.nextInt();
			
			ArrayList<Integer> primeList = createList(num);
			
			for (int p : primeList)
			{
				int n = num;
				int count = 0;
				while (n > 0)
				{
					n /= p;
					count += n;
				}
				System.out.print(count + " ");
			}
			
			System.out.print("\n");
		}
	}
	
	public static boolean isPrime(int n)
	{
		if (n <= 1)
			return false;
		if (n <= 3)
			return true;
		
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		
		// only go up to sqrt n)
		for (int x = 5; x * x <= n; x = x + 6)
		{
			if (n % x == 0 || n % (x + 2) == 0)
				return false;
		}
		
		return true;
	}
	
	public static ArrayList<Integer> createList(int n)
	{
		ArrayList<Integer> primeList = new ArrayList<Integer>();
		
		for (int i = 2; i <= n; i++)
			if (isPrime(i))
				primeList.add(i);
		
		return primeList;
	}
}