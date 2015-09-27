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
		 	<hr>
		 	<p><a href="${pageContext.request.contextPath}/viaje/mostrarViaje/${idViaje}"><img src="<c:url value="/resources/img/car.png"/>" /> Volver al viaje</a></p>
			<p><a href="${pageContext.request.contextPath}/gasto/mostrarGastos"><img src="<c:url value="/resources/img/coins.png"/>" /> Gastos</a></p>
		 	<c:if test="${idRol eq 1 or idRol eq 2}">
				<p><a href="${pageContext.request.contextPath}/viaje/editarViaje"><img src="<c:url value="/resources/img/editar.png"/>" /> Editar viaje</a></p>
				<p><a href="${pageContext.request.contextPath}/viaje/organizarUsuarios"><img src="<c:url value="/resources/img/group.png"/>" /> Organizar usuarios</a></p>	
			</c:if>
			<c:if test="${idRol eq 1}">
				<p><a href="${pageContext.request.contextPath}/viaje/cancelarViaje"><img src="<c:url value="/resources/img/borrar.png"/>" /> Cancelar viaje</a></p>
			</c:if>			 	
		</div>
		<div id="lat_derecho">
			<h1><img src="<c:url value="/resources/img/application_add.png" />" /> Nueva Parada</h1>
			<H2>Datos</H2>
			<form:form method="POST" modelAttribute="crearParadaForm" action="${pageContext.request.contextPath}/parada/crearParada/${idPlaning}">
				<table>
					<tr>
					    <td><form:label path="nombre">Nombre:</form:label></td>
						<td><form:input path="nombre" /></td>
						<td><form:errors path="nombre" cssClass="error-validacion" /></td>
					</tr>
					<tr>
						<td><form:label path="lugar">Lugar:</form:label></td>
						<td><form:input path="lugar" /></td>
						<td><form:errors path="lugar" cssClass="error-validacion" /></td>
					</tr>
					<tr>
						<td><form:label path="fecha">Fecha y hora:</form:label></td>
						<td><form:input type="datetime-local" pattern="yyyy-MM-dd HH:mm" path="fechaString" min="${fechaViaje}"></form:input></td>
						<td><form:errors path="fecha" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td colspan="3"><input type="submit" value="Confirmar"/></td>
					</tr>
				</table>
			</form:form>
			<a href="${pageContext.request.contextPath}/viaje/mostrarViaje/${idViaje}"><img src="<c:url value="/resources/img/saltar_paso.png"/>" /> Saltar este paso &#40;se puede a&ntilde;adir en cualquier momento&#41;</a>
		</div>
	</div>
</body>
</html>