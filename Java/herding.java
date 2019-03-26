import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class herding
{
	public static void main(String args[]) throws IOException
	{
		Scanner scan = new Scanner(new File("herding.in"));
		//Scanner scan = new Scanner(System.in);
		
		int bessie = scan.nextInt();
		int elsie = scan.nextInt();
		int mildred = scan.nextInt();
		
		ArrayList<Integer> order = new ArrayList<Integer>();
		order.add(bessie);
		order.add(elsie);
		order.add(mildred);
		order.sort(null);
		ArrayList<Integer> copy = (ArrayList<Integer>) order.clone();
		
		int min = 2;
		int max = Integer.MAX_VALUE;
		
		if (max > 0)
		{
			if (order.get(2) - order.get(1) > order.get(1) - order.get(0))
			{
				max = order.get(2) - order.get(1) - 1;
			}
			else
			{
				max = order.get(1) - order.get(0) - 1;
			}
		}
		
		if (check(order))
		{
			min = 0;
			max = 0;
		}
		else
		{
			copy.set(0, copy.get(2)-1);
			copy.sort(null);
			if (check(copy))
				min = 1;
			else
			{
				copy = (ArrayList<Integer>) order.clone();
				copy.set(2, copy.get(0)+1);
				copy.sort(null);
				if (check(copy))
					min = 1;
			}
		}
		
		PrintWriter output = new PrintWriter(new FileWriter("herding.out"));
		output.print(min + "\n");
		output.print(max);
		output.close();
		
		//System.out.println(min);
		//System.out.println(max);
	}
	
	public static boolean check(ArrayList<Integer> order)
	{
		if (order.get(2) - order.get(1) == 1 && order.get(1) - order.get(0) == 1)
			return true;
		else
			return false;
	}
}