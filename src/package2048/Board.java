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
		int number = choose24();
		chooseBlank();
		placeTile(pos, number);
	}
	
	public int choose24() { 
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
	
	//when the player makes a move, call one of 4 methods depending on the direction
	public void move(String dir) { 
		switch(dir) {
		case "up":
			moveUp();
			break;
		case "down":
			moveDown();
			break;
		case "right":
			moveRight();
			break;
		case "left":
			moveLeft();
			break;
		default:
			System.out.println("Error in choosing direction to move!");
		}
	}
	
	public boolean isValid(String dir) {
		switch(dir) {
		case "up":
			return isValidUp();
		case "down":
			return isValidDown();
		case "right":
			return isValidRight();
		case "left":
			return isValidLeft();
		default:
			return false;
		}
	}

	//UP
	public void moveUp() { //called when someone presses the up key
		moves++;
		removeBlanksUp(); //remove blank spaces and shift up
		removeBlanksUp();
		doubleUp(); //consolidate any doubles
		removeBlanksUp(); //remove any more blank spaces and shift up again
	}
	
	public boolean isValidUp() { //check if moving up is a valid move
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
	}
	
	public void doubleUp() { //assumes all pairs are adjacent, because we've already called removeBlanks()
		//System.out.println("double up " + moves);
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
	}
	
	public void removeBlanksUp() {
		for (int col = 0; col < 4; col ++) {
			for (int row = 0; row < 3; row ++)
				if (BOARD[row][col] == 0)
					shiftUp(row,col);
		}
	}
	
	public void shiftUp(int row, int col) { //shift everything from row+1 to row, within a column
		for (int r = row; r < 3; r ++) 
			BOARD[r][col] = BOARD[r+1][col];
		BOARD[3][col] = 0; //set last one to 0 because all others have been shifted
	}
	
	
	//LEFT
	public void moveLeft() { //called when someone presses the left key
		moves ++;
		removeBlanksLeft(); //remove blank spaces and shift left
		removeBlanksLeft();
		doubleLeft(); //consolidate any doubles
		removeBlanksLeft(); //remove any more blank spaces and shift left again
	}
	
	public boolean isValidLeft() {
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
	}
	
	public void doubleLeft() { //assumes all pairs are adjacent, because we've already called removeBlanks()
		//System.out.println("double left " + moves);
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
	}
	
	public void removeBlanksLeft() {
		for (int row = 0; row < 4; row ++) 
			for (int col = 0; col < 3; col ++) 
				if (BOARD[row][col] == 0) 
					shiftLeft(row, col); //shift left all tiles in the column
	}
	
	public void shiftLeft(int row, int col) { //shift everything from col+1 to col, within a row
		for (int c = col; c < 3; c ++) 
			BOARD[row][c] = BOARD[row][c+1];
		BOARD[row][3] = 0; //set last one to 0 because all others have been shifted
	}
	
	//DOWN
	public void moveDown() { //called when someone presses the down key
		moves ++;
		removeBlanksDown(); //remove blank spaces and shift down
		removeBlanksDown();
		doubleDown(); //consolidate any doubles
		removeBlanksDown(); //remove any more blank spaces and shift down again
	}
	
	public boolean isValidDown() {
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
		
	}
	
	public void doubleDown() { //assumes all pairs are adjacent, because we've already called removeBlanks()
		//System.out.println("double down " + moves);
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
	}
	
	public void removeBlanksDown() {
		for (int col = 0; col < 4; col ++) 
			for (int row = 3; row > 0; row --) 
				if (BOARD[row][col] == 0) 
					shiftDown(row, col); //shift down all tiles in the column
	}
	
	public void shiftDown(int row, int col) { //shift everything from row-1 to row, within a column
		for (int r = row; r > 0; r --) 
			BOARD[r][col] = BOARD[r-1][col];
		BOARD[0][col] = 0; //set last one to 0 because all others have been shifted
	}
	
	//RIGHT 
	public void moveRight() { //called when someone presses the right key
		moves ++;
		removeBlanksRight();
		removeBlanksRight(); //remove blank spaces and shift right 
		doubleRight(); //consolidate any doubles
		removeBlanksRight(); //remove any more blank spaces and shift right again
	}
	
	public boolean isValidRight() {
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
	}
	
	public void doubleRight() { //assumes all pairs are adjacent, because we've already called removeBlanks()
		//System.out.println("double right " + moves);
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
	}
	
	public void removeBlanksRight() {
		for (int row = 0; row < 4; row ++) 
			for (int col = 3; col > 0; col --) 
				if (BOARD[row][col] == 0) 
					shiftRight(row, col); //shift right all tiles in the row
	}
	
	public void shiftRight(int row, int col) { //shift everything from col-1 to col, within a row
		for (int c = col; c > 0; c --) 
			BOARD[row][c] = BOARD[row][c-1];
		BOARD[row][0] = 0; //set last one to 0 because all others have been shifted
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
		if (isValidUp() || isValidDown() || isValidRight() || isValidLeft())
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
