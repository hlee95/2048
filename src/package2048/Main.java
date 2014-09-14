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
}
