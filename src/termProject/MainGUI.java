package termProject;


import javax.swing.JFrame;


public class MainGUI {

	public static void main(String[] args){
		JFrame frame = new JFrame("Checkers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CheckersGUI layout = new CheckersGUI();
		frame.getContentPane().add(layout);
		frame.pack();
		frame.setVisible(true);
	}
}
