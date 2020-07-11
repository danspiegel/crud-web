package br.edu.devmedia.crud.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessaoListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("Sessão Começou!");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Sessão Terminou!");
	}

}
