// Tyler Teixeira
// ty034938
// COP 3402
// HW2

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef enum
{
	nulsym = 1, identsym, numbersym, plussym, minussym,
	multsym, slashsym, oddsym, eqsym, neqsym, lessym, leqsym,
	gtrsym, geqsym, lparentsym, rparentsym, commasym, semicolonsym,
	periodsym, becomessym, beginsym, endsym, ifsym, thensym,
	whilesym, dosym, callsym, constsym, varsym, procsym, writesym,
	readsym, elsesym
} token_type;

char *reservedWords[] = { "const", "var", "procedure", "call", "begin", "end", "if", "then", "else", "while", "do", "read","write", 0 };
token_type reservedTypes[] = { constsym, varsym, procsym, callsym, beginsym, endsym, ifsym, thensym, elsesym, whilesym, dosym, readsym, writesym };

typedef struct lexeme
{
	char id[12];
	token_type type;
}lexeme;

#define MAXLEX 1000

// Global Variables
lexeme lex[MAXLEX];
int numLex = 0;
int lexemes = 0;

// Prototypes
void printTable(lexeme *lex, FILE *fout);
void printList(lexeme *lex, FILE *fout);
int parse(FILE *fin);
void storeLex(token_type type, char *buf);

int main(int argc, char *argv[])
{
	char inputFilename[100];

	for (int i = 1; i < argc; i++)
	{
		if (!strcmp(argv[i], "-l"))
			lexemes = 1;
		else
			strcpy(inputFilename, argv[i]);
	}

	FILE *fin = fopen(inputFilename, "r");
	if (fin == NULL)
	{
		printf("Input file not found\n");
		exit(1);
	}

	FILE *fout = fopen("hw2.txt", "w");
	if (fout == NULL)
	{
		printf("Output file could not be created.\n");
		fclose(fin);
		exit(1);
	}

	// Prints to output the contents of input file.
	if (lexemes)
	{
		printf("Source Program:\n\n");
		char c;

		while ((c = fgetc(fin)) != EOF)
			printf("%c", c);

		printf("\n");

		rewind(fin);
	}

	int exitCode = parse(fin);

	// If no errors are found, proceed. Otherwise, report error.
	if (exitCode == 0)
	{
		printTable(lex, fout);
		printList(lex, fout);
	}
	else
		fprintf(fout, "An error was found. Check console for more details.\n");

	fclose(fin);
	fclose(fout);

	exit(exitCode);
}

