import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class sleepy
{
	public static void main(String args[]) throws IOException
	{
		Scanner scan = new Scanner(new File("sleepy.in"));
		
		int numCows = scan.nextInt();
		int[] cows = new int[numCows];
		int steps = 0;
		
		for (int i = 0; i < numCows; i++)
		{
			int cow = scan.nextInt();
			cows[i] = cow;
		}
		
		for (int k = 0; k < numCows-1; k++)
		{
			if (cows[k] > cows[k+1])
			{
				steps++;
				break;
			}
		}
		
		if (steps != 0)
		{
			for (int j = numCows-2; j > 0; j--)
			{
				if (cows[j] < cows[j-1])
					steps++;
			}
		}
		
		int result = numCows - steps;
		
		PrintWriter output = new PrintWriter(new FileWriter("sleepy.out"));
		output.print(result);
		output.close();
		System.out.println(result);
		scan.close();
	}
}
		