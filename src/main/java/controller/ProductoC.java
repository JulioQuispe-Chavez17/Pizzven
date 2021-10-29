package controller;

import dao.HistorialImpl;
import dao.ProductoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import model.Historial;
import model.Producto;
import services.Reporte;

@Named(value = "productoC")
@SessionScoped
public class ProductoC implements Serializable {
    private @Getter @Setter Producto modelo, update, historia;
    private @Getter @Setter ProductoImpl dao;
    private @Getter @Setter HistorialImpl daoHistoria;
    private @Getter @Setter List<Producto> listado;
    public ProductoC() {
        historia = new Producto();
        daoHistoria = new HistorialImpl();
        modelo = new Producto();
        update = new Producto();
        dao = new ProductoImpl();
        listado = new ArrayList<>();
    }
    @PostConstruct
    public void onInit(){
        try {
            listar();
        } catch (Exception e) {
        }
    }
    public void registrar() throws Exception{
        try {
             dao.registrar(modelo);
                 FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con éxito"));
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "No registrado con éxito"));
        }
   
    modelo = new Producto();
    };
    public void editar() throws Exception{
        try {
            dao.editar(update);
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Modificado con éxito"));
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "No modificado con éxito"));
        }
    };
    public void listar() throws Exception{
    listado = dao.listar();
    };
    public void registrarHistoria(){
        try {
            historia.setIDPRO(update.getIDPRO());
            daoHistoria.registrarIngreso(historia);
                  FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Ingreso Exitoso"));
        } catch (Exception e) {
        }
    
    }
    
     public void descargarPDF() throws Exception {
        Reporte report;
        try {
            report = new Reporte();
            report.exportPrograma("Stock.pdf", "Stock");
        } catch (Exception e) {
            System.out.println("Advertencia!, No tiene ningún registro para mostrar");
        }
    }
}