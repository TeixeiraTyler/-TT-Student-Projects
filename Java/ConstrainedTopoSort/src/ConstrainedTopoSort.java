// Tyler Teixeira
// ty034938

import java.io.*;
import java.util.*;

public class ConstrainedTopoSort 
{
	private Scanner input;
	private ArrayList<ArrayList<Integer>> list;
	private ArrayList<Integer> solution;
	private int[] in_vertices;
	private int[] save_vertices;
	private int nodes;
	private boolean result;
	
	public ConstrainedTopoSort(String filename) throws FileNotFoundException
	{
		try {
		input = new Scanner(new File(filename));
		}
		catch(Exception e)	{
			System.out.println("Could not find file. \n");
		}

		// first read integer is amount of nodes in graph.
		nodes = input.nextInt();
		
		// create safe copy of num vertices and version that is used.
		save_vertices = new int[nodes + 1];
		in_vertices = new int[nodes + 1];
		
		// create list to hold edges, and set 0 to null (I will be using 1 -> n instead of 0 -> n-1)
		list = new ArrayList<ArrayList<Integer>>();
		list.add(null);
		
		// fill list with edges and keep track of which vertices are being pointed to.
		for (int i = 1; i <= nodes; i++)
		{
			int num_vertices = input.nextInt();
			ArrayList<Integer> edges = new ArrayList<Integer>();
			edges.add(num_vertices); 
			
			for (int j = 1; j <= num_vertices; j++)
			{
				int temp = input.nextInt();
				save_vertices[temp]++;
				edges.add(temp); 
			}
			
			list.add(edges);
		}	
	}
	
	public boolean hasConstrainedTopoSort(int x, int y)
	{
		// return false if 'x' or 'y' is referencing a node not in graph.
		if (x > nodes || y > nodes || x < 1 || y < 1)
			return false;
		
		// created boolean array to keep track of visited nodes, and initialize all to false.
		boolean visited[] = new boolean[nodes + 1];
		
		in_vertices = save_vertices.clone();
		solution = new ArrayList<Integer>();
		topoSort(visited, x, y);
		
		return result;
	}
	
	public void topoSort(boolean visited[], int x, int y)
	{
		boolean flag = false;
		boolean cycle = false;
		result = false;
		
		for (int i = 1; i <= nodes; i++)
		{
			// proceed if all prerequisites have been met for node i.
			if (in_vertices[i] == 0 && !visited[i])
			{
				for (int j = 1; j < list.get(i).size(); j++)
					in_vertices[list.get(i).get(j)]--;
				
				solution.add(i);
				visited[i] = true;
				topoSort(visited, x, y);
				
				if (result == true || cycle == true)
					return;
				
				visited[i] = false;
				solution.remove(solution.size() - 1);
				
				for (int k = 1; k < list.get(i).size(); k++)
					in_vertices[list.get(i).get(k)]++;
				
				flag = true;
			}
		}
		
		if (!flag)
		{
			// algorithm will fail if cycle is present, so check if solution size is not equal to num nodes.
			if (solution.size() != nodes)
				cycle = true;
			else if (solution.indexOf(x) < solution.indexOf(y))
			{
				result = true;
				return;
			}
		}
	}
	
	public static double difficultyRating()
	{
		return 3;
	}
	
	public static double hoursSpent()
	{
		return 9;
	}
}
