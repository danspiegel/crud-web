package br.edu.devmedia.crud.validator;


import java.util.Map;

import br.edu.devmedia.crud.exception.ValidationException;
import br.edu.devmedia.crud.util.MensagemContantes;

public class LoginValidator implements Validator {

	public boolean validar(Map<String, Object> valores) throws ValidationException {
		String msgErro = "";
		for (String key : valores.keySet()) {
			String data = (String) valores.get(key);
			if (data == null || "".equals(data)) {
				msgErro += MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", key).concat("<br/>");
			}
		}
		if (!"".equals(msgErro)) {
			throw new ValidationException(msgErro);
		}
		
		return true;
	}
	
}
