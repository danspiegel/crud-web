package br.edu.devmedia.crud.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
	
	private static final String[] URLS_TO_EXCLUDE = {".css", ".js", ".png", ".jpg", ".gif", "login.jsp"};

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("Filtro de Login destruído: " + new Date());
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		String uri = httpRequest.getRequestURI();
		
		if (!isURIToExclusao(uri, httpRequest)) {
			HttpSession session = httpRequest.getSession();
			if (session.getAttribute("usuario") == null) {
				request.setAttribute("msgErro", "Acesso negado! Você precisar logar primeiro.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("Filtro de Login inicializado: " + new Date());
	}

	private boolean isURIToExclusao(String uri, HttpServletRequest httpRequest) {
		boolean retorno = false;
		for (String url : URLS_TO_EXCLUDE) {
			if (uri != null && uri.endsWith(url)) {
				retorno = true;
			}
			
			if (uri != null && uri.endsWith("main")
					&& (httpRequest.getParameter("acao") != null
					&& httpRequest.getParameter("acao").equals("login"))) {
				retorno = true;
			}
		}
		return retorno;
	}
	
}
