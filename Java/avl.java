import java.util.ArrayList;
import java.util.Scanner;

public class avl 
{
	static boolean done = false;
	
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		int inputs = scan.nextInt();
		
		for (int in = 1; in <= inputs; in++)
		{
			int inserts = scan.nextInt();
			
			if (inserts == 0)
			{
				System.out.print("Tree #" + in + ": ");
				System.out.print("KEEP\n");
				continue;
			}
			
			int r = scan.nextInt();
			Nod root = new Nod(r);
			
			System.out.print("Tree #" + in + ": ");
			
			for (int i = 1; i < inserts; i++)
			{
				int current = scan.nextInt();
				Nod node = new Nod(current);
				
				root = insert(root, node);
				
				if (done)
				{
					System.out.print("REMOVE\n");
					while (i < (inserts-1))
					{
						int x = scan.nextInt();
						i++;
					}
					break;
				}
			}
			
			if (!done)
				System.out.print("KEEP\n");
			
			done = false;
		}
		
	}
	
	public static Nod insert(Nod root, Nod current)
	{		
		if (root == null)
			return current;
		
		if (current.value < root.value)
			root.left = insert(root.left, current);
		else if (current.value > root.value)
			root.right = insert(root.right, current);
		else
			return root;

		if (root.left == null)
			root.depth = 1 + root.right.depth;
		else if (root.right == null)
			root.depth = 1 + root.left.depth;
		else
			root.depth = 1 + Math.max(root.left.depth, root.right.depth);
		
		int val = Math.abs(check(root));
		if (val > 1)
			done = true;
		
		return root;
	}
	
	public static int check(Nod node)
	{
		if (node == null)
			return 0;
		else if (node.left == null)
			return node.right.depth;
		else if (node.right == null)
			return node.left.depth;
		else
			return node.left.depth - node.right.depth;
	}
}

class Nod
{
	int value;
	int depth;
	Nod left, right;
	
	public Nod(int value)
	{
		this.value = value;
		depth = 1;
		left = null;
		right = null;
	}
}

/*2
3 1 2 3
7 4 2 6 7 3 1 5

6
4 10 4 9 12
10 10 4 12 9 11 6 2 14 16 8
7 10 5 15 4 6 14 16
7 10 5 15 4 3 14 16
7 10 5 15 4 6 14 13
6 5 4 11 12 3 2

1
8 10 5 15 3 17 7 2 4

*/
