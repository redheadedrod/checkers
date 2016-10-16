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
	 * @param args yarhargs mateys no initial args here.
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
