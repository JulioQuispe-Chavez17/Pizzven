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

@FacesValidator(value = "CelularDuplicado")
public class CelularDupli extends Conexion implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (AreaDuplicada(String.valueOf(value)) == true) {
            FacesMessage msg = new FacesMessage("Celular Duplicado");
            throw new ValidatorException(msg);
        }
    }

        public static boolean AreaDuplicada(String codigo) {
        try {
            PreparedStatement ps1 = CelularDupli.conectar().prepareStatement("SELECT CELUSU FROM USUARIO WHERE USUARIO.ESTUSU = 'A' AND CELUSU='" + codigo + "'");           
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return true;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en Celular Duplicado" + e.getMessage());
        }
        return false;
    }
}
