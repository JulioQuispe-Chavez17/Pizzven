package validaciòn;

import dao.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "UsuarioDuplicado")
public class PersonaDupli extends Conexion implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (AreaDuplicada(String.valueOf(value)) == true) {
            FacesMessage msg = new FacesMessage("Nombre de Usuario Duplicado");
            throw new ValidatorException(msg);
        }
    }

        public static boolean AreaDuplicada(String codigo) {
        try {
            PreparedStatement ps1 = PersonaDupli.conectar().prepareStatement("SELECT NOMPER FROM PERSONA WHERE PERSONA.ESTPER = 'A' AND NOMPER='" + codigo + "'");           
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return true;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en Nombre de Usuario Duplicado" + e.getMessage());
        }
        return false;
    }
}
