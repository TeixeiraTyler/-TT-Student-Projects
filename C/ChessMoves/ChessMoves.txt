//Tyler Teixeira
//ty034938

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include "ChessMoves.h"

//prototypes:

void findRook(char** board, Move* move);
void findBishop(char** board, Move* move);
void findKnight(char** board, Move* move);
void findKing(char** board, Move* move);
void findQueen(char** board, Move* move);
void findPawn(char** board, Move* move);
void setBoard(char** board, int row, char col, char newPiece);

char getBoard(char** board, int row, char col);

char whitePromotion, blackPromotion;
bool whiteWins = false, blackWins = false, draw = false;

//creats initial chess board with starting pieces.
char **createChessBoard(void)
{
	char **board = (char**)malloc(sizeof(char*) * 8);
	for (int i = 0; i < 8; i++)
		board[i] = (char*)malloc(sizeof(char) * 8);
	board[0][0] = 'R';
	board[0][7] = 'R';
	board[7][0] = 'r';
	board[7][7] = 'r';
	board[0][1] = 'N';
	board[0][6] = 'N';
	board[7][1] = 'n';
	board[7][6] = 'n';
	board[0][2] = 'B';
	board[0][5] = 'B';
	board[7][2] = 'b';
	board[7][5] = 'b';
	board[0][3] = 'Q';
	board[7][3] = 'q';
	board[0][4] = 'K';
board[7][4] = 'k';
for (int i = 0; i < 8; i++)
	board[1][i] = 'P';
for (int i = 0; i < 8; i++)
	board[6][i] = 'p';
for (int i = 2; i < 6; i++)
	for (int j = 0; j < 8; j++)
		board[i][j] = ' ';

return board;
}

//free up dynamically allocated memory used for board.
char **destroyChessBoard(char **board)
{
	for (int i = 0; i < 8; i++)
		free(board[i]);
	free(board);
	return board;
}

//prints to console what board looks like at the time of function call.
void printChessBoard(char **board)
{
	printf("========\n");

	for (int i = 0; i < 8; i++)
	{
		for (int j = 0; j < 8; j++)
			printf("%c", board[i][j]);

		printf("\n");
	}

	printf("========\n\n");
}

//takes the game, parses the strings, and moves the pieces.
char **playTheGame(Game *g)
{
	char **board = createChessBoard();
	printChessBoard(board);

	for (int i = 0; i < g->numMoves; i++)
	{
		struct Move whiteMove;
		struct Move blackMove;
		parseNotationString(g->moves[i], &whiteMove, &blackMove);
		movePiece(board, &whiteMove);
		printChessBoard(board);

		// has black lost/resigned? if so, don't execute black move
		if (blackMove.from_loc.row != -1 || blackMove.to_loc.row != -1)
		{
			movePiece(board, &blackMove);
			printChessBoard(board);
		}
	}

	return board;
}

