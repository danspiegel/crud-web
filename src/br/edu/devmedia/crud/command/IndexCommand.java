package br.edu.devmedia.crud.command;

import javax.servlet.http.HttpServletRequest;

public class IndexCommand implements Command {
	
	public String execute(HttpServletRequest request) {
		return "index.jsp";
	}
	
}
