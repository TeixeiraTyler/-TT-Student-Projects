// Tyler Teixeira
// ty034938
// COP 3402
// HW3

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_CODE_LENGTH 500
#define MAX_SYMBOL_TABLE_SIZE 1000
#define REGISTER_0 0
#define REGISTER_1 0
#define NUM_REGISTERS 16

typedef enum
{
	constkind, varkind, prockind
} kind_type;

typedef enum
{
	nulsym = 1, identsym, numbersym, plussym, minussym,
	multsym, slashsym, oddsym, eqsym, neqsym, lessym, leqsym,
	gtrsym, geqsym, lparentsym, rparentsym, commasym, semicolonsym,
	periodsym, becomessym, beginsym, endsym, ifsym, thensym,
	whilesym, dosym, callsym, constsym, varsym, procsym, writesym,
	readsym, elsesym
} token_type;

char *tokenTypeNames[] =
{
	"n/a", "nulsym", "identsym", "numbersym", "plussym", "minussym",
	"multsym", "slashsym", "oddsym", "eqsym", "neqsym", "lessym", "leqsym",
	"gtrsym", "geqsym", "lparentsym", "rparentsym", "commasym", "semicolonsym",
	"periodsym", "becomessym", "beginsym", "endsym", "ifsym", "thensym",
	"whilesym", "dosym", "callsym", "constsym", "varsym", "procsym", "writesym",
	"readsym", "elsesym"
};

char *reservedWords[] = { "const", "var", "procedure", "call", "begin", "end", "if", "then", "else", "while", "do", "read","write", 0 };
token_type reservedTypes[] = { constsym, varsym, procsym, callsym, beginsym, endsym, ifsym, thensym, elsesym, whilesym, dosym, readsym, writesym };

typedef struct symbol
{
	kind_type kind;
	char name[10];
	int val;
	int level;
	int addr;
}symbol;

typedef struct instruction
{
	int op;		// Operation mem_store
	int r;		// Register
	int l;		// Lexicographical level
	int m;		// Number/address/register
}instruction;

typedef enum
{
	lit = 1, rtn, lod, sto, cal, inc, jmp, jpc, sio, neg,
	add, sub, mul, divop, odd, mod, eql, neq, lss, leq, gtr, geq
}ops;

char* opNames[] = { "N/A", "lit", "rtn", "lod", "sto", "cal", "inc", "jmp", "jpc", "sio", "neg", "add", "sub", "mul", "div", "odd", "mod", "eql", "neq", "lss", "leq", "gtr", "geq" };

// Global Variables
symbol symbolTable[MAX_SYMBOL_TABLE_SIZE];
instruction code[MAX_CODE_LENGTH];
int symbolCount = 0;
int symcount = 0;
int cx = 0;
int lexemes = 0;
int assemblyCode = 0;
int level = 1;
int space = 4;
FILE *fout, *fin;

// Prototypes
void parse();
int block(int token);
int statement(int token);
int condition(int token);
int expression(int token);
int term(int token);
int factor(int token);
int getIntToken();
void getNameToken(char *name);
void printError(int errorNo);
void storeConst(char *name, int val);
void storeVar(char *name);
void storeProc(char *name);
void printSymbolTable();
void emit(int op, int r, int l, int m);
int getRegister();
void freeRegister();
int getSymbolNoForVar(char* name);
int storeDynamicConst(int val);
int popStackReg();
void pushStackReg(int symbolNo);
int getNextVarReg();
int pushTempReg();
int popTempReg();
void printCode();

int main(int argc, char *argv[])
{
	for (int i = 1; i < argc; i++)
	{
		if (!strcmp(argv[i], "-l"))
			lexemes = 1;
		else if (!strcmp(argv[i], "-a"))
			assemblyCode = 1;
	}

	fin = fopen("hw2.txt", "r");
	if (fin == NULL)
	{
		printf("Input file not found\n");
		exit(1);
	}

	fout = fopen("hw3.txt", "w");
	if (fout == NULL)
	{
		printf("Output file could not be created.\n");
		fclose(fin);
		exit(2);
	}

	//fprintf(fout, "Token file:\n");
	//char c;

	//while ((c = fgetc(fin)) != EOF)
	//{
	//	fprintf(fout, "%c", c);
	//}

	//fprintf(fout, "\n\n");

	//rewind(fin);

	//printf("\n");

	if (lexemes)
	{
		char token[20];
		while (fscanf(fin, "%s", token) == 1)
		{
			int sym = atoi(token);

			printf("%s ", tokenTypeNames[sym]);

			if (sym == identsym || sym == numbersym)
			{
				fscanf(fin, "%s", token);
				printf("%s ", token);
			}
		}


		printf("\n\n");

		rewind(fin);
	}

	parse();
	//printSymbolTable();
	printCode();

	fclose(fin);
	fclose(fout);
	exit(0);
}

