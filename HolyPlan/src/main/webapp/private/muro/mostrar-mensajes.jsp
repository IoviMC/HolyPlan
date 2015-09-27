<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Mensajes</title>
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
			<h1><img src="<c:url value="/resources/img/email.png" />" /> Tema: ${tema.titulo}</h1>
			<H2>Mensajes</H2>
			<ul>
				<c:choose>
					<c:when test="${idRol eq 1}">
						<c:forEach items="${mensajes}" var="mensaje">
							<li class="encabezado">Enviado por ${mensaje.nombreUsuario}, el <fmt:formatDate value="${mensaje.fecha}" pattern="dd-MM-yyyy" /></li>													
							<li class="descripcion">${mensaje.descripcion}</li>
							<li><a href="${pageContext.request.contextPath}/muro/eliminarMensaje/${mensaje.id}/${tema.id}"><img src="<c:url value="/resources/img/papelera.png"/>" /> Borrar mensaje</a></li>
							<br>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${mensajes}" var="mensaje">
							<li class="encabezado">${mensaje.nombreUsuario} <fmt:formatDate value="${mensaje.fecha}" pattern="yyyy-MM-dd" /></li>													
							<li class="descripcion">${mensaje.descripcion}</li>
							<br>
						</c:forEach>					
					</c:otherwise>
				</c:choose>
			</ul>
			
			<H2>Nuevo mensaje</H2>
			<form:form method="POST" modelAttribute="crearMensajeForm" action="${pageContext.request.contextPath}/muro/crearMensaje/${tema.id}">
				<table>
					<tr>
					   	<td><TEXTAREA name="descripcion" rows="5" cols="40"></TEXTAREA></td>
					    <td><form:errors path="descripcion" cssClass="error-validacion" /></td>
					</tr>
	
					<tr>
					    <td colspan="3"><input type="submit" value="Enviar Mensaje"/></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>
