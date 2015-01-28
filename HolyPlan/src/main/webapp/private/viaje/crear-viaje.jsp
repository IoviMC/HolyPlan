<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Crear Viaje</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<H2>Crear Viaje</H2>
		<form:form method="POST" modelAttribute="crearViajeForm" action="${pageContext.request.contextPath}/viaje/crearViaje">
			<table>
				<tr>
				    <td><form:label path="nombreViaje">Nombre:</form:label></td>
					<td><form:input path="nombreViaje" /></td>
					<td><form:errors path="nombreViaje" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="fecha">Fecha:</form:label></td>
				   	<td><form:input type="date" path="fecha" /></td>
				    <td><form:errors path="fecha" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="duracion">Duraci&oacute;n:</form:label></td>
					<td><form:input type="number" path="duracion" /></td>
					<td><form:errors path="duracion" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td><form:label path="descripcion">Descripci&oacute;n:</form:label></td>
					<td><form:input type="textarea" cols="50" rows="5" path="descripcion" /></td>
					<td><form:errors path="descripcion" cssClass="error-validacion" /></td>
				</tr>
				<tr>
				    <td colspan="3"><input type="submit" value="Confirmar"/></td>
				</tr>
			</table>
		</form:form>
		<p>${mensajeConfirmacion}</p>
		<p><a href="${pageContext.request.contextPath}/private/menu">Men&uacute;</a><p>
	</div>
</body>
</html>