void parse()
{
	//emit(jmp, 0, 0);	// address will be patched later

	int token = getIntToken();

	token = block(token);

	if (token != periodsym)
		printError(9);

	emit(sio, 0, 0, 3);

	if (lexemes)
		printf("No errors, program is syntactically correct\n");
}

int block(int token)
{
	int tkn = token;
	char name[10];

	level++;
	space = 4;
	//int jmpaddr = emit(jmp, 0, 0, 0);

	if (tkn == constsym)
	{
		do
		{
			tkn = getIntToken();
			if (tkn != identsym)
				printError(4);

			getNameToken(name);

			tkn = getIntToken();
			if (tkn != eqsym)
				printError(3);

			tkn = getIntToken();
			if (tkn != numbersym)
				printError(2);

			tkn = getIntToken();

			storeConst(name, tkn);
			tkn = getIntToken();
		} while (tkn == commasym);

		if (tkn != semicolonsym)
			printError(5);

		tkn = getIntToken();
	}

	if (tkn == varsym)
	{
		do
		{
			space++;
			tkn = getIntToken();
			if (tkn != identsym)
				printError(4);

			getNameToken(name);
			storeVar(name);

			tkn = getIntToken();
		} while (tkn == commasym);
		
		if (tkn != semicolonsym)
			printError(5);

		tkn = getIntToken();
	}

	while (tkn == procsym)
	{
		tkn = getIntToken();
		if (tkn != identsym)
			printError(4);

		getNameToken(name);
		storeProc(name);

		tkn = getIntToken();
		if (tkn != semicolonsym)
			printError(4);

		tkn = getIntToken();
		tkn = block(tkn);

		if (tkn != semicolonsym)
			printError(4);

		tkn = getIntToken();
	}

	//code[jmpaddr].addr = NEXT_CODE_ADDR;
	//emit(inc, 0, 0, space);
	tkn = statement(tkn);
	//emit(rtn, 0, 0, 0);
	level--;
	return tkn;
}

int statement(int token)
{
	int tkn = token;
	char name[20];

	if (tkn == identsym)
	{
		getNameToken(name);
		int symbolNo = getSymbolNoForVar(name);
		tkn = getIntToken();

		if (tkn != becomessym)
			printError(13);

		tkn = getIntToken();
		tkn = expression(tkn);

		int resultRegister = popTempReg();

		emit(sto, resultRegister, 0, 0);
		emit(lod, symbolTable[symbolNo].addr, 0, 0);
	}

	else if (tkn == callsym)
	{
		tkn = getIntToken();
		if (tkn != identsym)
			printError(4);

		getNameToken(name);
		tkn = getIntToken();
	}

	else if (tkn == beginsym)
	{
		tkn = getIntToken();
		tkn = statement(tkn);

		while (tkn == semicolonsym)
		{
			tkn = getIntToken();
			tkn = statement(tkn);
		}

		//if (tkn == elsesym)
		//{
		//	do
		//	{
		//		tkn = getIntToken();
		//		tkn = statement(tkn);
		//	} while (tkn == semicolonsym);
		//}

		if (tkn != endsym)
			printError(26);

		tkn = getIntToken();
	}

	else if (tkn == ifsym)
	{
		tkn = getIntToken();
		tkn = condition(tkn);

		if (tkn != thensym)
			printError(16);

		tkn = getIntToken();

		int resultRegister = popTempReg();
		
		int tempcx = cx;
		emit(jpc, resultRegister, 0, 0);
		tkn = statement(tkn);

		code[tempcx].m = cx;
	}

	else if (tkn == whilesym)
	{
		//int cx1 = cx;
		tkn = getIntToken();
		tkn = condition(tkn);
		//int cx2 = cx;

		/*emit(jpc, 0, 0);*/

		if (tkn != dosym)
			printError(18);

		tkn = getIntToken();
		tkn = statement(tkn);

		/*emit(jmp, 0, cx1);
		code[cx2].m = cx;*/
	}
	else if (tkn == writesym)
	{
		tkn = getIntToken();

		if (tkn != identsym)
			printError(28);

		getNameToken(name);
		int symbolNo = getSymbolNoForVar(name);
		emit(sio, symbolTable[symbolNo].addr, 0, 1);

		tkn = getIntToken();
	}

	return tkn;
}

