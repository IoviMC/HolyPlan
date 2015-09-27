<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Ver Viaje</title>
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
			<p><a href="${pageContext.request.contextPath}/historico/mostrarGastos"><img src="<c:url value="/resources/img/coins.png"/>" /> Gastos</a></p>
		</div>
		<div id="lat_derecho">
			<h1><img src="<c:url value="/resources/img/world.png"/>" /> ${viaje.nombreViaje}</h1>
			<div id="late_der_izquierdo">
				<h2>Datos</h2>
				<p>Fecha: <fmt:formatDate value="${viaje.fecha}" pattern="yyyy-MM-dd" /></p>
				<c:if test="${not empty viaje.bote}">
					<p>Bote: ${viaje.bote} &euro;</p>
				</c:if>
				<p>${viaje.descripcion}</p>
				
				<h2>Usuarios</h2>
				<table class="izquierda">
		    		<tr class="datos">
		    			<th>Asistentes</th>
					</tr> 
			    	<c:forEach items="${usuariosConfirmados}" var="usuarioConfirmado">
						<tr class="datos">
							<td>${usuarioConfirmado.nombreUsuario}</td>
						</tr>
					</c:forEach>
				</table>
				<table  class="izquierda">
		    		<tr class="datos">
		    			<th>Pendientes</th>
					</tr> 
			    	<c:forEach items="${usuariosPendientes}" var="usuarioPen">
						<tr class="datos">
							<td>${usuarioPen.nombreUsuario}</td>
						</tr>
					</c:forEach>
				</table>
			</div>

			<div id="late_der_derecho">
				<h2>Muro</h2>
				<c:choose>
					<c:when test="${empty temas}">
						<p>No hay temas en el muro</p>
					</c:when>
					<c:otherwise>
		    		<ul>
						<c:forEach items="${temas}" var="tema">
							<li><a href="${pageContext.request.contextPath}/historico/mostrarMensajes/${tema.id}">${tema.titulo}</a></li>
						</c:forEach>
					</ul>
					</c:otherwise>
				</c:choose>
				
				<h2>Listas de viaje</h2>
				<c:choose>
					<c:when test="${empty checkLists}">
						<p>No hay listas para este viaje</p>
					</c:when>
					<c:otherwise>
				    	<ul>
					    	<c:forEach items="${checkLists}" var="lista">
								<li><a href="${pageContext.request.contextPath}/historico/modificarCheckList/${lista.id}">${lista.nombre}</a></li>	
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div id="entero">
				<h2>Planings</h2>
				<c:choose>
					<c:when test="${empty planings}">
						<p>No hay planings para este viaje</p>
					</c:when>
					<c:otherwise>
			    		<ul>
					    	<c:forEach items="${planings}" var="planing">
								<li><a href="${pageContext.request.contextPath}/historico/mostrarPlaning/${planing.id}">${planing.nombre}</a></li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>