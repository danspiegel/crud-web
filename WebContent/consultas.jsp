<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="disp" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Consultas</title>
	<link rel="stylesheet" href="css/global.css"/>
	<link href="css/displaytag.css" rel="stylesheet" type="text/css"/>
	<link href="css/screen.css" rel="stylesheet" type="text/css"/>
	<link href="css/site.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		function popularComboCidades(comboEstados) {
			var idEstado = comboEstados.options[comboEstados.selectedIndex].value;
			var formCadastro = document.forms[0];
			formCadastro.action = 'main?acao=montagemCadastro&isSearch=true&getCidades=true&idEstado=' + idEstado;
			formCadastro.submit();
		}
	</script>
</head>
<body>
	<jsp:include page="cabecalho.jsp"/>
	
	<div class="main">
		<jsp:include page="msg.jsp"/>
		
		<fieldset>
			<legend>Consultas</legend> 
			<h1>Consultas</h1>
			
			<fieldset style="margin-bottom: 20px">
				<legend>Selecione os parâmetros</legend>
				
				<form action="main?acao=filtrar" method="post">
					<fieldset style="width: 50%;float: left;">
						<legend>Dados pessoais</legend>
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
						</table>
					</fieldset>
					<fieldset style="width: 44%;float: left;">
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
					<div style="clear:both"></div>
					<tr>
						<td colspan="2">
							<input type="submit" value="Consultar"/>
							<input type="button" value="Limpar" onclick="location.href='main?acao=consultas'"/>
						</td>
					</tr>
				</form>
			</fieldset>
			
			<table width="100%" border="1" cellspacing="0" cellpadding="5">
				<thead>
					<tr>
						<th>Id</th>
						<th>Nome</th>
						<th>Sexo</th>
						<th>CPF</th>
						<th>Dt. Nasc.</th>
						<th>Endereço</th>
						<th>Cidade</th>
						<th>UF</th>
						<th colspan="3">Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty listaPessoas}">
							<c:forEach items="${listaPessoas}" var="pessoa">
								<tr>
									<td class="alignCenter">${pessoa.idPessoa}</td>
									<td class="alignLeft">${pessoa.nome}</td>
									<td class="alignCenter">${pessoa.sexo}</td>
									<td class="alignCenter">${pessoa.cpf}</td>
									<td class="alignCenter">${pessoa.dtNasc}</td>
									<td class="alignLeft">${pessoa.endereco.logradouro}</td>
									<td class="alignLeft">${pessoa.endereco.cidade.descricao}</td>
									<td class="alignLeft">${pessoa.endereco.cidade.uf.descricao}</td>
									<td class="alignCenter">
										<c:forEach items="${pessoa.preferencias}" var="p" varStatus="status">
											<c:set var="preferencias" value="${status.first ? ' ' : preferencias} [${p.descricao}]" />
										</c:forEach>
										<a href="javascript:void(0)" title="Preferências" onclick="alert('${preferencias}');">
											<img alt="Preferências" src="img/preference.png"/>
										</a>
										<c:set var="preferencias" value="Sem preferências" />
									</td>
									<td class="alignCenter">
										<a href="main?acao=editarPessoa&id_pessoa=${pessoa.idPessoa}" title="Editar">
											<img alt="Edição de Pessoa" src="img/edit.png"/>
										</a>
									</td>
									<td class="alignCenter">
										<a href="main?acao=removerPessoa&id_pessoa=${pessoa.idPessoa}" title="Deletar">
											<img alt="Remoção de Pessoa" src="img/delete.png"/>
										</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr style="color: red;text-align: center;font-size: 15pt">
								<td colspan="9">Nenhum dado encontrado.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</fieldset>
		<fieldset>
			<legend>Lista de Pessoas</legend>
			<div id="tab1" class="tab_content" style="width: 100%; display: block">
				<disp:table name="sessionScope.listaPessoas" pagesize="3" style="width: 80%" uid="list" 
					sort="list" decorator="br.edu.devmedia.crud.decorator.PessoaDecorator">
					<disp:column property="idPessoa" class="sortable" title="Id" sortable="true"></disp:column>
					<disp:column property="nome" class="sortable" title="Nome" sortable="true"></disp:column>
					<disp:column property="cpf" class="sortable" title="CPF" sortable="true"></disp:column>
					<disp:column property="dtNasc" class="sortable" title="Dt Nasc" sortable="true"></disp:column>
					<disp:column property="sexo" class="sortable" title="Sexo" sortable="true"></disp:column>
					<disp:column property="endereco.logradouro" class="sortable" title="Endereço" sortable="true"></disp:column>
					<disp:column property="endereco.cidade.descricao" class="sortable" title="Cidade" sortable="true"></disp:column>
					<disp:column property="endereco.cidade.uf.descricao" class="sortable" title="UF" sortable="true"></disp:column>
					<disp:column property="preferencias" class="sortable" title="Editar" sortable="false"></disp:column>
					<disp:column property="editar" class="sortable" title="Editar" sortable="false"></disp:column>
					<disp:column property="remover" class="sortable" title="Remover" sortable="false"></disp:column>
				</disp:table>
			</div>
		</fieldset>
	</div>
	
	<jsp:include page="rodape.jsp"/>

</body>
</html>