<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Gastos</title>
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
			<h1><img src="<c:url value="/resources/img/coins.png"/>" /> Gastos de ${nombreViaje}</h1>
			<h2>Gastos Colectivos</h2>
			<table>
				<c:choose>
	    		<c:when test="${idRol eq 1 || idRol eq 2}">
					<tr class="datos">
						<th>Concepto</th>
						<th>Importe</th>
						<th>Fecha</th>
						<th>Editar</th>
						<th>Eliminar</th>
					</tr>
					<c:forEach items="${gastosColectivos}" var="gasto">
						<tr class="datos">
							<td>${gasto.concepto}</td>
							<td>${gasto.importe} &euro;</td>
							<td><fmt:formatDate value="${gasto.fecha}" pattern="yyyy-MM-dd" /></td>
							<td><a href="${pageContext.request.contextPath}/historico/modificarGastoColectivo/${gasto.id}"><img src="<c:url value="/resources/img/editar.png" />" /></a></td>	
							<td><a href="${pageContext.request.contextPath}/historico/eliminarGastoColectivo/${gasto.id}"><img src="<c:url value="/resources/img/borrar.png" />" /></a></td>
						</tr>
					</c:forEach>
	    		</c:when>
	    		<c:otherwise>
	    			<tr class="datos">
						<th>Concepto</th>
						<th>Importe</th>
						<th>Fecha</th>
					</tr>
					<c:forEach items="${gastosColectivos}" var="gasto">
						<tr class="datos">
							<td>${gasto.concepto}</td>
							<td>${gasto.importe} &euro;</td>
							<td><fmt:formatDate value="${gasto.fecha}" pattern="yyyy-MM-dd" /></td>
						</tr>
					</c:forEach>
				</c:otherwise>
		    	</c:choose>		    	
			</table>
			<p><a href="${pageContext.request.contextPath}/historico/verPagos"><img src="<c:url value="/resources/img/planing.png"/>" /> Ver desglose de pagos</a></p>
			
			
			<h2>Gastos Individuales</h2>
			<table>
				<tr class="datos">
					<th>Concepto</th>
					<th>Importe</th>
					<th>Fecha</th>
					<th>Editar</th>
					<th>Eliminar</th>
				</tr>
				<c:forEach items="${gastosIndividuales}" var="gasto">
					<tr class="datos">
						<td>${gasto.concepto}</td>
						<td>${gasto.importe} &euro;</td>
						<td><fmt:formatDate value="${gasto.fecha}" pattern="yyyy-MM-dd" /></td>
						<td><a href="${pageContext.request.contextPath}/historico/modificarGastoIndividual/${gasto.id}"><img src="<c:url value="/resources/img/editar.png" />" /></a></td>						
						<td><a href="${pageContext.request.contextPath}/historico/eliminarGastoIndividual/${gasto.id}"><img src="<c:url value="/resources/img/borrar.png" />" /></a></td>	
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${idRol eq 1 || idRol eq 2}">
				<h2>Crear gasto colectivo</h2>			
				<form:form method="POST" modelAttribute="crearGastoColectivoForm" action="${pageContext.request.contextPath}/historico/crearGastoColectivo">
					<table>
						<tr>
						    <td><form:label path="concepto">Concepto:</form:label></td>
							<td><form:input path="concepto" /></td>
							<td><form:errors path="concepto" cssClass="error-validacion" /></td>
						</tr>
						<tr>
						    <td><form:label path="importe">Importe:</form:label></td>
							<td><form:input type="number" step="0.01" path="importe" /></td>
							<td><form:errors path="importe" cssClass="error-validacion" /></td>
						</tr>
						<tr>
						    <td><form:label path="fecha">Fecha:</form:label></td>
						   	<td><form:input type="date" path="fecha" /></td>
						    <td><form:errors path="fecha" cssClass="error-validacion" /></td>
						</tr>
						<tr>
							<td>Pagado por:</td>
						</tr>
						<tr>
							<td><input type="radio" name="idUsuario" value="0" checked="checked">Todos</td>						
						</tr>
						<c:if test="${not empty bote}">
							<tr>
								<td><input type="radio" name="idUsuario" value="-1">Bote</td>
							</tr>
						</c:if>
						<c:forEach items="${usuarios}" var="usuario">
							<tr><td><input type="radio" name="idUsuario" value="${usuario.id}">${usuario.nombreUsuario}</td></tr>
						</c:forEach>						
						<tr>
						    <td colspan="3"><input type="submit" value="Confirmar"/></td>
						</tr>
					</table>
				</form:form>	
			</c:if>
			
			<h2>Crear gasto individual</h2>
			<form:form method="POST" modelAttribute="crearGastoIndividualForm" action="${pageContext.request.contextPath}/historico/crearGastoIndividual">
				<table>
					<tr>
					    <td><form:label path="concepto">Concepto:</form:label></td>
						<td><form:input path="concepto" /></td>
						<td><form:errors path="concepto" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td><form:label path="importe">Importe:</form:label></td>
						<td><form:input type="number" step="0.01" path="importe" /></td>
						<td><form:errors path="importe" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td><form:label path="fecha">Fecha:</form:label></td>
					   	<td><form:input type="date" path="fecha" /></td>
					    <td><form:errors path="fecha" cssClass="error-validacion" /></td>
					</tr>
					<tr>
					    <td colspan="3"><input type="submit" value="Confirmar"/></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>