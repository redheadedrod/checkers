package termProject;

import javax.swing.JFrame;

/**
 * Main class runs the gui for the users
 * 
 * @author Andrew Olesak
 * @version September 25, 2016
 */
public class MainGUI {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Checkers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CheckersGUI layout = new CheckersGUI();
		frame.getContentPane().add(layout);
		frame.pack();
		frame.setVisible(true);
	}
}
