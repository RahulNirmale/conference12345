package com.google.devrel.training.conference.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;



import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Servlet implementation class SendConfirmationEmailServlet
 */
public class SendConfirmationEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendConfirmationEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		String email=request.getParameter("email");
		
		String conferenceInfo=request.getParameter("conferenceInfo");
		
		Properties props = new Properties();
		
		Session session = Session.getDefaultInstance(props,null);
		
		String body ="Hi, you have created the following conference. \n" + conferenceInfo;
		
		try{
			
			Message message=new MimeMessage(session);
			InternetAddress from =new InternetAddress(
					String.format("noreply@%s.appspotmail.com",SystemProperty.applicationId.get()), "Conference Central");
					
			   message.setFrom(from);
			   message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, ""));
			   message.setSubject("You created a new Conference!");
			   message.setText(body);
			   Transport.send(message);
			  
		} catch (MessagingException e) {
		
		    throw new RuntimeException(e);
		}
		
	}

}
