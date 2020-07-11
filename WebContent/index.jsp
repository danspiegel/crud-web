<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="ola" uri="/WEB-INF/tags/Exemplo.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" href="css/global.css"/>
</head>
<body>

	<jsp:include page="cabecalho.jsp"/>
	
	<div class="main">
		<h1>Olá, seja bem vindo ao CRUD - DevMedia</h1>
		<fieldset>
			<legend>CRUD DevMedia</legend> 
			
			<h4>Esse é um sistema CRUD de cadastros, consultas e operações básicas em torno dos nossos clientes.</h4>
		</fieldset>
	</div>
	
	<jsp:include page="rodape.jsp"/>

</body>
</html>