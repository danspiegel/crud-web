package br.edu.devmedia.crud.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Servlet Contexto terminou!");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Servlet Contexto iniciou!");
	}

}
