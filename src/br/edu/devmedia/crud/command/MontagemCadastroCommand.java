package br.edu.devmedia.crud.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.edu.devmedia.crud.dao.PessoaDAO;
import br.edu.devmedia.crud.dto.CidadeDTO;
import br.edu.devmedia.crud.exception.PersistenciaException;

public class MontagemCadastroCommand implements Command {
	
	private String proximo;
	
	private PessoaDAO cadastroDAO;
	
	public String execute(HttpServletRequest request) {
		cadastroDAO = new PessoaDAO();
		String isEdit = request.getParameter("isEdit");
		String isSearch = request.getParameter("isSearch");
		if (isEdit != null && !"".equals(isEdit)) {
			proximo = "edicaoPessoa.jsp";
		} else if (isSearch != null && !"".equals(isSearch)) {
			proximo = "main?acao=filtrar";
		} else {
			proximo = "cadastroPessoa.jsp";
		}
		String getCidades = request.getParameter("getCidades");
		
		try {
			if (getCidades != null && !"".equals(getCidades)) {
				String id = request.getParameter("idEstado");
				int idEstado = Integer.parseInt(id);
				
				List<CidadeDTO> listaCidades = cadastroDAO.consultarCidadesPorEstado(idEstado);
				request.setAttribute("listaCidades", listaCidades);
			}
		} catch (PersistenciaException e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}
	
}
