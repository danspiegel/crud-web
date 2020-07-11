<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login - Devmedia CRUD Web</title>
	<link rel="stylesheet" href="css/global.css"/>
</head>
<body>
	<form method="post" id="login_form" action="main?acao=login">
		<jsp:include page="msg.jsp"/>
	
		<fieldset id="fieldset_login">
			<legend>Login do Sistema</legend>
		
			<div class="campo">
				<div class="label">
					<label for="login">Login</label>
				</div>
				<input type="text" id="login" name="login" maxlength="15" value="${param.login}"/>
			</div>
			
			<div class="campo">
				<div class="label">
					<label for="senha">Senha</label>
				</div>
				<input type="password" id="senha" name="senha" maxlength="15" value="${param.senha}"/>
			</div>
			
			<div class="campo">
				<input type="submit" value="Logar"/>
			</div>
			<div class="campo">
				<a href="">Esqueci a Senha</a>
			</div>
		</fieldset>
	</form>
</body>
</html>