import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

public class welcome
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        
        int inputs = scan.nextInt();
        
        while (inputs != 0) 
        { 
            HashMap<Character, HashSet<Character>> names = new HashMap<Character, HashSet<Character>>();
            for (int i = 0; i < inputs; i++) 
            {
                char first = scan.next().charAt(0);
                char last = scan.next().charAt(0); 
                if (!names.containsKey(last))
                    names.put(last, new HashSet<Character>());
                names.get(last).add(first);
            }
            
            Character[] lastSet = names.keySet().toArray(new Character[0]);
            int numLast = lastSet.length, min = numLast, nSets = (1 << numLast);
            
            for (int i = 0; i < nSets; i++) 
            { 
                HashSet<Character> firstUsed = new HashSet<Character>(); 
                int total = 0;
                for (int j = 0; j < numLast; j++)
                {
                    if ((i & (1 << j)) == 0) 
                        firstUsed.addAll(names.get(lastSet[j])); 
                    else 
                        total++; 
                }
                total += firstUsed.size(); 
                if (total < min) 
                	min = total;           
            }
            
            System.out.println(min);
            
            inputs = scan.nextInt();
        }
    }
}