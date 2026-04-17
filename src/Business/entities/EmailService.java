package Business.entities;

import Business.Exceptions.SendEmailException;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailService {

    private final String FROM = "gamecoffeeclicker@gmail.com";
    private final String PASSWORD = "mtzi hubp vqpf uymc";
    private final int STARTING_POINT = 100000;
    private final int BIG_NUMBER_TO_CHOOSE_RANDOM = 900000;

    public String sendVerificationCode(String toEmail) throws SendEmailException {

        String codeToSend = String.valueOf(STARTING_POINT + new Random().nextInt(BIG_NUMBER_TO_CHOOSE_RANDOM));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Verification Code");
            message.setText("Your authentication code is: " + codeToSend);
            Transport.send(message);

        } catch (SendFailedException e) {
            throw new SendEmailException("This email is not correct or could not be reached.");
        }
        catch(MessagingException e) {
            throw new SendEmailException("This email is not correct or could not be reached.");
        }
        return codeToSend;
    }


}
