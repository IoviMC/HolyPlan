<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Ver planing</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
	<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
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
			<h1><img src="<c:url value="/resources/img/planing.png"/>" /> ${planing.nombre}</h1>
			<p>${planing.descripcion}</p>
			<h2>Paradas</h2>
			<c:choose>
			    <c:when test="${empty paradas}">
			        <p>Este planing no dispone de ningua parada</p>
			        <c:if test="${idRol ne 3}">
			        	<a href="${pageContext.request.contextPath}/parada/crearParada/${planing.id}"><img src="<c:url value="/resources/img/application_add.png"/>" /> Nueva parada</a>
			        </c:if>
			    </c:when>
			    <c:otherwise>
					<c:forEach items="${paradas}" var="parada">
						<table class="ficha izquierda">
							<tr>
								<td>Nombre </td>
								<td colspan="2">${parada.nombre}</td>
							</tr>
							<tr>
								<td>Fecha </td>
								<td colspan="2"><fmt:formatDate value="${parada.fecha}" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td>Lugar </td>
								<td>${parada.lugar}</td>
								<td><a class="cargarMapa" href="#"><img src="<c:url value="/resources/img/map_magnify.png"/>" /> Ver en mapa</a></td>
							</tr>
							<c:if test="${idRol ne 3}">
								<tr>
									<td>Acciones </td>
									<td><a href="${pageContext.request.contextPath}/parada/crearParada/${planing.id}"><img src="<c:url value="/resources/img/application_add.png"/>" /> Nueva parada</a></td>
									<c:if test="${idRol eq 1}">
						    			<td><a href="${pageContext.request.contextPath}/parada/eliminarParada/${parada.id}"><img src="<c:url value="/resources/img/borrar.png"/>" /> Eliminar Parada</a></td>			    			
						    		</c:if>
						    	</tr>
							</c:if>
						</table>
					</c:forEach>
					
					<div id="entero">
						<h2>Mapa</h2>
						<iframe id="mapaViaje"
							src="https://www.google.com/maps/embed/v1/place?key=AIzaSyC5INV-Y3c57mvrlhHVCIMxC73x9Hew4FE&q=${proximaParada.lugar.replace(' ', '+')}"
							allowfullscreen
							allowtranparency="true">
						</iframe>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<script>
		$("a.cargarMapa").click(function(event){
			var lugar = $(this).parent().prev().text();
			var url = $("iframe#mapaViaje").attr("src");
			var urlMapa = "https://www.google.com/maps/embed/v1/place?key=AIzaSyC5INV-Y3c57mvrlhHVCIMxC73x9Hew4FE&q=";
			var nuevaUrl = urlMapa + lugar;
			$("iframe#mapaViaje").attr("src", nuevaUrl);
			event.preventDefault();
			$("img#cargandoMapa").removeClass("noVeo");
		});
	</script>
</body>
</html>