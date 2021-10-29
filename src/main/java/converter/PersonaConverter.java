package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("personaConverter")
public class PersonaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String Persona = "";
        if (value != null) {
            Persona = (String) value;
            switch (Persona) {
                case "A":
                    Persona = "Administrador";
                    break;
                case "R":
                    Persona = "Repartidor";
                    break;
                case "V":
                    Persona = "Vendedor";
                    break;
                case "C":
                    Persona = "Cliente";
                    break;
            }
        }
        return Persona;
    }
}
