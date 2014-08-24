package package2048;

import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class BottomBar extends JPanel {
	Board board;
	BoardGUI boardGUI;
	TopBar topbar;
	JButton resetButton = new JButton("New Game");
	JButton autoButton = new JButton("Random Play");
	JButton cupcakeButton = new JButton("Click for Cupcake Mode");
	JButton randomButton = new JButton("Randomize Colors");
	
	public BottomBar(Board inputBoard, BoardGUI inputBoardGUI, TopBar inputTopBar) {
		board = inputBoard;
		boardGUI = inputBoardGUI;
		topbar = inputTopBar;
		resetButton.setFont(resetButton.getFont().deriveFont(12.0f));
		autoButton.setFont(autoButton.getFont().deriveFont(12.0f));
		cupcakeButton.setFont(cupcakeButton.getFont().deriveFont(12.0f));
		randomButton.setFont(randomButton.getFont().deriveFont(12.0f));
		add(resetButton);
		add(cupcakeButton);
		add(randomButton);
		add(autoButton);
		initActions();
		//setBackground(Color.BLUE); //to-do: create a new color object to make the background 
	}
	
	public void initActions() {
		resetButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				board.newGame();
				boardGUI.updateLabels();
				topbar.reset(); //includes update() and then changing the message back to blank
				boardGUI.requestFocus(true);//always give focus back to the board
			}
		}
		);
		
		autoButton.addActionListener(new ActionListener() { //when pressed, execute random board moves until lose or win
			public void actionPerformed(ActionEvent e) {
				while (!board.checkWin() && !board.checkLose()) {
					board.randomMove();
					boardGUI.updateLabels();
					board.newRandomTile();
					boardGUI.updateLabels();
				}
				topbar.update();
			}
		}
		);
		
		cupcakeButton.addActionListener(new ActionListener() { //changes mode of game to normal or cupcake
			public void actionPerformed(ActionEvent e) {
				if (boardGUI.gameMode == "random") {
					//do nothing
				}else if (boardGUI.gameMode == "normal") {
					boardGUI.gameMode = "cupcake";
					boardGUI.updateLabels();
					cupcakeButton.setText("Click for Normal Mode");
				} else {
					boardGUI.gameMode = "normal";
					boardGUI.updateLabels();
					cupcakeButton.setText("Click for Cupcake Mode");
				}
				
				topbar.messageText.setText("  "); //clear the message if it's there 
				boardGUI.requestFocus(true);//always give focus back to the board
			}
		});
		
		randomButton.addActionListener(new ActionListener() { //turns on and off the random colors, but only in normal mode
			public void actionPerformed(ActionEvent e) {
				if (boardGUI.gameMode != "normal" && boardGUI.gameMode != "randomColor") {
					topbar.messageText.setText("Can only randomize colors in normal mode");
				} else if (boardGUI.gameMode == "randomColor" ) {
					boardGUI.gameMode = "normal";
					boardGUI.updateLabels(); 
				} else {
					boardGUI.gameMode = "randomColor";
					boardGUI.updateLabels();
				}
				boardGUI.requestFocus(true);
			}
		});
		
	}
}
