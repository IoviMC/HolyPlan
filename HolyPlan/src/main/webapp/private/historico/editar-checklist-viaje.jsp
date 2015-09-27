<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Modificar estado</title>
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
		 	<p><a href="${pageContext.request.contextPath}/historico/mostrarViajeHistorial/${idViaje}"><img src="<c:url value="/resources/img/car.png"/>" /> Volver al viaje</a></p>
			<p><a href="${pageContext.request.contextPath}/historico/mostrarGastos"><img src="<c:url value="/resources/img/coins.png"/>" /> Gastos</a></p>
		</div>
		
		<div id="lat_derecho">
			<h1><img src="<c:url value="/resources/img/list.png"/>" /> ${modificarCheckListViajeForm.nombre}</h1>
			<h2>Modificar estado del contenido</h2>
			<form:form method="POST" modelAttribute="modificarCheckListViajeForm" action="${pageContext.request.contextPath}/checkListViaje/modificarCheckListViaje">
				<form:input type="hidden" path="id" />
				<form:input type="hidden" path="nombre" />
				<ul>
					<c:forEach items="${modificarCheckListViajeForm.items}" var="item">
						<c:choose>
							<c:when test="${item.realizado}">
								<li><input name="itemsId" value="${item.id}" type="checkbox" disabled="disabled" checked="checked"/> ${item.descripcion} (Usuario: ${item.nombreUsuario})</li>						
							</c:when>
							<c:otherwise>
								<li><input name="itemsId" value="${item.id}" type="checkbox" disabled="disabled"/> ${item.descripcion}</li>						
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</form:form>
		</div>
	</div>
</body>
</html>