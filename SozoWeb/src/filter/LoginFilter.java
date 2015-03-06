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

import controller.FuncionarioController;

 
public class LoginFilter implements Filter {
 
         public void destroy() {

         }
 
         public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        	 	String contextPath = ((HttpServletRequest) request).getContextPath();
        	 	String area = ((HttpServletRequest) request).getRequestURI().split("/")[2];
        	 	System.out.println(area);
        	 	if(area.equals("restrito")) {
	                FuncionarioController funcionario = (FuncionarioController) ((HttpServletRequest) request).getSession().getAttribute("funcionario");
	                if (funcionario == null || funcionario.getFuncionarioLogado() == null) {
	                	((HttpServletResponse) response).sendRedirect(contextPath + "/login-cliente.xhtml");
	                } else {
	                	chain.doFilter(request, response);
	                }
        	 	}
         }
 
         public void init(FilterConfig arg0) throws ServletException {
           // TODO Auto-generated method stub
 
         }
 
}
