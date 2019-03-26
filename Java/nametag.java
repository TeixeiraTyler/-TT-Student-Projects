import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class nametag	
{
	public static void main(String[] args)	
	{
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		
		for (int c = 0; c < cases; c++)
		{
			String input = scan.next();
			int closestA = Integer.MAX_VALUE;
			int index = 0;
			boolean same = true;
			
			for (int i = 0; i < input.length(); i++)
			{
				char ch = input.charAt(i);
				int value = (int)ch - 'A' + 1;
				
				if (value < closestA)
				{
					if (i != 0)
						same = false;
					closestA = value;
					index = i;
				}
				
				else if (value == closestA)
				{
					int look = index+1;
					for (int j = i; j < input.length() + i; j++)
					{
						if (j >= input.length()-1 && look < input.length()-1)
						{
							int val = (int)input.charAt(j-input.length()+1) - 'A' + 1;
							int close = (int)input.charAt(look) - 'A' + 1;
							
							if (close < val)
								break;
							else if (val < close)
							{
								same = false;
								index = i;
								break;
							}
							else
							{
								look++;
							}
						}
						else if (j >= input.length()-1 && look >= input.length()-1)
						{
							int val = (int)input.charAt(j-input.length()+1) - 'A' + 1;
							int close = (int)input.charAt(look-input.length()+1) - 'A' + 1;
							
							if (close < val)
								break;
							else if (val < close)
							{
								same = false;
								index = i;
								break;
							}
							else
							{
								look++;
							}
						}
						else
						{
							int val = (int)input.charAt(j+1) - 'A' + 1;
							int close = (int)input.charAt(look) - 'A' + 1;
							
							if (close < val)
								break;
							else if (val < close)
							{
								same = false;
								index = i;
								break;
							}
							else
							{
								look++;
							}
						}
					}
				}
			}
			
			if (same)
				System.out.println(input);
			else
			{
				String newString1 = input;
				String newString2 = input;
				newString1 = newString1.substring(0, index);
				newString2 = newString2.substring(index);
				System.out.println(newString2 + "" + newString1);
			}
		}
		
	}
}