package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Linus on 19/09/2016.
 */
public class EmailUtilII {

    private static final String  SENDER_ADDR= "rmrf.9321.service@gmail.com";
    private static final String PASSWORD = "9321Sucks";

    /**
     * Sending simple email.
     * @param subject the subject of email e.g. Registration Confirmation
     * @param content content of email e.g. we have received your registration, please confirm via...
     * @param recipientAddress recipientAddress e.g. john@gmail.com
     * @return true if the email is sent and false if there is error in sending.
     */
    public boolean sendEmail(String subject, String content, String recipientAddress){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_ADDR, PASSWORD);
                    }
                });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_ADDR));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientAddress));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("SENT EMAIL TO "+recipientAddress);
        }catch (MessagingException e){
            System.err.println("EMAIL SEND FAILURE DUE TO: ");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
