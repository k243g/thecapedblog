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

 

public class OfySignBlogServlet extends HttpServlet {

	static {

        ObjectifyService.register(Post.class);

    }
		
    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();


        String blogName = "The Caped Blog";
        
        String content = req.getParameter("content");
        
        String title = req.getParameter("title");


        Post post = new Post(user, title, content);


        ofy().save().entity(post).now();
        
        Email dummy = new Email();
		dummy.sendEmail();

        resp.sendRedirect("/ofyblog.jsp?blogName=" + blogName);

    }

}