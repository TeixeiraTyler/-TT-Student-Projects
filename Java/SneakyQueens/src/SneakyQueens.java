//Tyler Teixeira

import java.util.ArrayList;

public class SneakyQueens 
{
	public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		int[] cols = new int[boardSize];
		int[] rows = new int[boardSize];
		int[] diag = new int[boardSize * 3];
		int[] coord = new int[2];
		int size = coordinateStrings.size();

		for (int i = 0; i < size; i++)
		{
			parseCoordinate(coordinateStrings.get(i), coord);
			
			int col = coord[0] - 1;
			int row = coord[1] - 1;
			
			// Check if a Queen already exists on given col. Otherwise mark col as taken.			
			if (cols[col] == 1)
				return false;
			cols[col] = 1;
			
			// Check if a Queen already exists on given row. Otherwise mark row as taken.
			if (rows[row] == 1)
				return false;
			rows[row] = 1;
			
			int diag1Index = col - row + boardSize - 1;	// calc the row intercept with slope=1
			int diag2Index = col + row + boardSize + 1;	// calc the row intercept with slope=-1

			// Check if a Queen already exists on positive slope diagonal. Otherwise, mark diagonal as taken.
			if (diag[diag1Index] == 1)
				return false;
			diag[diag1Index] = 1;
			
			// Check if a Queen already exists on negative slope diagonal. Otherwise, mark diagonal as taken.
			if (diag[diag2Index] == 1)
				return false;
			diag[diag2Index] = 1;
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
		return 2.5;
	}
	
	public static double hoursSpent()
	{
		return 9;
	}
}
