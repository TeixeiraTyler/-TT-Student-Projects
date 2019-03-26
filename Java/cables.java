import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class cables 
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int num = scan.nextInt();
		
		while (num != 0)
		{
			ArrayList<Point> points = new ArrayList<Point>();
			
			for (int point = 0; point < num; point++)
			{
				int x = scan.nextInt();
				int y = scan.nextInt();
				
				Point p = new Point(x, y);
				points.add(p);
			}
			
			ArrayList<Double> distances = solve(points.get(0), points);
			
			double sum = 0;
			for (Double d : distances)
				sum += d;
			
			System.out.printf("%.2f\n", sum);
			
			num = scan.nextInt();
		}
	}
	
	public static ArrayList<Double> solve(Point point, ArrayList<Point> points)
	{
		ArrayList<Point> unvisitedPoints = new ArrayList<Point>();
		ArrayList<Double> distances = new ArrayList<Double>();
		
		for (Point p : points)
		{			
			p.distance = Integer.MAX_VALUE;
			
			unvisitedPoints.add(p);
		}
		
		point.distance = 0;
		int index = 0;
		while (unvisitedPoints.size() > 0)
		{
			index = 0;
			double min = Integer.MAX_VALUE;
			for (int i = 0; i < unvisitedPoints.size(); i++)
			{
				double distance = Math.sqrt(Math.pow(Math.abs(point.x - unvisitedPoints.get(i).x), 2) + Math.pow(Math.abs(point.y - unvisitedPoints.get(i).y), 2));
				if (distance != 0 && distance < min)
				{
					min = distance;
					index = i;
				}
			}
			
			points.get(index).distance = min;
			Point closest = unvisitedPoints.get(index);
			unvisitedPoints.remove(index);
			
			for (Point p : unvisitedPoints)
			{
				double alternate = closest.distance + Math.sqrt(Math.pow(Math.abs(closest.x - p.x), 2) + Math.pow(Math.abs(closest.y - p.y), 2));
				
				if (alternate < Math.sqrt(Math.pow(Math.abs(point.x - p.x), 2) + Math.pow(Math.abs(point.y - p.y), 2)))
					points.get(index).distance = alternate;
			}
			
			distances.add(points.get(index).distance);
		}
		
		return distances;
	}
}

class Point
{
	int x;
	int y;
	double distance;
	boolean visited;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
		visited = false;
	}
}
