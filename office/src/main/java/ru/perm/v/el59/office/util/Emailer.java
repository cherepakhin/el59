package ru.perm.v.el59.office.util;

import ru.el59.office.db.Manager;
import ru.el59.office.db.dto.FileAttach;
import ru.perm.v.el59.dto.office.emailer.IEmailer;

import java.util.List;
//import java.util.List;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//import javax.mail.util.ByteArrayDataSource;
//
//import org.apache.commons.compress.archivers.ArchiveException;
//import org.apache.commons.compress.archivers.ArchiveOutputStream;
//import org.apache.commons.compress.archivers.ArchiveStreamFactory;
//import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
//import org.apache.commons.io.IOUtils;
//import org.apache.log4j.Logger;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import ru.perm.v.el59.office.db.Manager;
//import ru.perm.v.el59.office.db.dto.FileAttach;
//import ru.perm.v.el59.office.emailer.IEmailer;
//
//import com.sun.xml.messaging.saaj.util.ByteOutputStream;

public class Emailer implements IEmailer {

//	private JavaMailSender mailSender;
	private String fromAddress;
	private String nameAddress;

	private String controlMail;

//	@Override
	public String sendSimpleMail(String recipients, String message,
			String subject) {
//		try {
//			String[] r = recipients.split(";");
//			SimpleMailMessage m = new SimpleMailMessage();
//			m.setText(message);
//			m.setTo(r);
//			m.setSubject(subject);
//			m.setFrom(fromAddress);
//			mailSender.send(m);
//		} catch (Exception e) {
//			Logger.getLogger(this.getClass()).error(e);
//			e.printStackTrace();
//			return (e.getLocalizedMessage());
//		}
		return "";
	}

//	@Override
//	public String send(Manager manager, String recipients, String message,
//			String subject, List<FileAttach> listFileAttach, boolean needTrailer) {
//		MimeMessage email = mailSender.createMimeMessage();
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(email, true);
//
//			helper.setFrom(fromAddress, nameAddress);
//			if (manager != null && manager.getEmail() != null
//					&& !manager.getEmail().equals("")) {
//				recipients = recipients + ";" + manager.getEmail();
//			}
//			String[] r = recipients.split(";");
//			InternetAddress[] addressesTo = new InternetAddress[r.length];
//			int j = 0;
//			for (int i = 0; i < r.length; i++) {
//				if (!r[i].equals("")) {
//					addressesTo[j] = new InternetAddress(r[i]);
//					j++;
//				}
//			}
//			Logger.getLogger(this.getClass()).info(
//					"Отправка почты " + recipients);
//			helper.setTo(addressesTo);
//			helper.setSubject(subject);
//			if (message == null) {
//				message = "";
//			}
//
//			if (needTrailer) {
//				message = message
//						+ "\nЭто письмо создано программой-роботом.\nДля ответа используйте адрес"
//						+ " mailto:" + getControlMail();
//				if (manager == null) {
//					message = message + "\n";
//				} else {
//					message = message + ";" + manager.getEmail() + "\n";
//				}
//			}
//			helper.setText(message);
//			// helper.setText("Это письмо создано программой-роботом.\nДля ответа используйте адрес "+manager.getName()+"mailto:"+manager.getEmail()+"\n",
//			// false);
//			if (listFileAttach != null && listFileAttach.size() > 0) {
//				for (FileAttach fileAttach : listFileAttach) {
//					ByteArrayDataSource ds = new ByteArrayDataSource(
//							fileAttach.body, "application/octet-stream");
//					/*
//					 * String decoded = MimeUtility.decodeText(fileAttach.name);
//					 * String name = Normalizer.normalize(decoded,
//					 * Normalizer.Form.NFC);
//					 */
//					// helper.addAttachment(new
//					// String(fileAttach.name.getBytes(),"UTF-8"), ds);
//					String name = fileAttach.name;
//					// String name = "a.zip";
//					helper.addAttachment(
//							MimeUtility.encodeText(name.toString()), ds);
//				}
//			}
//		} catch (MessagingException e) {
//			Logger.getLogger(this.getClass()).error(e);
//			e.printStackTrace();
//			return e.getLocalizedMessage();
//		} catch (UnsupportedEncodingException e) {
//			Logger.getLogger(this.getClass()).error(e);
//			e.printStackTrace();
//			return e.getLocalizedMessage();
//		} catch (IOException e) {
//			Logger.getLogger(this.getClass()).error(e);
//			e.printStackTrace();
//			return e.getLocalizedMessage();
//		}
//		try {
//			mailSender.send(email);
//		} catch (MailException e) {
//			Logger.getLogger(this.getClass()).error(e);
//			e.printStackTrace();
//			return e.getLocalizedMessage();
//		}
//		Logger.getLogger(this.getClass()).info(
//				"Отправка почты закончена " + recipients);
//		return "";
//	}

