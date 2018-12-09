package AlphabetGame;
/*******************************************************************************************
Name: Rahul Sudharsan
Course: CS170
Lab #: Final Project
Submission Date: 12/5/18
Brief Description: Wall class with arraylist of letterblock and shuffles and positions them with
correct letterblock within it
*********************************************************************************************/

import java.awt.Color;
import java.util.*;

public class Wall { // wall class with arraylist 
	private String answer; // to hold answer letter
	private ArrayList<LetterBlock> letterwall; // arraylist of letterblocks
	
	public Wall(String ans) { // when created it will hold correct answer to ensure inclusion
			this.answer = ans;
		}
	
	public ArrayList<LetterBlock> generateRandomWall() { // method to generate random wall
		LetterBlock correct = new LetterBlock(answer); // creates letterblock with answer
		LetterBlock push; // temporary letterblock to add to array
		correct.setColor(genRandColor()); // generates random color
		correct.setX(600); // sets x for correct
		letterwall = new ArrayList<LetterBlock>();
		this.letterwall.add(correct);
		for (int i = 0; i < 5; i++) { // iterates through rest of blocks
			push = new LetterBlock(genRandLetter()); // creates new letter block
			push.setColor(genRandColor());
			push.setX(600); // sets initials x value
			letterwall.add(push); // adds to array
		}
		Collections.shuffle(letterwall); // shuffles array to add
		
		for (int i =0; i < 6; i++) { // iterates through array
			this.letterwall.get(i).setY(i*100);
		}
		return this.letterwall;
	}
	
	public Color genRandColor() { // generates random color from options
		Color[] possible = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.MAGENTA};
		Random generator = new Random(); // new random object to be created each time it is called
		int colorchoice = generator.nextInt(7);
		Color col = possible[colorchoice];
		return col; // returns random color generated
	}
	public String genRandLetter() {
		String[] possible = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		Random generator = new Random();
		int letterchoice = generator.nextInt(24);
		String letter = possible[letterchoice];
		return letter; // returns random letter
	}
	
	
}
