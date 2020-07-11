package br.edu.devmedia.crud.dto;

import java.io.Serializable;

public class CidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCidade;

	private String descricao;

	private UfDTO uf;

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UfDTO getUf() {
		return uf;
	}

	public void setUf(UfDTO uf) {
		this.uf = uf;
	}

}
