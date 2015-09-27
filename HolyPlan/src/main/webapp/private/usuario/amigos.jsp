<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Amigos</title>
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
			<h1><img src="<c:url value="/resources/img/group.png"/>" /> Amigos</h1>
			<div id="late_der_izquierdo">
				<h2>Mis amigos</h2>
				<form:form action="${pageContext.request.contextPath}/usuario/eliminarAmigo" method="POST">
					<c:choose>
					    <c:when test="${empty amigos}">
					        <p>No tienes ning&uacute; amigo. Busca a tus amigos.</p>
					    </c:when>
					    <c:otherwise>
							<table>
								<c:forEach items="${amigos}" var="amigo">
									<tr>
										<td>${amigo.nombreUsuario}</td>
										<td><button class="icono eliminar" type="submit" name="idAmigo" value="${amigo.id}"></button></td>
									</tr>
								</c:forEach>
							</table>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<div id="late_der_derecho">
				<h2>B&uacute;squeda de amigos</h2>
				<p>Busca a tus amigos entre los usuario de HolyPlan. As&iacute; podr&aacute;s invitarlos a los viajes que organices.</p>
				<form:form method="POST" modelAttribute="buscarUsuarioForm" action="${pageContext.request.contextPath}/usuario/anadirAmigo">
					<table>
						<tr>
							<td><form:input path="nombreUsuario" placeholder="Buscar por nombre" /></td>
							<td colspan="3"><input type="submit" value="A&ntilde;adir amigo"/></td>
						</tr>
						<tr>
						    <td><form:errors path="nombreUsuario" cssClass="error-validacion" /></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>