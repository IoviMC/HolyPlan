<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Modificar Viaje</title>
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
			<h1><img src="<c:url value="/resources/img/editar.png"/>" /> Editar planning</h1>
			<H2>Datos</H2>
			<form:form method="POST" modelAttribute="editarPlaningForm" action="${pageContext.request.contextPath}/planing/modificarPlaning">
				<table>
					<tr>
					    <td><form:label path="nombre">Nombre:</form:label></td>
						<td><form:input path="nombre" /></td>
						<td><form:errors path="nombre" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td colspan="3"><form:label path="descripcion">Descripci&oacute;n:</form:label></td>
					</tr>
					<tr>
						<td colspan="2"><TEXTAREA name="descripcion" rows="5" cols="40">${editarPlaningForm.descripcion}</TEXTAREA></td>
						<td><form:errors path="descripcion" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td colspan="3"><input type="submit" value="Confirmar"/></td>
					</tr>
					<tr>
						<td><form:input type="hidden" path="id" /></td>
					</tr>
				</table>
			</form:form>
			<p>${mensajeConfirmacion}</p>
			<%-- 
			<c:forEach items="${paradas}" var="parada">
				<h2>Parada: ${parada.nombre}</h2>
				Fecha: <fmt:formatDate value="${parada.fecha}" pattern="yyyy-MM-dd HH:mm" />
				<p>Lugar: ${parada.lugar}</p>
				<c:if test="${idRol eq 1}">
					<p><a href="${pageContext.request.contextPath}/parada/eliminarParada/${parada.id}">Eliminar Parada</a><p>
				</c:if>
			</c:forEach>--%>
		</div>
	</div>
</body>
</html>