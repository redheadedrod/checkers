package edu.gvsu.scis.cis350.termProject;

import javax.swing.JFrame;

/**
 * Main class runs the gui for the users.
 * 
 * @author Andrew Olesak
 * @version September 25, 2016
 */
final class MainGUI {
	/**
	 * a constructor for class MainGUI.
	 */
	private MainGUI() {
		
	}
	/**
	 * main method to drive the game.
	 * 
	 * @param args parameter for program arguments. this game has none.
	 */
    public static void main(final String[] args) {
        JFrame frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CheckersGUI layout = new CheckersGUI();
        frame.getContentPane().add(layout);
        frame.pack();
        frame.setVisible(true);
    }
}
