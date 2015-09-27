<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Organizar usuarios</title>
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
			<h1><img src="<c:url value="/resources/img/group.png"/>" /> Organizar usuarios</h1>
			<div id="late_der_izquierdo">
				<h2>Invitados</h2>
				<c:choose>
				    <c:when test="${empty invitados}">
				        <p>No hay usuarios invitados al viaje. Agrege a sus amigos!</p>
				    </c:when>
				    <c:otherwise>
				    	<table>
				    		<tr>
				    			<th>Nombre</th>
				    			<th>Eliminar</th>
				    			<c:if test="${idRol eq 1}">
				    				<th>Organizador</th>
				    			</c:if>
				    		</tr>
							<c:forEach items="${invitados}" var="invitado">
								<tr>
									<td>${invitado.nombreUsuario}</td>
									<td><a href="${pageContext.request.contextPath}/viaje/eliminarInvitado/${invitado.id}">
									<img src="<c:url value="/resources/img/borrar.png" />" /></a></td>
									<c:if test="${idRol eq 1}">
										<td><a href="${pageContext.request.contextPath}/viaje/agregarOrganizador/${invitado.id}" title="Agregar a organizadores">
											<img src="<c:url value="/resources/img/user_add.png" />" /></a></td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
				    </c:otherwise>
				</c:choose>
				<h2>Organizadores</h2>
				<c:choose>
				    <c:when test="${empty organizadores}">
				        <p>No hay ning&uacute;n usuario con el rol de organizador</p>
				    </c:when>
				    <c:otherwise>
				    	<c:choose>
			    		<c:when test="${idRol eq 1}">
			    			<table>
								<c:forEach items="${organizadores}" var="organizador">
									<tr>
										<td>${organizador.nombreUsuario}</td>
										<c:if test="${idRol eq 1}">
											<td><a href="${pageContext.request.contextPath}/viaje/eliminarOrganizador/${organizador.id}">
											<img src="<c:url value="/resources/img/borrar.png" />" /> Eliminar</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</table>
			    		</c:when>
			    		<c:otherwise>
			    			<ul>
			    				<li>${organizador.nombreUsuario}</li>
					    	</ul>
						</c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>				
			</div>
			<div id="late_der_derecho">
				<h2>Buscar usuario</h2>
				<p>Invita a usuarios de HolyPlan. Busca a los usuarios por el nombre e invitalos al viaje</p>
				<form:form method="POST" modelAttribute="buscarUsuarioForm" action="${pageContext.request.contextPath}/viaje/invitarUsuario">
					<table>
						<tr>
							<td><form:input path="nombreUsuario" placeholder="Buscar por nombre" /></td>
							<td colspan="3"><input type="submit" value="Invitar usuario"/></td>
						</tr>
						<tr>
						    <td><form:errors path="nombreUsuario" cssClass="error-validacion" /></td>
						</tr>
					</table>
				</form:form>
				
				<h2>Amigos</h2>
				<c:choose>
				    <c:when test="${empty amigosNoInvitados}">
				        <p>No tiene amigos disponibles para invitar</p>
				    </c:when>
				    <c:otherwise>
						<p>Seleccione los amigos que desea invitar</p>
						<form:form action="${pageContext.request.contextPath}/viaje/invitarAmigos" method="POST">
							<ul>
								<c:forEach items="${amigosNoInvitados}" var="amigo">
									<li><input type="checkbox" name="amigosId" value="${amigo.id}"> ${amigo.nombreUsuario}</li>
								</c:forEach>
								<li><input type="submit" value="Invitar"></li>
							</ul>
						</form:form>
				    </c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>
