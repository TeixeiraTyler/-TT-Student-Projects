import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class polling
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int votes = scan.nextInt();
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		ArrayList<String> Winners = new ArrayList<String>();
		
		for (int i = 0; i < votes; i++)
		{
			String name = scan.next();
			if (!hash.containsKey(name))
				hash.put(name, 1);
			else
				hash.put(name, hash.get(name) + 1);
		}
		
		int max = 0;
		for (Map.Entry<String, Integer> candidate : hash.entrySet())
		{
			String name = candidate.getKey();
			int voteCount = candidate.getValue();
			
			if (voteCount > max)
			{
				max = voteCount;
				Winners.clear();
			}
			if (voteCount == max)
			{
				Winners.add(name);
			}
			
		}
		
		Collections.sort(Winners, String.CASE_INSENSITIVE_ORDER);
		
		for (int j = 0; j < Winners.size(); j++)
			System.out.println(Winners.get(j));
	}
}