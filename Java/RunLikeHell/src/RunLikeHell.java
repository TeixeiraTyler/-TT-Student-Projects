// Tyler Teixeira
// ty034938
// CS2 - Szumlanski

import java.util.*;

public class RunLikeHell 
{
	public static int maxGain(int[] blocks)
	{
		int size = blocks.length;
		int[] array = new int[size];
		
		// Initial cases to instantly give answer.
		if (size == 0)
			return 0;
		if (size == 1)
			return blocks[0];
		if (size == 2)
			return Math.max(blocks[0], blocks[1]);
		
		// Set initials to proper values.
		array[size - 1] = blocks[size - 1];
		array[size - 2] = blocks[size - 2];
		array[size - 3] = array[size - 1] + blocks[size - 3];
		
		// Loop through from the back, adding to sum the route with maximum gain.
		for (int i = size - 4; i >= 0; i--)
		{
			array[i] = Math.max(array[i + 2], array[i + 3]) + blocks[i];
		}
		
		// Return whether the first or second starting point grants higher gain.
		return Math.max(array[0], array[1]);
	}
	
	public static double difficultyRating()
	{
		return 1.5;
	}
	
	public static double hoursSpent()
	{
		return 2;
	}
	
	private static void failwhale()
	{
		System.out.println("fail whale :(");
		System.exit(0);
	}

	public static void main(String [] args)
	{
		int [] blocks = new int[] {15, 1, 1, 1, 1, 10};
		int ans = 26;

		if (RunLikeHell.maxGain(blocks) != ans) failwhale();

		System.out.println("Hooray!");
	}
}
