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
import br.edu.devmedia.crud.exception.NegocioException;

public class FiltroCommand implements Command {

	private String proximo;
	
	private PessoaBO pessoaBO;
	
	public String execute(HttpServletRequest request) {
		proximo = "consultas.jsp";
		this.pessoaBO = new PessoaBO();
		
		try {
			String nome = request.getParameter("nome");
			String cpf = request.getParameter("cpf");
			String dtNasc = request.getParameter("dtNasc");
			String sexo = request.getParameter("sexo");
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
			
			EnderecoDTO enderecoDTO = new EnderecoDTO();
			enderecoDTO.setLogradouro(logradouro);
			
			CidadeDTO cidadeDTO = new CidadeDTO();
			cidadeDTO.setIdCidade(idCidade != null ? Integer.parseInt(idCidade) : null);
			
			UfDTO ufDTO = new UfDTO();
			ufDTO.setIdUF(idUf != null ? Integer.parseInt(idUf) : null);
			
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setNome(nome);
			pessoaDTO.setCpf(cpf);
			pessoaDTO.setDtNasc(dtNasc);
			pessoaDTO.setSexo(sexo != null ? sexo.charAt(0) : ' ');
			pessoaDTO.setPreferencias(listaPrefs);
			
			cidadeDTO.setUf(ufDTO);
			enderecoDTO.setCidade(cidadeDTO);
			pessoaDTO.setEndereco(enderecoDTO);

			List<PessoaDTO> listaPessoas = pessoaBO.filtrar(pessoaDTO);
			request.setAttribute("listaPessoas", listaPessoas);
			
			if (idUf != null) {
				List<CidadeDTO> listaCidades = pessoaBO.consultarCidadesPorEstado(idUf);
				request.getSession().setAttribute("listaCidades", listaCidades);
			}
		} catch (NegocioException e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}

}
