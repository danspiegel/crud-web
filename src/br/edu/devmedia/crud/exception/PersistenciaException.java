package br.edu.devmedia.crud.exception;

/**
 * Classe respons�vel por encapsular todas as exce��es de banco de dados
 * 
 * @author Devmedia
 * 
 */
public class PersistenciaException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenciaException(String erro) {
		super(erro);
	}

	public PersistenciaException(Exception e) {
		super(e.getMessage());
	}

	public PersistenciaException(String erro, Exception e) {
		super(erro, e);
	}

}
