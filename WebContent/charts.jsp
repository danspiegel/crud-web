<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="cewolf" uri="http://cewolf.sourceforge.net/taglib/cewolf.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Charts</title>
</head>
<body>
	<h1>Estatísticas</h1>
	<hr>
	
	<jsp:useBean id="viewJournals" class="br.edu.devmedia.crud.cewolf.ViewContDados"/>
	
	<cewolf:chart type="horizontalbar3d" id="grafico_linha" title="Estatísticas Jornais"
		xaxislabel="Jornais" yaxislabel="Visualizações">
		<cewolf:data>
			<cewolf:producer id="viewJournals" />
		</cewolf:data>
	</cewolf:chart>
	
	<p>
		<cewolf:img chartid="grafico_linha" height="300" width="420" renderer="cewolf"></cewolf:img>
	</p>
</body>
</html>