//parses input string with move.
void parseNotationString(char *str, Move *whiteMove, Move *blackMove)
{
	char* PieceSymbols = "PRNBQK";
	char* ColSymbols = "abcdefgh";
	char* RowSymbols = "12345678";

	// grab the move number from the front
	int moveNumber = atoi(str);
	char *remainder = strchr(str, ' ') + 1;

	whiteMove->color = WHITE;

	if (!strncmp(remainder, "O-O-O", 5))
	{
		whiteMove->piece = 'K';
		whiteMove->from_loc.row = 1;
		whiteMove->from_loc.col = 'e';
		whiteMove->to_loc.row = 1;
		whiteMove->to_loc.col = 'c';
		whiteMove->isCapture = 0;

		remainder += 5;
	}
	else if (!strncmp(remainder, "O-O", 3))
	{
		whiteMove->piece = 'K';
		whiteMove->from_loc.row = 1;
		whiteMove->from_loc.col = 'e';
		whiteMove->to_loc.row = 1;
		whiteMove->to_loc.col = 'g';
		whiteMove->isCapture = 0;

		remainder += 3;
	}
	else
	{
		// get piece if present
		if (strchr(PieceSymbols, *remainder))
			whiteMove->piece = *remainder++;
		else
			whiteMove->piece = 'P';

		// get first column if present
		if (strchr(ColSymbols, *remainder))
			whiteMove->from_loc.col = *remainder++;
		else
			whiteMove->from_loc.col = 'x';

		// get first row if present
		if (strchr(RowSymbols, *remainder))
			whiteMove->from_loc.row = *remainder++ - '0';
		else
			whiteMove->from_loc.row = -1;

		// ' ' means done with white move, there was not to_loc provided so swap the from_loc into the to_loc
		if (strchr(PieceSymbols, *remainder) || *remainder == ' ')
		{
			whiteMove->isCapture = false;
			whiteMove->to_loc.col = whiteMove->from_loc.col;
			whiteMove->to_loc.row = whiteMove->from_loc.row;
			whiteMove->from_loc.col = 'x';
			whiteMove->from_loc.row = -1;

			if (strchr(PieceSymbols, *remainder))
				whitePromotion = *remainder++;
			else if (!strncmp(remainder, "e.p.", 4))
				remainder += 4;	// for en passant, just skip over the e.p.
		}
		else if (*remainder == 'x')	// this is a capture, so to_locs must be present...grab them
		{
			remainder++;
			whiteMove->isCapture = true;
			whiteMove->to_loc.col = *remainder++;
			whiteMove->to_loc.row = *remainder++ - '0';

			if (strchr(PieceSymbols, *remainder))
				whitePromotion = *remainder++;
		}
	}

	remainder++;	// skip over the ' ' between white and black moves

	// check if black res
	if (!strncmp(remainder, "1-0", 3))
	{
		whiteWins = true;

		blackMove->from_loc.row = blackMove->to_loc.row = -1;
		blackMove->from_loc.col = blackMove->to_loc.col = 'x';

		return;
	}

	blackMove->color = BLACK;

	if (!strncmp(remainder, "O-O-O", 5))
	{
		blackMove->piece = 'K';
		blackMove->from_loc.row = 8;
		blackMove->from_loc.col = 'e';
		blackMove->to_loc.row = 1;
		blackMove->to_loc.col = 'c';
		blackMove->isCapture = 0;

		remainder += 5;
	}
	else if (!strncmp(remainder, "O-O", 3))
	{
		blackMove->piece = 'K';
		blackMove->from_loc.row = 8;
		blackMove->from_loc.col = 'e';
		blackMove->to_loc.row = 8;
		blackMove->to_loc.col = 'g';
		blackMove->isCapture = 0;

		remainder += 3;
	}
	else
	{
		// get piece if present
		if (strchr(PieceSymbols, *remainder))
			blackMove->piece = *remainder++;
		else
			blackMove->piece = 'P';

		// get first column if present
		if (strchr(ColSymbols, *remainder))
			blackMove->from_loc.col = *remainder++;
		else
			blackMove->from_loc.col = 'x';

		// get first row if present
		if (strchr(RowSymbols, *remainder))
			blackMove->from_loc.row = *remainder++ - '0';
		else
			blackMove->from_loc.row = -1;

		// done with string? this means that to_loc was not present, so swap from_loc into to_loc
		if (*remainder == NULL)
		{
			blackMove->to_loc.col = blackMove->from_loc.col;
			blackMove->to_loc.row = blackMove->from_loc.row;
			blackMove->from_loc.col = 'x';
			blackMove->from_loc.row = -1;
			blackMove->isCapture = 0;
		}
		else
		{
			// is a capture, so to_loc must be present...grab it
			if (*remainder == 'x')
			{
				remainder++;
				blackMove->isCapture = true;
			}
			else
				blackMove->isCapture = 0;

			blackMove->to_loc.col = *remainder++;
			blackMove->to_loc.row = *remainder++ - '0';

			if (strchr(PieceSymbols, *remainder))
				blackPromotion = *remainder++;
		}
	}

	if (!strncmp(remainder, "0-1", 3))
		blackWins = true;
	else if (!strncmp(remainder, "0.5-0.5", 7))
		draw = true;
}

