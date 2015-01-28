<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Modificar contrase&ntilde;a</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<form:form method="POST" modelAttribute="editarContrasenaForm" action="${pageContext.request.contextPath}/usuario/editarContrasena">
			<table>
				<tr>
				    <td><form:label path="contrasenaActual">Contrase&ntilde;a actual:</form:label></td>
					<td><form:password path="contrasenaActual" /></td>
					<td><form:errors path="contrasenaActual" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="contrasenaNueva">Contrase&ntilde;a nueva:</form:label></td>
					<td><form:password path="contrasenaNueva" /></td>
					<td><form:errors path="contrasenaNueva" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="contrasenaNuevaConf">Confirma la nueva contrase&ntilde;a:</form:label></td>
					<td><form:password path="contrasenaNuevaConf" /></td>
					<td><form:errors path="contrasenaNuevaConf" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td colspan="3"><input type="submit" value="Guardar cambios"/></td>
				</tr>
			</table>
		</form:form>
		${mensajeContrasena}
	</div>
</body>
</html>