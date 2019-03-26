import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class treesales
{
	public static void main(String args[]) throws IOException
	{
		Scanner scan = new Scanner(System.in);
		
		int structures = scan.nextInt();
		
		//PrintWriter output = new PrintWriter(new FileWriter("output.txt"));
		
		for (int i = 0; i < structures; i++)
		{
			System.out.println("COMPANY " + (i + 1));
			//output.println("Company " + (i + 1));
			
			int operations = scan.nextInt();
			HashMap<String, Node> members = new HashMap<String, Node>();
			Node root = new Node("ROOT", null);
			members.put("ROOT", root);
			
			for (int j = 0; j < operations; j++)
			{
				String action = scan.next();
				String name = scan.next();
				String asset = null;
				
				if (!action.equals("QUERY"))
					asset = scan.next();
				
				if (action.equals("ADD"))
				{
					Node node = new Node(asset, members.get(name));
					members.put(asset, node);
					members.get(name).recruit(node);
				}
				
				else if (action.equals("SALE"))
				{
					int money = Integer.parseInt(asset);
					members.get(name).calculate(members.get(name), money);
				}
				
				else if (action.equals("QUERY"))
				{
					
					//output.println(members.get(name).value);
					
					System.out.println(members.get(name).value);
				}
			}
		}
		//output.close();
	}
}

class Node
{
	Node parent;
	ArrayList<Node> children;
	String name;
	int value;
	
	Node(String name, Node parent)
	{
		this.name = name;
		this.parent = parent;
		value = 0;
		children = new ArrayList<Node>();
	}
	
	public void recruit(Node child)
	{
		children.add(child);
	}
	
	public void calculate(Node name, int sale)
	{	
		name.value += sale;
		
		if (name.parent == null)
			return;
		
		calculate(name.parent, sale);
	}
}