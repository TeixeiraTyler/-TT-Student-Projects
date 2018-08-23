//Tyler Teixeira
//ty034938

#include "airPdata.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MaxLineLength 512
#define HYPHEN '-'

//Prototypes:
void parse(FILE *f);
char* parseCSVLine(char*, int*);
float sexag2decimal(char *degreeString);
void sortByLocID(airPdata *airports);
void sortByLatitude(airPdata *airports);

void main(int argc, char *argv[])
{
	FILE *f;

	if (argc < 2)
	{
		printf("ERROR: missing file name.\n");
		return;
	}
	else if (argc < 3)
	{
		printf("ERROR: sortParameter invalid or not found. Proper sortParameters: 'a' for alphabetical sort, 'n' for northbound exit.");
		return;
	}

	if (argv[2] != "a" || argv[2] != "A" || argv[2] != "n" || argv[2] != "N")
	{
		printf("ERROR: sortParameter invalid or not found. Proper sortParameters: 'a' for alphabetical sort, 'n' for northbound exit.");
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

	char line[MaxLineLength];
	int index;

	printf("code,name,city,lat,lon\n");

	fgets(line, MaxLineLength, f);		// read the header line and ignore it

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

		char *lat = parseCSVLine(line, &index);
		data->latitude = sexag2decimal(lat);
		free(lat);

		char *lon = parseCSVLine(line, &index);
		data->longitude = sexag2decimal(lon);
		free(lon);

		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));
		free(parseCSVLine(line, &index));

		char* ctTemp = parseCSVLine(line, &index);
		data->controlTower = *ctTemp;
		free(ctTemp);

		printf("%s,%s,%s,%.4f,%.4f\n", data->LocID, data->fieldName, data->city, data->latitude, data->longitude);

		//free up dynamically allocated space.
		free(data->siteNumber);
		free(data->LocID);
		free(data->fieldName);
		free(data->city);
		free(data->state);
	}

	free(data);
}

//Function that is called within 'parse' that reads a single line and fills airPdata struct.
char* parseCSVLine(char* buf, int* index)
{
	int start = *index;

	while (buf[*index] != 0 && buf[*index] != '\n')	//reads through a line.
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

float sexag2decimal(char *degreeString)
{
	if (degreeString == NULL)
		return 0.0;

	int size = 0;
	int index = 0;

	while (degreeString[index + size] != HYPHEN)
	{
		if (!isdigit(degreeString[index + size]))
			return 0;

		size++;
	}

	char degString[32];
	strncpy(degString, &degreeString[index], size);
	degString[size] = 0;

	index += size + 1;		// skip over degrees and hyphen
	size = 0;

	while (degreeString[index + size] != HYPHEN)
	{
		if (!isdigit(degreeString[index + size]))
			return 0;

		size++;
	}

	char minString[32];
	strncpy(minString, &degreeString[index], size);
	minString[size] = 0;

	index += size + 1;		// skip over minutes and hyphen
	size = 0;

	while (degreeString[index + size] != '.')
	{
		if (!isdigit(degreeString[index + size]))
			return 0;

		size++;
	}

	char secString[32];
	strncpy(secString, &degreeString[index], size);
	secString[size] = 0;

	index += size + 1;		// skip over seconds and period

	while (strchr("NSEW", degreeString[index + size]) == NULL)
	{
		if (!isdigit(degreeString[index + size]))
			return 0;

		size++;
	}

	char masString[32];
	strncpy(masString, &degreeString[index], size);
	masString[size] = 0;

	index += size;			// skip over mas

	char dir = degreeString[index];

	if (strchr("NSEW", dir) == NULL)
		return 0;

	float degrees = (float)atof(degString);
	float minutes = (float)atof(minString);
	float seconds = (float)atof(secString);
	float mas = (float)atof(masString);

	// check for fields that are out of range
	if (degrees < 0 || degrees > 180 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59 || mas < 0 || mas > 9999)
		return 0;

	float conversion = degrees + (minutes / 60.0f) + (seconds / 3600.0f);

	if (dir == 'S' || dir == 'W')
		conversion = -conversion;

	return conversion;
}

/*“Your statement that the program is entirely your own work and that you have
neither developed your code together with any another person, nor copied program
code from any other person, nor permitted your code to be copied or otherwise used
by any other person, nor have you copied, modified, or otherwise used program
code that you have found in any external source, including but not limited to,
online sources”*/