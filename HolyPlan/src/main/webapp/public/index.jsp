<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>HolyPlan - Inicio</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body onload='document.loginForm.j_username.focus();'>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<div id="bienvenido">
			<h1>Bienvenido</h1>
			<h2>Organiza viajes con tus amigos de la forma m&aacute;s r&aacute;pida y sencilla
				con nuestra web</h2>
			<img src="<c:url value="/resources/img/imgInicio.jpg" />" />
		</div>
		<div id="login-box">
			<form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
				<table>
					<tr>
						<td>Usuario:</td>
						<td><input type='text' name='j_username' ></td>
					</tr>
					<tr>
						<td>Contrase&ntilde;a:</td>
						<td><input type='password' name='j_password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit" value="Entrar" /></td>
					</tr>
				</table>
			</form>		
			
			<p>¿Eres nuevo en HolyPlan?<a href="${pageContext.request.contextPath}/public/registroUsuario"> Reg&iacute;strate</a></p>
		</div>
	</div>
</body>
</html>