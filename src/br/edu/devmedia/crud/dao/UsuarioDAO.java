package br.edu.devmedia.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.devmedia.crud.dto.UsuarioDTO;
import br.edu.devmedia.crud.exception.PersistenciaException;
import br.edu.devmedia.crud.util.ConexaoUtil;

/**
 * Classe DAO de acesso a dados referentes às entidades de tipo Usuário.
 * 
 * @author Devmedia
 * 
 */
public class UsuarioDAO {

	/**
	 * Método de autenticação do usuário
	 * 
	 * @param usuarioDTO
	 * @return
	 * @throws PersistenciaException
	 */
	public boolean validarUsuario(UsuarioDTO usuarioDTO) throws PersistenciaException {
		try {
			Connection conexao = ConexaoUtil.getConexao();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_USUARIO ");
			sql.append(" WHERE USUARIO = ? AND SENHA = ?");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setString(1, usuarioDTO.getUsuario());
			statement.setString(2, usuarioDTO.getSenha());
			
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		}
	}
}
