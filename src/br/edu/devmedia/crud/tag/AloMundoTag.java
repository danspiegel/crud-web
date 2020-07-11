package br.edu.devmedia.crud.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AloMundoTag extends SimpleTagSupport {

	private String msg;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter jspWriter = getJspContext().getOut();
		jspWriter.println(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
