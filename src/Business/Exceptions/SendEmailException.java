package Business.Exceptions;

/**
 * Thrown when an email fails to send due to an invalid address or delivery error.
 */

public class SendEmailException extends RuntimeException {
    public SendEmailException(String message) {
        super(message);
    }
}
