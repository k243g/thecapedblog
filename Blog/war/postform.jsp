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
  
  <h2 class="header">  <span style="margin:0px auto; padding-top:20px;">
  		The&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br>   
  		Caped&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br>
  		Blog&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></h2>
  </br>
  <div class="header2">
  
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
		<script>
		   function checkForSubject(theForm) { // passing the form object
			   var val = theForm.title.value;
			   if (val==null || val.trim()=="") { 
			     alert('Please enter a subject before submitting a form.');
			     theForm.textField1.focus();
			     return false; // cancel submission
			  }
			  return true; // allow submit
			}
		</script>
		<form action="/ofysign" method="post" onsubmit="return checkForSubject(this)">
		
		  <div style="font-family: Courier; font-weight: bold; font-size: 140%; text-align: left; vertical-align: text-middle;">
		  
		  Title:&nbsp;
		  <input style="width: 300px; height: 24px;" type="text" name="title"> </br>
		  Post:&nbsp;&nbsp;
		  <textarea style="width: 83%; height: 20%; vertical-align: top;" name="content" rows="3" cols="60"></textarea>
		  </div>
	
	      <div style="text-align: center; padding-top: 15px;"><input type="submit" value="Submit Post" />
	      <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>
	      </br></br></div>
	
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
</div>



 

  </body>

</html>