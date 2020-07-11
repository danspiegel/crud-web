package br.edu.devmedia.crud.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SerlvetReqListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent req) {
		System.out.println("Destruiu. IP Remoto: " + req.getServletRequest().getRemoteAddr());
	}

	@Override
	public void requestInitialized(ServletRequestEvent req) {
		System.out.println("Iniciou. IP Remoto: " + req.getServletRequest().getRemoteAddr());
	}

}
