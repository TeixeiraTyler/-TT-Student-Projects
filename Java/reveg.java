import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class reveg
{
	public static void main(String args[]) throws IOException
	{
		Scanner scan = new Scanner(new File("revegetate.in"));
		//Scanner scan = new Scanner(System.in);
		
		int numPastures = scan.nextInt();
		int numCows = scan.nextInt();
		int[] seed = new int[numPastures+1];
		ArrayList<PastureCombo> combos = new ArrayList<PastureCombo>();
		
		for (int i = 1; i <= numPastures; i++)
		{
			PastureCombo pasture = new PastureCombo(i);
			combos.add(pasture);
		}
		
		for (int n = 0; n < numCows; n++)
		{
			int pasture1 = scan.nextInt();
			int pasture2 = scan.nextInt();
			
			combos.get(pasture1-1).others.add(pasture2);
			combos.get(pasture2-1).others.add(pasture1);
		}
		
		for (PastureCombo p : combos)
		{
			if (seed[p.pasture] == 0)
				seed[p.pasture] = 1;
			
			p.others.sort(null);
			
			for (int other : p.others)
			{
				if (other > p.pasture)
				{
					if (seed[p.pasture] == seed[other])
					{
						seed[other]++;
					}
					else if (seed[other] == 0)
					{
						seed[other] = 1;
					}
				}
				else
				{
					if (seed[p.pasture] == seed[other])
					{
						seed[p.pasture]++;
					}
				}
			}
		}
		
		PrintWriter output = new PrintWriter(new FileWriter("revegetate.out"));
		for (int i = 1; i <= seed.length-1; i++)
		{
			//System.out.print(seed[i]);
			output.print(seed[i]);
		}
			
		output.close();
	}
}

class PastureCombo
{
	int pasture;
	ArrayList<Integer> others;
	
	public PastureCombo(int pasture)
	{
		this.pasture = pasture;
		others = new ArrayList<Integer>();
	}
}