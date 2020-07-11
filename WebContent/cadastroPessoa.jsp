<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastros</title>
<link rel="stylesheet" href="css/global.css"/>
<script type="text/javascript">
	function popularComboCidades(comboEstados) {
		var idEstado = comboEstados.options[comboEstados.selectedIndex].value;
		var formCadastro = document.forms[0];
		formCadastro.action = 'main?acao=montagemCadastro&getCidades=true&idEstado=' + idEstado;
		formCadastro.submit();
	}
	
	function cadastrar() {
		var formCadastro = document.forms[0];
		formCadastro.action='main?acao=cadastroPessoa';
		formCadastro.submit();
	}
</script>
</head>
<body>

	<jsp:include page="cabecalho.jsp"/>
		<h1>Cadastros</h1>
	
		<div class="main">
			<form action="main?acao=cadastroPessoa" method="post">
				<jsp:include page="msg.jsp"/>
				<fieldset>
					<legend>Cadastro de Pessoa</legend>
					 
					<table cellpadding="5">
						<tr>
							<td>Nome*:</td>
							<td><input type="text" name="nome" maxlength="45" value="${param.nome}"/></td>
						</tr>
						<tr>
							<td>CPF*:</td>
							<td><input type="text" name="cpf" maxlength="11" value="${param.cpf}"/></td>
						</tr>
						<tr>
							<td>Data Nascimento:</td>
							<td><input type="text" name="dtNasc" maxlength="10" value="${param.dtNasc}"/></td>
						</tr>
						<tr>
							<td>Sexo*:</td>
							<td><input type="radio" name="sexo" value="M" ${param.sexo eq 'M' ? 'checked' : ''} /> Masculino
							<input type="radio" name="sexo" value="F" ${param.sexo eq 'F' ? 'checked' : ''} /> Feminino</td>
						</tr>
						<tr>
							<td>Preferências:</td>
							<td>
								<c:if test="${sessionScope.listaPreferencias != null}">
									<c:forEach items="${sessionScope.listaPreferencias}" var="preferencia">
										<c:set var="isPrefValid" value="${false}"/>
										<c:forEach items="${paramValues['gostos']}" var="gosto">
											<c:if test="${preferencia.idPreferencia eq gosto}">
												<c:set var="isPrefValid" value="${true}"/>
											</c:if>
										</c:forEach>
										
										<c:choose>
											<c:when test="${isPrefValid}">
												<input type="checkbox" name="gostos" value="${preferencia.idPreferencia}" checked="checked"/> ${preferencia.descricao}
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="gostos" value="${preferencia.idPreferencia}" /> ${preferencia.descricao}
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
							</td>
						</tr>
						<tr>
							<td>Mini-biografia:</td>
							<td>
								<textarea rows="5" cols="35" name="miniBio">${param.miniBio}</textarea>
							</td>
						</tr>
					</table>
					
					<fieldset>
						<legend>Endereço</legend>
						
						<table cellpadding="5">
							<tr>
								<td>UF*:</td>
								<td>
									<select name="uf" id="uf" onchange="popularComboCidades(this)">
										<option value="0">Selecione...</option>
										
										<c:forEach items="${sessionScope.listaUF}" var="ufAux">
											<option value="${ufAux.idUF}"
												${(param.uf != null and param.uf eq ufAux.idUF) ? 'selected=true' : ''}>${ufAux.descricao}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>Cidade*:</td>
								<td>
									<select name="cidade">
										<option value="0">Selecione...</option>
										<c:if test="${listaCidades != null}">
											<c:forEach items="${listaCidades}" var="cidade">
												<option value="${cidade.idCidade}"
													${(param.cidade != null and param.cidade eq cidade.idCidade) ? 'selected=true' : ''}>${cidade.descricao}
												</option>
											</c:forEach>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td>Logradouro*:</td>
								<td>
									<input type="text" name="logradouro" value="${param.logradouro}"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</fieldset>
				<span>* Campos obrigatórios</span>
				<input type="reset" value="Limpar"/>
				<input type="button" value="Cadastrar" onclick="cadastrar()"/>
			</form>
		</div>
	
	<jsp:include page="rodape.jsp"/>

</body>
</html>