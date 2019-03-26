import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class guess
{
	public static void main(String args[]) throws IOException
	{
		Scanner scan = new Scanner(new File("test.txt"));
		
		int numAnimals = scan.nextInt();
		ArrayList<Animal> animals = new ArrayList<Animal>();
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		
		for (int i = 0; i < numAnimals; i++)
		{
			String name = scan.next();
			int numChar = scan.nextInt();
			Animal animal = new Animal(name, numChar);
			animals.add(animal);
			
			for (int j = 0; j < numChar; j++)
			{
				String characteristic = scan.next();
				animals.get(i).addChar(characteristic);
				
				if (!hash.containsKey(characteristic))
					hash.put(characteristic, 1);
				else
					hash.put(characteristic, hash.get(characteristic) + 1);
			}
		}
		
		int max = 0;
		String biggest = "";
		for (Map.Entry<String, Integer> characteristic : hash.entrySet())
		{
			String name = characteristic.getKey();
			int charCount = characteristic.getValue();
			
			if (charCount > max)
			{
				max = charCount;
				biggest = name;
			}
		}
		
		int[] possible = new int[numAnimals];
		for (int k = 0; k < animals.size(); k++)
		{
			for (int l = 0; l < animals.get(k).numChars; l++)
			{
				if (animals.get(k).chars.get(l).equals(biggest))
					possible[k] = 1;
			}
		}
		
		for (int x = 0; x < numAnimals; x++)
		{
			if (possible[x] == 0)
				continue;
			
		}
		
		//PrintWriter output = new PrintWriter(new FileWriter("guess.out"));
		//output.print(max);
		//output.close();
		System.out.println(max);
		scan.close();
	}
}

class Animal
{
	String name;
	int numChars;
	ArrayList<String> chars;
	
	Animal(String name, int numChar)
	{
		this.name = name;
		this.numChars = numChar;
		chars = new ArrayList<String>();
	}
	
	public void addChar(String ch)
	{
		this.chars.add(ch);
	}
}