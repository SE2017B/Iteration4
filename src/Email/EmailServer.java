package Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServer {
    private String smtpServer;
    private int port = 587;
    private String userid;
    private String password;
    private String contentType;
    private String from;
    private String bounceAddr;
    private Properties props;

    //Singleton stuff
    private static EmailServer singleton;
    private EmailServer(){init();}
    public static EmailServer getEmailServer(){
        if(singleton == null){
            singleton =  new EmailServer();
        }
        return singleton;
    }

    private void init(){
        this.smtpServer = "smtp.gmail.com";
        this.port = 587;
        this.userid = "softengteamh";//change accordingly
        this.password = "TeamHRocks";//change accordingly
        this.contentType = "text/html";

        this.from = "softengteamh@gmail.com";
        this.bounceAddr = "nafajardo15@gmail.com";//change accordingly

        this.props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.smtpServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.from", this.bounceAddr);
    }

    public void sendEmail(String toAdress, String messageInput) throws EmailFormatException, MessagingException {
        //TODO processing of the toAdress
        if(toAdress.contains(" ") || !toAdress.contains("@") || !toAdress.contains(".")){
            throw new EmailFormatException();
        }

        String to = toAdress;//some invalid address
        String body = messageInput;
        String subject = "test: bounce an email to a different address " +
                "from the sender";

        String tempUserID = this.userid;
        String tempPassword = this.password;

        Session mailSession = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(tempUserID, tempPassword);
                    }
                });

        MimeMessage message = new MimeMessage(mailSession);
        message.addFrom(InternetAddress.parse(this.from));
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(body, this.contentType);

        Transport transport = mailSession.getTransport();
        try {
            System.out.println("Sending ....");
            transport.connect(this.smtpServer, this.port, this.userid, this.password);
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            System.out.println("Sending done ...");
        } catch (Exception e) {
            System.err.println("Error Sending: ");
            e.printStackTrace();
        }
        transport.close();
    }
}
