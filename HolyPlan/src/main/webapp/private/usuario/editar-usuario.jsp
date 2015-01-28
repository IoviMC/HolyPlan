<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Modificar Cuenta</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<form:form method="POST" modelAttribute="editarUsuarioForm" action="${pageContext.request.contextPath}/usuario/editarUsuario">
			<table>
				<tr>
				    <td><form:label path="nombreUsuario">Nombre usuario:</form:label></td>
					<td><form:input path="nombreUsuario" /></td>
					<td><form:errors path="nombreUsuario" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="email">Email:</form:label></td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td colspan="3"><input type="submit" value="Guardar cambios"/></td>
				</tr>
			</table>
		</form:form>
		${mensajeCambios}
		${mensajeCorreo}
		<p><a href="${pageContext.request.contextPath}/usuario/editarContrasena">Modificar contrase&ntilde;a</a><p>
		<p><a href="${pageContext.request.contextPath}/usuario/desactivarCuenta">Desactivar mi cuenta</a></p>
		<p><a href="${pageContext.request.contextPath}/private/menu">Men&uacute;</a></p>
	</div>
</body>
</html>