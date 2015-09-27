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
		<div id="cabe_izquierdo"><h1>HolyPlan</h1></div>
		<div id="cabe_derecho">
			<a href="${pageContext.request.contextPath}/logout"><img src="<c:url value="/resources/img/user_go.png"/>" /> Salir</a>
		</div>
	</div>
	<div id="cuerpo">
		<div id="lat_izquierdo">
		 	<p><a href="${pageContext.request.contextPath}/private/menu"><img src="<c:url value="/resources/img/house.png"/>" /> Men&uacute;</a></p>		 			 
		 	<p><a href="${pageContext.request.contextPath}/usuario/editarUsuario"><img src="<c:url value="/resources/img/datos.png"/>" /> Datos</a></p>
		 	<p><a href="${pageContext.request.contextPath}/usuario/amigos"><img src="<c:url value="/resources/img/group.png"/>" /> Amigos</a></p>
		 	<p><a href="${pageContext.request.contextPath}/checkListPersona/checkLists"><img src="<c:url value="/resources/img/list.png"/>" /> Listas personales</a></p>
		 	<p><a href="${pageContext.request.contextPath}/viaje/mostrarHistorialViajes"><img src="<c:url value="/resources/img/hourglass.png"/>" /> Historial de viajes</a></p>			 	
		</div>
		<div id="lat_derecho">
			<h1><img src="<c:url value="/resources/img/car_add.png" />" /> Nuevo Viaje</h1>
			<H2>Datos</H2>
			<form:form method="POST" modelAttribute="crearViajeForm" action="${pageContext.request.contextPath}/viaje/crearViaje">
				<table>
					<tr>
					    <td><form:label path="nombreViaje">Nombre:*</form:label></td>
						<td><form:input path="nombreViaje" /></td>
						<td><form:errors path="nombreViaje" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td><form:label path="fecha">Fecha:*</form:label></td>
					   	<td><form:input type="date" path="fecha" /></td>
					    <td><form:errors path="fecha" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td><form:label path="duracion">Duraci&oacute;n:</form:label></td>
						<td><form:input type="number" path="duracion" /></td>
						<td><form:errors path="duracion" cssClass="error-validacion" /></td>
					</tr>
					<tr>
						<td><form:label path="bote">Bote: </form:label></td>
						<td colspan="2"><form:input type="number" step="0.01" path="bote" /></td>
					</tr>
					<tr>
					    <td colspan="3"><form:label path="descripcion">Descripci&oacute;n:</form:label></td>
					</tr>
					<tr>
						<td colspan="2"><TEXTAREA name="descripcion" rows="5" cols="40">${crearViajeForm.descripcion}</TEXTAREA></td>
						<td><form:errors path="descripcion" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td colspan="3"><input type="submit" value="Confirmar"/></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>