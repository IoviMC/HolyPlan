<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Editar usuario</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<div id="cabe_izquierdo"><h1>HolyPlan</h1></div>
		<div id="cabe_derecho">
			Hola ${nombreUsuario}! <a href="${pageContext.request.contextPath}/logout"><img src="<c:url value="/resources/img/user_go.png"/>" /> Salir</a>
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
			<h1><img src="<c:url value="/resources/img/datos.png"/>" /> Configuraci&oacute;n de usuario</h1>
			<form:form method="POST" modelAttribute="editarUsuarioForm" action="${pageContext.request.contextPath}/usuario/editarUsuario">
				<h2>Informaci&oacute;n general</h2>
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
			<p>${mensajeCorreo}</p>
			
			<form:form method="POST" modelAttribute="editarContrasenaForm" action="${pageContext.request.contextPath}/usuario/editarContrasena">
				<h2>Cambiar contrase&ntilde;a</h2>
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
			<p>${mensajeContrasena}</p>

			<p><a href="${pageContext.request.contextPath}/usuario/desactivarCuenta"><img src="<c:url value="/resources/img/user_delete.png"/>" /> Desactivar mi cuenta</a></p>
		</div>
	</div>
</body>
</html>