package ru.perm.v.el59.office.util;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ru.el59.office.util.Emailer;

public class EmailerTest {

	@Test
	public void send() {
		Emailer emailer = new Emailer();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("vasi.che@gmail.com");
		mailSender.setPassword("PolPot67");
		mailSender.setDefaultEncoding("UTF-8");
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.userset", "true");
		props.setProperty("mail.mime.charset", "ISO-8859-1");
		
		mailSender.setJavaMailProperties(props);
		emailer.setMailSender(mailSender );
		emailer.setFromAddress("vasi.che@gmail.com");
		emailer.setControlMail("");
		emailer.setNameAddress("vasi.che@gmail.com");
		// smtp_gate@bsms.tele2.ru
//		String ret=emailer.sendSimpleMail("bob1970@yandex.ru", "test", "test1");
		String ret=emailer.sendSimpleMail("smtp_gate@bsms.tele2.ru", "ht740620055:VNVFvJ7v:0:0:0,0,el59.ru:79125831975:sssssssssss", "");
		assertTrue(ret.isEmpty());
	}

}
