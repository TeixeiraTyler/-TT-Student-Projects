import java.util.ArrayList;
import java.util.Scanner;

public class editor 
{
	static int cursorRow, cursorCol;
	static int destinationRow, destinationCol;
	static ArrayList<Coord> q = new ArrayList<Coord>();
	static Coord current;
	
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		
		for (int c = 0; c < cases; c++)
		{
			int numLines = scan.nextInt();
			int maxLength = 0;
			int[] lengths = new int[numLines];
			
			for (int i = 0; i < numLines; i++)
			{
				lengths[i] = scan.nextInt();
				
				if (lengths[i] > maxLength)
					maxLength = lengths[i];
			}
			
			cursorRow = scan.nextInt() - 1;
			cursorCol = scan.nextInt();
			destinationRow = scan.nextInt() - 1;
			destinationCol = scan.nextInt();
			
			boolean[][] visited = new boolean[numLines][maxLength + 1];
			
			q.clear();
			Coord start = new Coord(cursorRow, cursorCol, 0, "START");
			q.add(start);
			
			while (true)
			{
				current = q.get(0);
				q.remove(0);
				//System.out.println(current.move);
				//System.out.println(current.vertical + " " + current.horizontal + " " + current.depth);
				
				if (visited[current.vertical][current.horizontal])
					continue;
				
				if (current.vertical == destinationRow && current.horizontal == destinationCol)
				{
					System.out.println(current.depth);
					break;
				}
				
				visited[current.vertical][current.horizontal] = true;
				
				// move left
				if (current.horizontal > 0)
				{
					if (check(current.vertical, current.horizontal - 1, current.depth, "LEFT"))
						break;
				}
				else if (current.horizontal == 0 && current.vertical > 0)
				{
					if (check(current.vertical - 1, lengths[current.vertical - 1], current.depth, "LEFT->UPRIGHT"))
						break;
				}
				
				// move right
				if (current.horizontal < lengths[current.vertical])
				{
					if (check(current.vertical, current.horizontal + 1, current.depth, "RIGHT"))
						break;
				}
				else if (current.horizontal == lengths[current.vertical] && current.vertical < numLines - 1)
				{
					if (check(current.vertical + 1, 0, current.depth, "Right->DOWNLEFT"))
						break;
				}

				// move up
				if (current.vertical > 0)
				{
					int col = (lengths[current.vertical - 1] < current.horizontal ? lengths[current.vertical - 1] : current.horizontal);
					
					if (check(current.vertical - 1, col, current.depth, "UP"))
						break;
				}
				
				// move down
				if (current.vertical < numLines - 1)
				{
					int col = (lengths[current.vertical + 1] < current.horizontal ? lengths[current.vertical + 1] : current.horizontal);
					
					if (check(current.vertical + 1, col, current.depth, "DOWN"))
						break;
				}
			}
		}
	}
	
	public static boolean check(int row, int col, int depth, String move)
	{
		
		if (row == destinationRow && col == destinationCol)
		{
			System.out.println(depth+1);
			return true;
		}
		
		Coord temp = new Coord(row, col, depth + 1, move);

		for (Coord cord : current.previous)
		{
			temp.previous.add(cord);
		}
		temp.previous.add(temp);
		
		q.add(temp);
		return false;
	}
}

class Coord
{
	int vertical;
	int horizontal;
	int depth;
	String move;
	ArrayList<Coord> previous;
	
	public Coord(int x, int y, int dep, String move)
	{
		vertical = x;
		horizontal = y;
		depth = dep;
		this.move = move;
		previous = new ArrayList<Coord>();
	}
}