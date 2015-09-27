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
		 	<%-- <p><a href="${pageContext.request.contextPath}/planing/mostrarPlanings"><img src="<c:url value="/resources/img/planing.png"/>" /> Ver planings</a><p>	--%>
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
			<h1><img src="<c:url value="/resources/img/car.png"/>" /> ${viaje.nombreViaje}</h1>
			<div id="late_der_izquierdo">
				<h2>Datos</h2>
				<p>Fecha: <fmt:formatDate value="${viaje.fecha}" pattern="yyyy-MM-dd" /></p>
				<c:if test="${not empty viaje.bote}">
					<p>Bote: ${viaje.bote} &euro;</p>
				</c:if>
				<p>${viaje.descripcion}</p>
				
				<h2>Usuarios</h2>
				<table class="izquierda">
					<tr>
		    			<th>Asistentes</th>
					</tr> 
			    	<c:forEach items="${usuariosConfirmados}" var="usuarioConfirmado">
						<tr>
							<td>${usuarioConfirmado.nombreUsuario}</td>
						</tr>
					</c:forEach>
				</table>
				<table  class="izquierda">
		    		<tr>
		    			<th>Pendientes</th>
					</tr> 
			    	<c:forEach items="${usuariosPendientes}" var="usuarioPen">
						<tr>
							<td>${usuarioPen.nombreUsuario}</td>
						</tr>
					</c:forEach>
				</table>
				<div id="entero">
					<h2>Asistencia</h2>
					<c:choose>
						<c:when test="${viajeAceptado}">
							<form:form action="${pageContext.request.contextPath}/viaje/editarEstadoViaje" method="POST">
								<p><input type="radio" name="idEstado" value="2">Cancelar asistencia</p>
								<p><input type="submit" value="Confirmar"></p>
							</form:form>
						</c:when>
						<c:otherwise>
							<form:form action="${pageContext.request.contextPath}/viaje/editarEstadoViaje" method="POST">
								<p><input type="radio" name="idEstado" value="1">Asistir&eacute; &nbsp;<input type="radio" name="idEstado" value="2">No asistir&eacute;</p>
								<p><input type="submit" value="Confirmar"></p>
							</form:form>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<div id="late_der_derecho">				
				<h2>Muro</h2>
				<c:choose>
					<c:when test="${empty temas}">
						<p>No hay temas en el muro</p>
					</c:when>
					<c:otherwise>
						<c:choose>
					    	<c:when test="${idRol eq 1}"> 
					    		<table>
						    	<c:forEach items="${temas}" var="tema">
									<tr>
										<td><a title="Ver mensajes" href="${pageContext.request.contextPath}/muro/mostrarMensajes/${tema.id}">
											<img alt="icono mensaje" src="<c:url value="/resources/img/email.png" />" /> ${tema.titulo}</a></td>
										<td><a title="Eliminar tema" href="${pageContext.request.contextPath}/muro/eliminarTema/${tema.id}">
											<img alt="icono eliminar" src="<c:url value="/resources/img/borrar.png" />" /></a></td>
									</tr>	
								</c:forEach>
								</table>
					    	</c:when>
					    	<c:otherwise>
					    		<table>
									<c:forEach items="${temas}" var="tema">
										<tr>
											<td><a title="Ver mensajes" href="${pageContext.request.contextPath}/muro/mostrarMensajes/${tema.id}">
												<img alt="icono mensaje" src="<c:url value="/resources/img/email.png" />" /> ${tema.titulo}</a></td>
										</tr>
									</c:forEach>
								</table>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<p><a href="${pageContext.request.contextPath}/muro/crearTema"><img src="<c:url value="/resources/img/application_add.png" />" /> Nuevo Tema</a></p>
				
				<h2>Listas de viaje</h2>
				<c:choose>
					<c:when test="${empty checkLists}">
						<p>No hay listas para este viaje</p>
					</c:when>
					<c:otherwise>
						<c:choose>
					    	<c:when test="${idRol eq 3}"> 
					    		<table>
							    	<c:forEach items="${checkLists}" var="lista">
										<tr>
											<td><a title="Mostrar lista" href="${pageContext.request.contextPath}/checkListViaje/modificarCheckList/${lista.id}">
												<img alt="icono lista" src="<c:url value="/resources/img/list.png" />" /> ${lista.nombre}</a></td>	
									</c:forEach>
								</table>
					    	</c:when>
					    	<c:otherwise>
					    		<table>
									<c:forEach items="${checkLists}" var="lista">
										<tr>
											<td><a title="Mostrar lista" href="${pageContext.request.contextPath}/checkListViaje/modificarCheckList/${lista.id}">
												<img alt="icono lista" src="<c:url value="/resources/img/list.png" />" /> ${lista.nombre}</a></td>
											<td><a title="Eliminar lista" class="eliminar" href="${pageContext.request.contextPath}/checkListViaje/eliminarCheckList/${lista.id}">
												<img alt="icono eliminar" src="<c:url value="/resources/img/borrar.png" />" /></a></td>
										</tr>
									</c:forEach>
								</table>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<c:if test="${idRol ne 3}">
					<p><a href="${pageContext.request.contextPath}/checkListViaje/crearCheckList"><img alt="icono a&ntilde;adir lista" src="<c:url value="/resources/img/application_add.png" />" /> Nueva Lista</a></p>
				</c:if>
			</div>
			
			<div id="entero">
				<h2>Plannings</h2>
				<c:choose>
					<c:when test="${empty planings}">
						<p>No hay planings para este viaje</p>
					</c:when>
					<c:otherwise>
			    		<table class="datos">
					    	<c:forEach items="${planings}" var="planing">
								<tr>
									<td>${planing.nombre}</td>
									<td><a href="${pageContext.request.contextPath}/planing/mostrarPlaning/${planing.id}"><img src="<c:url value="/resources/img/planing.png"/>" /> Mostrar</a></td>
									<c:if test="${idRol ne 3}">
										<td><a href="${pageContext.request.contextPath}/planing/modificarPlaning/${planing.id}"><img src="<c:url value="/resources/img/editar.png"/>" /> Editar</a></td>
									</c:if>
									<c:if test="${idRol eq 1}">
										<td><a href="${pageContext.request.contextPath}/planing/eliminarPlaning/${planing.id}"><img src="<c:url value="/resources/img/borrar.png"/>" /> Eliminar</a></td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
				<c:if test="${idRol ne 3}">
					<p><a href="${pageContext.request.contextPath}/planing/crearPlaning"><img src="<c:url value="/resources/img/planing_add.png"/>" /> Nuevo planning</a><p>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>