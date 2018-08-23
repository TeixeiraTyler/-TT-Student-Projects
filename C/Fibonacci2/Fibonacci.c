//Tyler Teixeira
//ty034938

#include "Fibonacci.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <ctype.h>
#include <stdbool.h>
#include <limits.h>

HugeInteger *hugeAdd(HugeInteger *p, HugeInteger *q)
{
	if (p == NULL || q == NULL)
		return NULL;

	HugeInteger *result = (HugeInteger*)malloc(sizeof(HugeInteger));

	if (result == NULL)
		return NULL;

	//set result length equal to larger number's length + buffer.
	if (p->length < q->length)
		result->length = q->length;
	else
		result->length = p->length;

	result->digits = (int*)malloc(sizeof(int) * result->length);

	if (result->digits == NULL)
	{
		free(result);	// prevent memory leak
		return NULL;
	}

	int carry = 0;
	int sum;

	for (int i = 0; i < result->length; i++)			//loop through input arrays (digits) to add.
	{
		if (i > p->length - 1)
			sum = q->digits[i];
		else if (i > q->length - 1)
			sum = p->digits[i];
		else
			sum = p->digits[i] + q->digits[i];

		sum += carry;									// add the carry if present

		carry = (sum >= 10 ? 1 : 0);					//checks to see if a 'carry' is necessary.

		result->digits[i] = (sum % 10);					//sets value of result digit[i] to value of sum (digit 0-9).
	}

	if (carry > 0)
	{
		// if we need one extra array item for the last carry, reallocate the memory one bigger
		result->length++;
		result->digits = (int*)realloc(result->digits, sizeof(int) * result->length);

		if (result->digits == NULL)
		{
			hugeDestroyer(result);
			return NULL;
		}

		result->digits[result->length - 1] = 1;
	}

	return result;
}

HugeInteger *hugeDestroyer(HugeInteger *p)
{
	if (p == NULL)
		return NULL;

	free(p->digits);
	free(p);

	return NULL;
}

HugeInteger *parseString(char *str)
{
	if (str == NULL)                                                        //checks made for null pointers...
		return NULL;

	HugeInteger *newHI = (HugeInteger*)malloc(sizeof(HugeInteger));

	if (newHI == NULL)
		return NULL;

	char *stringToParse;

	if (str[0] == NULL)
		stringToParse = "0";
	else
		stringToParse = str;

	int len = strlen(stringToParse);

	newHI->length = len;
	newHI->digits = (int*)malloc(sizeof(int) * newHI->length);

	if (newHI->digits == NULL)                                  //freeing memory.
	{
		free(newHI);
		return NULL;
	}

	for (int i = 0; i < len; i++)
		newHI->digits[len - 1 - i] = stringToParse[i] - '0';

	return newHI;
}

// this could be implement like this... but that's too easy

//HugeInteger *parseInt(unsigned int n)
//{
//	char buf[16];
//	sprintf(buf, "%u", n);
//	return parseString(buf);
//}

HugeInteger *parseInt(unsigned int n)
{
	HugeInteger *newHI = (HugeInteger*)malloc(sizeof(HugeInteger));

	if (newHI == NULL)
		return NULL;

	char stringToParse[16];
	sprintf(stringToParse, "%u", n);

	int len = strlen(stringToParse);

	newHI->length = len;
	newHI->digits = (int*)malloc(sizeof(int) * newHI->length);

	if (newHI->digits == NULL)
	{
		free(newHI);
		return NULL;
	}

	for (int i = 0; i < len; i++)
		newHI->digits[len - 1 - i] = stringToParse[i] - '0';

	return newHI;
}

unsigned int *toUnsignedInt(HugeInteger *p)
{
	if (p == NULL)									//check if argument is null.
		return NULL;

	unsigned int *newUI = (unsigned int*)malloc(sizeof(unsigned int));

	if (newUI == NULL)								//check for malloc fail
		return NULL;

	unsigned int last;

	*newUI = last = 0;

	//loop through and sum the digits
	for (int i = p->length - 1; i >= 0; i--)
	{
		*newUI = *newUI * 10 + p->digits[i];

		// if new value is less that previous iteration, then it wrapped (i.e. overflow)
		if (*newUI < last)
		{
			free(newUI);
			return NULL;
		}

		last = *newUI;
	}

	return newUI;
}

HugeInteger *fib(int n)
{
	if (n == 0)
		return parseInt(0);

	if (n == 1)
		return parseInt(1);

	HugeInteger *last = parseInt(0);            //variables to keep track of fib.
	HugeInteger *current = parseInt(1);
	HugeInteger *sum = NULL;

	for (int i = 0; i < n - 1; i++)             //iterative fibonnaci function.
	{
		sum = hugeAdd(last, current);

		hugeDestroyer(last);
		last = current;
		current = sum;
	}

	hugeDestroyer(last);

	return current;
}

double difficultyRating(void)
{
	return 3;
}
double hoursSpent(void)
{
	return 10;
}
