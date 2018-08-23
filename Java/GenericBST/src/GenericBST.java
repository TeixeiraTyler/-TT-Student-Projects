// Tyler Teixeira
// ty034938
// COP 3503, Fall 2017


import java.io.*;
import java.util.*;

// Node declaration for traversing BST
class Node<T>
{
	T data;
	Node<T> left, right;

	Node(T data)
	{
		this.data = data;
	}
}

// GenericBST extends Comparable interface as seen in class.
public class GenericBST<Type extends Comparable<Type>>
{
	private Node<Type> root;

	public void insert(Type data)
	{
		root = insert(root, data);
	}

	// Recursively moves down the BST searching for a location to place data of type Type.
	private Node<Type> insert(Node<Type> root, Type data)
	{
		if (root == null)
		{
			return new Node<Type>(data);
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}
		else
		{
			// Stylistically, I have this here to explicitly state that we are
			// disallowing insertion of duplicate values. This is unconventional
			// and a bit cheeky.
			;
		}

		return root;
	}

	public void delete(Type data)
	{
		root = delete(root, data);
	}

	// Recursively moves down the BST in search of the node with value 'data'. Then replaces that node with a child node if one is available, effectively deleting it.
	private Node<Type> delete(Node<Type> root, Type data)
	{
		if (root == null)
		{
			return null;
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			if (root.left == null && root.right == null)
			{
				return null;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else if (root.left == null)
			{
				return root.right;
			}
			// Else find max node of left tree to replace current node when 2 child nodes are present.
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private Type findMax(Node<Type> root)
	{
		// Loops down to the right until it reaches the right most value of the BST(max value).
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Returns true if the value is contained in the BST, false otherwise.
	public boolean contains(Type data)
	{
		return contains(root, data);
	}

	// Recursively search the BST until a value equal to data is found, or whole tree has been searched.
	private boolean contains(Node<Type> root, Type data)
	{
		if (root == null)
		{
			return false;
		}
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		}
	}

	//***********************************************SELF EXPLANATORY***********************************************\\
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	private void inorder(Node<Type> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	private void preorder(Node<Type> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	private void postorder(Node<Type> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
	
	public static double difficultyRating()
	{
		return 1.5;
	}
	
	public static double hoursSpent()
	{
		return 3;
	}
}
