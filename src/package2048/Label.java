package package2048;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

@SuppressWarnings({ "serial", "unused" })
public class Label extends JLabel{
	int index;
	
	static String [] numbers = {" ", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048", "4096", "8192"};
	static Color [] allColors = new Color [2744];
	static String [] colorNames = {"white", "light salmon", "pink", "sharp lavender", "lightish purple", "purpleish blue", "periwinkle blue", 
			"aqua", "mint", "light bright green", "somewhat pale yellow", "gold ish", "orange", "red"};
	static Color [] colors = {Color.WHITE, new Color(255, 200, 200), new Color(255, 96, 190), new Color(255, 96, 255), new Color(190, 96, 224), 
			new Color(96, 127, 255), new Color(127, 190, 255), new Color(63, 224, 224), new Color(63, 255, 190), new Color(63, 224, 96), 
			new Color(255, 255, 127), new Color(255, 190, 63), new Color(255, 30, 130), new Color(240,30,45)};
	static String [] cupcakeFiles = {"blank.jpeg", "carrot.jpeg", "cherry_blossom.jpeg", "milk_chocolate_birthday.jpeg", "chocolate_mint_fudge.jpeg", 
			"cookies_and_creme.jpeg", "key_lime.jpeg", "lavender_earl_grey_teacake.jpeg", "white_chocolate_peppermint.jpeg", 
			"lemon_blossom.jpeg", "red_velvet.jpeg", "strawberry_cheesecake.jpeg", "salted_caramel.jpeg", "babyblue_chocolate.jpeg"};		
	static ImageIcon [] cupcakeIcons = new ImageIcon[14];
	
	
	public Label(int num) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setOpaque(true);
		setFont (getFont().deriveFont (32.0f)); //set font size to 32
		setSize(new Dimension(75, 75));
		setHorizontalAlignment(SwingConstants.CENTER);
		setLabel(num); //sets the label based on the number it's supposed to represent
	}
	
	public void setLabel(int num) { //changes the label to represent the correct number
		index = 0;
		if (num != 0)
			index = log(num); //take log base 2, since all the numbers are nice powers of 2 
		setIcon(null);
		setBackground(colors[index]);
		setText(numbers[index]);
	}
	
	public void setLabelCupcake(int num) {
		index = 0;
		if (num!= 0) 
			index = log(num);
		setBackground(colors[index]);
		setIcon(cupcakeIcons[index]);
		setText("");
	}
	
	public void setLabelRandom(int num) {
		index = 0;
		if (num!=0)
			index = log(num);
		setBackground(allColors[randInt(0,allColors.length-1)]);
		setText(numbers[index]);
	}
	
	public static void setupCupcakes() { //called in BoardGUI constructor
		for (int i = 0; i < cupcakeFiles.length; i ++) {
			ImageIcon icon = new ImageIcon(Label.class.getResource("/package2048/"+cupcakeFiles[i]));
			//ImageIcon icon = new ImageIcon("/Users/hannalee/Documents/workspace/2048/bin/package2048/"+cupcakeFiles[i]);
			Image image = icon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			cupcakeIcons[i] = new ImageIcon(image);
		}
	}
	 //TESTING
	public static ImageIcon testing() {
		ImageIcon icon = new ImageIcon(cupcakeFiles[1]);
		return icon;
	}
	
	public static void initRandomColors() { //called in BoardGUI constructor 
		//tried to not use dark colors
		int i = 0;
		for (int red = 4; red < 18; red ++)
			for (int green = 4; green < 18; green ++)
				for (int blue = 4; blue < 18; blue ++) {
					allColors[i] = new Color(15*red, 15*green, 15*blue);
					i++;
				}
	}
	
	public static int log(int x) { //returns log base 2
	    return (int) (Math.log(x) / Math.log(2));
	}
	
	public static int randInt(int min, int max) { //returns pseudo-random integer between min and max, inclusive
	    int randomNum = new Random().nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
