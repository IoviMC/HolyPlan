<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HolyPlan - Desactivar Cuenta</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css" />
</head>
<body>
	<div id="cabecera">
		<h1>HolyPlan</h1>
	</div>
	<div id="cuerpo">
		<p>Si desactivas tu cuenta no podrás volver a acceder a tus datos</p>
		<p>¿Seguro que quieres desactivarla?</p>
		<a href="${pageContext.request.contextPath}/private/confDesactivarCuenta?uid=${hash}">Desactivar</a>
	</div>
</body>
</html>