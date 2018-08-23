//Tyler Teixeira HW7
#include<stdio.h>
#include<stdlib.h>

struct node {
  int priority;
  char info[20];
  struct node *link;
} *front = NULL;

void insert(char item[20], int item_priority);
int delete();
void print();
int isEmpty();
void serve(char look[]);
void load();

int main()
{
    int choice, item_priority, key;
    char item[20], look[20];

    //load();

    while (1)
    {
        printf("1. Insert \n");
        printf("2. Delete \n");
        printf("3. Search \n");
        printf("4. Serve \n");
        printf("5. Print \n");
        printf("6. Save to file \n");
        printf("Type choice : ");
        scanf("%d", &choice);

        switch (choice) {
        case 1:
            printf("Type item name: ");
            scanf("%s", &item);
            printf("Its Priority: ");
            scanf("%d", &item_priority);
            insert(item, item_priority);
            system("cls");
            break;
        case 2:
            system("cls");
            printf("Deleted entry 1. ");
            delete();
            break;
        case 3:
            system("cls");
            printf("Enter a priority to search for(int): ");
            scanf("%d", &key);
            search(front, key);
            break;
        case 4:
            system("cls");
            printf("Which item are you looking for? ");
            scanf("%s", &look);
            serve(look);
            break;
        case 5:
            system("cls");
            print();
            break;
        case 6:
            save();
            printf("Data saved\n");
            break;
        default:
            printf("Invalid Choice\n");
        }
    }
    return 0;
}


void insert(char item[20], int item_priority)
{
    struct node *tmp, *p;

    tmp = (struct node *) malloc(sizeof(struct node));

    if (tmp == NULL) {
        printf("No Memory available\n");
        return;
    }

    strcpy(tmp->info, item);
    tmp->priority = item_priority;

    if (isEmpty() || item_priority < front->priority)
    {
        tmp->link = front;
        front = tmp;
    }
    else
    {
        p = front;
        while (p->link != NULL && p->link->priority <= item_priority)
            p = p->link;
        tmp->link = p->link;
        p->link = tmp;
    }
}

int delete()
{
    struct node *tmp;
    int item;

    if (isEmpty())
    {
        printf("Empty Queue\n");
        exit(1);
    }
    else
    {
        tmp = front;
        item = tmp->info;
        front = front->link;
        free(tmp);
    }

    return item;
}

void search(struct node *front, int key)
{
    while (front != NULL)
    {
        if(front->priority == key)
        {
            printf("%s \n", front->info);
        }
        front = front->link;
    }
    printf("End\n");
}

void serve(char look[])
{
    while (front != NULL)
    {
        if(front->info == look)
        {
            printf("%d \n", front->priority);
        }
        front = front->link;
    }
    printf("End\n");
}

int isEmpty()
{
    if (front == NULL)
        return 1;
    else
        return 0;
}

void print()
{
    struct node *ptr;
    ptr = front;
    if (isEmpty())
        printf("Empty \n");
    else {
        printf("Priority       Name\n");
        while (ptr != NULL)
        {
            printf("%5d        %5s\n", ptr->priority, ptr->info);
            ptr = ptr->link;
        }
    printf("\n");
    }
}

void load()
{
    int tempi;
    char tempc[20];
    char buf[1000];

    FILE* fin = fopen("California.txt", "r");
    while (fgets(buf, 1000, fin) != NULL)
    {
        fscanf(buf, "%d", "%s", &tempi, &tempc);
        insert(tempc, tempi);
    }
    fclose(fin);
}

void save()
{
    FILE* fout = fopen("California.txt", "w");

    while (front != NULL)
    {
        fprintf(fout,"%s\n",front->info);
        fprintf(fout,"%d\n",front->priority);
    }
    front = front->link;
    fclose(fout);
}
