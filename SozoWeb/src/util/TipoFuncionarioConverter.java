package util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.EntityManagerHelper;
import dao.TipoFuncionarioDAO;
import model.TipoFuncionario;


@FacesConverter(value="TipoFuncConverter",forClass=TipoFuncionario.class)
public class TipoFuncionarioConverter implements Converter {
	
	private TipoFuncionarioDAO dao;
	
	 public Object getAsObject(FacesContext context, UIComponent component, String value)  {     
		 TipoFuncionario tipFunc = null;
			if (value == null)
				return null;
			Integer id = new Integer(value);
			if (id == -1) return null;
			EntityManagerHelper eme = new EntityManagerHelper();
			dao = new TipoFuncionarioDAO(eme.getEntityManager());
			tipFunc = dao.getById(id);
			return tipFunc; 
		}
  
    public String getAsString(FacesContext context, UIComponent componente, Object value) {  
        
    	if (value == null) 
	           return null;  
	    if (value instanceof TipoFuncionario) {  
	    	TipoFuncionario tipFunc = (TipoFuncionario)value; 
	        if (tipFunc.getId() != null)
	        	return tipFunc.getId().toString();  
	    }  
	    else if(value instanceof String && ((String)value).equalsIgnoreCase("-1")){  
	        return "-1";  
	    }
		return null;
    } 	
}