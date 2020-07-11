package br.edu.devmedia.crud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Classe de geração de novas conexões
 * 
 * @author Devmedia
 */
public class ConexaoUtil {

	private static ResourceBundle configDB = ResourceBundle.getBundle(Constantes.CONEXAO_BD_PROPERTIES);

	public static Connection getConexao() throws ClassNotFoundException, SQLException {
		Class.forName(configDB.getString(Constantes.CONEXAO_BD_DRIVER));
		return DriverManager.getConnection(configDB.getString(Constantes.CONEXAO_BD_URL), 
				configDB.getString(Constantes.CONEXAO_BD_USER),
				configDB.getString(Constantes.CONEXAO_BD_PASSWORD));
	}
}
