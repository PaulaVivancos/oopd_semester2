package Business.Exceptions;

/**
 * Thrown when an email fails to send due to an invalid address or delivery error.
 */

public class SendEmailException extends RuntimeException {
    /**
     * Creates a new SendEmailException with the given detail message.
     * @param message a description of the reason the email could not be sent
     */
    public SendEmailException(String message) {
        super(message);
    }
}
