//Tyler Teixeira
//ty034938

#include "KindredSpirits.h"
#include <stdio.h>
#include <stdlib.h>

node *makeReflection(node *root);
node *createNode(int data);
int isReflection(node *a, node  *b);
int kindredSpirits(node *a, node *b);
int *preorder_recursive(node *root, int *results, int *count);
int *postorder_recursive(node *root, int *results, int *count);
double difficultyRating(void);
double hoursSpent(void);



// Function that recursively navigates through 2 trees to check if they are reflections
int isReflection(node *a, node  *b)
{
	if (a == NULL && b == NULL)
		return 1;

	if (a == NULL || b == NULL)
		return 0;

	if (a->data == b->data)
		return ((isReflection(a->left, b->right) + isReflection(a->right, b->left))/2);

	return 0;
}

// Function that recursively creates a new tree that is a reflection of the tree passed to it
node *makeReflection(node *root)
{
	if (root == NULL)
		return root;

	node *newRoot = createNode(root->data);
	
	newRoot->left = makeReflection(root->right);
	newRoot->right = makeReflection(root->left);

	return newRoot;
}

// Function that compares the pre-order and post-order of 2 trees and determines if there is any alignment.
int kindredSpirits(node *a, node *b)
{
	if (a == NULL && b == NULL)
		return 1;

	if (a == NULL || b == NULL)
		return 0;

	int pre_count = 0;
	int* pre = NULL;
	pre = preorder_recursive(a, pre, &pre_count);					//record pre-order of tree 'a'
	
	int post_count = 0;
	int* post = NULL;
	post = postorder_recursive(b, post, &post_count);				//record post-order of tree 'b'
	
	int isKindredSpirit;

	if (pre_count == post_count)					//iteratively compare pre-order of tree 'a' with post-order of 'b'
	{
		isKindredSpirit = 1;

		for (int i = 0; i < pre_count; i++)
			if (pre[i] != post[i])
			{
				isKindredSpirit = 0;
				break;
			}
	}
	else
		isKindredSpirit = 0;

	if (isKindredSpirit != 1)						//iteratively compare pre-order of tree 'b' with post-order of 'a'
	{
		int pre_count = 0;
		int* pre = NULL;
		pre = preorder_recursive(b, pre, &pre_count);

		int post_count = 0;
		int* post = NULL;
		post = postorder_recursive(a, post, &post_count);

		if (pre_count == post_count)
		{
			isKindredSpirit = 1;

			for (int i = 0; i < pre_count; i++)
				if (pre[i] != post[i])
				{
					isKindredSpirit = 0;
					break;
				}
		}
	}

	free(pre);			//clean up
	free(post);			//clean up

	return isKindredSpirit;
}

double difficultyRating(void)
{
	return 1.5;
}

double hoursSpent(void)
{
	return 5;
}

//****** Start of personal functions *******

node *createNode(int data)									//code from class notes.
{
	node *n = (node*)calloc(1, sizeof(node));
	n->data = data;
	return n;
}

int* preorder_recursive(node *root, int* results, int* count)
{
	if (root == NULL)
		return results;

	if (results == NULL)
		results = (int*)malloc(sizeof(int));	// if null, alloc brand new memory block
	else
		results = (int*)realloc(results, sizeof(int) * (*count + 1));	// if not, make the memory block bigger by one int

	// stash the value and increment the counter
	results[*count] = root->data;
	*count += 1;

	// in pre order, we stash the value and then do the recursion
	results = preorder_recursive(root->left, results, count);
	results = preorder_recursive(root->right, results, count);

	return results;
}

int* postorder_recursive(node *root, int* results, int* count)
{
	if (root == NULL)
		return results;

	// in post order, we do the recursion first, then store the value
	results = postorder_recursive(root->left, results, count);
	results = postorder_recursive(root->right, results, count);

	if (results == NULL)
		results = (int*)malloc(sizeof(int));	// if null, alloc brand new memory block
	else
		results = (int*)realloc(results, sizeof(int) * (*count + 1));	// if not, make the memory block bigger by one int

	// stash the value and increment the counter
	results[*count] = root->data;
	*count += 1;

	return results;
}