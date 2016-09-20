package blog;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

 




import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

public class OfySubscribeServlet extends HttpServlet {

	static {

        ObjectifyService.register(Subscriber.class);

    }
		
    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();


        String blogName = "The Caped Blog";

        Subscriber sub = new Subscriber(user);


        ofy().save().entity(sub).now();

        resp.sendRedirect("/ofyblog.jsp?blogName=" + blogName);

    }

}
