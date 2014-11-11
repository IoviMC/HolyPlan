<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Registro de usuario</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<h2>Registro</h2>
		<form:form method="POST" modelAttribute="registroUsuarioForm" action="${pageContext.request.contextPath}/public/registroUsuario">
			<table>
				<tr>
				    <td><form:label path="nombreUsuario">Nombre usuario:</form:label></td>
					<td><form:input path="nombreUsuario" /></td>
					<td><form:errors path="nombreUsuario" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="contrasena">Contrase&ntilde;a:</form:label></td>
				   	<td><form:password path="contrasena" showPassword="true" /></td>
				    <td><form:errors path="contrasena" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="contrasenaConf">Repite la contrase&ntilde;a:</form:label></td>
				    <td colspan="2"><form:password path="contrasenaConf" showPassword="true" /></td>
				</tr>
				<tr>
				    <td><form:label path="email">Direcci&oacute;n de correo:</form:label></td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td colspan="3"><input type="submit" value="Reg&iacute;strate"/></td>
				</tr>
			</table>
		</form:form>
	
		${mensajeRegistro}
		<p><a href="${pageContext.request.contextPath}/public/index">&Iacute;ndice</a><p>
	</div>
</body>
</html>
