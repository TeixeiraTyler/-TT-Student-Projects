import java.util.Scanner;
import java.util.ArrayList;

public class sorting 
{
	static int steps = 0;
	
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		int sessions = scan.nextInt();
		
		for (int i = 0; i < sessions; i++)
		{
			steps = 0;
			
			int groups = scan.nextInt();
			ArrayList<Integer> piles = new ArrayList<Integer>();
			
			for (int j = 0; j < groups; j++)
			{
				int num = scan.nextInt();
				if (num != 0)
					piles.add(num);
			}
			
			if (piles.size() > 1)
			{
				piles.sort(null);
	
				ArrayList<Integer> myStacks = piles;
	
				while (myStacks.size() != 1)
					myStacks = stack2(myStacks);
			}
			
			System.out.println(steps);
		}
	}
	
	public static ArrayList<Integer> stack2(ArrayList<Integer> piles)
	{
		ArrayList<Integer> stacks = new ArrayList<Integer>();

		int x, y;
		
		while (piles.size() > 0)
		{
			if (stacks.size() > 0 && stacks.get(0) < piles.get(0))
				x = stacks.remove(0);
			else
				x = piles.remove(0);
			
			if (piles.size() == 0 || (stacks.size() > 0 && stacks.get(0) < piles.get(0)))
				y = stacks.remove(0);
			else
				y = piles.remove(0);
			
			steps += (x + y);
			stacks.add(x + y);
		
			stacks.sort(null);
		}
		
		return stacks;
	}
}