package AlphabetGame;
/*******************************************************************************************
Name: Rahul Sudharsan
Course: CS170
Lab #: Final Project
Submission Date: 12/5/18
Brief Description: Character class that is controlled by player and extends jlabel with a jet image icon
and generates random value that is the answer to the round
*********************************************************************************************/

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.swing.*;

public class Character extends JLabel {
	private int x = 100; // initial x value
	private int y = 200; // initial y value
	private int width = 100;
	private int height = 100;
	private String letter; // the answer of the block
	private int letterchoice; 
	public Character() throws MalformedURLException {
		super(new ImageIcon(new URL("file:///Users/sudhu/eclipse-workspace/CS170_FinalProject/images/jet.gif"))); // sets jlabel to image icon of jet in ddierctory
		setValue(); // randomly generates value
	}
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
	public void setValue() { // sets letter value to random letter
		String[] possible = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		Random generator = new Random();
		letterchoice = generator.nextInt(24);
		letter = possible[letterchoice];
		
	}
	public String getValue() { // returns generated letter value
		return letter;
	}
	public String getSound() { // converts letter to directory for sound file
		return "wav/" + letter + ".wav";
	}
}
