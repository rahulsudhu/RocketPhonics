package AlphabetGame;
/*******************************************************************************************
Name: Rahul Sudharsan
Course: CS170
Lab #: Final Project
Submission Date: 12/5/18
Brief Description: Individual letter block that draws colored block and has jlabel properties that
form wall 
*********************************************************************************************/

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.UIManager;
public class LetterBlock extends JLabel{ // letter block individual class
	private String letter; // letter to display
	private int x, y; // coordinates
	private int width = 100; // height and widht
	private int height = 100;
	private Color col; // color to draw
	
	
	public LetterBlock(String let) {
		super(let);
		this.letter = let; 
		setFont(new Font("Verdana", Font.BOLD, 40)); // sets font of blco kadn stores letter
	
	}

	public void draw(Graphics g) { // draws colored rectangle around the jlabel
		g.setColor(col); // sets color to variable set by other method
		g.fillRect(x, y, width, height); // draws rectangle in the graph
	}
	// setters and getters for x and y coordinates
	public void setX(int x) {  
		this.x = x;
	}
	public void setY(int y) { 
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setColor(Color color) { // sets color
		this.col = color;
	}
	public String getValue() { // returns letter value of the block
		return letter;
	}
}
