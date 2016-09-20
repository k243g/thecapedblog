package blog;

import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Subscriber implements Comparable<Subscriber>{

	@Id Long id;
    User user;
    Date date;
    //String email;
	
    private Subscriber(){}
    
    public Subscriber(User user){
    	this.user = user;
    	date = new Date();
    }
    
    public Long getID(){
    	return id;
    }
    
    public User getUser(){
    	return user;
    }
    
    public String getEmail(){
    	return user.getEmail();
    }

	@Override
	public int compareTo(Subscriber o) {
		if (date.after(o.date)) {

            return 1;

        } else if (date.before(o.date)) {

            return -1;

        }

        return 0;
	}
    
    

}
