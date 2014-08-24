package package2048;

public class Main {

	public static void main(String[] args) {
		Board testBoard = new Board(); //should create a new game and board
		GUI test_gui = new GUI(testBoard); //GUI takes in testboard, so all components take in testboard
		runGame(test_gui);
		
		//testing(testBoard);
		//testGame(testBoard);
	}
		
	public static void runGame(GUI gui) {
		gui.createAndShowGUI();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//random testing stuff 
	
	public static void testing(Board board) {
		//board.newRandomTiles(5);
		board.clear();
		System.out.println("\n\n");
		board.showBoard();
		/*board.setTile(0, 0, 2);
		board.setTile(0, 1, 2);
		board.setTile(0, 2, 2);
		board.setTile(0, 3, 2);
		*/
		
		/*board.newRandomTile();
		System.out.println("\n added random tile \n");
		board.showBoard();
		board.moveUp();
		System.out.println("\n moved up \n");
		board.showBoard();
		*/
		for (int i = 0; i < 100; i ++) {
			board.moveUp();
			System.out.println("\nmoved up\n");
			board.showBoard();
			if (board.checkLose())
				board.newGame();
			board.newRandomTile();
			System.out.println("\nrandom tile added\n");
			board.showBoard();
		}		
	}

}
