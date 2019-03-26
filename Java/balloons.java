import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class balloons
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int teams = 0;
		int inputs = 0;
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		while (scan.hasNext())
		{
			int[] input = new int[3];
			int check = 0;
			for (int i = 0; i < 3; i++)
			{
				input[i] = scan.nextInt();
				if (input[i] == 0)
					check++;
			}
			
			if (check == 3)
				break;
			
			results.add(0);
			teams = input[0];
			int roomA = input[1];
			int roomB = input[2];
			ArrayList<Integer> ballNeed = new ArrayList<Integer>();
			int[] disA = new int[teams];
			int[] disB = new int[teams];
			
			for (int j = 0; j < teams; j++)
			{
				for (int k = 0; k < 3; k++)
					input[k] = scan.nextInt();
				
				ballNeed.add(input[0]);
				disA[j] = input[1];
				disB[j] = input[2];
			}
			
			HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
			for (int k = 0; k < teams; k++)
			{
				hash.put(k, Math.abs(disA[k] - disB[k]));
			}
			
			for (int a = 0; a < teams; a++)
			{
				
				int max = 0;
				for (Map.Entry<Integer, Integer> team : hash.entrySet())
				{
					if (team.getValue() > max)
					{
						max = team.getKey();
					}
				}
				
				if (disA[max] < disB[max] && roomA >= ballNeed.get(max))
				{
					roomA -= ballNeed.get(max);
					results.set(inputs, results.get(inputs) + disA[max] * ballNeed.get(max));
					hash.remove(max);
					//ballNeed.remove(max);
				}
				else if (disB[max] < disA[max] && roomB >= ballNeed.get(max))
				{
					roomB -= ballNeed.get(max);
					results.set(inputs, results.get(inputs) + disB[max] * ballNeed.get(max));
					hash.remove(max);
					//ballNeed.remove(max);
				}
				else if (disA[max] <= disB[max])
				{
					while (ballNeed.get(max) != 0 && roomA != 0)
					{
						results.set(inputs, results.get(inputs) + disA[max]);
						ballNeed.set(max, ballNeed.get(max)-1);
						roomA--;
					}
					
					while (ballNeed.get(max) != 0 && roomB != 0)
					{
						results.set(inputs, results.get(inputs) + disB[max]);
						ballNeed.set(max, ballNeed.get(max)-1);
						roomB--;
					}
					
					hash.remove(max);
					//ballNeed.remove(max);
				}
				else if (disB[max] <= disA[max])
				{
					while (ballNeed.get(max) != 0 && roomB != 0)
					{
						results.set(inputs, results.get(inputs) + disB[max]);
						ballNeed.set(max, ballNeed.get(max)-1);
						roomB--;
					}
					
					while (ballNeed.get(max) != 0 && roomA != 0)
					{
						results.set(inputs, results.get(inputs) + disA[max]);
						ballNeed.set(max, ballNeed.get(max)-1);
						roomA--;
					}
					
					hash.remove(max);
					//ballNeed.remove(max);
				}
			}
			//System.out.println(results.get(inputs));
			inputs++;
		}
		
		//System.out.print("\n");
		for (int i = 0; i < results.size(); i++)
			System.out.println(results.get(i));
	}
}