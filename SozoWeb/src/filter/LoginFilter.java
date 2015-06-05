package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Funcionario;
import controller.LoginController;

 
public class LoginFilter implements Filter {
	public static Funcionario funcionarioLogado;
	
	public void destroy() {

	}
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	String contextPath = ((HttpServletRequest) request).getContextPath();
    	String area = ((HttpServletRequest) request).getRequestURI().split("/")[2];
    	String pagina = ((HttpServletRequest) request).getRequestURI().split("/")[4];
        if(area.equals("restrito")) {
        	LoginController login = (LoginController) ((HttpServletRequest) request).getSession().getAttribute("login");
        	if (login == null || login.getFuncionarioLogado() == null) {
        		((HttpServletResponse) response).sendRedirect(contextPath + "/login-funcionario.xhtml");
        	} else {
        		funcionarioLogado = login.getFuncionarioLogado();
        		if(login.getFuncionarioLogado().getTipoFuncionario().getNome().equals("Administrador")) {
        			chain.doFilter(request, response);
	        	}else {
	        		System.out.println(pagina);
	        		if(pagina.equals("ocorrencias.xhtml") || pagina.equals("index.xhtml") || pagina.equals("ocorrencia.xhtml") || pagina.equals("ocorrencias")
	        			||	pagina.equals("acompanhamentoFunc.xhtml") ||pagina.equals("acompanhamento-detalhe.xhtml") ) {
	        			chain.doFilter(request, response);
	        		}else {
	        			((HttpServletResponse) response).sendRedirect(contextPath + "/restrito/pagina/index.xhtml");
	        		}
	           	}
	        }
        }
    }
 
    public void init(FilterConfig arg0) throws ServletException {

    }
 
}
