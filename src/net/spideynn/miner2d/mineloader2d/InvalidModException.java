package net.spideynn.miner2d.mineloader2d;

/**
 * Thrown when a mod is not loaded correctly,
 */

public class InvalidModException extends Exception {
	
	private static final long serialVersionUID = 6618588584177074227L;

  public InvalidModException(Throwable cause) {
    super(cause);
  }

  public InvalidModException() {
  }

  public InvalidModException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidModException(String message) {
    super(message);
  }
}