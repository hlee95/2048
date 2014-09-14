package package2048;

import java.util.Random;

public class Board {
	int BOARD [][] = new int[4][4]; //4x4 grid 
	int [] pos = new int[2]; //position to place new random tiles 
	int winningNum = 2048; //the number needed to win
	int score = 0; //score updated every time a number is consolidated and doubled 
	int moves = 0; //number of moves updated every time a moveX() method is called (which only happens when the move is valid)
	boolean validMove = true;
	String [] directions = {"up", "down", "right", "left"}; //used to call a random move
	
	public Board() { 
		newGame();
	}
	
	public void newGame() { //set all to blank, then call two random tiles 
		clear();
		newRandomTiles(2);
		validMove = true;
		score = 0;
		moves = 0;
	}
	
	public void clear() {
		for (int row = 0; row < 4; row++) 
			for (int col = 0; col < 4; col++) 
				BOARD[row][col] = 0; // let 0 represent a blank; during showBoard it will be blank
	}
	
	public void newRandomTiles(int repeat) { //sets a set number of new random tiles
		for (int count = 0; count < repeat; count ++) 
			newRandomTile();
	}
	
	public void newRandomTile() { //sets one of the non-blank tiles to either a 2 or 4, with 50% probability
		int number = choose2or4();
		chooseBlank();
		placeTile(pos, number);
	}
	
	public int choose2or4() { 
		int choice = randInt(0,7); //randInt is inclusive
		if (choice != 0) 
			return 2;
		else
			return 4;
	}
	
	public void chooseBlank() { //chooses a random spot out of the non-filled ones to place the next 2 or 4 tile
		int [] rowArray = new int[16];
		int [] colArray = new int[16];
		int count = 0;
		for (int row = 0; row < 4; row++) 
			for (int col = 0; col < 4; col++) 
				if (BOARD[row][col] == 0) {
					rowArray[count] = row;
					colArray[count] = col;
					count ++;
				}
		int choice = randInt(0, count-1); //prevents index out of range, must subtract 1, because randInt() is inclusive
		pos[0] = rowArray[choice]; //set pos, a variable that newRandomTile() uses
		pos[1] = colArray[choice];
	}
	
	public void placeTile(int[] position, int num) { //sets the number at the desired position
		BOARD[position[0]][position[1]] = num;
	}
	
	public void setTile(int r, int c, int n) { //explicitly set tiles, mostly useful for testing scenarios
		BOARD[r][c] = n;
	}
	
