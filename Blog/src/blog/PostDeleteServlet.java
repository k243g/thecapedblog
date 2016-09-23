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

 







import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

public class PostDeleteServlet extends HttpServlet {

	static {

        ObjectifyService.register(Post.class);

    }
		
    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();


        String blogName = "The Caped Blog";
        
        String postUser = req.getParameter("postUser");
        
        String postId = req.getParameter("postId");
        

        List<Post> posts = ObjectifyService.ofy().load().type(Post.class).list();

        for(Post p: posts){
        	if(p.getId().toString().equals(postId)){
        		if(user.equals(p.getUser())){
        			ofy().delete().entity(p).now();
        		}
        	}
		}
        
        resp.sendRedirect("/ofyblog.jsp?blogName=" + blogName);
    }

}
