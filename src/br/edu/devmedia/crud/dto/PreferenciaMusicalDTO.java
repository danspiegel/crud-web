package br.edu.devmedia.crud.dto;

import java.io.Serializable;

public class PreferenciaMusicalDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idPreferencia;
	
	private String descricao;

	public Integer getIdPreferencia() {
		return idPreferencia;
	}

	public void setIdPreferencia(Integer idPreferencia) {
		this.idPreferencia = idPreferencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
