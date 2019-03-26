import java.util.Scanner;

public class monkey
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int datasets = scan.nextInt();
		scan.nextLine();
		
		for (int i = 0; i < datasets; i++)
		{
			String tree = scan.nextLine();
			
			int count;
			
			if (tree.length() == 0)
				count = 1;
			else
				count = solve(tree, 0, tree.length()-1);
			
			System.out.println((i + 1) + " " + count);
		}
	}
	
	// []
	// [[][[]]]
	// [[]]
	
	public static int solve(String tree, int start, int end)
	{
		int count = 2;
		//change
		if (start < tree.length() -1  && tree.charAt(start + 1) == '[')
		{
			int bStart = start + 1;
			int bEnd = start + 2;
			int bracketCount = 1;
			
			while (bracketCount > 0)
			{
				if (tree.charAt(bEnd) == '[')
					bracketCount++;
				
				if (tree.charAt(bEnd) == ']')
					bracketCount--;
				
				if (bracketCount > 0)
					bEnd++;
			}
			
			int branch1 = solve(tree, bStart, bEnd);
			
			bStart = bEnd + 1;
			bEnd++;
			bracketCount = 1;
			int branch2 = 1;
			
			if (tree.charAt(bEnd) != ']')
			{
				do
				{
					if (tree.charAt(bEnd) == '[')
						bracketCount++;
					
					if (tree.charAt(bEnd) == ']')
						bracketCount--;
					
					if (bracketCount > 0)
						bEnd++;
				}
				while (bracketCount > 0);
				
				branch2 = solve(tree, bStart, bEnd);
			}

			
			count = Math.max(branch1, branch2) * 2;
		}
		
		return count;
	}
}