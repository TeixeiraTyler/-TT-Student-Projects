// Tyler Teixeira
// ty034938

import java.io.*;
import java.util.*;


public class TopoPath 
{
	private static Scanner input;
	private static ArrayList<ArrayList<Integer>> list;
	private static ArrayList<ArrayList<Integer>> solutions;
	private static ArrayList<Integer> solution;
	private static ArrayList<Integer> solutionCopy;
	private static int[] in_vertices;
	private static int[] save_vertices;
	private static int nodes;
	private static boolean found;
	
	public static boolean hasTopoPath(String filename) throws FileNotFoundException
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
		solutions = new ArrayList<ArrayList<Integer>>();
		solutions.add(null);
			
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
		
		// stores all possible toposorts into an arraylist called solutions.
		getTopoSort();
		
		// loop through solutions and check if a path is possible.
		for (int i = 1; i < solutions.size(); i++)
		{
			for (int j = 0; j < nodes; j++)
			{
				for (int k = 1; k < list.get(solutions.get(i).get(j)).size(); k++)
				{
					if (list.get(solutions.get(i).get(j)).get(k) == solutions.get(i).get(j+1))
					{
						found = true;
						break;
					}
				}
				
				if (found == true)
				{
					if (j == nodes - 2)
						return true;
					found = false;
				}
				else
					break;
			}
		}
			
		return false;
	}
	
	public static void getTopoSort()
	{
		// created boolean array to keep track of visited nodes, and initialize all to false.
		boolean visited[] = new boolean[nodes + 1];
		
		in_vertices = save_vertices.clone();
		solution = new ArrayList<Integer>();
		topoSort(visited);
	}
	
	public static void topoSort(boolean visited[])
	{
		boolean flag = false;
		boolean cycle = false;
		
		for (int i = 1; i <= nodes; i++)
		{
			// proceed if all prerequisites have been met for node i.
			if (in_vertices[i] == 0 && !visited[i])
			{
				for (int j = 1; j < list.get(i).size(); j++)
					in_vertices[list.get(i).get(j)]--;
				
				solution.add(i);
				visited[i] = true;
				topoSort(visited);
				
				if (cycle == true)
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
			else
			{
				solutionCopy = new ArrayList<Integer>();
				solutionCopy.addAll(solution);
				solutions.add(solutionCopy);
			}
		}
	}
	
	public static double difficultyRating()
	{
		return 2;
	}
	
	public static double hoursSpent()
	{
		return 3;
	}
}
