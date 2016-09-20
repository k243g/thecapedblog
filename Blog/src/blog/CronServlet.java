package blog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	
	
	static
	{
		ObjectifyService.register(Subscriber.class);
		ObjectifyService.register(Post.class);
	}
	
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props,null);
			
		try {
			
			_logger.info("Sending emails to subscribers");
			List<Subscriber> subs = ObjectifyService.ofy().load().type(Subscriber.class).list();
			List<Post> posts = ObjectifyService.ofy().load().type(Post.class).list();
			Collections.sort(posts);
			Collections.reverse(posts);
			
			for(Subscriber s: subs){
				_logger.info("Sending email to:"+s.getEmail());
				//make email
				try{
					
					Message msg = new MimeMessage(session);
					_logger.info("1");
					msg.setFrom(new InternetAddress("admin@thecapedblog.appspotmail.com", "The Caped Admin"));
					//msg.setFrom(new InternetAddress(s.getEmail(), "The Caped Admin"));
					_logger.info("2");
					msg.addRecipient(Message.RecipientType.TO,
					                   new InternetAddress(s.getEmail(), "Mr. User"));
					_logger.info("3");
					msg.setSubject("Your Example.com account has been activated");
					_logger.info("4");
					//Transport.sendMessage(msg, null);
					Transport.send(msg);
					
					
					_logger.info("Sent email to:"+s.getEmail());
				
				
				}catch (AddressException e) {
				      // ...
					_logger.info("A");
			    } catch (MessagingException e) {
			      // ...
			    	_logger.info("M");
			    } catch (UnsupportedEncodingException e) {
			      // ...
			    	_logger.info("U");
			    }
				
				
				//need to get the posts only from today
				//for (Post post : posts) {}
			}
			//Put your logic here
			//BEGIN
			
			
			//END
		}
		catch (Exception ex) {
			//Log any exceptions in your Cron Job
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}