package util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Mensagem {
	public static final Severity ERRO = FacesMessage.SEVERITY_ERROR;
	public static final Severity FATAL = FacesMessage.SEVERITY_FATAL;
	public static final Severity INFO = FacesMessage.SEVERITY_INFO;
	public static final Severity AVISO = FacesMessage.SEVERITY_WARN;
			
	public static void alerta(FacesMessage.Severity tipo, String titulo, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, titulo, msg));
	}
}
