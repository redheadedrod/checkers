package termProject;

/**
 * Class provides all of the functionality of a checkers piece
 * 
 * @author Andrew Olesak
 * @version September 13,2016
 */
public class CheckersPiece {

	private boolean isKing;
	private Player owner;
	
	public CheckersPiece(Player o){
		this.isKing = false;
		this.owner = o;
	}
	
	public Player getPlayer(){
		return this.owner;
	}
	
	public boolean isKing(){
		return isKing;
	}
	
}
