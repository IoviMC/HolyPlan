<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Modificar lista</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
	<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
	<script>
		$(document).ready(function() {
		    var wrapper         = $("#checkList"); 
		    var add_button      = $(".anadir");

		    add_button.click(function(e){
		       e.preventDefault();
		       wrapper.append('<tr><td><input type="text" name="itemsNuevos"/></td></tr>');
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
			<h1><img src="<c:url value="/resources/img/editar.png" />" /> Modificar contenido</h1>
			<div id="late_der_izquierdo">
				<h2>${editarItemsViajeForm.nombre}</h2>
				<table>
			    	<c:forEach items="${editarItemsViajeForm.items}" var="item">
			    		<tr>
							<c:choose>
								<c:when test="${item.realizado}">
									<td><input name="itemsId" value="${item.id}" type="checkbox" disabled="disabled" checked="checked"/> ${item.descripcion} </td>
									<td><a class="eliminar" href="${pageContext.request.contextPath}/checkListViaje/eliminarItemList/${item.id}/${editarItemsViajeForm.id}"><img src="<c:url value="/resources/img/borrar.png" />" /></a></td>
								</c:when>
								<c:otherwise>
									<td><input name="itemsId" value="${item.id}" type="checkbox" disabled="disabled"/> ${item.descripcion} </td>
									<td><a class="eliminar" href="${pageContext.request.contextPath}/checkListViaje/eliminarItemList/${item.id}/${editarItemsViajeForm.id}"><img src="<c:url value="/resources/img/borrar.png" />" /></a></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="late_der_derecho">
				<h2>Nuevo contenido</h2>
				<form:form method="POST" modelAttribute="editarItemsViajeForm" action="${pageContext.request.contextPath}/checkListViaje/editarItemsViaje">
					<form:input type="hidden" path="id" />
					<table id=checkList>
						<tr>
							<td><input type="text" name="itemsNuevos"/></td>
							<td><button class="anadir"><img src="<c:url value="/resources/img/add.png"/>" /></button></td>
						</tr>
					</table>
					<p><input type="submit" value="Guardar"/></p>
				</form:form>
			</div>
			<div id="entero">
				<p><a href="${pageContext.request.contextPath}/checkListViaje/modificarCheckList/${editarItemsViajeForm.id}"><img src="<c:url value="/resources/img/list.png"/>" /> Volver a la lista</a></p>
			</div>
		</div>
	</div>
</body>
</html>