int condition(int token)
{
	int tkn = token;

	if (tkn == oddsym)
	{
		tkn = getIntToken();
		tkn = expression(tkn);
	}

	else
	{
		tkn = expression(tkn);

		if (tkn < eqsym || tkn > geqsym)	//if not relation
			printError(20);

		int conditionTkn = tkn;

		tkn = getIntToken();
		tkn = expression(tkn);

		int rightResultReg = popTempReg();
		int leftResultReg = popTempReg();
		int resultRegister = pushTempReg();

		int tempcx = cx;
		
		switch (conditionTkn)
		{
		case eqsym:
		case neqsym:
		case lessym:
		case leqsym:
			break;
		case gtrsym:
			emit(gtr, resultRegister, leftResultReg, rightResultReg);
			break;
		case geqsym:
			break;
		}
	}

	return tkn;
}

int expression(int token)
{
	int tkn = token;

	if (tkn == plussym || tkn == minussym)
		tkn = getIntToken();

	tkn = term(tkn);

	if (tkn != plussym && tkn != minussym)
	{
		int op = popStackReg();
		int resultRegister = pushTempReg();

		if (symbolTable[op].kind == constkind)
			emit(lit, resultRegister, 0, symbolTable[op].val);
		else
		{
			emit(sto, symbolTable[op].addr, 0, 0);
			emit(lod, resultRegister, 0, 0);
		}
	}

	while (tkn == plussym || tkn == minussym)
	{
		int addop = tkn;
		tkn = getIntToken();
		tkn = term(tkn);

		int op1 = popStackReg();
		int op2 = popStackReg();
		int rightRegister, leftRegister, resultRegister;

		resultRegister = pushTempReg();

		if (symbolTable[op1].kind == constkind)
		{
			rightRegister = pushTempReg();
			emit(lit, rightRegister, 0, symbolTable[op1].val);
		}
		else
			rightRegister = symbolTable[op1].addr;

		if (symbolTable[op2].kind == constkind)
		{
			leftRegister = pushTempReg();
			emit(lit, leftRegister, 0, symbolTable[op2].val);
		}
		else
			leftRegister = symbolTable[op2].addr;

		if (addop == plussym)
			emit(add, resultRegister, leftRegister, rightRegister);
		else
			emit(sub, resultRegister, leftRegister, rightRegister);

		if (symbolTable[op1].kind == constkind)
			popTempReg();

		if (symbolTable[op2].kind == constkind)
			popTempReg();
	}

	return tkn;
}

int term(int token)
{
	int tkn = token;
	//int mulop;

	tkn = factor(tkn);

	while (tkn == multsym || tkn == slashsym)
	{
		//mulop = tkn;
		tkn = getIntToken();
		tkn = factor(tkn);

		/*if (mulop == multsym)
			emit(mul, 0, tkn);
		else
			emit(div, 0, tkn);*/
	}

	return tkn;
}

int factor(int token)
{
	int tkn = token;
	char name[20];

	if (tkn == identsym)
	{
		getNameToken(name);
		pushStackReg(getSymbolNoForVar(name));
	}
	else if (tkn == numbersym)
	{
		tkn = getIntToken();
		pushStackReg(storeDynamicConst(tkn));
	}
	else if (tkn == lparentsym)
	{
		tkn = getIntToken();
		tkn = expression(tkn);

		if (tkn != rparentsym)
			printError(22);

		tkn = getIntToken();
	}
	else
		printError(23);

	tkn = getIntToken();
	return tkn;
}

int getIntToken()
{
	char buf[20];

	fscanf(fin, "%s", buf);

	if (isdigit(buf[0]))
		return atoi(buf);

	return -1;
}

void getNameToken(char *name)
{
	fscanf(fin, "%s", name);
}

void storeConst(char *name, int val)
{
	symbolTable[symbolCount].kind = constkind;
	strcpy(symbolTable[symbolCount].name, name);
	symbolTable[symbolCount].val = val;
	symbolCount++;
}

int storeDynamicConst(int val)
{
	char name[20];

	symbolTable[symbolCount].kind = constkind;
	sprintf(name, "const_%d", symbolCount);
	strcpy(symbolTable[symbolCount].name, name);
	symbolTable[symbolCount].val = val;

	return symbolCount++;
}

