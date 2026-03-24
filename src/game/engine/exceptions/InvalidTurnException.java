package game.engine.exceptions;

public class InvalidTurnException extends GameActionException {
	private static final long serialVersionUID = 1L;
	private  static final String MSG  = "Action done on wrong turn";
	
	public InvalidTurnException() {
		super(MSG);
	}
	
	public InvalidTurnException(String message) {
		super(message);
	}
	
	
	
	
}
