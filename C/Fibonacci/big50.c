//Tyler Teixeira
//ty034938

#include "big50.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include <limits.h>

// Prototypes
void trim(char *str);
Integer50 *i50Creator();
Integer50 *loadHWConfigVariable(int seed);

Integer50 *big50Add(Integer50 *p, Integer50 *q)			//Adds two Integer50 arrays into a single Integer50 array.
{
	if (p == NULL || q == NULL)					// Check for nullptr.
		return NULL;

	Integer50 *result = i50Creator();			// Use personal function to create an Integer50.

	if (result == NULL)
		return NULL;

	int carry = 0;
	int sum;

	for (int i = 0; i < 50; i++)						//loop through input arrays (digits) to add.
	{
		sum = p->digits[i] + q->digits[i];

		sum += carry;							// add the carry if present.

		carry = (sum >= 10 ? 1 : 0);			//checks to see if a 'carry' is necessary.

		result->digits[i] = (sum % 10);			//sets value of result digit[i] to value of sum (digit 0-9).
	}

	return result;
}

Integer50 *i50Destroyer(Integer50 *p)					// If not given a nullptr, free int* and Integer50*.
{
	if (p != NULL)		
	{
		free(p->digits);
		free(p);
	}

	return NULL;
}

Integer50 *big50Destroyer(Integer50 *p)					// Extra destroyer function because it is named differently from pdf.
{
	return i50Destroyer(p);
}

// Personal Function
Integer50 *i50Creator()									// Creates and returns and Integer50, as well as checks for nullptr.
{
	Integer50 *p = (Integer50 *)malloc(sizeof(Integer50));

	if (p == NULL)
		return NULL;

	p->digits = (int *)malloc(sizeof(int) * 50);

	if (p->digits == NULL)
	{
		free(p);
		return NULL;
	}

	return p;
}
// End of Personal Function

Integer50 *parseString(char *str)						// Parse function to change a string to Integer50, stored in reverse order.
{
	if (str == NULL)
		return NULL;
		
	Integer50 *newHI = i50Creator();

	if (newHI == NULL)
		return NULL;

	char *stringToParse;

	if (str[0] == 0)							// Check if empty string is passed.
		stringToParse = "0";
	else
		stringToParse = str;

	int length = strlen(stringToParse);

	if (length > MAX50)
		length = MAX50;

	int index = 0;

	for (int i = length - 1; i >= 0; i--)		// Loop to fill Integer50 with str in reverse.
		newHI->digits[index++] = stringToParse[i] - '0';

	if (length < MAX50)
		fprintf(stderr, "String length not equal to 50 ");

	for (int j = index; j < MAX50; j++)			// if the length is < MAX50, then pre-pad digits with 0's
		newHI->digits[j] = 0;

	return newHI;
}

Integer50 *fibBig50(int n, Integer50 *first, Integer50 *second)		// Function that performs fibonacci sequence starting from initial positions given as arguments, n times.
{
	if (n == 0)
		return parseString("0");

	if (n == 1)
		return parseString("1");

	Integer50 *sum = NULL;
	Integer50 *a = first;
	Integer50 *b = second;

	for (int i = 0; i < n - 1; i++)				// Loop to perform fibonacci n times.
	{
		sum = big50Add(a, b);

		if (a != first && a != second)
			i50Destroyer(a);

		a = b;
		b = sum;
	}

	if (a != first && a != second)
		i50Destroyer(a);

	return b;
}

Integer50 *loadHwConfigVariable(int seed)				// Generates a random sequence of digits.
{
	Integer50 *i50 = i50Creator();

	if (i50 == NULL)
		return NULL;

	if (seed == 0)								// Generates 5 digits selected randomly, 5 times.
	{
		int index = 0;

		while (index < MAX50)
		{
			srand(0);

			for (int i = 0; i < 5; i++)
				i50->digits[index++] = rand() % 10;
		}
	}
	else                                        // Generates 50 random digits.
	{
		for (int i = 0; i < 50; i++)
			i50->digits[i] = rand() % 10;
	}

	return i50;
}

Integer50 *loadHWConfigVariable(int seed)				// Extra loadhw function because it is named differently from pdf.
{
	return loadHwConfigVariable(seed);
}

// Personal Function
void trim(char *str)									// Function that removes non-digits from char*.
{
	int len = strlen(str);

	for (int i = 0; i < len; i++)
		if (!isdigit(str[i]))
			str[i] = 0;
}
// End of Personal Function

Integer50 *loadCryptoVariable(char *cryptoVariableFilename)			// Loads a number sequence from a file and parses it.
{
	FILE *fp = fopen(cryptoVariableFilename, "r");

	if (fp == NULL)
		return NULL;

	char buf[51];
	fgets(buf, 51, fp);
	trim(buf);
	Integer50 *i50 = parseString(buf);

	return i50;
}

void big50Rating()													// Prints various info to STDERR.
{
	big50RatingStruct *rate = (big50RatingStruct *)malloc(sizeof(big50RatingStruct));
	
	rate->NID = "ty034938";
	rate->degreeOfDifficulty = 3;
	rate->duration = 12;

	fprintf(stderr, "%s;%f;%f;", rate->NID, rate->degreeOfDifficulty, rate->duration);
}