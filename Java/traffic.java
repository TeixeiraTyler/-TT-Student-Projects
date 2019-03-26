import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class traffic
{
	public static void main(String args[]) throws IOException
	{
		//Scanner scan = new Scanner(new File("traffic.in"));
		Scanner scan = new Scanner(System.in);
		
		int miles = scan.nextInt();
		int[] prior = new int[2];
		int[] post = new int[2];
		int[] delta = new int[2];
		int[] current = new int[2];
		int[] temp = new int[2];
		boolean pre = false;
		int count = 0;
		
		for (int m = 0; m < miles; m++)
		{
			String event = scan.next();
			int bottom = scan.nextInt();
			int top = scan.nextInt();
			
			if (event.equals("on"))
			{
				if (pre)
				{
					pre = false;
					prior[0] = current[0] + temp[0];
					prior[1] = current[1] + temp[1];
				}
				
				if (count < 1)
				{
					temp[0] -= bottom;
					temp[1] -= top;
				}
				
				if (current[0] == 0 && current[1] == 0)
				{
					
				}
				else
				{
					delta[0] += bottom;
					delta[1] += top;
				}
			}
			else if (event.equals("off"))
			{
				if (pre)
				{
					pre = false;
					prior[0] = current[0] + temp[0];
					prior[1] = current[1] + temp[1];
				}
				
				if (count < 1)
				{
					temp[0] += top;
					temp[1] += bottom;
				}
				
				if (current[0] == 0 && current[1] == 0)
				{
					
				}
				else
				{
					delta[0] -= top;
					delta[1] -= bottom;
				}
			}
			else if (event.equals("none"))
			{
				count++;
				if (count == 1)
					pre = true;
				
				if (current[0] == 0 && current[1] == 0)
				{
					current[0] = bottom;
					current[1] = top;
				}
				else
				{
					if (bottom > current[0])
						current[0] = bottom;
					if (top < current[1])
						current[1] = top;
				}
			}
		}
		
		post[0] = current[0] + delta[0];
		post[1] = current[1] + delta[1];
		
		//PrintWriter output = new PrintWriter(new FileWriter("traffic.out"));
		//output.print(prior[0] + " " + prior[1] + "\n");
		//output.print(post[0] + " " + post[1]);
		//output.close();
		
		System.out.println(prior[0] + " " + prior[1]);
		System.out.println(post[0] + " " + post[1]);
	}
}