//function to move the piece on the board.
void movePiece(char **board, Move *move)
{
	findFromLoc(board, move);

	char pieceToMove = getBoard(board, move->from_loc.row, move->from_loc.col);
	char pieceAtTarget = getBoard(board, move->to_loc.row, move->to_loc.col);

	setBoard(board, move->to_loc.row, move->to_loc.col, pieceToMove);
	setBoard(board, move->from_loc.row, move->from_loc.col, ' ');

	// check if it's a pawn that is being promoted
	if (move->piece == 'P')
	{
		if (move->color == WHITE)
		{
			if (move->to_loc.row == 8)
				setBoard(board, move->to_loc.row, move->to_loc.col, whitePromotion);
			else if (move->isCapture && pieceAtTarget == ' ')
			{
				// en passant!
				setBoard(board, move->to_loc.row - 1, move->to_loc.col, ' ');
			}
		}
		else if (move->color == BLACK)
		{
			if (move->to_loc.row == 1)
				setBoard(board, move->to_loc.row, move->to_loc.col, blackPromotion);
			else if (move->isCapture && pieceAtTarget == ' ')
			{
				// en passant!
				setBoard(board, move->to_loc.row + 1, move->to_loc.col, ' ');
			}
		}
	}
	else if (move->piece == 'K')
	{
		if (move->to_loc.row == 1 && move->to_loc.col == move->from_loc.col + 2)	// white, king side castle!
		{
			setBoard(board, 1, 'f', 'r');
			setBoard(board, 1, 'h', ' ');
		}
		else if (move->to_loc.row == 1 && move->to_loc.col == move->from_loc.col - 2)	// white, queen side castle!
		{
			setBoard(board, 1, 'd', 'r');
			setBoard(board, 1, 'a', ' ');
		}
		else if (move->to_loc.row == 8 && move->to_loc.col == move->from_loc.col - 2)	// black, queen side castle!
		{
			setBoard(board, 8, 'd', 'r');
			setBoard(board, 8, 'a', ' ');
		}
		else if (move->to_loc.row == 8 && move->to_loc.col == move->from_loc.col + 2)	// black, king side castle!
		{
			setBoard(board, 8, 'f', 'r');
			setBoard(board, 8, 'h', ' ');
		}
	}
}

//finds the from_loc given the to_loc.
void findFromLoc(char **board, Move *move)
{
	// check if the from_loc is already populated
	if (move->from_loc.col != 'x' && move->from_loc.row != -1)
		return;

	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	// the row is present, but the column is missing
	if (move->from_loc.row != -1 && move->from_loc.col == 'x')
	{
		for (char c = 'a'; c <= 'h'; c++)
			if (getBoard(board, move->from_loc.row, c) == coloredPiece)
			{
				move->from_loc.col = c;
				return;
			}

		// this is bad...there is no piece on the row!!!
	}

	// the column is present, but the row is missing
	if (move->from_loc.row == -1 && move->from_loc.col != 'x')
	{
		for (int r = 1; r <= 8; r++)
			if (getBoard(board, r, move->from_loc.col) == coloredPiece)
			{
				move->from_loc.row = r;
				return;
			}

		// this is bad...there is no piece on the column!!!
	}

	// both the row and column are missing
	if (move->piece == 'R')
		findRook(board, move);
	else if (move->piece == 'B')
		findBishop(board, move);
	else if (move->piece == 'P')
		findPawn(board, move);
	else if (move->piece == 'N')
		findKnight(board, move);
	else if (move->piece == 'K')
		findKing(board, move);
	else if (move->piece == 'Q')
		findQueen(board, move);
}

//return double 1-5 of difficulty.
double difficultyRating(void)
{
	return 4;
}

//return estimated number of hours spent.
double hoursSpent(void)
{
	return 30;
}

//Start of personal functions.																^^^^^Personal Functions^^^^^

//finds rook from_location.
void findRook(char **board, Move *move)
{
	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	for (int r = 1; r < move->to_loc.row; r++)		//from top.
	{
		if (getBoard(board, r, move->to_loc.col) == coloredPiece)
		{
			bool clear = true;

			for (int r1 = r + 1; r1 < move->to_loc.row; r1++)
				if (getBoard(board, r1, move->to_loc.col) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = r;
				move->from_loc.col = move->to_loc.col;
				return;
			}
		}
	}

	for (int r = move->to_loc.row + 1; r <= 8; r++)	//from bottom.
	{
		if (getBoard(board, r, move->to_loc.col) == coloredPiece)
		{
			bool clear = true;

			for (int r1 = move->to_loc.row + 1; r1 < r; r1++)
				if (getBoard(board, r1, move->to_loc.col) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = r;
				move->from_loc.col = move->to_loc.col;
				return;
			}
		}
	}

	for (char c = 'a'; c < move->to_loc.col; c++)		//from left.
	{
		if (getBoard(board, move->to_loc.row, c) == coloredPiece)
		{
			bool clear = true;

			for (char c1 = c + 1; c1 < move->to_loc.col; c1++)
				if (getBoard(board, move->to_loc.row, c1) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row;
				move->from_loc.col = c;
				return;
			}
		}
	}

	for (char c = move->to_loc.col + 1; c <= 'h'; c++)	//from right.
	{
		if (getBoard(board, move->to_loc.row, c) == coloredPiece)
		{
			bool clear = true;

			for (char c1 = move->to_loc.col + 1; c1 < c; c1++)
				if (getBoard(board, move->to_loc.row, c1) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row;
				move->from_loc.col = c;
				return;
			}
		}
	}
}

