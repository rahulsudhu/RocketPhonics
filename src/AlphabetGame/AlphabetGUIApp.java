package AlphabetGame;
/*******************************************************************************************
Name: Rahul Sudharsan
Course: CS170
Lab #: Final Project
Submission Date: 12/5/18
Brief Description: Driver class that creates jframe that uses the jpanel and sets in middle of the screen
*********************************************************************************************/

import java.awt.*;
import javax.swing.*;

public class AlphabetGUIApp { //driver class

	public static void main(String[] args) { // main method
		JFrame frame = new JFrame("Rocket Phonics");  // creates frame
		frame.setSize(800, 500); // sets frame properties
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane(); // creates pane to add the board
		AlphabetGUI g = new AlphabetGUI(); // game baord
		pane.add(g);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // uses toolkit to set frame in middle of the screen
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true); // shows
	}

}
