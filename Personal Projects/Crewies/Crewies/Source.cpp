// Tyler Teixeira
// Crewies

#include <stdio.h>
#include <string.h>

#define AWARDS 40

char *reserved[] = { "Philip", "Morgan", "Tyler", "Bobby", "Katie", "Allen", "Jared", "Alec", "Cory", 0 };

typedef struct people
{
	char name[10];
	char *votes[AWARDS];
}people;

void main()
{
	people nominees[10];
	char name[100];
	char ans[100];
	int current = 0;

	printf("To begin, type your name: ");
	scanf("%s", name);

	while (!check(name))
	{
		printf("Incorrect input. Example of correct input: Philip\n");
		scanf("%s", name);
	}

	// initialize nominee

	printf("\nFor each of the following awards, type the name of the intended recipient of your vote and press enter. You will be unable to vote for yourself.\nIf at any point you wish to abstain from voting on a particular award, submit 0 and continue.\nIf you wish to start over, submit -1.\nWhen you are ready, press enter...");
	
	for (int i = 0; i < AWARDS; i++)
	{
		askQuestion(i);
		scanf("%s", ans);

		while (!strcmp(ans, name))
		{
			printf("You cannot vote for yourself\n");
			scanf("%s", ans);
		}

		// store vote into struct
	}
}

bool check(char *name)
{
	for (int i = 0; reserved[i] != 0; i++)
		if (!strcmp(reserved[i], name))
			return true;

	return false;
}

void askQuestion(int i)
{
	char* questions[40] =
	{
		"Best quote",										// 0
		"Best supporting actor/actress",
		"Biggest dick",
		"Best hair",
		"Most pimpin",
		"Best",												// 5
		"Da real MVP",
		"Most likely to pass out after 1 beer",
		"First to die in a zombie apocalypse",
		"Most obnoxious laugh",
		"Gun nut",											// 10
		"Most likely to be part of a boy band",
		"Most likely to skip out on a social gathering",
		"Best friend",
		"Most likely to have alcohol poisoning",
		"Most likely to be a trophy husband/wife",			// 15
		"Most likely to lose a limb",
		"Most likely to die in a car accident",
		"Insomniac",
		"Stinkiest feet",
		"Best at board games",								// 20
		"Dankest meme",
		"Prettiest eyes",
		"Best to take home to your parents",
		"Best dressed",
		"Most nicest",										// 25
		"Most likely to be part of a pyramid scheme",
		"Funniest",
		"Least funniest",
		"Play of the game",
		"Most likely to be successful",						// 30
		"Most bootielicious",
		"Best dancer",
		"Worst dancer"
	};

	printf("Award %d of %d, %s\n", i+1, AWARDS, questions[i - 1]);
}