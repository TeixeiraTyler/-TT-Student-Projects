import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class news 
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		int cases = scan.nextInt();
		
		for (int c = 0; c < cases; c++)
		{
			int numemployees = scan.nextInt();
			
			if (numemployees == 0)
			{
				System.out.println("0");
				continue;
			}
			
			Employee root = new Employee(0);
			
			for (int e = 1; e < numemployees; e++)
			{
				int sup = scan.nextInt();
				Employee newEmployee = new Employee(e);
				addSub(root, newEmployee, sup);
			}
			
			int minutes = solve(root);
			
			System.out.println(minutes);
		}
	}
	
	public static void addSub(Employee root, Employee e, int supervisor)
	{
		if (root.label == supervisor)
			root.subordinates.add(e);
		
		for (Employee sub : root.subordinates)
			addSub(sub, e, supervisor);
	}
	
	public static int solve(Employee root)
	{
		ArrayList<Integer> calls = new ArrayList<Integer>();
		
		for (Employee sub : root.subordinates)
		{
			int minutes = solve(sub);
			calls.add(minutes);
		}
		
		if (calls.size() > 0)
		{
			Collections.sort(calls, Collections.reverseOrder());
			int max = 0;
			for (int i = 1; i <= calls.size(); i++)
			{
				int value = calls.get(i-1) + i;
				if (value > max)
					max = value;
			}
			return max;
		}
		else
			return 0;
	}
}

class Employee
{
	int label;
	ArrayList<Employee> subordinates;
	
	public Employee(int label)
	{
		this.label = label;
		subordinates = new ArrayList<Employee>();
	}
}