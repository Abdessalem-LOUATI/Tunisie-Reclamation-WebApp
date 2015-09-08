<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Authentification</title>
  <link rel="stylesheet" href="css/style.css">
  <link rel="icon" type="image/png" href="images/ic_launcher.png" />
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>Formulaire d'authentification</h1>
      <form:form method="POST" action="/reclamation/authenticationUser">
        <p><input type="email" name="email" value="" placeholder="E-mail" required ></p>
        <p><input type="password" name="password" value="" placeholder="Mot de passe"></p>
        <p>
	       <select required="required" name="typeUser">
		    	<option value="">Connecter en tantque ...</option>
				<option value=admin>Aministrateur</option>
				<option value=agent>Agent</option>
	       </select>
	    </p>
        <p class="submit"><input type="submit" name="commit" value="Login"></p>
      </form:form>
      <%
      	if(request.getAttribute("erreurAuthentification")!=null)
      	{	
      		%>
				<p style="text-align: center; color: #ff0000; font-weight: bold;"><spring:message code="authentification.erronee" /></p>		  
		 	<%      		
      	}
      %>
    </div>

    <div class="login-help">
     
    </div>
  </section>

  <section class="about">
    <p class="about-links">
      <a href="https://www.facebook.com/slouma.louati" target="_blank">Fb</a>
      <a href="mailto:abdessalemlouati@gmail.com" target="_blank">E-mail</a>
    </p>
    <p class="about-author">
      &copy; 2015&ndash; <a href="#" target="_blank">Abdessalem LOUATI</a> -
      <a href="http://www.enis.rnu.tn/" target="_blank">Ingénieur En informatique à l'ENIS</a><br>
      <a href="http://www.sifast.com/" target="_blank">SIFAST</a>
    </p>
  </section>
</body>
</html>