void storeVar(char *name)
{
	symbolTable[symbolCount].kind = varkind;
	strcpy(symbolTable[symbolCount].name, name);
	symbolTable[symbolCount].addr = getNextVarReg();
	symbolCount++;
}

void storeProc(char *name)
{
	symbolTable[symbolCount].kind = prockind;
	symbolTable[symbolCount].addr = cx;
	strcpy(symbolTable[symbolCount].name, name);
	symbolCount++;
}

void emit(int op, int r, int l, int m)
{
	if (cx > MAX_CODE_LENGTH)
		printError(25);
	else {
		code[cx].op = op;	// opcode
		code[cx].r = r;		// register
		code[cx].l = l;		// lexicographical level
		code[cx].m = m;		// modifier
		cx++;
	}
}

void printSymbolTable()
{
	fprintf(fout, "\nSymbolic representation: \n");

	for (int i = 0; i < symbolCount; i++)
	{
		if (symbolTable[i].kind == constkind)
			fprintf(fout, "const %s = %d\n", symbolTable[i].name, symbolTable[i].val);
		else if (symbolTable[i].kind == varkind)
			fprintf(fout, "var %s @ %d\n", symbolTable[i].name, symbolTable[i].addr);
		else if (symbolTable[i].kind == prockind)
			fprintf(fout, "proc %s\n", symbolTable[i].name);
	}

	printf("\n\n");
	fprintf(fout, "\n\n");
}

void printError(int errorNo)
{
	char* errorText[29] =
	{
		"Use = instead of : = .",
		"= must be followed by a number.",
		"Identifier must be followed by = .",
		"const, var, procedure must be followed by identifier.",
		"Semicolon or comma missing.",
		"Incorrect symbol after procedure declaration.",		// 5
		"Statement expected.",
		"Incorrect symbol after statement part in block.",
		"Period expected.",
		"Semicolon between statements missing.",
		"Undeclared identifier.",								// 10
		"Assignment to constant or procedure is not allowed.",
		"Assignment operator expected.",
		"call must be followed by an identifier.",
		"Call of a constant or variable is meaningless.",
		"then	 expected.",									// 15
		"Semicolon or } expected.",
		"do expected.",
		"Incorrect symbol following statement.",
		"Relational operator expected.",
		"Expression must not contain a procedure identifier.",	// 20
		"Right parenthesis missing.",
		"The preceding factor cannot begin with this symbol.",
		"An expression cannot begin with this symbol.",
		"This number is too large.",
		"'end' expected",										// 25
		"Ran out of registers!",
		"Temporary register stack underflow",
		"Identifier expected",
	};

	if (errorNo >= 1 && errorNo <= 29)
		printf("   ***** Error number %d, %s\n", errorNo, errorText[errorNo - 1]);
	else
		printf("   ***** Error number %d, MISSING ERROR TEXT!!!!\n", errorNo);

	exit(errorNo);
}

int varRegisterCount = 0;
int stackRegisterCount = 0;
int tempRegisterCount = 0;
int stack[NUM_REGISTERS];

int getNextVarReg()
{
	if (varRegisterCount == NUM_REGISTERS - 1)
		printError(27);

	return varRegisterCount++;
}

void pushStackReg(int symbolNo)
{
	stack[stackRegisterCount++] = symbolNo;
}

int popStackReg()
{
	return stack[--stackRegisterCount];
}

int pushTempReg()
{
	if (varRegisterCount + tempRegisterCount == NUM_REGISTERS - 1)
		printError(27);

	return varRegisterCount + tempRegisterCount++;
}

int popTempReg()
{
	if (tempRegisterCount <= 0)
		printError(28);

	tempRegisterCount--;
	return varRegisterCount + tempRegisterCount;
}

int getSymbolNoForVar(char* name)
{
	for (int i = 0; i < symbolCount; i++)
	{
		if (!strcmp(symbolTable[i].name, name) && symbolTable[i].kind == varkind)
			return i;
	}

	printError(10);		// this will exit

	return 0;
}

void printCode()
{
	if (assemblyCode)
	{
		printf("\nGenerated Code:  \n\n");
		printf("Op  r  l  m \n");
	}

	for (int i = 0; i < cx; i++)
	{
		if (assemblyCode)
			printf("%2d %2d %2d %2d \n", code[i].op, code[i].r, code[i].l, code[i].m);
		fprintf(fout, "%2d %2d %2d %2d \n", code[i].op, code[i].r, code[i].l, code[i].m);
	}

	if (assemblyCode)
		printf("\n\n");
}