package br.edu.devmedia.crud.dto;

import java.io.Serializable;

public class EnderecoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEndereco;

	private String logradouro;

	private CidadeDTO cidade;

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public CidadeDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeDTO cidade) {
		this.cidade = cidade;
	}

}
