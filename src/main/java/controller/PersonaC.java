package controller;

import dao.PersonaImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import model.Persona;

@Named(value = "personaC")
@SessionScoped
public class PersonaC implements Serializable {

    private @Getter
    @Setter
    Persona modelo, update;
    private @Getter
    @Setter
    PersonaImpl dao;
    private @Getter
    @Setter
    List<Persona> lista;

    public PersonaC() {
        dao = new PersonaImpl();
        modelo = new Persona();
        update = new Persona();
        lista = new ArrayList<>();
    }

    @PostConstruct
    public void onInit() {
        try {
            listar();
        } catch (Exception e) {
        }

    }

    public void registrar() throws Exception {
        try {
            modelo.setCODUBI(dao.obtenerCodigoUbigeo(modelo.getCODUBI()));
            dao.registrar(modelo);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "No registrado con éxito"));
        }

        modelo = new Persona();
        listar();
    }

    public void modificar() throws Exception {
        try {
            dao.editar(update);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Modificado con éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "No modificado con éxito"));
        }

    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(modelo);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", " Eliminado con éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No eliminar con éxito"));
        }
    }

    public void listar() throws Exception {
        lista = dao.listar();
    }

    public List<String> completeTextUbigeo(String query) throws SQLException, Exception {
        PersonaImpl daoUbi = new PersonaImpl();
        return daoUbi.autocompleteUbigeo(query);
    }

}
