package resilience;

public class BadFileFormatException extends Exception {
	
	private static final long serialVersionUID = 9999;
	
	public BadFileFormatException(String name) {
		super(name);
	}
}
