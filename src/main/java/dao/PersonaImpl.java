package dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Persona;

public class PersonaImpl extends Conexion implements ICRUD<Persona> {

    @Override
    public void registrar(Persona modelo) throws Exception {
        String sql = "INSERT INTO PERSONA (NOMPER, APEPER, DNIPER, CELPER, TIPPER, DIRPER, CODUBI, ESTPER) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, modelo.getNOMPER());
            ps.setString(2, modelo.getAPEPER());
            ps.setString(3, modelo.getDNIPER());
            ps.setString(4, modelo.getCELPER());
            ps.setString(5, modelo.getTIPPER());
            ps.setString(6, modelo.getDIRPER());
            ps.setString(7, modelo.getCODUBI());
            ps.setString(8, " A ");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registrar Dao " + e.getMessage());
        }
    }

    @Override
    public void editar(Persona modelo) throws Exception {
        String sql = "UPDATE PERSONA SET NOMPER = ?, APEPER = ?, DNIPER = ?, CELPER = ?, TIPPER = ?, DIRPER= ?, CODUBI = ?, ESTPER = ?"
                + " WHERE IDPER = ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, modelo.getNOMPER());
            ps.setString(2, modelo.getAPEPER());
            ps.setString(3, modelo.getDNIPER());
            ps.setString(4, modelo.getCELPER());
            ps.setString(5, modelo.getTIPPER());
            ps.setString(6, modelo.getDIRPER());
            ps.setString(7, modelo.getCODUBI());
            ps.setString(8, "A");
            ps.setInt(9, modelo.getIDPER());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en modificar Persona Dao " + e.getMessage());
        } finally {
            this.cerrarCnx();
        }
    }

    public void eliminar(Persona modelo) throws Exception {
        String SQL = "UPDATE PERSONA SET ESTPER = ? WHERE IDPER = ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(SQL);
            ps.setString(1, "I");
            ps.setInt(2, modelo.getIDPER());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminar Persona Dao" + e);
        }
    }

    @Override
    public void cambiarEstado(Persona modelo) throws Exception {
        String sql = "UPDATE PERSONA SET ESTPER = 'I' WHERE IDPER = ?";
        PreparedStatement ps = this.conectar().prepareStatement(sql);
        ps.setString(1, "I");
        ps.setInt(2, modelo.getIDPER());
        ps.executeUpdate();
        ps.close();

    }

    @Override
    public List<Persona> listar() throws Exception {
        List<Persona> listado = new ArrayList<>();
        Persona modelo;
        String sql = "SELECT * FROM PERSONA WHERE ESTPER = 'A'";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Persona();
                modelo.setIDPER(rs.getInt(1));
                modelo.setNOMPER(rs.getString(2));
                modelo.setAPEPER(rs.getString(3));
                modelo.setDNIPER(rs.getString(4));
                modelo.setCELPER(rs.getString(5));
                modelo.setTIPPER(rs.getString(6));
                modelo.setDIRPER(rs.getString(7));
                modelo.setCODUBI(rs.getString(8));
                modelo.setESTPER(rs.getString(9));
                listado.add(modelo);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en la listado Persona Dao " + e.getMessage());
        }
        return listado;
    }

    public List<String> autocompleteUbigeo(String consulta) throws SQLException, Exception {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT TOP 16 CONCAT(DISTUBI, ', ', PROUBI, ', ',DEPUBI) AS UBIGEODESC FROM UBIGEO WHERE DISTUBI LIKE ?";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("UBIGEODESC"));
            }
        } catch (Exception e) {
            System.out.println("Error en Autocomplete Ubigeo Dao" + e.getMessage());
        }
        return lista;
    }

    public String obtenerCodigoUbigeo(String cadenaUbi) throws SQLException, Exception {
        String sql = "SELECT CODUBI FROM UBIGEO WHERE CONCAT(DISTUBI, ', ', PROUBI, ', ',DEPUBI) = ?";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, cadenaUbi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("CODUBI");
            }
            return rs.getString("CODUBI");
        } catch (Exception e) {
            System.out.println("Error en Obtener Codigo Ubigeo " + e.getMessage());
            throw e;
        }
    }

    public void buscardni(Persona per) throws Exception {
        String leerdni = per.getDNIPER();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/" + leerdni + token;
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                JsonObject rootobj = root.getAsJsonObject();
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
                per.setNOMPER(nombres);
                per.setAPEPER(apellido_paterno + " " + apellido_materno);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Busqueda", "DNI no encontrado"));
        }
    }

    @Override
    public boolean existe(Persona modelo, List<Persona> listaModelo) {
        for (Persona per : listaModelo) {
// 	65789012  ---->	65789011, 65789032, 65789012  
            if (modelo.getDNIPER().equals(per.getDNIPER())) {
                return true;
            }
        }
        return false;
    }
    
    }
    
    
