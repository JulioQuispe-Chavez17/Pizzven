package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("personaConverter")
public class ProductoConverter {
   
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }
    
    
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String Producto = "";
        if (value != null) {
            Producto = (String) value;
            switch (Producto) {
                case "P":
                    Producto = "Personal";
                    break;
                case "M":
                    Producto = "Medidana";
                    break;
                case "F":
                    Producto = "Familiar";
                    break;
            }
        }
        return Producto;
    }
}