//finds bishop from_location.
void findBishop(char **board, Move *move)
{
	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	for (int x = 1; x < 8; x++)		//up-left.
	{
		if (getBoard(board, move->to_loc.row - x, move->to_loc.col - x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row - h, move->to_loc.col - h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row - x;
				move->from_loc.col = move->to_loc.col - x;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)		//up-right.
	{
		if (getBoard(board, move->to_loc.row - x, move->to_loc.col + x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row - h, move->to_loc.col + h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row - x;
				move->from_loc.col = move->to_loc.col + x;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)	//down-right.
	{
		if (getBoard(board, move->to_loc.row + x, move->to_loc.col + x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row + h, move->to_loc.col + h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row + x;
				move->from_loc.col = move->to_loc.col + x;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)	//down-left.
	{
		if (getBoard(board, move->to_loc.row + x, move->to_loc.col - x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row + h, move->to_loc.col - h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row + x;
				move->from_loc.col = move->to_loc.col - x;
				return;
			}
		}
	}
}

//finds pawn from_location
void findPawn(char **board, Move *move)
{
	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	if (move->color == WHITE)
	{
		if (move->isCapture)
		{
			if (getBoard(board, move->to_loc.row - 1, move->to_loc.col - 1) == coloredPiece)
			{
				move->from_loc.row = move->to_loc.row - 1;
				move->from_loc.col = move->to_loc.col - 1;
			}
			else	// must be the other diagonal.
			{
				move->from_loc.row = move->to_loc.row - 1;
				move->from_loc.col = move->to_loc.col + 1;
			}

			return;
		}

		move->from_loc.col = move->to_loc.col;

		if (getBoard(board, move->to_loc.row - 1, move->to_loc.col) == coloredPiece)
			move->from_loc.row = move->to_loc.row - 1;
		else
			move->from_loc.row = move->to_loc.row - 2;
	}

	if (move->color == BLACK)
	{
		if (move->isCapture)
		{
			if (getBoard(board, move->to_loc.row + 1, move->to_loc.col + 1) == coloredPiece)
			{
				move->from_loc.row = move->to_loc.row + 1;
				move->from_loc.col = move->to_loc.col + 1;
			}
			else	// must be other diagonal.
			{
				move->from_loc.row = move->to_loc.row + 1;
				move->from_loc.col = move->to_loc.col - 1;
			}

			return;
		}

		move->from_loc.col = move->to_loc.col;

		if (getBoard(board, move->to_loc.row + 1, move->to_loc.col) == coloredPiece)
			move->from_loc.row = move->to_loc.row + 1;
		else
			move->from_loc.row = move->to_loc.row + 2;
	}
}

//finds knight from_location
void findKnight(char **board, Move *move)
{
	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	if (getBoard(board, move->to_loc.row + 2, move->to_loc.col + 1) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row + 2;
		move->from_loc.col = move->to_loc.col + 1;
	}
	else if (getBoard(board, move->to_loc.row + 2, move->to_loc.col - 1) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row + 2;
		move->from_loc.col = move->to_loc.col - 1;
	}
	else if (getBoard(board, move->to_loc.row - 2, move->to_loc.col + 1) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row - 2;
		move->from_loc.col = move->to_loc.col + 1;
	}
	else if (getBoard(board, move->to_loc.row - 2, move->to_loc.col - 1) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row - 2;
		move->from_loc.col = move->to_loc.col - 1;
	}
	else if (getBoard(board, move->to_loc.row + 1, move->to_loc.col + 2) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row + 1;
		move->from_loc.col = move->to_loc.col + 2;
	}
	else if (getBoard(board, move->to_loc.row + 1, move->to_loc.col - 2) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row + 1;
		move->from_loc.col = move->to_loc.col - 2;
	}
	else if (getBoard(board, move->to_loc.row - 1, move->to_loc.col + 2) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row - 1;
		move->from_loc.col = move->to_loc.col + 2;
	}
	else if (getBoard(board, move->to_loc.row - 1, move->to_loc.col - 2) == coloredPiece)
	{
		move->from_loc.row = move->to_loc.row - 1;
		move->from_loc.col = move->to_loc.col - 2;
	}
}

//finds king from_location
void findKing(char **board, Move *move)
{
	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	for (int r = 1; r <= 8; r++)
		for (char c = 'a'; c <= 'h'; c++)
			if (getBoard(board, r, c) == coloredPiece)
			{
				move->from_loc.row = r;
				move->from_loc.col = c;
				return;
			}

	// this is bad...this means the king was not found! this should never happen
}

//finds queen from_location
void findQueen(char **board, Move *move)
{
	char coloredPiece = (move->color == WHITE ? tolower(move->piece) : toupper(move->piece));

	for (int r = 1; r < move->to_loc.row; r++)		//from top.
	{
		if (getBoard(board, r, move->to_loc.col) == coloredPiece)
		{
			bool clear = true;

			for (int r1 = r + 1; r1 < move->to_loc.row; r1++)
				if (getBoard(board, r1, move->to_loc.col) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = r;
				move->from_loc.col = move->to_loc.col;
				return;
			}
		}
	}

	for (int r = move->to_loc.row + 1; r <= 8; r++)	//from bottom.
	{
		if (getBoard(board, r, move->to_loc.col) == coloredPiece)
		{
			bool clear = true;

			for (int r1 = move->to_loc.row + 1; r1 < r; r1++)
				if (getBoard(board, r1, move->to_loc.col) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = r;
				move->from_loc.col = move->to_loc.col;
				return;
			}
		}
	}

	for (char c = 'a'; c < move->to_loc.col; c++)		//from left.
	{
		if (getBoard(board, move->to_loc.row, c) == coloredPiece)
		{
			bool clear = true;

			for (char c1 = c + 1; c1 < move->to_loc.col; c1++)
				if (getBoard(board, move->to_loc.row, c1) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row;
				move->from_loc.col = c;
				return;
			}
		}
	}

	for (char c = move->to_loc.col + 1; c <= 8; c++)	//from right.
	{
		if (getBoard(board, move->to_loc.row, c) == coloredPiece)
		{
			bool clear = true;

			for (char c1 = move->to_loc.col + 1; c1 < c; c1++)
				if (getBoard(board, move->to_loc.row, c1) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row;
				move->from_loc.col = c;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)		//up-left.
	{
		if (getBoard(board, move->to_loc.row - x, move->to_loc.col - x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row - h, move->to_loc.col - h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row - x;
				move->from_loc.col = move->to_loc.col - x;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)		//up-right.
	{
		if (getBoard(board, move->to_loc.row - x, move->to_loc.col + x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row - h, move->to_loc.col + h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row - x;
				move->from_loc.col = move->to_loc.col + x;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)	//down-right.
	{
		if (getBoard(board, move->to_loc.row + x, move->to_loc.col + x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row + h, move->to_loc.col + h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row + x;
				move->from_loc.col = move->to_loc.col + x;
				return;
			}
		}
	}

	for (int x = 1; x < 8; x++)	//down-left.
	{
		if (getBoard(board, move->to_loc.row + x, move->to_loc.col - x) == coloredPiece)
		{
			bool clear = true;

			for (int h = 1; h < x - 1; h++)
				if (getBoard(board, move->to_loc.row + h, move->to_loc.col - h) != ' ')
					clear = false;

			if (clear)
			{
				move->from_loc.row = move->to_loc.row + x;
				move->from_loc.col = move->to_loc.col - x;
				return;
			}
		}
	}
}

//personal function that grabs the board.
char getBoard(char** board, int row, char col)
{
	int r = 8 - row;
	int c = col - 'a';

	// test if the coords are off the board, if so, send the magic * character
	if (r < 0 || r > 7 || c < 0 || c > 7)
		return '*';

	return board[r][c];
}

//personal function that sets a modified board.
void setBoard(char** board, int row, char col, char newPiece)
{
	int r = 8 - row;
	int c = col - 'a';

	board[r][c] = newPiece;
}

