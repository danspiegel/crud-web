package br.edu.devmedia.crud.bo;

import java.util.HashMap;
import java.util.Map;

import br.edu.devmedia.crud.dao.UsuarioDAO;
import br.edu.devmedia.crud.dto.UsuarioDTO;
import br.edu.devmedia.crud.exception.NegocioException;
import br.edu.devmedia.crud.util.MensagemContantes;
import br.edu.devmedia.crud.validator.LoginValidator;

/**
 * Classe respons�vel por gerenciar os m�todos de neg�cio do usu�rio
 * 
 * @author Devmedia
 * 
 */
public class UsuarioBO {

	/**
	 * M�todo respos�vel por validar o usu�rio
	 * 
	 * @param request
	 * @return
	 * @throws NegocioException 
	 */
	public boolean validarUsuario(UsuarioDTO usuarioDTO) throws NegocioException {
		boolean isValido = true;
		try {
			// Valida campos obg
			Map<String, Object> valores = new HashMap<>();
			valores.put("Usu�rio", usuarioDTO.getUsuario());
			valores.put("Senha", usuarioDTO.getSenha());
			if (new LoginValidator().validar(valores)) {
				isValido = true;
			}
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			isValido = usuarioDAO.validarUsuario(usuarioDTO);
			if (!isValido) {
				throw new NegocioException(MensagemContantes.MSG_ERR_USUARIO_SENHA_INVALIDOS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return isValido;
	}

}
