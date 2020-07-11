package br.edu.devmedia.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.devmedia.crud.dto.CidadeDTO;
import br.edu.devmedia.crud.dto.EnderecoDTO;
import br.edu.devmedia.crud.dto.PessoaDTO;
import br.edu.devmedia.crud.dto.PreferenciaMusicalDTO;
import br.edu.devmedia.crud.dto.UfDTO;
import br.edu.devmedia.crud.exception.PersistenciaException;
import br.edu.devmedia.crud.util.ConexaoUtil;


/**
 * Classe DAO de acesso a dados referentes aos cadastros da aplicação
 * 
 * @author Devmedia
 * 
 */
public class PessoaDAO {

	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Método que retorna a lista de UF's
	 * 
	 * @return
	 * @throws PersistenciaException
	 */
	public List<UfDTO> listarUFs() throws PersistenciaException {
		List<UfDTO> lista = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_UF");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UfDTO ufDTO = new UfDTO();
				ufDTO.setIdUF(resultSet.getInt(1));
				ufDTO.setSigla(resultSet.getString(2));
				ufDTO.setDescricao(resultSet.getString(3));

				lista.add(ufDTO);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	/**
	 * Método que retorna a lista das preferências musicais na tabela
	 * tb_preferencia
	 * 
	 * @return
	 * @throws PersistenciaException
	 */
	public List<PreferenciaMusicalDTO> listarPreferencias() throws PersistenciaException {
		List<PreferenciaMusicalDTO> listaPreferencias = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_PREFERENCIA");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PreferenciaMusicalDTO preferenciaMusical = new PreferenciaMusicalDTO();
				preferenciaMusical.setIdPreferencia(resultSet.getInt(1));
				preferenciaMusical.setDescricao(resultSet.getString(2));

				listaPreferencias.add(preferenciaMusical);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaPreferencias;
	}

	/**
	 * Método de consulta das cidades de acordo com o id do estado passado por
	 * parâmetro
	 * 
	 * @param idEstado
	 * @return
	 * @throws PersistenciaException
	 */
	public List<CidadeDTO> consultarCidadesPorEstado(Integer idEstado) throws PersistenciaException {
		List<CidadeDTO> listaCidades = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_CIDADE ");
			sql.append(" WHERE COD_ESTADO = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idEstado);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				CidadeDTO cidadeDTO = new CidadeDTO();
				cidadeDTO.setIdCidade(resultSet.getInt("id_cidade"));
				cidadeDTO.setDescricao(resultSet.getString("descricao"));

				UfDTO ufDTO = new UfDTO();
				ufDTO.setIdUF(resultSet.getInt("cod_estado"));

				cidadeDTO.setUf(ufDTO);

				listaCidades.add(cidadeDTO);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaCidades;
	}

	/**
	 * Método de cadastro da lista de preferências passada por parâmetro em
	 * conjunto com o código da pessoa associada.
	 * 
	 * @param preferencias
	 * @param codPessoa
	 * @throws PersistenciaException
	 */
	public void cadastrarPreferencias(List<PreferenciaMusicalDTO> preferencias, Integer codPessoa) throws PersistenciaException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TB_PREFERENCIA_PESSOA ");
			sql.append(" VALUES(?, ?)");

			for (PreferenciaMusicalDTO preferencia : preferencias) {
				PreparedStatement statement = conexao.prepareStatement(sql.toString());
				statement.setInt(1, preferencia.getIdPreferencia());
				statement.setInt(2, codPessoa);

				statement.execute();
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método principal de cadastro da pessoa, responsável por gerenciar as
	 * chamadas aos demais métodos de inserção das entidades relacionadas.
	 * 
	 * @param pessoaDTO
	 * @throws PersistenciaException
	 */
	public void cadastrarPessoa(PessoaDTO pessoaDTO) throws PersistenciaException {
		Connection conexao = null;
		try {
			Integer codPessoa = null;
			// Cadastra o endereço e recebe o id gerado
			Integer codEndereco = cadastrarEndereco(pessoaDTO.getEndereco());

			conexao = ConexaoUtil.getConexao();

			/*
			 * Cadastra a pessoa com o codEndereco gerado e retorna o id da
			 * pessoa.
			 */
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TB_PESSOA(NOME, CPF, DT_NASC, SEXO, MINI_BIO, COD_ENDERECO)");
			sql.append(" VALUES(?, ?, ?, ?, ?, ?)");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, pessoaDTO.getNome());
			statement.setString(2, pessoaDTO.getCpf());
			java.sql.Date dtNasc = new java.sql.Date(dateFormat.parse(pessoaDTO.getDtNasc()).getTime());
			statement.setDate(3, dtNasc);
			statement.setString(4, String.valueOf(pessoaDTO.getSexo()));
			statement.setString(5, pessoaDTO.getMiniBio());
			statement.setInt(6, codEndereco);

			statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.first()) {
				codPessoa = resultSet.getInt(1);
			}
			// Chama o cadastro de preferências
			cadastrarPreferencias(pessoaDTO.getPreferencias(), codPessoa);
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método de inserção do objeto de EnderecoDTO para a tabela de TB_ENDERECO
	 * retornando o id gerado para o mesmo insert.
	 * 
	 * @param enderecoDTO
	 * @return
	 * @throws PersistenciaException
	 */
	public Integer cadastrarEndereco(EnderecoDTO enderecoDTO) throws PersistenciaException {
		Integer idGerado = null;
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TB_ENDERECO(LOGRADOURO, COD_CIDADE)");
			sql.append(" VALUES(?, ?)");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, enderecoDTO.getLogradouro());
			statement.setInt(2, enderecoDTO.getCidade().getIdCidade());
			statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.first()) {
				idGerado = resultSet.getInt(1);
			}
			return idGerado;
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método responsável por atualizar as informações das pessoas.
	 * 
	 * @param pessoaDTO
	 * @throws PersistenciaException
	 */
	public void atualizarPessoa(PessoaDTO pessoaDTO) throws PersistenciaException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE TB_PESSOA ");
			sql.append(" SET NOME = ?, CPF = ?, DT_NASC = ?, SEXO = ?, MINI_BIO = ?");
			sql.append(" WHERE ID_PESSOA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setString(1, pessoaDTO.getNome());
			statement.setString(2, pessoaDTO.getCpf());

			Date dtNasc = new SimpleDateFormat("dd/MM/yyyy").parse(pessoaDTO.getDtNasc());

			statement.setDate(3, new java.sql.Date(dtNasc.getTime()));
			statement.setString(4, String.valueOf(pessoaDTO.getSexo()));
			statement.setString(5, pessoaDTO.getMiniBio());
			statement.setInt(6, pessoaDTO.getIdPessoa());

			statement.executeUpdate();

			removerPreferencias(pessoaDTO.getIdPessoa());
			cadastrarPreferencias(pessoaDTO.getPreferencias(), pessoaDTO.getIdPessoa());

			atualizarEndereco(pessoaDTO.getEndereco());
		} catch (ParseException | ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método responsável por atualizar as informações de endereço.
	 * 
	 * @param enderecoDTO
	 * @throws PersistenciaException
	 */
	public void atualizarEndereco(EnderecoDTO enderecoDTO) throws PersistenciaException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE TB_ENDERECO SET LOGRADOURO = ?, COD_CIDADE = ?");
			sql.append(" WHERE ID_ENDERECO = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setString(1, enderecoDTO.getLogradouro());
			statement.setInt(2, enderecoDTO.getCidade().getIdCidade());
			statement.setInt(3, enderecoDTO.getIdEndereco());

			statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	/**
	 * Método de consulta de todas as entidades de pessoas e suas respectivas
	 * dependências.
	 * 
	 * @return
	 * @throws PersistenciaException
	 */
	public List<PessoaDTO> listarPessoas() throws PersistenciaException {
		List<PessoaDTO> listaPessoas = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PE.ID_PESSOA, PE.NOME, PE.CPF, PE.DT_NASC, PE.SEXO,");
			sql.append("	EN.LOGRADOURO, CID.DESCRICAO AS DESC_CID, UF.DESCRICAO AS DESC_UF");
			sql.append(" FROM TB_PESSOA PE");
			sql.append(" INNER JOIN TB_ENDERECO EN");
			sql.append("	ON PE.COD_ENDERECO = EN.ID_ENDERECO");
			sql.append("		INNER JOIN TB_CIDADE CID");
			sql.append("			ON EN.COD_CIDADE = CID.ID_CIDADE");
			sql.append("		INNER JOIN TB_UF UF");
			sql.append("			ON CID.COD_ESTADO = UF.ID_UF");
			sql.append(" ORDER BY PE.ID_PESSOA;");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PessoaDTO pessoaDTO = new PessoaDTO();
				pessoaDTO.setIdPessoa(resultSet.getInt("id_pessoa"));
				pessoaDTO.setNome(resultSet.getString("nome"));
				pessoaDTO.setCpf(resultSet.getString("cpf"));
				pessoaDTO.setSexo(resultSet.getString("sexo").charAt(0));
				pessoaDTO.setDtNasc(dateFormat.format(resultSet.getDate("dt_nasc")));

				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setLogradouro(resultSet.getString("logradouro"));

				CidadeDTO cidadeDTO = new CidadeDTO();
				cidadeDTO.setDescricao(resultSet.getString("desc_cid"));

				UfDTO ufDTO = new UfDTO();
				ufDTO.setDescricao(resultSet.getString("desc_uf")); 

				enderecoDTO.setCidade(cidadeDTO);
				cidadeDTO.setUf(ufDTO);
				pessoaDTO.setEndereco(enderecoDTO);

				pessoaDTO.setPreferencias(consultarPreferencias(pessoaDTO.getIdPessoa()));

				listaPessoas.add(pessoaDTO);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listaPessoas;
	}

	/**
	 * Método de consulta de uma pessoa pelo seu id.
	 * 
	 * @param idPessoa
	 * @return
	 * @throws PersistenciaException
	 */
	public PessoaDTO consultarPessoaPorId(Integer idPessoa) throws PersistenciaException {
		PessoaDTO pessoaDTO = null;
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PE.ID_PESSOA, PE.NOME, PE.CPF, PE.DT_NASC, PE.SEXO,");
			sql.append("	EN.ID_ENDERECO, EN.LOGRADOURO, PE.MINI_BIO, CID.ID_CIDADE, CID.DESCRICAO AS DESC_CID, ");
			sql.append("	UF.ID_UF, UF.DESCRICAO AS DESC_UF");
			sql.append(" FROM TB_PESSOA PE");
			sql.append(" INNER JOIN TB_ENDERECO EN");
			sql.append("	ON PE.COD_ENDERECO = EN.ID_ENDERECO");
			sql.append("		INNER JOIN TB_CIDADE CID");
			sql.append("			ON EN.COD_CIDADE = CID.ID_CIDADE");
			sql.append("		INNER JOIN TB_UF UF");
			sql.append("			ON CID.COD_ESTADO = UF.ID_UF");
			sql.append(" WHERE ID_PESSOA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				pessoaDTO = new PessoaDTO();
				pessoaDTO.setIdPessoa(resultSet.getInt("id_pessoa"));
				pessoaDTO.setNome(resultSet.getString("nome"));
				pessoaDTO.setCpf(resultSet.getString("cpf"));
				pessoaDTO.setMiniBio(resultSet.getString("mini_bio"));
				pessoaDTO.setSexo(resultSet.getString("sexo").charAt(0));
				pessoaDTO.setDtNasc(dateFormat.format(resultSet.getDate("dt_nasc")));

				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setIdEndereco(resultSet.getInt("id_endereco"));
				enderecoDTO.setLogradouro(resultSet.getString("logradouro"));

				CidadeDTO cidadeDTO = new CidadeDTO();
				cidadeDTO.setIdCidade(resultSet.getInt("id_cidade"));
				cidadeDTO.setDescricao(resultSet.getString("desc_cid"));

				UfDTO ufDTO = new UfDTO();
				ufDTO.setIdUF(resultSet.getInt("id_uf"));
				ufDTO.setDescricao(resultSet.getString("desc_uf")); 

				enderecoDTO.setCidade(cidadeDTO);
				cidadeDTO.setUf(ufDTO);
				pessoaDTO.setEndereco(enderecoDTO);

				pessoaDTO.setPreferencias(consultarPreferencias(pessoaDTO.getIdPessoa()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pessoaDTO;
	}

	/**
	 * Método de busca de todas as preferências musicais atreladas a um único id
	 * da pessoa.
	 * 
	 * @param idPessoa
	 * @return
	 * @throws PersistenciaException
	 */
	public List<PreferenciaMusicalDTO> consultarPreferencias(Integer idPessoa) throws PersistenciaException {
		List<PreferenciaMusicalDTO> listaPreferencias = new ArrayList<>();

		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PRE.ID_PREFERENCIA, PRE.DESCRICAO FROM TB_PREFERENCIA PRE");
			sql.append("	INNER JOIN TB_PREFERENCIA_PESSOA PREPES");
			sql.append("		ON PRE.ID_PREFERENCIA = PREPES.COD_PREFERENCIA");
			sql.append("	INNER JOIN TB_PESSOA PES");
			sql.append("		ON PES.ID_PESSOA = PREPES.COD_PESSOA");
			sql.append(" WHERE PES.ID_PESSOA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PreferenciaMusicalDTO preferenciaMusical = new PreferenciaMusicalDTO();
				preferenciaMusical.setIdPreferencia(resultSet.getInt("id_preferencia"));
				preferenciaMusical.setDescricao(resultSet.getString("descricao"));

				listaPreferencias.add(preferenciaMusical);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaPreferencias;
	}

	/**
	 * Método de remoção de uma pessoa a partir do seu id.
	 * 
	 * @param idPessoa
	 * @throws PersistenciaException
	 */
	public void removerPessoa(Integer idPessoa) throws PersistenciaException {
		Connection conexao = null;
		try {
			PessoaDTO pessoaDTO = consultarPessoaPorId(idPessoa);

			if (pessoaDTO.getPreferencias() != null && !pessoaDTO.getPreferencias().isEmpty()) {
				removerPreferencias(pessoaDTO.getIdPessoa());
			}
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TB_PESSOA WHERE ID_PESSOA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, pessoaDTO.getIdPessoa());

			statement.execute();
			
			if (pessoaDTO.getEndereco() != null && pessoaDTO.getEndereco().getIdEndereco() != null) {
				removerEndereco(pessoaDTO.getEndereco().getIdEndereco());
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método responsável por remover um endereço pelo seu id.
	 * 
	 * @param idEndereco
	 * @throws PersistenciaException
	 */
	public void removerEndereco(Integer idEndereco) throws PersistenciaException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TB_ENDERECO WHERE ID_ENDERECO = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idEndereco);

			statement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que remove a lista de preferências passada em relação ao id da
	 * pessoa
	 * 
	 * @param idPessoa
	 * @throws PersistenciaException
	 */
	public void removerPreferencias(Integer idPessoa) throws PersistenciaException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TB_PREFERENCIA_PESSOA WHERE COD_PESSOA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);

			statement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que filtra as informações de pessoas de acordo com o parâmetro
	 * passado.
	 * 
	 * @param pessoaDTO
	 * @return
	 * @throws PersistenciaException
	 */
	public List<PessoaDTO> filtrar(PessoaDTO pessoaDTO) throws PersistenciaException {
		Connection conexao = null;
		List<PessoaDTO> listaPessoas = new ArrayList<PessoaDTO>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PE.ID_PESSOA, PE.NOME, PE.CPF, PE.DT_NASC, PE.SEXO,");
			sql.append("	EN.ID_ENDERECO, EN.LOGRADOURO, PE.MINI_BIO, CID.ID_CIDADE, CID.DESCRICAO AS DESC_CID, ");
			sql.append("	UF.ID_UF, UF.DESCRICAO AS DESC_UF");
			sql.append(" FROM TB_PESSOA PE");
			sql.append(" INNER JOIN TB_ENDERECO EN");
			sql.append("	ON PE.COD_ENDERECO = EN.ID_ENDERECO");
			sql.append("		INNER JOIN TB_CIDADE CID");
			sql.append("			ON EN.COD_CIDADE = CID.ID_CIDADE");
			sql.append("		INNER JOIN TB_UF UF");
			sql.append("			ON CID.COD_ESTADO = UF.ID_UF");
			sql.append("	WHERE 1 = 1 ");

			if (pessoaDTO.getNome() != null && !"".equals(pessoaDTO.getNome())) {
				sql.append(" AND PE.NOME LIKE ? ");
			}

			if (pessoaDTO.getCpf() != null && !"".equals(pessoaDTO.getCpf())) {
				sql.append(" AND PE.CPF LIKE ? ");
			}

			if (pessoaDTO.getDtNasc() != null && !"".equals(pessoaDTO.getDtNasc())) {
				sql.append(" AND PE.DT_NASC = ? ");
			}

			if (pessoaDTO.getSexo() != 0 && pessoaDTO.getSexo() != ' ') {
				sql.append(" AND PE.SEXO = ? ");
			}
			
			if (!pessoaDTO.getPreferencias().isEmpty()) {
				List<Integer> listaIds = filtrarPreferencias(pessoaDTO.getPreferencias());
				if (!listaIds.isEmpty()) {
					String cont = "";
					for (Integer idPessoa : listaIds) {
						cont += idPessoa;
						if (listaIds.indexOf(idPessoa) + 1 != listaIds.size()) {
							cont += ", ";
						}
					}
					sql.append(" AND PE.ID_PESSOA IN (").append(cont).append(")");
				}
			}

			EnderecoDTO endereco = pessoaDTO.getEndereco();
			Integer idUf = endereco.getCidade().getUf().getIdUF();
			if (idUf != null && idUf != 0) {
				sql.append(" AND UF.ID_UF = ").append(endereco.getCidade().getUf().getIdUF());
			}
			
			Integer idCidade = endereco.getCidade().getIdCidade();
			if (idCidade != null && idCidade != 0) {
				sql.append(" AND CID.ID_CIDADE = ").append(endereco.getCidade().getIdCidade());
			}
			
			if (endereco.getLogradouro() != null && !"".equals(endereco.getLogradouro())) {
				sql.append(" AND EN.LOGRADOURO LIKE ? ");
			}
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			int cont = 0;
			if (pessoaDTO.getNome() != null && !"".equals(pessoaDTO.getNome())) {
				statement.setString(++cont, "%" + pessoaDTO.getNome() + "%");
			}
			if (pessoaDTO.getCpf() != null && !"".equals(pessoaDTO.getCpf())) {
				statement.setString(++cont, "%" + pessoaDTO.getCpf() + "%");
			}
			if (pessoaDTO.getDtNasc() != null && !"".equals(pessoaDTO.getDtNasc())) {
				statement.setDate(++cont, new java.sql.Date(dateFormat.parse(pessoaDTO.getDtNasc()).getTime()));
			}
			if (pessoaDTO.getSexo() != 0 && pessoaDTO.getSexo() != ' ') {
				statement.setString(++cont, String.valueOf(pessoaDTO.getSexo()));
			}
			if (endereco.getLogradouro() != null && !"".equals(endereco.getLogradouro())) {
				statement.setString(++cont, "%" + endereco.getLogradouro() + "%");
			}

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				PessoaDTO pessoaAux = new PessoaDTO();
				pessoaAux.setIdPessoa(resultSet.getInt("id_pessoa"));
				pessoaAux.setNome(resultSet.getString("nome"));
				pessoaAux.setCpf(resultSet.getString("cpf"));
				pessoaAux.setSexo(resultSet.getString("sexo").charAt(0));
				pessoaAux.setDtNasc(dateFormat.format(resultSet.getDate("dt_nasc")));

				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setIdEndereco(resultSet.getInt("id_endereco"));
				enderecoDTO.setLogradouro(resultSet.getString("logradouro"));

				CidadeDTO cidadeDTO = new CidadeDTO();
				cidadeDTO.setIdCidade(resultSet.getInt("id_cidade"));
				cidadeDTO.setDescricao(resultSet.getString("desc_cid"));

				UfDTO ufDTO = new UfDTO();
				ufDTO.setIdUF(resultSet.getInt("id_uf"));
				ufDTO.setDescricao(resultSet.getString("desc_uf")); 

				enderecoDTO.setCidade(cidadeDTO);
				cidadeDTO.setUf(ufDTO);
				pessoaAux.setEndereco(enderecoDTO);
				
				pessoaAux.setPreferencias(consultarPreferencias(pessoaAux.getIdPessoa()));
				
				listaPessoas.add(pessoaAux);
			}
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listaPessoas;
	}
	
	/**
	 * Método para filtrar as preferências musicais.
	 * 
	 * @param preferencias
	 * @return
	 * @throws PersistenciaException
	 */
	public List<Integer> filtrarPreferencias(List<PreferenciaMusicalDTO> preferencias) throws PersistenciaException {
		List<Integer> listaIdsPessoas = new ArrayList<>();

		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct pes.id_pessoa");
			sql.append("	 from tb_preferencia pref inner join tb_preferencia_pessoa tpp ");
			sql.append("	  on pref.id_preferencia = tpp.cod_preferencia ");
			sql.append("	  inner join tb_pessoa pes ");
			sql.append("	  on pes.id_pessoa = tpp.cod_pessoa ");
			String cont = "";
			for (PreferenciaMusicalDTO p : preferencias) {
				cont += p.getIdPreferencia();
				if (preferencias.indexOf(p) + 1 != preferencias.size()) {
					cont += ", ";
				}
			}
			sql.append("	  where pref.id_preferencia in (");
			sql.append(cont);
			sql.append(")");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listaIdsPessoas.add(resultSet.getInt(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaIdsPessoas;
	}
}

