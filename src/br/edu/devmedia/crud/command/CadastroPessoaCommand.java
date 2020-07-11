package br.edu.devmedia.crud.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.edu.devmedia.crud.bo.PessoaBO;
import br.edu.devmedia.crud.dto.CidadeDTO;
import br.edu.devmedia.crud.dto.EnderecoDTO;
import br.edu.devmedia.crud.dto.PessoaDTO;
import br.edu.devmedia.crud.dto.PreferenciaMusicalDTO;
import br.edu.devmedia.crud.dto.UfDTO;
import br.edu.devmedia.crud.util.MensagemContantes;

public class CadastroPessoaCommand implements Command {
	
	private String proximo;
	
	private PessoaBO pessoaBO;
	
	public String execute(HttpServletRequest request) {
		pessoaBO = new PessoaBO();
		proximo = "cadastroPessoa.jsp";
		
		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");
		String dtNasc = request.getParameter("dtNasc");
		String sexo = request.getParameter("sexo");
		String miniBio = request.getParameter("miniBio");
		String idUf = request.getParameter("uf");
		String idCidade = request.getParameter("cidade");
		String logradouro = request.getParameter("logradouro");
		
		String[] preferencias = request.getParameterValues("gostos");
		List<PreferenciaMusicalDTO> listaPrefs = new ArrayList<>();
		if (preferencias != null) {
			for (String pref : preferencias) {
				PreferenciaMusicalDTO preferenciaMusical = new PreferenciaMusicalDTO();
				preferenciaMusical.setIdPreferencia(Integer.parseInt(pref));
				
				listaPrefs.add(preferenciaMusical);
			}
		}
		try {
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setNome(nome);
			pessoaDTO.setCpf(cpf);
			pessoaDTO.setDtNasc(dtNasc);
			pessoaDTO.setMiniBio(miniBio);
			pessoaDTO.setSexo(sexo != null ? sexo.charAt(0) : ' ');
			pessoaDTO.setPreferencias(listaPrefs);
			
			EnderecoDTO enderecoDTO = new EnderecoDTO();
			enderecoDTO.setLogradouro(logradouro);
			
			CidadeDTO cidadeDTO = new CidadeDTO();
			cidadeDTO.setIdCidade(idCidade != null ? Integer.parseInt(idCidade) : null);
			
			UfDTO ufDTO = new UfDTO();
			ufDTO.setIdUF(idUf != null ? Integer.parseInt(idUf) : null);
			
			cidadeDTO.setUf(ufDTO);
			enderecoDTO.setCidade(cidadeDTO);
			pessoaDTO.setEndereco(enderecoDTO);
			
			boolean isValido = pessoaBO.validarPessoa(pessoaDTO);
			if (!isValido) {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_PESSOA_DADOS_INVALIDOS);
			} else {
				pessoaBO.cadastrarPessoa(pessoaDTO);
				proximo = "main?acao=consultas";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_PESSOA);
			}
		} catch (Exception e) {
			String idEstado = request.getParameter("uf");
			proximo = "main?acao=montagemCadastro&getCidades=true&idEstado=" + idEstado;
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}
	
}
