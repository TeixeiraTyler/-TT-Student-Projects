import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class facts
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		
		for (int c = 0; c < cases; c++)
		{
			int temp = scan.nextInt();
			String number = Integer.toString(temp);
			BigInteger num = new BigInteger(number);
			
			for (int i = temp-1; i > 1; i--)
			{
				String m = Integer.toString(i);
				BigInteger mult = new BigInteger(m);
				num = num.multiply(mult);
			}
			
			ArrayList<Integer> factorization = new ArrayList<Integer>();
			BigInteger n = num;
			BigInteger i = new BigInteger("2");
			BigInteger one = new BigInteger("1");
			BigInteger zero = new BigInteger("0");
			int s = (int)Math.sqrt(temp);
			BigInteger sqt = new BigInteger(Integer.toString(s));
			
			while (i.compareTo(sqt) < 1)
			{
				if (n.mod(i).compareTo(zero) == 0)
				{
					factorization.add(i.intValue());
					n = n.divide(i);
				}
				else
					i = i.add(one);
			}
			
			int count = 1;
			for (int p = 0; p < factorization.size()-1; p++)
			{
				if (factorization.get(p+1) == factorization.get(p))
					count++;
				else
				{
					System.out.print(count + " ");
					count = 1;
				}
			}
			System.out.print(count + " ");
			
			System.out.print("\n");
		}
	}
}