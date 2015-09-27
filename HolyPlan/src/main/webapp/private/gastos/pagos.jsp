<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Pagos</title>
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
			<h1><img src="<c:url value="/resources/img/planing.png"/>" /> Desglose de pagos del viaje</h1>
			<c:choose>
				<c:when test="${empty pagos}">
					<p>No hay pagos asociados a este viaje</p>
				</c:when>
				<c:otherwise>
					<h2>Pagos de cada usuario</h2>		
					<table>
						<tr class="datos">
							<th>Gasto</th>
							<th>Usuario</th>
							<th>Importe</th>
							<th>Cobros pendientes</th>
						</tr>
						<c:forEach items="${pagos}" var="pago">
							<tr class="datos">
								<td>${pago.concepto}</td>
								<td>${pago.nombreUsuario}</td>
								<td class="importe">${pago.importe} &euro;</td>
								<c:choose>
					    		<c:when test="${pago.pagoGastoTotal and pago.nombreUsuario eq nombreUsuario}">					
									<td><a href="${pageContext.request.contextPath}/gasto/cobrarGastoColectivo/${pago.id}">Cobrar deuda</a></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
					
					<h2>Pago total por usuario</h2>
					<table>
						<tr class="datos">
							<th>Usuario</th>
							<th>Pag&oacute;</th>
							<th>Gasto por persona</th>
							<th>Pago - Gasto</th>
						</tr>
						<c:forEach items="${pagoTotalPorUsuario}" var="pagoTotal">
							<tr class="datos">
								<td>${pagoTotal.key}</td>
								<td class="importe">${pagoTotal.value} &euro;</td>
								<td class="importe">${gastoTotalUsuario} &euro;</td>						
								<td class="importe">${pagoTotal.value - gastoTotalUsuario} &euro;</td>						
							</tr>
						</c:forEach>
					</table>
					<c:if test="${idRol eq 1}">
						<p><a title="Las deudas de todos los usuarios quedarán amortizadas" href="${pageContext.request.contextPath}/gasto/saldarCuenta"><img alt="icono billetes" src="<c:url value="/resources/img/money.png"/>" /> Saldar cuenta</a></p>
					</c:if>
				</c:otherwise>
			</c:choose>
			
			<h2>Bote</h2>
			<p>Gasto total del bote: ${gastoTotalBote} &euro;</p>
			<p>Bote actual: ${bote} &euro;</p>
		</div>
	</div>
</body>
</html>