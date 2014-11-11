<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Error login</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<div id="lat-izq">
			<p>Usuario o contrase&ntilde;a incorrectos</p>
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