int parse(FILE *fin)
{
	char c;
	char buf[100];
	int bufIndex = 0;
	int scanNumber = 0;
	int scanIdent = 0;
	int scan2Char = 0;
	int inAComment = 0;

	while ((c = fgetc(fin)) != EOF)
	{
		if (inAComment)
		{
			if (c == '*' && fgetc(fin) == '/')
				inAComment = 0;

			continue;
		}

		if (!scanNumber && !scanIdent && isspace(c))		// ignore whitespace between tokens
			continue;

		if (scan2Char)
		{
			scan2Char = 0;

			if (buf[0] == ':')
			{
				if (c != '=')
				{
					printf("Invalid becomes operator. ':' was not followed by '='.\n");
					return 3;
				}

				buf[bufIndex++] = c;
				buf[bufIndex] = 0;

				storeLex(becomessym, buf);
				bufIndex = 0;
				continue;
			}
			else if (buf[0] == '<')
			{
				if (c == '=')
				{
					buf[bufIndex++] = c;
					buf[bufIndex] = 0;
					storeLex(leqsym, buf);
					bufIndex = 0;
					continue;
				}
				else if (c == '>')
				{
					buf[bufIndex++] = c;
					buf[bufIndex] = 0;
					storeLex(neqsym, buf);
					bufIndex = 0;
					continue;
				}
				else
				{
					buf[bufIndex] = 0;
					storeLex(lessym, buf);
					bufIndex = 0;
				}
			}
			else if (buf[0] == '>')
			{
				if (c == '=')
				{
					buf[bufIndex++] = c;
					buf[bufIndex] = 0;
					storeLex(geqsym, buf);
					bufIndex = 0;
					continue;
				}
				else
				{
					buf[bufIndex] = 0;
					storeLex(gtrsym, buf);
					bufIndex = 0;
				}
			}
			else if (buf[0] == '*')
			{
				buf[bufIndex] = 0;
				storeLex(multsym, buf);
				bufIndex = 0;
			}
			else if (buf[0] == '/')
			{
				if (c == '*')
				{
					inAComment = 1;
					bufIndex = 0;
					continue;
				}

				buf[bufIndex] = 0;
				storeLex(slashsym, buf);
				bufIndex = 0;
			}
		}
		else if (scanNumber)
		{
			if (isdigit(c))
			{
				buf[bufIndex++] = c;
				continue;
			}
			else if (!isalpha(c))
			{
				scanNumber = 0;
				buf[bufIndex] = 0;

				if (strlen(buf) > 5)
				{
					printf("Number is too long '%s'.\n", buf);
					return 1;
				}

				storeLex(numbersym, buf);
				bufIndex = 0;
			}
			else
			{
				printf("Variables must start with a letter '%s%c'.", buf, c);
				return 5;
			}
		}
		else if (scanIdent)
		{
			if (isalnum(c))
			{
				buf[bufIndex++] = c;
				continue;
			}
			else
			{
				scanIdent = 0;
				buf[bufIndex] = 0;

				int i;

				for (i = 0; reservedWords[i] != 0; i++)
				{
					if (!strcmp(reservedWords[i], buf))
					{
						storeLex(reservedTypes[i], buf);
						break;
					}
				}

				if (reservedWords[i] == 0)
				{
					if (strlen(buf) > 11)
					{
						printf("Identifier too long '%s'.\n", buf);
						return 2;
					}

					if (!strcmp(buf, "odd"))
						storeLex(oddsym, buf);
					else if(!strcmp(buf, "nul") || !strcmp(buf, "null"))		// Unclear what nulsym text should be.
						storeLex(nulsym, buf);
					else
						storeLex(identsym, buf);
				}

				bufIndex = 0;
			}
		}

		if (isdigit(c))
		{
			buf[bufIndex++] = c;
			scanNumber = 1;
		}
		else if (isalpha(c))
		{
			buf[bufIndex++] = c;
			scanIdent = 1;
		}
		else if (c == ',')
			storeLex(commasym, ",");
		else if (c == ';')
			storeLex(semicolonsym, ";");
		else if (c == ':' || c == '<' || c == '>' || c == '/' || c == '*')
		{
			buf[bufIndex++] = c;
			scan2Char = 1;
		}
		else if (c == '+')
			storeLex(plussym, "+");
		else if (c == '.')
			storeLex(periodsym, ".");
		else if (c == '(')
			storeLex(lparentsym, "(");
		else if (c == ')')
			storeLex(rparentsym, ")");
		else if (c == '=')
			storeLex(eqsym, "=");
		else if (c == '-')
			storeLex(minussym, "-");
		else if (!isspace(c))
		{
			printf("Invalid symbol found '%c'.\n", c);
			return 4;
		}
	}

	return 0;
}

// Function for ease of use.
void storeLex(token_type type, char *buf)
{
	lex[numLex].type = type;
	strcpy(lex[numLex].id, buf);
	numLex++;
}

// Prints lexemes and token type in a 2 column table to both file and console.
void printTable(lexeme *lex, FILE *fout)
{
	/*printf("Lexeme Table:\n");
	printf("lexeme   token type\n");

	fprintf(fout, "Lexeme Table:\n");
	fprintf(fout, "lexeme   token type\n");

	for (int i = 0; i < numLex; i++)
	{
		printf("%-10s %d\n", lex[i].id, lex[i].type);
		fprintf(fout, "%-10s %d\n", lex[i].id, lex[i].type);
	}*/
}

// Prints a lexeme list to both file and console.
void printList(lexeme *lex, FILE *fout)
{
	//printf("Lexeme List:\n");
	//fprintf(fout, "Lexeme List:\n");

	for (int i = 0; i < numLex; i++)
	{
		if (lexemes)
			printf("%d ", lex[i].type);
		fprintf(fout, "%d ", lex[i].type);

		if (lex[i].type == identsym || lex[i].type == numbersym)
		{
			if (lexemes)
				printf("%s ", lex[i].id);
			fprintf(fout, "%s ", lex[i].id);
		}
	}

	if (lexemes)
		printf("\n\n");
}