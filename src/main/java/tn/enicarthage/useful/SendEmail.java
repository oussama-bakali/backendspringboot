package tn.enicarthage.useful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
@Service
public class SendEmail {
@Autowired
private JavaMailSender javamail;

public SendEmail(JavaMailSender javamail) {
	super();
	this.javamail = javamail;
}

public JavaMailSender getJavamail() {
	return javamail;
}

public void setJavamail(JavaMailSender javamail) {
	this.javamail = javamail;
}
public void sendmessage(String email,String subject,String body) {
	
	SimpleMailMessage msg =new SimpleMailMessage();
	msg.setFrom("ousso3527@gmail.com");
	msg.setTo(email);
	
	msg.setText(body);
	msg.setSubject(subject);
	this.javamail.send(msg);
	
	
}
}
