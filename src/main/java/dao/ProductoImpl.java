package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Producto;

public class ProductoImpl extends Conexion implements ICRUD<Producto> {

    @Override
    public void registrar(Producto modelo) throws Exception {
        String sql = "INSERT INTO PRODUCTO (CANTPRO, TAMPRO, NOMPRO, PRECVENTPRO, ESTPRO) "
                + "VALUES(?,?,?,?,?)";
        PreparedStatement ps = this.conectar().prepareStatement(sql);
        ps.setInt(1, modelo.getCANTPRO());
        ps.setString(2, modelo.getTAMPRO());
        ps.setString(3, modelo.getNOMPRO());
        ps.setFloat(4, modelo.getPRECVENTPRO());
        ps.setString(5, "A");
        ps.executeUpdate();
    }

    @Override
    public void editar(Producto modelo) throws Exception {
        String sql = "UPDATE PRODUCTO SET NOMPRO = ?, PRECVENTPRO = ?"
                + "WHERE IDPRO = ?";
        PreparedStatement ps = this.conectar().prepareStatement(sql);
        ps.setString(1, modelo.getNOMPRO());
        ps.setFloat(2, modelo.getPRECVENTPRO());
        ps.setInt(3, modelo.getIDPRO());

        ps.executeUpdate();
    }

    @Override
    public void cambiarEstado(Producto modelo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Producto> listar() throws Exception {
        List<Producto> listado = new ArrayList<>();
        Producto modelo;
        String sql = "SELECT IDPRO, CANTPRO, TAMPRO, NOMPRO, PRECVENTPRO FROM PRODUCTO";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Producto();
                modelo.setIDPRO(rs.getInt(1));
                modelo.setCANTPRO(rs.getInt(2));
                modelo.setTAMPRO(rs.getString(3));
                modelo.setNOMPRO(rs.getString(4));
                modelo.setPRECVENTPRO(rs.getFloat(5));
                listado.add(modelo);
            }
        } catch (Exception e) {

        }
        return listado;
    }

    @Override
    public boolean existe(Producto modelo, List<Producto> listaModelo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
