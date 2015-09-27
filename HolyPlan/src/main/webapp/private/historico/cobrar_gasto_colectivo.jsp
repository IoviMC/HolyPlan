<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Cobrar deuda</title>
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
			<p><a href="${pageContext.request.contextPath}/gasto/mostrarGastos"><img src="<c:url value="/resources/img/coins.png"/>" /> Gastos</a></p>	
		</div>
		<div id="lat_derecho">
			<h1><img src="<c:url value="/resources/img/money.png"/>" /> Cobrar deuda de un gasto colectivo</h1>
			<h2>Usuarios con deuda</h2>
			<c:choose>
			    <c:when test="${empty deudas}">
			        <p>No hay usuarios con deuda para este pago</p>
			    </c:when>
			    <c:otherwise>
					<p>Seleccione los usuarios que pagaron su deuda</p>
					<form:form action="${pageContext.request.contextPath}/historico/cobrarGastoColectivo/${idPago}" method="POST">
						<ul>
							<c:forEach items="${deudas}" var="deuda">
								<li><input type="checkbox" name="deudasId" value="${deuda.id}">${deuda.nombreUsuario}<br></li>
							</c:forEach>
						</ul>
						<p><input type="submit" value="Guardar"></p>
					</form:form>
			    </c:otherwise>
			</c:choose>
			${mensajeConfirmacion}
			<p><a href="${pageContext.request.contextPath}/historico/verPagos"><img src="<c:url value="/resources/img/planing.png"/>" /> Volver al desglose de pagos</a></p>
		</div>
	</div>
</body>
</html>