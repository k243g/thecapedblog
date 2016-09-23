package blog;

import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.googlecode.objectify.ObjectifyService;

public class Email {
	
	static {

        ObjectifyService.register(Post.class);
        ObjectifyService.register(Subscriber.class);

    }
	
	//private Date todaysDate = new Date();
	
	
	public Email(){
		
	}
	
	public void sendEmail(){
		
		
		List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
    	Collections.sort(subscribers);
    	
    	List<Post> posts = ObjectifyService.ofy().load().type(Post.class).list();
    	Collections.sort(posts);
    	Collections.reverse(posts);
    	
    	StringBuilder emailBody = new StringBuilder();
		emailBody.append("Posts: \n\n");
    	
    	//ArrayList<Post> newPosts = new ArrayList<Post>();
    	//Date todaysDate = new Date();
    	//Calendar calTD = Calendar.getInstance();
    	//Calendar cal = Calendar.getInstance();
    	//calTD.setTime(todaysDate);
    	//int tYear = calTD.get(Calendar.YEAR);
    	//int tMonth = calTD.get(Calendar.MONTH);
    	//int tDay = calTD.get(Calendar.DAY_OF_MONTH);
    	
    	
    	for(Post p: posts){  	
    		//cal.setTime(p.getDate());
    		//if( (cal.get(Calendar.DAY_OF_MONTH) == tDay) )
    		//if((todaysDate.getDay() == p.getDate().getDay()) && (todaysDate.getMonth() == p.getDate().getMonth() ) )
    		{
    			emailBody.append(p.getTitle());
        		emailBody.append("\n");
        		emailBody.append(p.getContent());
        		emailBody.append("\n\n\n");
    		}
    		
    	}
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        for(Subscriber sub: subscribers){
        	
            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("admin@thecapedblog.appspotmail.com", "The Caped Admin"));
                msg.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(sub.getEmail(), "The Caped User"));
                msg.setSubject("New Posts for Today");
                msg.setText(emailBody.toString());
               
                Transport.send(msg);
                
              } catch (AddressException e) {
                // ...
              } catch (MessagingException e) {
                // ...
              }catch (UnsupportedEncodingException e) {
                  // ...
              } 
              // [END simple_example]
        	
        	
        }


	}

}