	/*
	 * Logger.getLogger(this.getClass()).info("Подготовка сообщения. Отправитель:"
	 * +manager.getName()+".Тема:"+subject+" Сообщение"+message); //
	 * message="Для ответа используйте адрес <a href='mailto:"
	 * +manager.getEmail()+"'>"+manager.getName()+"</a>\n"+message; //
	 * message="Для ответа используйте адрес <a href='mailto:"
	 * +manager.getEmail()+"'>"+manager.getName()+"</a>\n"+message;
	 * Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	 * Properties props = new Properties();
	 * props.setProperty("mail.transport.protocol", "smtp");
	 * props.setProperty("mail.host", getHost()); props.put("mail.smtp.auth",
	 * "true"); props.put("mail.smtp.port", getPort().toString());
	 * props.put("mail.smtp.socketFactory.port", getPort().toString());
	 * props.put("mail.smtp.socketFactory.class",
	 * "javax.net.ssl.SSLSocketFactory");
	 * props.put("mail.smtp.socketFactory.fallback", "false");
	 * props.setProperty("mail.smtp.quitwait", "false");
	 * 
	 * Session session = Session.getDefaultInstance(props,new
	 * javax.mail.Authenticator() { protected PasswordAuthentication
	 * getPasswordAuthentication() { return new
	 * PasswordAuthentication(getUsername(),getPassword()); } });
	 * 
	 * MimeMessage email = new MimeMessage(session);
	 * 
	 * MimeMultipart multipart=new MimeMultipart(); BodyPart messageBodyPart =
	 * new MimeBodyPart(); try {
	 * messageBodyPart.setContent(message,"text/html;charset=UTF-8" );
	 * multipart.addBodyPart(messageBodyPart);
	 * 
	 * if(listFileAttach!=null && listFileAttach.size()>0) { for (FileAttach
	 * fileAttach : listFileAttach) { MimeBodyPart fileBodyPart = new
	 * MimeBodyPart(); ByteArrayDataSource ds = new
	 * ByteArrayDataSource(fileAttach.body, "application/octet-stream");
	 * fileBodyPart.setDataHandler(new DataHandler(ds));
	 * fileBodyPart.setHeader("Content-Type", ds.getContentType());
	 * fileBodyPart.setFileName(fileAttach.name);
	 * multipart.addBodyPart(fileBodyPart); } } email.setContent(multipart);
	 * email.setSender(new InternetAddress(getUsername())); email.setFrom(new
	 * InternetAddress(getUsername(),getFromUsername()));
	 * email.setSubject(subject); String[] r = recipients.split(";"); for (int i
	 * = 0; i < r.length; i++) { email.setRecipient(Message.RecipientType.TO,
	 * new InternetAddress(r[i])); } // Transport transport =
	 * session.getTransport(); // transport.connect();
	 * 
	 * email.saveChanges();
	 * Logger.getLogger(this.getClass()).info("Отправка сообщения. Отправитель:"
	 * +manager.getName()+".Тема:"+subject+" Сообщение"+message);
	 * Transport.send(email); Logger.getLogger(this.getClass()).info(
	 * "Отправка сообщения закончена. Отправитель:"
	 * +manager.getName()+".Тема:"+subject+" Сообщение"+message); //
	 * transport.close(); Logger.getLogger(this.getClass()).info("Отправлено");
	 * } catch (MessagingException e) {
	 * Logger.getLogger(this.getClass()).error("Ошибка", e);
	 * e.printStackTrace(); return e.getLocalizedMessage(); } catch (IOException
	 * e) { Logger.getLogger(this.getClass()).error("Ошибка", e);
	 * e.printStackTrace(); return e.getLocalizedMessage(); } return "";
	 */
	/*
	 * String head=
	 * "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
	 * +
	 * "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Insert title here</title></head><body>"
	 * ;
	 * message=head+"Для ответа используйте адрес <a href='mailto:"+manager.getEmail
	 * ()+"'>"+manager.getName()+"</a>\n"+message;
	 * message=message+"</body></html>"; System.out.println(recipients);
	 * System.out.println(message);
	 * 
	 * Email email; if(listFileAttach==null || listFileAttach.size()==0) { email
	 * = new HtmlEmail(); } else { email = new MultiPartEmail(); for (FileAttach
	 * fileAttach : listFileAttach) { try { File tmpFile =
	 * File.createTempFile("out", "."+getFileExtension(fileAttach.name));
	 * tmpFile.deleteOnExit(); FileUtils.writeByteArrayToFile(tmpFile,
	 * fileAttach.body); EmailAttachment attachment = new EmailAttachment();
	 * attachment.setPath(tmpFile.getCanonicalPath());
	 * attachment.setDisposition(EmailAttachment.ATTACHMENT);
	 * attachment.setDescription(fileAttach.name);
	 * attachment.setName(fileAttach.name); ((MultiPartEmail)
	 * email).attach(attachment); } catch (IOException e) {
	 * Logger.getLogger(this.getClass()).error("Ошибка", e);
	 * e.printStackTrace(); return e.getLocalizedMessage(); } catch
	 * (EmailException e) { Logger.getLogger(this.getClass()).error("Ошибка",
	 * e); e.printStackTrace(); return e.getLocalizedMessage(); }
	 * 
	 * } } email.setHostName(getHost()); email.setSmtpPort(getPort());
	 * email.setAuthenticator(new DefaultAuthenticator(getUsername(),
	 * getPassword())); email.setSSL(true); email.setCharset(getCharSet()); try
	 * { email.setFrom(getFromUsername()); email.setSubject(subject);
	 * email.setMsg(message); String[] r = recipients.split(";"); for (int i =
	 * 0; i < r.length; i++) { email.addTo(r[i]); } email.send(); } catch
	 * (EmailException e) { Logger.getLogger(this.getClass()).error("Ошибка",
	 * e); e.printStackTrace(); return e.getLocalizedMessage(); } return "";
	 */
	/*
	 * Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	 * Properties props = new Properties();
	 * props.setProperty("mail.transport.protocol", "smtp");
	 * props.setProperty("mail.host", getHost());
	 * 
	 * props.put("mail.smtp.auth", "true"); props.put("mail.smtp.port",
	 * getPort().toString());
	 * 
	 * props.put("mail.debug", "true");
	 * props.put("mail.smtp.socketFactory.port", getPort().toString());
	 * 
	 * props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"
	 * ); props.put("mail.smtp.socketFactory.fallback", "false");
	 * 
	 * Session session = Session.getDefaultInstance(props,new
	 * javax.mail.Authenticator() { protected PasswordAuthentication
	 * getPasswordAuthentication() { return new
	 * PasswordAuthentication(getUsername(),getPassword()); } });
	 * session.setDebug(true); Transport transport; try { transport =
	 * session.getTransport(); InternetAddress addressFrom = new
	 * InternetAddress(getFromUsername(),manager.getName()); MimeMessage mes =
	 * new MimeMessage(session); mes.setSender(addressFrom); mes.setSubject("");
	 * mes.setContent(message, "text/plain;charset=\"UTF-8\"");
	 * 
	 * String sendTo [] = recipients.split(";"); InternetAddress[] addressTo =
	 * new InternetAddress[sendTo.length]; for (int i = 0; i < sendTo.length;
	 * i++) { addressTo[i] = new InternetAddress(sendTo[i]); }
	 * mes.setRecipients(Message.RecipientType.TO, addressTo);
	 * transport.connect(); transport.send(mes); transport.close(); } catch
	 * (NoSuchProviderException e) {
	 * Logger.getLogger(this.getClass()).error("Ошибка", e);
	 * e.printStackTrace(); return e.getLocalizedMessage(); } catch
	 * (AddressException e) { Logger.getLogger(this.getClass()).error("Ошибка",
	 * e); e.printStackTrace(); return e.getLocalizedMessage(); } catch
	 * (MessagingException e) {
	 * Logger.getLogger(this.getClass()).error("Ошибка", e);
	 * e.printStackTrace(); return e.getLocalizedMessage(); } catch
	 * (UnsupportedEncodingException e) {
	 * Logger.getLogger(this.getClass()).error("Ошибка", e);
	 * e.printStackTrace(); return e.getLocalizedMessage(); } return "";
	 */

//	private byte[] getBody(FileAttach fileAttach) throws ArchiveException,
//			IOException {
//		ByteOutputStream bo = new ByteOutputStream();
//		ArchiveOutputStream logical_zip = new ArchiveStreamFactory()
//				.createArchiveOutputStream(ArchiveStreamFactory.ZIP, bo);
//		logical_zip.putArchiveEntry(new ZipArchiveEntry(fileAttach.name));
//		IOUtils.copy(new ByteArrayInputStream(fileAttach.body), logical_zip);
//		logical_zip.closeArchiveEntry();
//		logical_zip.finish();
//		return bo.getBytes();
//	}

//	public JavaMailSender getMailSender() {
//		return mailSender;
//	}

//	public void setMailSender(JavaMailSender mailSender) {
//		this.mailSender = mailSender;
//	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getNameAddress() {
		return nameAddress;
	}

	public void setNameAddress(String nameAddress) {
		this.nameAddress = nameAddress;
	}

	public String getControlMail() {
		return controlMail;
	}

	public void setControlMail(String controlMail) {
		this.controlMail = controlMail;
	}

	@Override
	public String send(Manager manager, String s, String s1, String s2, List<FileAttach> list) {
		return null;
	}
}
