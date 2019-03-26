import java.util.Scanner;
import java.io.*;

public class shell
{
	public static void main(String args[]) throws IOException
	{
		Scanner scan = new Scanner(new File("shell.in"));
		
		int correct = 0;
		int[] shells = {0, 1, 2, 3};
		int[] guesses = {0, 0, 0, 0};
		int swaps = scan.nextInt();
		
		for (int i = 0; i < swaps; i++)
		{
			int a = scan.nextInt();
			int b = scan.nextInt();
			int g = scan.nextInt();
			
			int temp = shells[a];
			shells[a] = shells[b];
			shells[b] = temp;
			//shells = swap(shells, a, b);
			guesses[shells[g]]++;
		}
		
		for (int j = 0; j < guesses.length; j++)
		{
			if (guesses[j] > correct)
				correct = guesses[j];
		}
		
		PrintWriter output = new PrintWriter(new FileWriter("shell.out"));
		output.print(correct);
		output.close();
		//System.out.println(correct);
		scan.close();
	}
	
	public static int[] swap(int[] shells, int a, int b)
	{
		int position1 = 0;
		int position2 = 0;
		
		for (int i = 0; i < shells.length; i++)
		{
			if (shells[i] == a)
				position1 = i;
			if (shells[i] == b)
				position2 = i;
		}
		
		shells[position1] = b;
		shells[position2] = a;
		
		return shells;
	}
}