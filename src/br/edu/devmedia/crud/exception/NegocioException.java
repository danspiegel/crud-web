package br.edu.devmedia.crud.exception;

/**
 * Classe responsável por gerenciar as exceções da camada de negócio
 * 
 * @author Devmedia
 * 
 */
public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	public NegocioException(Exception e) {
		super(e.getMessage());
	}
	
	public NegocioException(String msg) {
		super(msg);
	}

}
