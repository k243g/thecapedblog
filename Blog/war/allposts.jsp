<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="blog.Post" %>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 

<html>

  <head>
	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

 

  <body>
  
  <h2 class="header">The </br> Caped </br> Blog</h2>

<%
    //String blogName = request.getParameter("blogName");
	String blogName = "The Caped Blog";
    
    pageContext.setAttribute("blogName", blogName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    
    if (user != null) {

      pageContext.setAttribute("user", user);
		%>
		<p align="left">Hello, ${fn:escapeXml(user.nickname)}! (You can
		
		<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
		
		<%
    } else {

		%>
		
		<p>Hello!
		
		<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
		
		to create new blog post.</p>
		
		<%

    }

%>

 
 <%
    
    if (user != null) {

      pageContext.setAttribute("user", user);
		%>
		<input type="button" class="button" value="Create Post" onclick="window.open('http://thecapedblog.appspot.com/postform.jsp')" />
		
		<%
    }

%>
 

<%

	ObjectifyService.register(Post.class);
    // Run an ancestor query to ensure we see the most up-to-date

    // view of the Greetings belonging to the selected blog.


	List<Post> posts = ObjectifyService.ofy().load().type(Post.class).list();
	Collections.sort(posts);
	Collections.reverse(posts);
	int limit =0;
    if (posts.isEmpty()) {
        %>

        <p align="center">${fn:escapeXml(blogName)} has no posts</p>

        <%

    } else {

        %>

        <p align="center">Latest posts in ${fn:escapeXml(blogName)}</p>

        <%

        for (Post post : posts) {

            pageContext.setAttribute("post_content",post.getContent());
            pageContext.setAttribute("post_user", post.getUser());
            pageContext.setAttribute("post_title", post.getTitle());
            pageContext.setAttribute("post_date", post.getDate());  

            %>
            
            <section >
            	<h1><b>${fn:escapeXml(post_title)}</b></h1>
            	<p class="para">${fn:escapeXml(post_content)}</p>
            	<p align="right"><strong>${fn:escapeXml(post_user.nickname)} </strong> </p>
            	<p align="right"> <strong>${fn:escapeXml(post_date)}</strong> </p>
            </section>
            

            <%
            
    		}

        }
%>



  </body>

</html>