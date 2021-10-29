package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Producto;

public class HistorialImpl extends Conexion{
    public void registrarIngreso(Producto modelo) throws Exception{
      String sql = "INSERT INTO HISTORIAL (IDPER,IDPRO,CANTHIST,TIPHIS)"
                + " VALUES(?,?,?,?)";
        try {
           PreparedStatement ps = this.conectar().prepareStatement(sql);
           ps.setInt(1, 6);
           ps.setInt(2, modelo.getIDPRO());
           ps.setInt(3, modelo.getCANTPRO());
            ps.setString(4, "I");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error historia "+e.getMessage());
        }
    }
}
