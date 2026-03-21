package game.engine.exceptions;

public class OutOfEnergyException extends GameActionException {
	 private static final long serialVersionUID = 1L;
	 public static final String MSG  = "Not Enough Energy for Power Up";
	
	public OutOfEnergyException() {
		super(MSG);
	}
	
	public OutOfEnergyException(String message) {
		super(message);
	}
	

}
