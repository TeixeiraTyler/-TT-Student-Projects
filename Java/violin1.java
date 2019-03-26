import java.util.Scanner;
import java.util.ArrayList;

public class violin1
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int sequences = scan.nextInt();
		int[] movements = new int[sequences];
		
		for (int i = 0; i < sequences; i++)
		{
			int[] fingers = new int[5];
			int noteCount = scan.nextInt();
			int[] notes = new int[noteCount];
			
			for (int j = 0; j < noteCount; j++)
			{
				notes[j] = scan.nextInt();
			}
			
			for (int note : notes)
			{
				if (note == 0 && highest(fingers) <= note)
					continue;
				if (fingers[note] == 1 && highest(fingers) <= note)
					continue;
				if (fingers[note] == 0 && highest(fingers) < note)
				{
					fingers[note] = 1;
					movements[i]++;
				}
				else if (highest(fingers) > note)
				{
					for (int n = note+1; n <= highest(fingers); n++)
					{
						if (fingers[n] == 1)
							movements[i]++;
						fingers[n] = 0;
					}
					
					if (note == 0)
						continue;
					if (fingers[note] == 0)
					{
						fingers[note] = 1;
						movements[i]++;
					}
				}
			}
		}
		
		for (int k = 0; k < sequences; k++)
			System.out.println(movements[k]);
	}
	
	public static int highest(int[] fingers)
	{
		int max = 0;
		
		for (int i = 0; i < fingers.length; i++)
		{
			if (fingers[i] == 1)
				max = i;
		}
		
		return max;
	}
}