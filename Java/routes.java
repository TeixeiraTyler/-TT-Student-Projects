import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class routes
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		
		for (int c = 0; c < cases; c++)
		{
			int numintersections = scan.nextInt();
			int numroads = scan.nextInt();
			int housing = scan.nextInt();
			
			int x = numintersections + 1;
			ArrayList<Road>[] roads = new ArrayList[x];
	        
	        int[] times = new int[x];
	        for(int i = 0; i < x; i++)
	        {
	            times[i] = Integer.MAX_VALUE;
	            roads[i] = new ArrayList<Road>();
	        }
	        times[1] = 0;
	        
	        PriorityQueue<Road> queue = new PriorityQueue<Road>();
	        
	        for(int r = 0; r < numroads; r++)
	        {
	            int start = scan.nextInt();
	            int end = scan.nextInt();
	            int time = scan.nextInt();
	            
	            if(start == 1)
	            {
	               queue.offer(new Road(start, end, time));
	               roads[end].add(new Road(end, start, time));
	            }
	            else if(end == 1)
	            {
	               queue.offer(new Road(end, start, time));
	               roads[start].add(new Road(start, end, time));
	            }
	            else
	            {
	               roads[end].add(new Road(end, start, time));
	               roads[start].add(new Road(start, end, time));
	            }
	        }
	         
	        while (queue.peek() != null) 
	        {
	        	Road rd = queue.poll();
	            
	            if(times[rd.start] + rd.time < times[rd.end]) 
	            {
	               times[rd.end] = rd.time;
	               for(Road r : roads[rd.end]) 
	               {
	                  r.time += times[rd.end];
	                  queue.offer(r);
	               }
	            } 
	        }

	        for(int h = 0; h < housing; h++)
	        {
	            int house = scan.nextInt();
	            System.out.println(times[house]);
	        }
		}
	}
}

class Road implements Comparable<Road>
{
	int time;
	int start;
	int end;
	
	public Road(int start, int end, int time)
	{
		this.time = time;
		this.start = start;
		this.end = end;
	}
	
	public int compareTo(Road b)
	{
	      return (this.time - b.time);
	}
}