	public void showBoard() { // prints out the 4x4 array representing the BOARD to the console 
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (BOARD[row][col] == 0) {
					System.out.print("-  ");
				} else {
					System.out.print(BOARD[row][col]+ " ");
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	public void randomMove() {
		int dir = randInt(0,3);
		while (!isValid(directions[dir])) {
			dir = randInt(0,3);
		}
			move(directions[dir]);
	}

	//validate that a move is legal before executing it 
	public boolean isValid(String dir) {
		switch(dir) {
		
		case "up":
			//if it results in any doubling
			for (int col = 0; col < 4; col ++) { 
				for (int row = 1; row < 4; row ++)
					if ((BOARD[row][col] == BOARD[row-1][col]) && (BOARD[row][col] != 0)) {
						return true;
					}
			}
			//if there's whitespace between two tiles;
			for (int col = 0; col < 4; col ++)
				for (int row = 0; row < 4; row ++)
					if (BOARD[row][col] == 0)
						for (int r = row+1; r < 4; r ++)
							if (BOARD[r][col] != 0) {
								return true;
							}
			return false;
			
		case "down":
			//check for doubles 
			for (int col = 0; col < 4; col ++) //for each column
				for (int row = 3; row > 0; row --)
					if ((BOARD[row][col] == BOARD[row-1][col]) && (BOARD[row][col] != 0)) 
						return true;
			//check for extra whitespace
			for (int col = 0; col < 4; col ++) 
				for (int row = 3; row > 0; row --) 
					if (BOARD[row][col] == 0) 
						for (int r = row-1; r > -1; r --) 
							if (BOARD[r][col] != 0)
								return true;
			return false;
			
		case "left":
			//if anything doubled
			for (int row = 0; row < 4; row ++)
				for (int col = 1; col < 4; col ++) 
					if ((BOARD[row][col] == BOARD[row][col-1]) && (BOARD[row][col] != 0))
						return true;
			//if there's whitespace between two tiles
			for (int row = 0; row < 4; row ++) 
				for (int col = 0; col < 3; col ++) 
					if (BOARD[row][col] == 0) 
						for (int c = col+1; c < 4; c ++) 
							if (BOARD[row][c] != 0)
								return true;
			return false;
			
		case "right":
			//check if there are any possible doubles
			for (int row = 0; row < 4; row ++) 
				for (int col = 3; col > 0; col --)
					if ((BOARD[row][col] == BOARD[row][col-1]) && (BOARD[row][col] != 0))
						return true;
			//check if any extra whitespace
			for (int row = 0; row < 4; row ++) 
				for (int col = 3; col > 0; col --) 
					if (BOARD[row][col] == 0) 
						for (int c = col-1; c > -1; c --)
							if (BOARD[row][c] != 0)
								return true;
			return false;
			
		default:
			return false;
		}
	}
	
	//when the player makes a move, call one of 4 methods depending on the direction
	public void move(String dir) {
		//remove blanks twice
		removeBlanks(dir);
		removeBlanks(dir);
		//consolidate doubles 
		doubleWhenPossible(dir);
		//remove blanks again
		removeBlanks(dir);			
	}
	
	//removes any blank spaces in between tiles, in the direction specified by String dir 
	public void removeBlanks(String dir) {
		switch(dir) {
		case "up":
			for (int col = 0; col < 4; col ++) 
				for (int row = 0; row < 3; row ++)
					if (BOARD[row][col] == 0)
						shift(dir, row,col); //shift up all tiles in the column
			break;
		case "down":
			for (int col = 0; col < 4; col ++) 
				for (int row = 3; row > 0; row --) 
					if (BOARD[row][col] == 0) 
						shift(dir, row, col); //shift down all tiles in the column
			break;
		case "left":
			for (int row = 0; row < 4; row ++) 
				for (int col = 0; col < 3; col ++) 
					if (BOARD[row][col] == 0) 
						shift(dir, row, col); //shift left all tiles in the row
			break;
		case "right":
			for (int row = 0; row < 4; row ++) 
				for (int col = 3; col > 0; col --) 
					if (BOARD[row][col] == 0) 
						shift(dir, row, col); //shift right all tiles in the row
			break;
		default:
			System.out.println("remove blanks twice, input must be up, down, left or right");				
		}
	}
	
	//shifts tiles one space in the direction specified by String dir
	public void shift(String dir, int row, int col) {
		switch (dir) {
		case "up":
			for (int r = row; r < 3; r ++) 
				BOARD[r][col] = BOARD[r+1][col];
			BOARD[3][col] = 0; //set last one to 0 because all others have been shifted
			break;
		case "down":
			for (int r = row; r > 0; r --) 
				BOARD[r][col] = BOARD[r-1][col];
			BOARD[0][col] = 0; //set last one to 0 because all others have been shifted
			break;
		case "left":
			for (int c = col; c < 3; c ++) 
				BOARD[row][c] = BOARD[row][c+1];
			BOARD[row][3] = 0; //set last one to 0 because all others have been shifted
			break;
		case "right":
			for (int c = col; c > 0; c --) 
				BOARD[row][c] = BOARD[row][c-1];
			BOARD[row][0] = 0; //set last one to 0 because all others have been shifted
			break;
		default:
			System.out.println("in shift method, input needs to be up, down, left or right");
		}
	}
	//consolidates any two identical numbers in the direction specified by String dir 
	public void doubleWhenPossible(String dir) {
		switch(dir) {
		case "up":
			for (int col = 0; col < 4; col ++) { //for each column
				int row = 1;
				while (row < 4) {
					if ((BOARD[row][col] == BOARD[row-1][col]) && (BOARD[row][col] != 0)) {
						score += dub(BOARD[row][col]);
						BOARD[row-1][col] = dub(BOARD[row][col]); //consolidate into the space further in specified direction
						BOARD[row][col] = 0; //clear the other space
						row ++; //skip a row so that we don't double consolidate and cause chain reactions
					}
					row ++;
				}
			}
			break;
		case "down":
			for (int col = 0; col < 4; col ++) { //for each column
				int row = 3;
				while (row > 0) {
					if ((BOARD[row][col] == BOARD[row-1][col]) && (BOARD[row][col] != 0)) {
						score += dub(BOARD[row][col]);
						BOARD[row][col] = dub(BOARD[row][col]); //consolidate into the space further in specified direction
						BOARD[row-1][col] = 0; //clear the other space
						row --; //skip a row so that we don't double consolidate and cause chain reactions
					}
					row --;
				}
			}
			break;
		case "left":
			for (int row = 0; row < 4; row ++) { //for each row
				int col = 1;
				while (col < 4) {
					if ((BOARD[row][col] == BOARD[row][col-1]) && (BOARD[row][col] != 0)) {
						score += dub(BOARD[row][col]);
						BOARD[row][col-1] = dub(BOARD[row][col]); //consolidate into the space further in specified direction
						BOARD[row][col] = 0; //clear the other space
						col ++; //skip a column so that we don't double consolidate and cause chain reactions
					}
					col ++;
				}
			}
			break;
		case "right":
			for (int row = 0; row < 4; row ++) { //for each row
				int col = 3;
				while (col > 0) {
					if ((BOARD[row][col] == BOARD[row][col-1]) && (BOARD[row][col] != 0)) {
						score += dub(BOARD[row][col]);
						BOARD[row][col] = dub(BOARD[row][col]); //consolidate into the space further in specified direction
						BOARD[row][col-1] = 0; //clear the other space
						col --; //skip a row so that we don't double consolidate and cause chain reactions
					}
					col --;
				}
			}
			break;
		default:
			System.out.println("doubling, input must be up, down, left or right.");
		}
	}
	
	//conditions for game to end 
	public boolean checkWin() { //returns true if player has reached 2048
		for (int row = 0; row < 4; row ++) 
			for (int col = 0; col < 4; col ++) 
				if (BOARD[row][col] == winningNum) 
					return true;
		return false;
	}
	
	public boolean checkLose() { //returns true if player has lost
		//if no valid moves, you've lost (because it always checks for wins before checking for losses)
		if (isValid("up") || isValid("down") || isValid("left") || isValid("right"))
			return false;
		return true;
	}
	
	//helper math methods 
	public static int randInt(int min, int max) { //returns pseudo-random integer between min and max, inclusive
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	static int log(int x, int base) {
	    return (int) (Math.log(x) / Math.log(base));
	}
	
	public int dub(int num) {
		return num * 2;
	}
}