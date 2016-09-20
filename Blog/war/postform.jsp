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
    
    pageContext.setAttribute("user", user);

    
    ObjectifyService.register(Post.class);
    
    %>

<%
    
    if (user != null) {

      pageContext.setAttribute("user", user);
		%>
		<form action="/ofysign" method="post">
		
		  <div>Title: <input type="text" name="title"><br> </div>
		
	      <div><textarea name="content" rows="3" cols="60"></textarea></div>
	
	      <div><input type="submit" value="Create Post" /></div>
	
	      <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>
	
	    </form>
		
		<%
    }else {

        %>

        <p align="center">Please sign in to make new blog post.</p>
        <p>Hello!
		
		<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
		
		to create new blog post.</p>

        <%
    }
	
	

%>



 

  </body>

</html>