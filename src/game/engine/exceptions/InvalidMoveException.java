package game.engine.exceptions;

public class InvalidMoveException extends GameActionException {
	private static final long serialVersionUID = 1L;
	public static final String MSG  ="Invalid move attempted";
	
	public InvalidMoveException() {
		super(MSG);
		
	}
	public InvalidMoveException(String message) {
		super(message);
	}
	
	
	
}
