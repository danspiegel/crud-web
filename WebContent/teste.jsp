<%@page import="br.edu.devmedia.crud.dto.PessoaDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="disp" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/displaytag.css" rel="stylesheet" type="text/css"/>
<link href="css/screen.css" rel="stylesheet" type="text/css"/>
<link href="css/site.css" rel="stylesheet" type="text/css"/>
</head>
<body>

	<%
		List<PessoaDTO> lista = new ArrayList<PessoaDTO>();
		lista.add(new PessoaDTO("Diogo", "4847"));
		lista.add(new PessoaDTO("Diogo", "1245"));
		lista.add(new PessoaDTO("Carlos", "1245"));
		lista.add(new PessoaDTO("Diogo", "1245"));
		lista.add(new PessoaDTO("Ana", "1245"));
		lista.add(new PessoaDTO("Diogo", "1245"));
		lista.add(new PessoaDTO("Diogo", "32423"));
		lista.add(new PessoaDTO("Pedro", "1245"));
		lista.add(new PessoaDTO("Diogo", "1245"));
		
		session.setAttribute("lista", lista);
	%>

	<div id="tab1" class="tab_content" style="width: 100%; display: block">
		<h3>Lista de Pessoas</h3>
		<p>Pessoas cadastradas na base</p>
		<disp:table name="sessionScope.lista" pagesize="3" style="width: 80%" uid="list" 
			export="true" sort="list" >
			<disp:column property="nome" class="sortable" title="Nome" sortable="true"></disp:column>
			<disp:column property="cpf" class="sortable" title="CPF" sortable="true"></disp:column>
		</disp:table>
	</div>

</body>
</html>