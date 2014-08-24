package package2048;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class BoardGUI extends JPanel{ //action listener for the newGame button; KeyListeners called in GUI.java 
	Board board = new Board();
	TopBar topbar;
	AbstractAction UpAction, DownAction, LeftAction, RightAction;
	Label [] labels = new Label [16]; //array of labels
	String gameMode; //tells the mode (numbers or cupcakes); to add modes, make a button and add a case in updateLabels()
	
	public BoardGUI(Board inputBoard, TopBar inputTopBar) {
		topbar = inputTopBar;
		board = inputBoard;
		setLayout(new GridLayout(4,4)); //4x4 board
		Label.setupCupcakes(); //set up the cupcake images
		Label.initRandomColors(); //setup random colors
		initLabels();
		createActions();
		setKeyBindings();
		gameMode = "normal";
	}
	
	public void initLabels() { //for each space on the board, add it to the labels array and to the JPanel 
		int value;
		int index = 0;
		for (int r = 0; r < 4; r++) //labels counted left to right, up to down
			for (int c = 0; c < 4; c++) {
				value = board.BOARD[r][c];
				labels[index] = new Label(value);
				add(labels[index]);
				index++;
			}
	}
	
	//sets the key bindings for up, down, left, right arrow keys; maps them to the actions defined above
	public void setKeyBindings() {
		getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
		getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
		getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		
		getActionMap().put("moveUp", UpAction);
		getActionMap().put("moveDown", DownAction);
		getActionMap().put("moveRight", RightAction);
		getActionMap().put("moveLeft", LeftAction);
	}
	
	
	public void updateLabels(){ //sets all the new labels depending on the board configuration
		int value;
		int index = 0;
		switch (gameMode) { //set labels differently depending on the game mode
			case "normal" :
				for (int r = 0; r < 4; r++) //labels will be counted left to right, up to down
					for (int c = 0; c < 4; c++) {
						value = board.BOARD[r][c];
						labels[index].setLabel(value);
						index ++;
					}
				break;
				
			case "cupcake":
				index = 0;
				for (int r = 0; r < 4; r++) 
					for (int c = 0; c < 4; c++) {
						value = board.BOARD[r][c];
						labels[index].setLabelCupcake(value);
						index++;
					}
				break;	
			case "randomColor":
				index = 0;
				for (int r = 0; r < 4; r++) 
					for (int c = 0; c < 4; c++) {
						value = board.BOARD[r][c];
						labels[index].setLabelRandom(value);
						index ++;
					}
				break;
		}
	}
	
	public void createActions() { //create the action objects that are used when arrow keys are pressed	
		UpAction = new AbstractAction("Move Up") {  
		    public void actionPerformed(ActionEvent e)  
		    {  
		        if (board.isValidUp() && board.validMove) {
			    	board.moveUp();
			        board.newRandomTile();
			        updateLabels(); //update after moving and after a new tile appears
			        topbar.update();
		        }
		    }  
		};
		
		DownAction = new AbstractAction("Move Down") {  
		    public void actionPerformed(ActionEvent e)  
		    {  
		    	if (board.isValidDown() && board.validMove) {
			    	board.moveDown();
			        board.newRandomTile();
			        updateLabels();
			        topbar.update();
		    	}
		    }  
		};
		
		RightAction = new AbstractAction("Move Right") {  
		    public void actionPerformed(ActionEvent e)  
		    {  
		        if (board.isValidRight() && board.validMove) {
			    	board.moveRight();
			        board.newRandomTile();
			        updateLabels();
			        topbar.update();
		        }
		    }  
		};
		
		LeftAction = new AbstractAction("Move Left") {  
		    public void actionPerformed(ActionEvent e)  
		    {  
		    	if (board.isValidLeft() && board.validMove) {
			        board.moveLeft();
			        board.newRandomTile();
			        updateLabels();
			        topbar.update();
		    	}
		    }  
		};
	}
	
}
