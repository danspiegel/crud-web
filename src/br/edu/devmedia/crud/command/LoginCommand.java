package br.edu.devmedia.crud.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.edu.devmedia.crud.bo.PessoaBO;
import br.edu.devmedia.crud.bo.UsuarioBO;
import br.edu.devmedia.crud.dto.PreferenciaMusicalDTO;
import br.edu.devmedia.crud.dto.UfDTO;
import br.edu.devmedia.crud.dto.UsuarioDTO;
import br.edu.devmedia.crud.exception.NegocioException;

public class LoginCommand implements Command {
	
	private UsuarioBO usuarioBO;

	private String proximo;
	
	private PessoaBO pessoaBO;
	
	public String execute(HttpServletRequest request) {
		// Sempre iniciar com o caso onde o erro pode acontecer
		proximo = "login.jsp";
		usuarioBO = new UsuarioBO();
		pessoaBO = new PessoaBO();
		
		String usuario = request.getParameter("login");
		String senha = request.getParameter("senha");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setUsuario(usuario);
		usuarioDTO.setSenha(senha);
		
		try {
			if (usuarioBO.validarUsuario(usuarioDTO)) {
				request.getSession().setAttribute("usuario", usuarioDTO);
				proximo = "index.jsp";
				
				List<UfDTO> listaUFs = pessoaBO.listarUFs();
				List<PreferenciaMusicalDTO> listaPreferencias = pessoaBO.listarPreferencias();
				request.getSession().setAttribute("listaUF", listaUFs);
				request.getSession().setAttribute("listaPreferencias", listaPreferencias);
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}
		return proximo;
	}
	
}
