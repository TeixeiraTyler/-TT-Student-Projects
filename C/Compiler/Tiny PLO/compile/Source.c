#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
	int lexemes = 0;
	int assemblyCode = 0;
	int vmTrace = 0;
	char inputFilename[100];

	for (int i = 1; i < argc; i++)
	{
		if (!strcmp(argv[i], "-l"))
			lexemes = 1;
		else if (!strcmp(argv[i], "-a"))
			assemblyCode = 1;
		else if (!strcmp(argv[i], "-v"))
			vmTrace = 1;
		else
			strcpy(inputFilename, argv[i]);
	}

	char cmd[100];
	sprintf(cmd, "./SS-HW2.exe %s%s", lexemes ? "-l " : "", inputFilename);

	int exitCode = system(cmd);

	if (exitCode == 0)
	{
		char cmdLine[100];
		strcpy(cmdLine, "./SS-HW3.exe");

		if (lexemes)
			sprintf(cmdLine + strlen(cmdLine), " -l");

		if (assemblyCode)
			sprintf(cmdLine + strlen(cmdLine), " -a");

		exitCode = system(cmdLine);

		if (exitCode == 0)
			system(vmTrace ? "./SS-HW1.exe -v" : "./SS-HW1.exe");
	}
}