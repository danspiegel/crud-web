package br.edu.devmedia.crud.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.edu.devmedia.crud.bo.PessoaBO;
import br.edu.devmedia.crud.dao.PessoaDAO;
import br.edu.devmedia.crud.dto.CidadeDTO;
import br.edu.devmedia.crud.dto.PessoaDTO;
import br.edu.devmedia.crud.exception.NegocioException;
import br.edu.devmedia.crud.exception.PersistenciaException;

public class EditarPessoaCommand implements Command {

	private String proximo;
	
	private PessoaBO pessoaBO;

	private PessoaDAO cadastroDAO;
	
	public String execute(HttpServletRequest request) {
		proximo = "edicaoPessoa.jsp";
		this.cadastroDAO = new PessoaDAO();
		this.pessoaBO = new PessoaBO();
		
		try {
			Integer idPessoa = Integer.parseInt(request.getParameter("id_pessoa"));
			PessoaDTO pessoa = pessoaBO.consultarPessoaPorId(idPessoa);
			
			List<CidadeDTO> listaCidades = cadastroDAO.consultarCidadesPorEstado(pessoa.getEndereco().getCidade().getUf().getIdUF());
			request.setAttribute("listaCidades", listaCidades);
			request.setAttribute("pessoa", pessoa);
		} catch (NegocioException | PersistenciaException e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}

}
