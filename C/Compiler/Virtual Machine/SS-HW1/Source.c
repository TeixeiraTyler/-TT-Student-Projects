// Tyler Teixemem_storea
// ty034938
// COP-3402 Systems Software
// HW1 - Vmem_storetual Machine

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUM_REGS 8
#define STACK_SIZE 2000
#define LEXI_LEVELS 3
#define MAX_CODE_LENGTH 500

// Instruction struct
typedef struct instruction {
	int op;		// Operation mem_store
	int r;		// Register
	int l;		// Lexicographical level
	int m;		// Number/address/register
}instruction;

// Prototypes
void parse();
void fetch();
void execute();
void show();
void printout();
int base(l, base);

// Global Variables
int running = 1;

int vmTrace = 0;
int regs[NUM_REGS];
int stack[STACK_SIZE];
int bp = 1, sp = 0, pc = 0;
int num_instructions = 0;
instruction ir;
instruction mem_store[MAX_CODE_LENGTH];

char* opNames[] = {"N/A", "lit", "rtn", "lod", "sto", "cal", "inc", "jmp", "jpc", "sio", "neg", "add", "sub", "mul", "div", "odd", "mod", "eql", "neq", "lss", "leq", "gtr", "geq"};
char output[2084];

FILE *fp;

int main(int argc, char *argv[])
{
	output[0] = 0;

	fp = fopen("hw3.txt", "r");

	// parse info into 'code' store (mem_store).
	parse();
	fclose(fp);

	if (argc > 1 && !strcmp(argv[1], "-v"))
		vmTrace = 1;

	if (vmTrace)
	{
		printout();
		printf("VM Execution Trace: \n\n");
		printf("                     pc  bp  sp\n");
	}

	while (running)
	{
		if (vmTrace)
			printf("%2d ", pc);	// Print line number

		fetch(mem_store);
		execute();

		if (vmTrace)
			show();				// Print contents of RF
	}

	printf("\nOutput:\n%s", output);

	exit(0);
}

void parse()
{
	num_instructions = 0;

	// Loop through file and store instructions into mem_store.
	while (fscanf(fp, "%d %d %d %d", &mem_store[num_instructions].op, &mem_store[num_instructions].r, &mem_store[num_instructions].l, &mem_store[num_instructions].m) == 4)
		num_instructions++;
}

void fetch()
{
	// Set IR equal to current mem_store instruction.
	ir.op = mem_store[pc].op;
	ir.r = mem_store[pc].r;
	ir.l = mem_store[pc].l;
	ir.m = mem_store[pc].m;
	pc++;
}

void printout()
{
	printf("Op Printout: \n");

	for (int i = 0; i < num_instructions; i++)
		printf("%2d %s %d %d %d \n", i, opNames[mem_store[i].op], mem_store[i].r, mem_store[i].l, mem_store[i].m);

	printf("\n");
}


void show()
{
	printf("     %2d  %2d  %2d     %d %d %d %d %d %d %d ", pc, bp, sp, stack[0], stack[1], stack[2], stack[3], stack[4], stack[5], stack[6]);

	for (int i = 7; i < sp; i += 4)
		printf("| %d %d %d %d ", stack[i], stack[i+1], stack[i+2], stack[i+3]);

	/*if (more - less >= 1)
	{
		printf("| %d, %d, %d, %d ", stack[7], stack[8], stack[9], stack[10]);
	}

	if (more - less >= 2)
	{
		printf("| %d, %d, %d, %d ", stack[11], stack[12], stack[13], stack[14]);
	}

	if (more - less >= 3)
	{
		printf("| %d, %d, %d, %d ", stack[15], stack[16], stack[17], stack[18]);
	}

	if (more - less >= 4)
	{
		printf("| %d, %d, %d, %d ", stack[19], stack[20], stack[21], stack[22]);
	}*/

	printf("\n RF: ");

	for (int i = 0; i < NUM_REGS; i++)
		printf("%s%d", i > 0 ? ", " : "", regs[i]);

	printf("\n");
}

int base(l, base)
{
	int bl;
	bl = base;

	while (l > 0)
	{
		bl = stack[bl + l];
		l--;
	}
	return bl;
}

void execute()
{
	if (vmTrace)
		printf("%s %2d %2d %2d", opNames[ir.op], ir.r, ir.l, ir.m);

	switch (ir.op)
	{
	case 0:
		/* END */
		printf("No instruction found");
		running = 0;
		break;
	case 1:
		/* LIT */
		regs[ir.r] = ir.m;
		break;
	case 2:
		/* RTN */
		sp = bp - 1;
		bp = stack[sp + 3];
		pc = stack[sp + 4];
		break;
	case 3:
		/* LOD */
		regs[ir.r] = stack[base(ir.l, bp) + ir.m];
		break;
	case 4:
		/* STO */
		stack[base(ir.l, bp) + ir.m] = regs[ir.r];
		break;
	case 5:
		/* CAL */
		stack[sp + 1] = 0;
		stack[sp + 2] = base(ir.l, bp);
		stack[sp + 3] = bp;
		stack[sp + 4] = pc;
		bp = sp + 1;
		pc = ir.m;
		break;
	case 6:
		/* INC */
		sp += ir.m;
		break;
	case 7:
		/* JMP */
		pc = ir.m;
		break;
	case 8:
		/* JPC */
		if (regs[ir.r] == 0)
			pc = ir.m;
		break;
	case 9:
		/* SIO */
		if (ir.m == 1)
			sprintf(&output[strlen(output)], "%d\n", regs[ir.r]);
		else if (ir.m == 2)
			scanf("%d", &regs[ir.r]);
		else
			running = 0;
		break;
	case 10:
		/* NEG */
		regs[ir.r] = -regs[ir.l];
		break;
	case 11:
		/* ADD */
		regs[ir.r] = regs[ir.l] + regs[ir.m];
		break;
	case 12:
		/* SUB */
		regs[ir.r] = regs[ir.l] - regs[ir.m];
		break;
	case 13:
		/* MUL */
		regs[ir.r] = regs[ir.l] * regs[ir.m];
		break;
	case 14:
		/* DIV */
		regs[ir.r] = regs[ir.l] / regs[ir.m];
		break;
	case 15:
		/* ODD */
		regs[ir.r] = regs[ir.r] % 2;
		break;
	case 16:
		/* MOD */
		regs[ir.r] = regs[ir.l] % regs[ir.m];
		break;
	case 17:
		/* EQL */
		if (regs[ir.l] == regs[ir.m])
			regs[ir.r] = 1;
		else
			regs[ir.r] = 0;
		break;
	case 18:
		/* NEQ*/
		if (regs[ir.l] != regs[ir.m])
			regs[ir.r] = 1;
		else
			regs[ir.r] = 0;
		break;
	case 19:
		/* LSS */
		if (regs[ir.l] < regs[ir.m])
			regs[ir.r] = 1;
		else
			regs[ir.r] = 0;
		break;
	case 20:
		/* LEQ */
		if (regs[ir.l] <= regs[ir.m])
			regs[ir.r] = 1;
		else
			regs[ir.r] = 0;
		break;
	case 21:
		/* GTR */
		if (regs[ir.l] > regs[ir.m])
			regs[ir.r] = 1;
		else
			regs[ir.r] = 0;
		break;
	case 22:
		/* GEQ */
		if (regs[ir.l] >= regs[ir.m])
			regs[ir.r] = 1;
		else
			regs[ir.r] = 0;
		break;
	}
}