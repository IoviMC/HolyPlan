<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Listas</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
	<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
	<script>
		$(document).ready(function() {
		    var wrapper         = $("#checkList"); 
		    var add_button      = $(".anadir");

		    add_button.click(function(e){
		       e.preventDefault();
		       wrapper.append('<tr><td></td><td><input type="text" name="items"/></td></tr>');
		    });
		});
	</script>
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
			<h1><img src="<c:url value="/resources/img/list.png"/>" /> Administrar listas personales</h1>
			<div id="late_der_derecho">
				<h2>Nueva lista</h2>
				<form:form method="POST" modelAttribute="crearCheckListPersonaForm" action="${pageContext.request.contextPath}/checkListPersona/crearCheckList">
					<table id="checkList">
						<tr>
						    <td><form:label path="nombre">Nombre:</form:label></td>
							<td><form:input path="nombre" /></td>
							<td><form:errors path="nombre" cssClass="error-validacion" /></td>
						</tr>
						<tr>
							<td><label>Contenido:</label></td>
							<td><input type="text" name="items"/></td>
							<td><button class="anadir"><img src="<c:url value="/resources/img/add.png"/>" /></button></td>						
						</tr>
					</table>
					<p><input type="submit" value="Guardar"/></p>
				</form:form>
			</div>
			<div id="late_der_izquierdo">
				<h2>Listas</h2>
				<c:choose>
				    <c:when test="${empty listasPersona}">
				        <p>No tienes ning&uacute;a lista. Crea tus listas.</p>
				    </c:when>
				    <c:otherwise>
						<table>
							<tr>
								<td>Ver lista</td>
								<td>Editar</td>
								<td>Eliminar</td>
							</tr>
							<c:forEach items="${listasPersona}" var="listaP">
								<tr>
									<td><a href="${pageContext.request.contextPath}/checkListPersona/mostrarCheckList/${listaP.id}">${listaP.nombre}</a></td>
									<td><a href="${pageContext.request.contextPath}/checkListPersona/editarItemsPersona/${listaP.id}"><img src="<c:url value="/resources/img/editar.png" />" /></a></td>
									<td><a href="${pageContext.request.contextPath}/checkListPersona/eliminarCheckList/${listaP.id}"><img src="<c:url value="/resources/img/borrar.png" />" /></a></td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>