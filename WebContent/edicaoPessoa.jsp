<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Atualização</title>
<link rel="stylesheet" href="css/global.css"/>
<script type="text/javascript">
	function popularComboCidades(comboEstados) {
		var idEstado = comboEstados.options[comboEstados.selectedIndex].value;
		var formCadastro = document.forms[0];
		formCadastro.action = 'main?acao=montagemCadastro&isEdit=true&getCidades=true&idEstado=' + idEstado;
		formCadastro.submit();
	}
	
	function atualizar() {
		var formCadastro = document.forms[0];
		formCadastro.action='main?acao=atualizarPessoa';
		formCadastro.submit();
	}
</script>
</head>
<body>

	<jsp:include page="cabecalho.jsp"/>
		<h1>Atualização</h1>
	
		<div class="main">
			<form method="post">
				<jsp:include page="msg.jsp"/>
				<fieldset>
					<legend>Atualização de Pessoa</legend>
					 
					 <input type="hidden" value="${pessoa != null ? pessoa.idPessoa : param.id_pessoa}" name="id_pessoa"/>
					<table cellpadding="5">
						<tr>
							<td>Nome*:</td>
							<td><input type="text" name="nome" maxlength="45" value="${pessoa != null ? pessoa.nome : param.nome}"/></td>
						</tr>
						<tr>
							<td>CPF*:</td>
							<td><input type="text" name="cpf" maxlength="11" value="${pessoa != null ? pessoa.cpf : param.cpf}"/></td>
						</tr>
						<tr>
							<td>Data Nascimento:</td>
							<td><input type="text" name="dtNasc" maxlength="10" value="${pessoa != null ? pessoa.dtNasc : param.dtNasc}"/></td>
						</tr>
						<tr>
							<td>Sexo*:</td>
							<td><input type="radio" name="sexo" value="M" ${'M' eq (pessoa != null ? pessoa.sexo.toString() : param.sexo) ? 'checked' : ''}/> Masculino
							<input type="radio" name="sexo" value="F" ${'F' eq (pessoa != null ? pessoa.sexo.toString() : param.sexo) ? 'checked' : ''}/> Feminino</td>
						</tr>
						<tr>
							<td>Preferências:</td>
							<td>
								<c:if test="${sessionScope.listaPreferencias != null}">
									<c:forEach items="${sessionScope.listaPreferencias}" var="preferencia">
										<c:set var="isPrefValid" value="${false}"/>
										<c:choose>
											<c:when test="${pessoa != null}">
												<c:forEach items="${pessoa.preferencias}" var="prefAux">
													<c:if test="${preferencia.idPreferencia eq prefAux.idPreferencia}">
														<c:set var="isPrefValid" value="${true}"/>
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach items="${paramValues['gostos']}" var="gosto">
													<c:if test="${preferencia.idPreferencia eq gosto}">
														<c:set var="isPrefValid" value="${true}"/>
													</c:if>
												</c:forEach>
											</c:otherwise>
										</c:choose>
										
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
								<textarea rows="5" cols="35" name="miniBio">${pessoa != null ? pessoa.miniBio : param.miniBio}</textarea>
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
											<c:choose>
												<c:when test="${pessoa != null}">
													<option value="${ufAux.idUF}"
														${(pessoa.endereco.cidade.uf.idUF eq ufAux.idUF) ? 'selected=true' : ''}>${ufAux.descricao}</option>
												</c:when>
												<c:otherwise>
													<option value="${ufAux.idUF}"
														${(param.uf != null and param.uf eq ufAux.idUF) ? 'selected=true' : ''}>${ufAux.descricao}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>Cidade*:</td>
								<td>
									<select name="cidade">
										<option value="0">Selecione...</option>
										
										<c:if test="${listaCidades ne null}">
											<c:forEach items="${listaCidades}" var="cidadeAux">
												<c:choose>
													<c:when test="${pessoa != null}">
														<option value="${cidadeAux.idCidade}"
															${(pessoa.endereco.cidade.idCidade eq cidadeAux.idCidade) ? 'selected=true' : ''}>${cidadeAux.descricao}</option>
													</c:when>
													<c:otherwise>
														<option value="${cidadeAux.idCidade}"
															${(param.cidade != null and param.cidade eq cidadeAux.idCidade) ? 'selected=true' : ''}>${cidadeAux.descricao}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
 									</select>
								</td>
							</tr>
							<tr>
								<td>Logradouro*:</td>
								<td>
									<input type="hidden" name="id_endereco" value="${pessoa != null ? pessoa.endereco.idEndereco : param.id_endereco}"/>
									<input type="text" name="logradouro" value="${pessoa != null ? pessoa.endereco.logradouro : param.logradouro}"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</fieldset>
				<span>* Campos obrigatórios</span>
				<input type="reset" value="Limpar"/>
				<input type="button" value="Atualizar" onclick="atualizar()"/>
			</form>
		</div>
	
	<jsp:include page="rodape.jsp"/>

</body>
</html>