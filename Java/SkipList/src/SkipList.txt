// Tyler Teixeira
// ty034938

import java.util.*;

class Node<T extends Comparable<T>>
{
	T dataValue;
	ArrayList<Node<T>> nextNodes;
	
	Node(int height)
	{
		nextNodes = new ArrayList<Node<T>>();
		
		for (int i = 0; i < height; i++)
			nextNodes.add(null);
	}
	
	Node(T data, int height)
	{
		this(height);
		this.dataValue = data;
	}
	
	public T value()
	{
		return dataValue;
	}
	
	public int height()
	{
		return nextNodes.size();
	}
	
	public Node<T> next(int level)
	{
		// check to make sure level is within bounds
		if (level < 0 || level >= height())
			return null;
		
		return nextNodes.get(level);
	}
	
	public void setNext(int level, Node<T> node)
	{
		// set reference to next node at given level
		nextNodes.set(level, node); 
	}
	
	public void grow()
	{
		nextNodes.add(null);
	}
	
	public void trim(int height)
	{
		// keep removing the last (top) item in the list until the correct size
		while (nextNodes.size() > height)
			nextNodes.remove(nextNodes.size() - 1);
	}
}

public class SkipList<T extends Comparable<T>>
{
	Node<T> headNode;
	int size = 0;
	Random rng = new Random();

	SkipList()
	{
		headNode = new Node<T>(1);
	}
	
	SkipList(int height)
	{
		// creates a headNode for a skip list with a height of at least 1
		headNode = new Node<T>(Math.max(1, height));
	}
	
	// personal function for testing
	public void printout()
	{
		System.out.printf("SkipList, height=%d, size=%d\n", height(), size());
		
		for (int lvl = height() - 1; lvl >= 0; lvl--)
		{
			for (Node<T> node = headNode.next(0); node != null; node = node.next(0))
			{
				if (node.height() > lvl)
					System.out.printf("%3s ", node.value().toString());
				else
					System.out.printf("    ");
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
	
	public int size()
	{
		return size;
	}
	
	public int height()
	{
		return headNode.height();
	}
	
	public Node<T> head()
	{
		return headNode;
	}
	
	public void insert(T data)
	{
		Node<T> newNode = new Node<T>(data, 1);
		
		// conditional for if the skip list is empty
		if (size == 0)
		{
			headNode.setNext(0, newNode);
			size++;
		}
		else
		{
			Node<T> currentNode = headNode;
			
			for (int lvl = currentNode.height() - 1; lvl >= 0; )
			{
				Node<T> nextNode = currentNode.next(lvl);
				
				if (nextNode != null && data.compareTo(nextNode.value()) > 0)
					currentNode = nextNode;
				else
					lvl--;
			}
			
			// insertion!!!
			Node<T> temp = currentNode.next(0);
			currentNode.setNext(0, newNode);
			newNode.setNext(0, temp);
			
			size++;

			int growTimes = getMaxHeight(size()) - 1;
			
			while (growTimes-- > 0)
			{
				if (rng.nextInt(2) == 1)
					newNode.grow();
				else
					break;
			}
			
			patchupNewNode(newNode);
		}
	}
	
	public void insert(T data, int height)
	{
		Node<T> newNode = new Node<T>(data, 1);
		
		Node<T> currentNode = headNode;
		
		for (int lvl = currentNode.height() - 1; lvl >= 0;)
		{
			Node<T> nextNode = currentNode.next(lvl);
			
			if (nextNode != null && data.compareTo(nextNode.value()) > 0)
				currentNode = nextNode;
			else
				lvl--;
		}
		
		// insertion!!!
		Node<T> temp = currentNode.next(0);
		currentNode.setNext(0, newNode);
		newNode.setNext(0, temp);

		while (newNode.height() < height)
			newNode.grow();

		patchupNewNode(newNode);
		
		size++;
	}
	
	//function to connect references on varying heights of newNode
	private void patchupNewNode(Node<T> newNode)
	{
		while (newNode.height() > headNode.height())
			headNode.grow();
		
		Node<T> currentNode = headNode;
		
		for (int lvl = headNode.height() - 1; lvl > 0;)
		{
			Node<T> nextNode = currentNode.next(lvl);
			
			if (nextNode != null && newNode.value().compareTo(nextNode.value()) > 0)
				currentNode = nextNode;
		
			else if (lvl < newNode.height())	// a check for if we are on a level that we know contains newNode
			{
				if (nextNode == null || newNode.value().compareTo(nextNode.value()) < 0)	// if true, patch!
				{
					currentNode.setNext(lvl,  newNode);
					newNode.setNext(lvl, nextNode);
				}
				lvl--;
			}
			
			else
				lvl--;
		}
		
		growSkipList();
	}
	
	public void delete(T data)
	{
		Node<T> currentNode = headNode;
	
		for (int lvl = currentNode.height() - 1; lvl >= 0; )
		{
			Node<T> nextNode = currentNode.next(lvl);
		
			if (nextNode != null)
			{
				int compare = data.compareTo(nextNode.value());

				if (compare > 0)
				{
					currentNode = nextNode;
					continue;
				}
				else if (data.compareTo(nextNode.value()) == 0 && lvl == 0)
				{
					// delete!
					deleteNode(nextNode);
					size--;
					trimSkipList();
					return;
				}
			}
			
			lvl--;
		}
	}

	// function that patches deletion
	private void deleteNode(Node<T> node)
	{
		for (int lvl = 0; lvl < node.height(); lvl++)
		{
			Node<T> currentNode = headNode;
	
			while (true)
			{
				Node<T> nextNode = currentNode.next(lvl);
				
				// is this the node we're deleting?
				if (nextNode == node)
				{
					// patch references over the node to be removed
					currentNode.setNext(lvl, nextNode.next(lvl));
					break;
				}
				
				// no, so move to the next node on this level
				currentNode = nextNode;
			}
		}
	}
	
	public boolean contains(T data)
	{
		return (get(data) != null);
	}
	
	public Node<T> get(T data)
	{
		Node<T> currentNode = headNode;
		
		for (int lvl = currentNode.height() - 1; lvl >= 0; )
		{
			Node<T> nextNode = currentNode.next(lvl);
		
			if (nextNode != null)
			{
				int compare = data.compareTo(nextNode.value());
				
				if (compare == 0)
					return nextNode;
				
				if (compare > 0)
				{
					currentNode = nextNode;
					continue;
				}
			}
			
			lvl--;
		}
		
		return null;
	}
	
	private static int getMaxHeight(int n)
	{
		// calculate log2(n)
		return Math.max(1, (int)Math.ceil(Math.log10(n) / Math.log10(2)));
	}

	private void growSkipList()
	{
		while (height() < getMaxHeight(size))
			headNode.grow();
	}
	
	private void trimSkipList()
	{
		int max = getMaxHeight(size);
		
		while (height() > max)
		{
			Node<T> currentNode = headNode;
			
			int trimLevel = height() - 1;
			
			while (currentNode != null)
			{
				Node<T> nextNode = currentNode.next(trimLevel);
				
				currentNode.trim(trimLevel);
				
				currentNode = nextNode;
			}
		}
	}
	
	public static double difficultyRating()
	{
		return 4.5;
	}
	
	public static double hoursSpent()
	{
		return 18;
	}
}
