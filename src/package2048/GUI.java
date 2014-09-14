package package2048;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GUI extends JPanel { //should show score; the board; a reset/new game button
	Board board;
	BoardGUI boardGUI;
	TopBar topbar; 
	BottomBar bottombar;
	JLabel empty1 = new JLabel(); //empty labels just for spacing on the sides of the border layout
	JLabel empty2 = new JLabel();
	
	public GUI(Board inputBoard) {
		board = inputBoard;
		initComponents(board);
		setFocusable(true); //keyboard focus can be set on this component
		setLayout(new BorderLayout());
	}
	
	public void initComponents(Board board) {
		topbar = new TopBar(board);
		boardGUI = new BoardGUI(board, topbar); //labels initialized in constructor
		bottombar = new BottomBar(board, boardGUI, topbar);
		empty1.setBorder(new EmptyBorder(50, 50, 50, 50));
		empty1.setOpaque(true);
		empty2.setOpaque(true);
		empty2.setBorder(new EmptyBorder(50, 50, 50, 50));
	}
	
	//method called to bring up the GUI
	public void createAndShowGUI() {
		JFrame frame = new JFrame("Hanna's 2048");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(600, 500);
		
		frame.add(topbar, BorderLayout.NORTH);
		frame.add(boardGUI, BorderLayout.CENTER);
		frame.add(bottombar, BorderLayout.SOUTH);
		frame.add(empty1, BorderLayout.WEST);
		frame.add(empty2, BorderLayout.EAST);

		frame.setVisible(true);
		
		boardGUI.requestFocus(true); //gives focus to the boardGUI which is the board
	}

}
