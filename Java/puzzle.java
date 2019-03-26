import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class puzzle 
{
	static HashMap<String, Integer> boardMap = new HashMap<String, Integer>();
	static LinkedList<Board> q = new LinkedList<Board>();

	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		int cases = scan.nextInt();

		boardMap.put("123456780", 0);
		
		Board b = new Board("123456780", 0);
		q.add(b);
		
		while (!q.isEmpty())
		{
			AnalyzeNext();
		}
		
		for (int c = 0; c < cases; c++)
		{
			String start = "";
			
			for (int i = 0; i < 9; i++)
			{
				start = start + scan.next();
			}
			
			System.out.println(boardMap.get(start));
		}
	}
	
	public static void AnalyzeNext()
	{
		Board b = q.remove();
		Board moved;
		
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 3; col++)
			{
				if (row > 0 && b.board[row-1][col] == '0')
				{
					moved = new Board(b);
					moved.board[row-1][col] = moved.board[row][col];
					moved.board[row][col] = '0';
					
					String s = moved.ToString();
					
					if (!boardMap.containsKey(s))
					{
						boardMap.put(s, moved.depth);
						q.add(moved);
					}
				}
				
				if (row < 2 && b.board[row+1][col] == '0')
				{	
					moved = new Board(b);
					moved.board[row+1][col] = moved.board[row][col];
					moved.board[row][col] = '0';
					
					String s = moved.ToString();
					
					if (!boardMap.containsKey(s))
					{
						boardMap.put(s, moved.depth);
						q.add(moved);
					}
				}
				
				if (col > 0 && b.board[row][col-1] == '0')
				{	
					moved = new Board(b);
					moved.board[row][col-1] = moved.board[row][col];
					moved.board[row][col] = '0';
					
					String s = moved.ToString();
					
					if (!boardMap.containsKey(s))
					{
						boardMap.put(s, moved.depth);
						q.add(moved);
					}
				}
				
				if (col < 2 && b.board[row][col+1] == '0')
				{	
					moved = new Board(b);
					moved.board[row][col+1] = moved.board[row][col];
					moved.board[row][col] = '0';
					
					String s = moved.ToString();
					
					if (!boardMap.containsKey(s))
					{
						boardMap.put(s, moved.depth);
						q.add(moved);
					}
				}
			}
		}
	}
}

class Board
{
	public char[][] board = new char[3][3];
	public int depth;
	
	public Board(String contents, int depth)
	{
		this.depth = depth;
		
		board[0][0] = contents.charAt(0);
		board[0][1] = contents.charAt(1);
		board[0][2] = contents.charAt(2);
		board[1][0] = contents.charAt(3);
		board[1][1] = contents.charAt(4);
		board[1][2] = contents.charAt(5);
		board[2][0] = contents.charAt(6);
		board[2][1] = contents.charAt(7);
		board[2][2] = contents.charAt(8);
	}
	
	public Board(Board clone)
	{
		depth = clone.depth + 1;
		
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++)
				board[row][col] = clone.board[row][col];
	}
	
	public String ToString()
	{
		String s = "";
		
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++)
				s += board[row][col];
		
		return s;
	}
}

