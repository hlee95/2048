package package2048;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class TopBar extends JPanel{ //default layout is already FlowLayout
	
	Board board;
	JLabel scoreLabel = new JLabel("          Score: ");
	JLabel scoreNumber = new JLabel("0");
	JLabel moveLabel = new JLabel("Moves: ");
	JLabel moveNumber = new JLabel("0");
	JLabel messageLabel = new JLabel("       ");
	JLabel messageText =  new JLabel(" ");
	
	public TopBar(Board inputBoard) {
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		board = inputBoard;
		messageText.setOpaque(true);
		//set fonts to 16 for the labels on the top bar
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(16.0f));
		scoreNumber.setFont(scoreNumber.getFont().deriveFont(16.0f));
		moveLabel.setFont(moveLabel.getFont().deriveFont(16.0f));
		moveNumber.setFont(moveNumber.getFont().deriveFont(16.0f));
		messageLabel.setFont(messageLabel.getFont().deriveFont(20.0f));
		messageText.setFont(messageText.getFont().deriveFont(20.0f));
		//add the components in the correct order 
		add(moveLabel);
		add(moveNumber);
		add(scoreLabel);
		add(scoreNumber);
		add(messageLabel);
		add(messageText);

	}
	
	public void reset() {
		update();
		messageText.setText(" ");
		messageText.setBackground(Color.WHITE);
	}
	
	public void update() {
		setScore();
		setMoves();
		checkWinLose();
	}
	
	public void setScore() {
		scoreNumber.setText(Integer.toString(board.score));
	}
	
	public void setMoves() {
		moveNumber.setText(Integer.toString(board.moves));
	}
	
	public void checkWinLose() {
		if (board.checkWin()) {
			messageText.setText("Congrats! You win! Play again!");
			messageText.setBackground(Color.GREEN);
		}
		if (board.checkLose()) {
			messageText.setText("Aww, you lost! Try again!");
			messageText.setBackground(Color.RED);
			board.validMove = false; //prevent further moves if the player has lost
		}
	}

}
