package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Venta;
import model.Venta_Detalle;

public class VentaImpl extends Conexion {

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public void registrar(Venta modelo) {
        String sql = "INSERT INTO VENTA (IDCLI, IDVEND, FECVEN, ESTVEN) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, codigo(modelo.getCLIPER()));
            ps.setInt(2, 6);
            ps.setString(3, formato.format(modelo.getFECVEN()));
            ps.setString(4, "A");
            ps.executeUpdate();
        } catch (Exception e) {
        }

    }

    public void agregar(List<Venta_Detalle> lista) throws Exception {
        String sql = "INSERT INTO VENTA_DETALLE (IDVEN,CANVENDET,IDPRO,TOTVENTDET) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            int id = obtenerID();
            for (Venta_Detalle modelo : lista) {
                ps.setInt(1, id);
                ps.setInt(2, modelo.getCANVENDET());
                ps.setInt(3, modelo.getIDPRO());
                ps.setFloat(4, (modelo.getCANVENDET() * modelo.getProducto().getPRECVENTPRO()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en insertardetalle " + e.getMessage());
        }
    }

    public void eliminar(Venta venta) throws Exception {
        try {
            String sql = "UPDATE VENTA.VENTA SET ESTVEN='I' WHERE IDVEN =?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, venta.getESTVEN());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar : " + e.getMessage());
        }
    }

    public int obtenerID() throws Exception {
        String sql = "SELECT MAX(IDVEN) FROM VENTA";
        int id = 1;
        PreparedStatement ps = this.conectar().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    public String obtenerDetallesProducto(Venta_Detalle modelo) throws Exception {
        String sql = "SELECT DISTINCT NOMPRO FROM PRODUCTO WHERE IDPRO = ?";
        PreparedStatement ps = this.conectar().prepareStatement(sql);
        ps.setInt(1, modelo.getIDPRO());
        ResultSet rs = ps.executeQuery();
        String producto;
        while (rs.next()) {
            producto = rs.getString(1);
            return producto;
        }
        return null;

    }

    public float obtenerPrecio(Venta_Detalle modelo) throws Exception {
        String sql = "SELECT DISTINCT PRECVENTPRO FROM PRODUCTO WHERE IDPRO = ?";
        PreparedStatement ps = this.conectar().prepareStatement(sql);
        ps.setFloat(1, modelo.getIDPRO());
        ResultSet rs = ps.executeQuery();
        Float producto;
        while (rs.next()) {
            producto = rs.getFloat(1);
            return producto;
        }
        return (float) 0.0;

    }

    public List<String> completar(String consulta) {
        ResultSet rs;
        List<String> lista = null;
        String sql = "SELECT TOP 10 CONCAT(NOMPER,' ', APEPER) AS INDIVIDUO FROM PERSONA WHERE CONCAT(NOMPER,' ',APEPER) LIKE ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "%" + consulta + "%");
            lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("INDIVIDUO"));
            }
            ps.close();
        } catch (Exception e) {
        }
        return lista;
    }

    public int codigo(String individuo) {
        ResultSet rs;
        String sql = "SELECT IDPER FROM PERSONA WHERE CONCAT(NOMPER,' ', APEPER) LIKE ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, individuo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }
}
