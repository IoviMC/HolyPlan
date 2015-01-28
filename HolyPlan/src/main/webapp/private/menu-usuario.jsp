<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Men&uacute;</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<div id="lat_izquierdo">
			<p>Hola ${nombreUsuario}</p>
			<p><a href="${pageContext.request.contextPath}/usuario/amigos">Mis amigos</a></p>
			<p><a href="${pageContext.request.contextPath}/usuario/editarUsuario">Modificar cuenta</a></p>
			<p><a href="${pageContext.request.contextPath}/viaje/crearViaje">Crear viaje</a></p>
			<p><a href="${pageContext.request.contextPath}/logout">Cerrar sesi&oacute;n</a></p>
		</div>
		<div id="lat_derecho">
			<c:choose>
			    <c:when test="${empty viajes}">
			        <p>No tiene ning&uacute; viaje planificado. Organice sus propios viajes.</p>
			    </c:when>
			    <c:otherwise>
					<c:forEach items="${viajes}" var="viaje">
						<h1>${viaje.getNombreViaje()}</h1>
						<fmt:formatDate value="${viaje.getFecha()}" pattern="yyyy-MM-dd" />
						<p>${viaje.getDescripcion()}</p>
			    		<p><a href="${pageContext.request.contextPath}/viaje/mostrarViaje/${viaje.getId()}">Ver</a></p>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>