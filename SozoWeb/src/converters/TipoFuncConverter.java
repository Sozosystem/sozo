package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import dao.TipoFuncDAO;
import model.TipoFunc;


@FacesConverter(value="TipoFuncConverter",forClass=TipoFunc.class)
public class TipoFuncConverter implements Converter {
	
	private TipoFuncDAO dao;
	
	 public Object getAsObject(FacesContext context, UIComponent component, String value)  {     
		 TipoFunc tipFunc = null;
			if (value == null)
				return null;
			Integer id = new Integer(value);
			if (id == -1)
				return null;
				tipFunc = dao.pesquisarTipoFunc(id);
			return tipFunc; 
		}
  
    public String getAsString(FacesContext context, UIComponent componente, Object value) {  
        
    	if (value == null) 
	           return null;  
	    if (value instanceof TipoFunc) {  
	        TipoFunc tipFunc = (TipoFunc)value; 
	        if (tipFunc.getId() != null)
	        	return tipFunc.getId().toString();  
	    }  
	    else if(value instanceof String && ((String)value).equalsIgnoreCase("-1")){  
	        return "-1";  
	    }
		return null;
    } 	
}