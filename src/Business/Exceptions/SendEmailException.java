package Business.Exceptions;

import javax.mail.MessagingException;

public class SendEmailException extends RuntimeException {
    public SendEmailException(String message) {
        super(message);
    }
}
