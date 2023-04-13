package com.trillon.camp.common.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.trillon.camp.common.code.Code;
import com.trillon.camp.common.code.ErrorCode;
import com.trillon.camp.common.exception.HandlableException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSender {
	
	private final JavaMailSender mailSender;
	
	public void send(String to, String subject, String html) {
		
		MimeMessage msg = mailSender.createMimeMessage();
			
		try {
			
			msg.setFrom(Code.SMTP_FROM.desc);
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject);
			msg.setContent(html, "text/html; charset=UTF-8");
			System.out.println("mailSender -> "+msg);
			
			
		} catch (MessagingException e) {
			throw new HandlableException(ErrorCode.FAILED_SEND_EMAIL);
		}
		finally {
			mailSender.send(msg);
		}
		
		
			
		
		
	}
	
	
	
	
	
	
	

}
