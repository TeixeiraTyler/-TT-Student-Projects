//Tyler Teixeira
//ty034938

/* Format:

	Ask for input

	return prime factorization
	return fibonacci sequence location
	return unique arrangements of the digits of 'n'

*/

#include <math.h>
#include <stdio.h>
#include <stdlib.h>

//Prototypes
void find_primes(int n);
void is_fib(int n);
void arrange(int n);
void sort(int *array, int digits);
int factorial(int n);

void main(void)
{
	int n = 0;

	while (n != -1)																			// Loop until exit.
	{
		printf("Please enter a number (n >= 0)... Otherwise, type -1 to exit! ");
		scanf("%d", &n);

		if (n == -1)																		// Exit condition.
			return;

		printf("\n");

		//personal function calls:
		find_primes(n);
		is_fib(n);
		arrange(n);

		printf("\n");
	}
}

									// ********** PERSONAL FUNCTIONS ********** \\

void find_primes(int n)																		// Function that finds the prime factors of 'n'.
{
	int two = 0;
	int count;

	if (n < 2)																				// Conditional to see if number has any prime factors.
	{
		printf("'%d' does not contain any prime factors.\n", n);
		return;
	}

	printf("Prime factors: ");

	while (n % 2 == 0)																		// Count how many 2's.
	{
		two++;
		n = n / 2;
	}

	if (two != 0)
		printf("2^%d, ", two);

	for (int i = 3; i <= sqrt(n); i = i + 2)												// Count remaining prime factors.
	{
		count = 0;

		while (n % i == 0)
		{
			count++;
			n = n / i;
		}

		if (count != 0)
			printf("%d^%d, ", i, count);
	}

	if (n > 2)
		printf("%d^1", n);

	printf("\n");
}

void is_fib(int n)																			// Function that determines if 'n' is a fibonacci number. If so, which number in the sequence?
{
	int a = 0, b = 1, c = 1;
	int count = 2;

	if (n == 0)
		printf("'%d' is fibonacci(0)!\n", n); 
	else if (n == 1)
		printf("'%d' is fibonacci(1)!\n", n);
	else
	{
		while (c < n)																		// Perform fibonacci sequence to find if 'n' is in the sequence.
		{
			c = a + b;
			a = b;
			b = c;

			if (c == n)
			{
				printf("'%d' is fibonacci(%d)!\n", n, count);
				return;
			}
			count++;
		}
		printf("'%d' is a not a fibonacci number :(\n", n);
	}
}

void arrange(int n)																			// Function the counts how many different numbers can be created with 'n' digits.
{
	int temp = n;
	int digits = 0;
	int numerator = 1;
	int denominator = 1;
	int arrange;

	while (temp > 0)																		// Determine how many digits in number 'n'.
	{
		digits++;
		temp /= 10;
	}

	int dig = digits;
	for (dig; dig > 0; dig--)																// Find top number of permutation.
		numerator *= dig;
	
	dig = digits;
	int index = 0;
	int *digit_array = (int *)malloc(sizeof(int) * digits);
	int *repeat_array = (int *)malloc(sizeof(int) * digits);

	temp = n;
	for (dig; dig > 0; dig--)																// Loop to place digits into array.
	{
		digit_array[index] = temp / pow(10, dig - 1);
		temp -= digit_array[index] * pow(10, dig - 1);
		repeat_array[index] = 1;
		index++;
	}

	sort(digit_array, digits);
	int i = 0;

	while (i < digits - 1)																	// 2D loop to store and compare digits to find repeats.
		for (int j = i + 1; j < digits; j++)
			if (digit_array[i] != digit_array[j])
			{
				i = j;
				j = digits;
			}
			else
			{
				repeat_array[i] += 1;
				if (j == digits - 1)
					i = digits;
			}

	for (int i = 0; i < digits - 1; i++)													// Loop to find denominator of the permutation.
	{
		denominator *= factorial(repeat_array[i]);
	}

	//clean up dynamically allocated memory.
	free(digit_array);
	free(repeat_array);

	arrange = numerator / denominator;

	printf("You can arrange '%d' in %d different ways!\n", n, arrange);
}

int factorial(int n)																		// Recursive function to find factorial. Used in above function.
{
	if (n == 0)
		return 1;
	if (n == 1)
		return 1;
	return n * factorial(n - 1);
}

void sort(int *array, int digits)															// Bubble sort function used to count repetitions without overcounting.
{
	for (int i = 0; i < digits; i++)
		array[i] = array[i];

	for (int i = 0; i < digits - 1; i++)													// Bubble bigger numbers up.
		for (int j = 0; j < digits - i - 1; j++)
			if (array[j] > array[j + 1])
			{
				int swap = array[j];
				array[j] = array[j + 1];
				array[j + 1] = swap;
			}
}