//Tyler Teixeira - ty034938

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class hillcipher
{
	public static void main(String[] args) throws IOException
	{
		//stores key to matrix.
		int[] key = null;
		
		try
		{
			key = readInt(args[0]);
		}
		catch (Exception ex)
		{
			System.out.println("Could not find key file.");
			return;
		}

		int x = key[0];
		int k = 1;
		int matrix[][] = new int[x][x];
		for (int i=0; i<x; i++)
			for (int j=0; j<x; j++)
			{
				matrix[i][j] = key[k];
				k++;
			}

		//stores plaintext to character array.
		FileReader textR = null;
		
		try
		{ 
			textR = new FileReader(args[1]);
		}
		catch (Exception ex)
		{
			System.out.println("Could not find plaintext file.");
			return;
		}
		
		char[] rawText = new char[10000];
		textR.read(rawText);
		textR.close();
		
		//converts character array to string with only lowercase.
		String text = new String(rawText);
		text = text.replaceAll("[^a-zA-Z]", "");
		text = text.toLowerCase();
		
		int len = text.length();

		//pad the text to an even multiple of the matrix size (if needed)
		if (len%x != 0)
			for (int i=0; i < (x - len%x); i++)
				text = text.concat("x");
	
	
		//converts letters in text file to number values.
		int[] value = new int[10000];
		for (int i=0; i<text.length(); i++)
		{
			char letter = text.charAt(i);
			int ascii = (int)letter - 97;
			value[i] = ascii;
		}
		
		//performs the hill cipher encryption.
		int y = 0;
		int z = 0;
		int ctr = 0;
		char[] cipher = new char[10000];
		int[] encryption = new int[10000];
		while (ctr < text.length())
		{
			for (int i=0; i<x; i++)
			{
				for (int j=0; j<x; j++)
				{
					int temp = matrix[i][j] * value[z];
					encryption[y] += temp;
					z++;
				}
				encryption[y] = encryption[y]%26;
				encryption[y] += 97;
				if (encryption[y] == 97)
					if (encryption[y-1] == 97)
					{
						encryption[y] = 0;
						encryption[y-1] = 9;
					}
				cipher[y] = (char)encryption[y];
				java.util.Arrays.toString(cipher);
				y++;
				ctr++;
				z -= x;
			}
			z += x;
		}
		
		//stores key to character array to echo input file.
		FileReader keyR = new FileReader(args[0]);
		char[] rawKey = new char[1000];
		keyR.read(rawKey);
		keyR.close();
		
		//prints output to screen.
		System.out.println(rawKey);
		System.out.println(rawText);
		
		String Cipher = new String(cipher);
		Cipher = Cipher.replaceAll("[^a-z]", "");
		System.out.println(Cipher);
	}
			
	//function to manually store integers into an array.
	public static int[] readInt(String file) throws FileNotFoundException
	{
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			int ctr = 0;
			while (s.hasNextInt())
			{
				ctr++;
				s.nextInt();
			}
			int[] arr = new int[ctr];
			
			Scanner s1 = new Scanner(f);
			
			for (int i=0; i<arr.length; i++)
				arr[i] = s1.nextInt();
			
			return arr;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}