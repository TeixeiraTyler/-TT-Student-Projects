//Tyler Teixeira
//ty034938

#include "airPdata.h"
#include <stdio.h>
#include <stdlib.h>

#define MaxLineLength 512

//Prototypes:
void parse(FILE *f);
char* parseCSVLine(char*, int*);

void main(int argc, char *argv[])
{
	FILE *f;

	if (argc < 2)
	{
		printf("ERROR: missing file name\n");
		return;
	}

	f = fopen(argv[1], "r");

	if (f == NULL)
	{
		printf("ERROR: File '%s' was not found.\n", argv[1]);
		return;
	}

	parse(f);

	fclose(f);
}

//Function that is responsible for parsing the file and displaying output.
void parse(FILE *f)
{
	airPdata *data = (airPdata*)malloc(sizeof(airPdata));

	if (data == NULL)
	{
		printf("Could not allocate memory\n");
		return;
	}

	printf("FAA Site#  Short Name  Airport Name                              City                  ST  Latitude        Longitude        Tower\n");
	printf("=========  ==========  =================                         ==========            ==  ========        =========        =====\n");
	
	char line[MaxLineLength];
	int index;

	while (fgets(line, MaxLineLength, f) != NULL)
	{
		index = 0;

		data->siteNumber = parseCSVLine(line, &index);
		data->LocID = parseCSVLine(line, &index);
		data->fieldName = parseCSVLine(line, &index);
		data->city = parseCSVLine(line, &index);
		data->state = parseCSVLine(line, &index);
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));
		data->latitude = parseCSVLine(line, &index);
		data->longitude = parseCSVLine(line, &index);
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));

		char* ctTemp = parseCSVLine(line, &index);
		data->controlTower = *ctTemp;
		free(ctTemp);

		//print with modified format.
		printf("%-12s   %-4s     %-40s %-20s  %-2s  %s  %s    %c\n", data->siteNumber, data->LocID, data->fieldName,
			data->city, data->state, data->latitude, data->longitude, data->controlTower);

		//free up dynamically allocated space.
		free(data->siteNumber);
		free(data->LocID);
		free(data->fieldName);
		free(data->city);
		free(data->state);
		free(data->latitude);
		free(data->longitude);
	}

	free(data);
}

//Function that is called within 'parse' that reads a single line and fills airPdata struct.
char* parseCSVLine(char* buf, int* index)
{
	int start = *index;

	while (buf[*index] != NULL && buf[*index] != '\n')	//reads through a line.
	{
		if (buf[*index] == ',')
			break;

		*index += 1;
	}

	char* result = (char*)malloc(sizeof(char) * (*index - start) + 1);	// add 1 byte for the null terminator

	for (int i = start; i < *index; i++)
		result[i - start] = buf[i];

	result[*index - start] = 0;	// terminate the string with a null

	*index += 1;	// skip over the comma separator

	return result;
}
