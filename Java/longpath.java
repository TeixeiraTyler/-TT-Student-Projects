import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class longpath 
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int cities = scan.nextInt();
		
		for (int city = 0; city < cities; city++)
		{
			int numintersections = scan.nextInt();
			int numoneways = scan.nextInt();
			int home = 0;
			int destination = numintersections - 1;
			
			ArrayList<Intersection> intersections = new ArrayList<Intersection>();
			
			for (int i = 0; i < numintersections; i++)
			{
				Intersection newintersection = new Intersection(i);
				intersections.add(newintersection);
			}
			
			for (int i = 0; i < numoneways; i++)
			{
				int start = scan.nextInt();
				int end = scan.nextInt();
				int minutes = scan.nextInt();
				
				intersections.get(start).connections.put(intersections.get(end), minutes);
			}
			
			ArrayList<Intersection> visitedIntersections = new ArrayList<Intersection>();
			int shortest = findShortest(visitedIntersections, intersections.get(0), intersections.get(intersections.size()-1), 0, Integer.MAX_VALUE);
			
			visitedIntersections = new ArrayList<Intersection>();
			int longest = findLongest(visitedIntersections, intersections.get(0), intersections.get(intersections.size()-1), 0, Integer.MAX_VALUE);
			
			System.out.println(shortest + " " + longest);
		}
	}
	
	public static int findShortest(ArrayList<Intersection> visitedIntersections, Intersection start, Intersection end, int traveled, int min)
	{
		visitedIntersections.add(start);
		
		if (start == end)
		{
			if (traveled < min)
				min = traveled;
			
		}
		
		return 0;
	}
	
	public static int findLongest(ArrayList<Intersection> visitedIntersections, Intersection start, Intersection end, int traveled, int min)
	{
		
		
		return 0;
	}
}

class Intersection
{
	int label;
	HashMap<Intersection, Integer> connections;
	
	public Intersection(int start)
	{
		this.label = start;
		connections = new HashMap<Intersection, Integer>();
	}
}