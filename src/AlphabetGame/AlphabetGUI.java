package AlphabetGame;
/*******************************************************************************************
Name: Rahul Sudharsan
Course: CS170
Lab #: Final Project
Submission Date: 12/5/18
Brief Description: Game board that has all game logic and stores and displays scores 
*********************************************************************************************/


import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class AlphabetGUI extends JPanel implements Runnable{ // operational board that contains all game logic
	Character rocket; // declares variables
	private Thread game; // thread that controls game play
	public ArrayList<LetterBlock> wall = new ArrayList<LetterBlock>(); // wall to be generated
	boolean genwall = true; // boolean to generate wall or not
	private AudioInputStream audio; // audio to store wav file into the clip
	private Clip clip; // clip to play audio
	private JLabel explosion; // temporarily shown explosion label 
	private JLabel scoreboard; // scoreboard lable
	private int score; // stores player score
	private String playername = "anonplayer"; // stores palyer nape
	TreeMap<String, Integer> scores = new TreeMap<String, Integer>(); // treemap that stores the unsorted text file
	
	public AlphabetGUI() {	 // consturctor
		setSize(800,500); 
		setLayout(null); // sets layout for absolute x y coordinate manipulation in the game
		try {
			rocket = new Character(); // creates new character
			rocket.setBounds(rocket.getX(), rocket.getY(), 100, 100); // sets initial position of the class
			add(rocket); // adds to screen
		} catch (MalformedURLException e) { // when the new url is created and not found 
			e.printStackTrace();
		}
		scoreboard = new JLabel("Rocket Phonics Score: \n" + score); // initial scoreboard
		scoreboard.setBounds(300, 0, 300, 50); // sets location of board
		scoreboard.setFont(new Font("Helvetica", Font.BOLD, 18)); //sets font
		add(scoreboard); 
		this.setBackground(Color.GRAY); // sets background of the board
		addKeyListener(new ArrowListener()); // uses private arrow listener class
		setFocusable(true);	 // focuses to events on the panel
		game = new Thread(this); // creates new thread of the runnable implemenation
		JOptionPane.showMessageDialog(null, "Welcome to Rocket Phonics. Listen to the letter said and move your ship to hit the right\n"
				+ "letter block to destroy the wall and earn points! Click ok to begin and enjoy!"); // initital prompt message
		game.start();			 // starts thread
	}
	public void paintComponent(Graphics g) { // paint component method
		super.paintComponent(g); // paints initial scren using super method
		scoreboard.setText("Rocket Phonics Score: \n" + score); // sets new text of scoreboard
		scoreboard.repaint(); // repaints scoreboard
		add(new JLabel(Integer.toString(score))); // adds label 
		if (genwall) {			
			for (int i = 0; i < wall.size(); i++) {
			remove(wall.get(i));		// remove each block of old wall if exists 
			}
			
			g.clearRect(0, 600, 100, 500); // clears painted rectange on wall that corresponded with the old jlabels
			ArrayList<LetterBlock> newwall = new Wall(rocket.getValue()).generateRandomWall();  // generates random wall
			playSound();
			this.wall = newwall; // sets current wall to be moved as the generated wall
			for (int i = 0; i < 6; i++) { // iterates through each block for its initial position
				wall.get(i).setBounds(wall.get(i).getX(), wall.get(i).getY(), 100, 100);
				add(wall.get(i));
				wall.get(i).draw(g);
				}
			if (explosion != null) { 
				remove(explosion);	 // removes explosion from the last wall			
			}
			
			genwall = false; // sets to false for next repaint call
		}
		for (int i = 0; i < wall.size(); i++) { // always used when repainted to repain new wall position
			wall.get(i).draw(g);
		}		
		g.clearRect(rocket.getX(), rocket.getY(), 100, 100);
	}
	
	private class ArrowListener extends KeyAdapter { // key listener to be used by panel to control the plane
		public void keyPressed(KeyEvent e) { // on key pressed event
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				rocket.setY(rocket.getY() - 100); 
				rocket.setBounds(rocket.getX(), rocket.getY(), 100, 100); // positons y coordinate and places in new location
				repaint(); // repaints to remove original rectangel 
				//rocket.repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				rocket.setY(rocket.getY() + 100); // moves up
				rocket.setBounds(rocket.getX(), rocket.getY(), 100, 100);
				repaint();
				//rocket.repaint();
			}
		}
		public void keyReleased(KeyEvent ke) {
			int key = ke.getKeyCode();
			if (key == KeyEvent.VK_UP) {
				rocket.setY(rocket.getY());
			}
			if (key == KeyEvent.VK_DOWN) {
				rocket.setY(rocket.getY());
			}
		}
			
	}
	public void start() {
		game.start(); // implemented method
	}
	public void run() { // implemented method to constantly check and move blocks while thread runs
	while (true) {	 
		for (int i = 0; i < wall.size(); i ++) {
			wall.get(i).setX(wall.get(i).getX() - 20); // moves wall slightly to left
			wall.get(i).setBounds(wall.get(i).getX(), wall.get(i).getY(), 100, 100);
		}
		if (wall.size() > 0) {
			checkCollide(); // game logic to always check when wall reaches collision point
		}
			repaint();  // calls repaint method for new position of wl
		try {
			Thread.sleep(100); //
		} catch (InterruptedException e1) {		
			e1.printStackTrace();
		} }
	
	}
	public void checkCollide() { // game logic method
		
		if (wall.get(0).getX() < 80) { // if wall hits area where plane is
			for (LetterBlock i : wall ) { // iterates through wall
				if (rocket.getValue() == i.getValue()) { // if the rockets answer isth tevalue of the blcok value
					if (rocket.getY() == i.getY()) { // if is in same y location
						score +=1; // success and adds point
						animateExplosion();	 // animates explosion
						explosion.repaint();	// repaints the explosion				
					} else { // if user fails
						clip.stop(); // ends clip
						playername = JOptionPane.showInputDialog(null, "Game Over! Your score was " + score + ". What is your name?");	 // prompt user to enter name to store
						try {
							saveScore(playername, score); // saves scoare to text file
							readScores(); // extract from fremont
							displayScores(); // displays test scores
						} catch (IOException e) { // catches io exception from any one of those methods
							e.printStackTrace(); 
						}
						game.suspend(); // stops thread
					}
				}
				
			}
			rocket.setValue(); // creates new value
			clip.stop(); // ends clip for next audio
			
			genwall = true; // sets as true if continued
		}
	}
	
	public void animateExplosion() { // adds new jlabel 
		try {
			explosion = new JLabel(new ImageIcon(new URL("file:///Users/sudhu/eclipse-workspace/CS170_FinalProject/images/explosion.jpg"))); // sets new jlabel
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		explosion.setBounds(rocket.getX() + 100, rocket.getY(), 100, 100); // postions label to where rocket hit the block
		add(explosion); // adds to screen
	}
	public void playSound() { // play sound method
		try {
			audio = AudioSystem.getAudioInputStream(new File(rocket.getSound()).getAbsoluteFile()); // loads input stream from wave file
			clip = AudioSystem.getClip();// loads in to clip
			clip.open(audio); // opens audio clip
			clip.loop(Clip.LOOP_CONTINUOUSLY); // plays and loops continuously until told to stop
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveScore(String name, int score) throws IOException {  // stores data into a text file for future use
		PrintWriter out = new PrintWriter(new FileWriter("./highscores/highscores.txt", true));  // printwriter object to write into file
		out.println(name + "\t" + score); // prints score with name into file
		   out.close(); // closes
		}
	
	private void readScores() throws IOException{ // read from text file
		File highscores = new File("./highscores/highscores.txt"); //Create a file object 
		BufferedReader in = new BufferedReader(new FileReader(highscores)); // input reader of the file scores
		String line = in.readLine(); //Read a record
		while (line != null){ //Continue to until no more
			StringTokenizer token = new StringTokenizer(line, "\t"); // gets whole line with tab as delimitetr
		        String name = token.nextToken(); // reads name
		        String score = token.nextToken(); // stores value as string
		        int intscore = Integer.parseInt(score); // parses into integer
		        scores.put(name, intscore); // adds to treemap
		        line = in.readLine(); // next line buffer
		   }
		   in.close(); // clsoes reader
	}
	public void displayScores() { 
		String scoretable =""; // initialize string to be returned and displayed
		int place = 1; //initializes place as 1
		LinkedHashMap<String, Integer> sortedmap = (LinkedHashMap<String, Integer>) sortByValue(scores); // sorts treemap into linked hash map
		for(Map.Entry<String,Integer> entry : sortedmap.entrySet()) { // interates through each map entyt
			  String player = entry.getKey(); // returns string to store and add to final string
			  int score = entry.getValue(); // gets integer value to enter into score
			  scoretable += place + ": " + player + " Score: " + score + "\n"; //addds to string
			  place ++; // goes to next place
			}
		JOptionPane.showMessageDialog(null, "High Scores: \n" + scoretable); // displays table in joption pane
	}
	private static <String, Integer> Map<String, Integer> sortByValue(Map<String, Integer> map) { // method to sort treemap to linked hashmap
	    LinkedList<Entry<String, Integer>> list = new LinkedList<>(map.entrySet()); // creates linked list of entreis
	    Collections.sort(list, new Comparator<Object>() { // scorts list using comparator to keep key and value together
	        public int compare(Object o1, Object o2) { // implemented meod
	            return ((Comparable<Integer>) ((Map.Entry<String, Integer>) (o2)).getValue()).compareTo(((Map.Entry<String, Integer>) (o1)).getValue()); // compares integer value of two objects for desceding list
	        }
	    });

	    Map<String, Integer> result = new LinkedHashMap<>(); // creates linked hash map
	    for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) { // interates thro the linked list of entries
	        Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next(); // stores entry through the next iteration
	        result.put(entry.getKey(), entry.getValue()); // adds entry by key and value to linked hash map
	    }
	    return result; // returns linked hash map
	}
	
}
