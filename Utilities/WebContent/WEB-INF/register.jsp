<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrarse</title>
</head>
<body>
	<h1>Registro</h1>
	<form method="post" action="register">
		<input type="text" name="mail" placeholder="correo" />
		<br />
		<input type="password" name="pass" placeholder="contraseña" />
		<br />
		<input type="password" name="repeatPass" placeholder="repetir contraseña" />
		<br />
		<input type="submit" name="enviar" value="Registrar">
	</form>
	
	<a href="login">¿Ya estás registrado? Accede ahora</a>
</body>
</html>