package br.edu.devmedia.crud.decorator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import br.edu.devmedia.crud.dto.PessoaDTO;
import br.edu.devmedia.crud.dto.PreferenciaMusicalDTO;

public class PessoaDecorator extends TableDecorator {

	public String getNome() {
		PessoaDTO pessoaDTO = (PessoaDTO) getCurrentRowObject();
		return pessoaDTO.getNome() + " *";
	}

	public String getCpf() {
		PessoaDTO pessoaDTO = (PessoaDTO) getCurrentRowObject();
		String cpf = pessoaDTO.getCpf();
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
	}
	
	public String getDtNasc() {
		PessoaDTO pessoaDTO = (PessoaDTO) getCurrentRowObject();
		String data = pessoaDTO.getDtNasc();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFormat = null;
		try {
			dataFormat = df.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return df.format(dataFormat);
	}
	
	public String getEditar() {
		PessoaDTO pessoaDTO = (PessoaDTO) getCurrentRowObject();
		
		StringBuilder edit = new StringBuilder();
		edit.append("<a href='main?acao=editarPessoa&id_pessoa=")
			.append(pessoaDTO.getIdPessoa())
			.append("' title='Editar'> ")
			.append("		<img alt='Edição de Pessoa' src='img/edit.png'/></a>");
		
		return edit.toString();
	}
	
	public String getRemover() {
		PessoaDTO pessoaDTO = (PessoaDTO) getCurrentRowObject();
		
		StringBuilder edit = new StringBuilder();
		edit.append("<a href='main?acao=removerPessoa&id_pessoa=")
		.append(pessoaDTO.getIdPessoa())
		.append("' title='Remover'> ")
		.append("		<img alt='Remoção de Pessoa' src='img/delete.png'/></a>");
		
		return edit.toString();
	}
	
	public String getPreferencias() {
		PessoaDTO pessoaDTO = (PessoaDTO) getCurrentRowObject();
		
		StringBuilder prefs = new StringBuilder();
		if (pessoaDTO.getPreferencias() != null && !pessoaDTO.getPreferencias().isEmpty()) {
			for (PreferenciaMusicalDTO p : pessoaDTO.getPreferencias()) {
				prefs.append("[").append(p.getDescricao()).append("]");
			}
		} else {
			prefs.append("Sem preferências");
		}
		
		return prefs.toString();
	}

}
