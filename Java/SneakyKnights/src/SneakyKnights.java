//Tyler Teixeira
//ty034938
//COP 3503

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Hashtable;

public class SneakyKnights 
{
	public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		int[] coord = new int[2];
		int size = coordinateStrings.size();
		Hashtable<Integer, Hashtable<Integer, Integer>> targets = new Hashtable<Integer, Hashtable<Integer, Integer>>();
		Hashtable<Integer, Integer> ht;
		int[][] moves = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
		
		for (int i = 0; i < size; i++)
		{
			parseCoordinate(coordinateStrings.get(i), coord);
			
			int col = coord[0] - 1;
			int row = coord[1] - 1;
			
			//System.out.printf("%d (%d): %d,%d\n", i, targets.size(), row, col);
			
			// Check if current location is targeted. If so, collision.
			ht = targets.get(col);
			if (ht != null)
			{
				Integer rht = ht.get(row);
			
				if (rht != null && rht == 1)
					return false;
			}	
			
			// Mark all target positions that are in bounds.	
			for (int x = 0; x < 8; x++)
			{
				int c = col + moves[x][0];
				int r = row + moves[x][1];
				
				//	Check boundaries before setting target.
				if (c >= 0 && r >= 0 && c < boardSize && r < boardSize)
				{
					ht = targets.get(c);
					
					if (ht == null)
					{
						ht = new Hashtable<Integer, Integer>();
						targets.put(c, ht);
					}
					
					ht.put(r, 1);
				}
			}
		}

		return true;
	}
	
	//function to take whole coordinate string and returns array with col, row
	public static void parseCoordinate(String coordinate, int[] result)
	{
		int col = 0;
		int index = 0;
		
		// Parse letters left to right in base 26.
		for (; index < coordinate.length() && Character.isLetter(coordinate.charAt(index)); index++)
		{
			col *= 26;
			col += coordinate.charAt(index) - 'a' + 1;
		}
		
		int row = 0;

		// Parse digits left to right in base 10.
		for (; index < coordinate.length() && Character.isDigit(coordinate.charAt(index)); index++)
		{
			row *= 10;
			row += coordinate.charAt(index) - '0';
		}

		// Package row and col into int array (result).
		result[0] = col;
		result[1] = row;
	}

	public static double difficultyRating()
	{
		return 3;
	}
	
	public static double hoursSpent()
	{
		return 6;
	}
}
