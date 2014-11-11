<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<p>Hola ${nombreUsuario}</p>
		<p><a href="${pageContext.request.contextPath}/private/editarUsuario">Modificar cuenta</a></p>
		<p><a href="${pageContext.request.contextPath}/private/crearViaje">Crear viaje</a></p>
		<p><a href="${pageContext.request.contextPath}/logout">Cerrar sesi&oacute;n</a></p>
	</div>
</body>
</html>