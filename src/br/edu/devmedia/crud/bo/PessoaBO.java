package br.edu.devmedia.crud.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.devmedia.crud.dao.PessoaDAO;
import br.edu.devmedia.crud.dto.CidadeDTO;
import br.edu.devmedia.crud.dto.PessoaDTO;
import br.edu.devmedia.crud.dto.PreferenciaMusicalDTO;
import br.edu.devmedia.crud.dto.UfDTO;
import br.edu.devmedia.crud.exception.NegocioException;
import br.edu.devmedia.crud.exception.PersistenciaException;
import br.edu.devmedia.crud.util.MensagemContantes;
import br.edu.devmedia.crud.validator.CPFValidator;
import br.edu.devmedia.crud.validator.DataValidator;

/**
 * Classe responsável por gerenciar os métodos de negócio da pessoa
 * 
 * @author Devmedia
 * 
 */
public class PessoaBO {
	
	private PessoaDAO pessoaDAO;
	
	public PessoaBO() {
		pessoaDAO = new PessoaDAO();
	}

	/**
	 * Método resposável por validar a pessoa
	 * 
	 * @param request
	 * @return
	 * @throws NegocioException 
	 */
	public boolean validarPessoa(PessoaDTO pessoaDTO) throws NegocioException {
		boolean isValido = true;
		try {
			if (pessoaDTO.getNome() == null || "".equals(pessoaDTO.getNome())) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Nome"));
			}
			
			// Valida campos obg
			Map<String, Object> valores = new HashMap<>();
			valores.put("CPF", pessoaDTO.getCpf());
			if (new CPFValidator().validar(valores)) {
				isValido = true;
			}
			
			valores = new HashMap<>();
			valores.put("Data Nascimento", pessoaDTO.getDtNasc());
			if (new DataValidator().validar(valores)) {
				isValido = true;
			}
			
			if (pessoaDTO.getSexo() == ' ') {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Sexo"));
			}
			
			CidadeDTO cidade = pessoaDTO.getEndereco().getCidade();
			if (cidade.getUf().getIdUF() == null || cidade.getUf().getIdUF() == 0) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Estado"));
			}
			
			if (cidade.getIdCidade() == null || cidade.getIdCidade() == 0) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Cidade"));
			}
			
			if (pessoaDTO.getEndereco().getLogradouro() == null || "".equals(pessoaDTO.getEndereco().getLogradouro())) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Logradouro"));
			}
			
			if (!isValido) {
				throw new NegocioException(MensagemContantes.MSG_ERR_PESSOA_DADOS_INVALIDOS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return isValido;
	}
	
	/**
	 * Método de cadastro de pessoa negocialmente.
	 * 
	 * @param pessoaDTO
	 * @throws NegocioException
	 */
	public void cadastrarPessoa(PessoaDTO pessoaDTO) throws NegocioException {
		try {
			pessoaDAO.cadastrarPessoa(pessoaDTO);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}

	/**
	 * Método de listagem das pessoas a partir do acesso à camada de
	 * persistência
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<PessoaDTO> listarPessoas() throws NegocioException {
		List<PessoaDTO> listaPessoas = null;
		try {
			listaPessoas = pessoaDAO.listarPessoas();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
		return listaPessoas;
	}
	
	/**
	 * Método de remoção da pessoaDTO passada por parâmetro.
	 * 
	 * @param idPessoa
	 * @throws NegocioException
	 */
	public void removerPessoa(Integer idPessoa) throws NegocioException {
		try {
			pessoaDAO.removerPessoa(idPessoa);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Método de atualização das pessoas.
	 * 
	 * @param pessoaDTO
	 * @throws NegocioException
	 */
	public void atualizarPessoa(PessoaDTO pessoaDTO) throws NegocioException {
		try {
			pessoaDAO.atualizarPessoa(pessoaDTO);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Método de consulta de uma pessoa pelo seu id passado.
	 * 
	 * @param idPessoa
	 * @return
	 * @throws NegocioException
	 */
	public PessoaDTO consultarPessoaPorId(Integer idPessoa) throws NegocioException {
		try {
			return pessoaDAO.consultarPessoaPorId(idPessoa);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Método de filtragem das pessoas pelos campos de filtro.
	 * 
	 * @param pessoaDTO
	 * @return
	 * @throws NegocioException
	 */
	public List<PessoaDTO> filtrar(PessoaDTO pessoaDTO) throws NegocioException {
		try {
			return pessoaDAO.filtrar(pessoaDTO);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
	
	public List<UfDTO> listarUFs() throws NegocioException {
		try {
			return pessoaDAO.listarUFs();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
	
	public List<PreferenciaMusicalDTO> listarPreferencias() throws NegocioException {
		try {
			return pessoaDAO.listarPreferencias();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}

	public List<CidadeDTO> consultarCidadesPorEstado(String idUf) throws NegocioException {
		try {
			return pessoaDAO.consultarCidadesPorEstado(Integer.parseInt(idUf));
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
}
