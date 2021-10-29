package controller;

import dao.VentaImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import model.Producto;
import model.Venta;
import model.Venta_Detalle;
import services.Reporte;

@Named(value = "ventaC")
@SessionScoped
public class VentaC implements Serializable {

    private @Getter
    @Setter
    Venta modelo;
    private @Getter
    @Setter
    Producto producto;
    private @Getter
    @Setter
    Venta_Detalle detalle;
    private @Getter
    @Setter
    List<Venta_Detalle> lista;
    private @Getter
    @Setter
    VentaImpl dao;

    public VentaC() {
        modelo = new Venta();
        detalle = new Venta_Detalle();
        lista = new ArrayList<>();
        dao = new VentaImpl();
        producto = new Producto();
    }

    public void agregar() throws Exception {
        producto.setNOMPRO(dao.obtenerDetallesProducto(detalle));
        producto.setPRECVENTPRO(dao.obtenerPrecio(detalle));
        detalle.setProducto(producto);
        lista.add(detalle);
        total();
        detalle = new Venta_Detalle();
    }

    public void limpiar() {
        modelo = new Venta();
    }

    public void nuevo() {
        
    }

    public void registrar() throws Exception {
        if (lista != null) {
            if (modelo != null) {
                dao.registrar(modelo);
                dao.agregar(lista);
                lista.clear();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con exito"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo registrar"));

            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo registrar"));

        }

        modelo = new Venta();

    }

    public void eliminarC(Venta venta) {
        try {
            dao.eliminar(venta);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registro eliminado con éxito"));
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo registrar"));
        }
    }

    public List<String> autoCompletar(String consulta) {
        return dao.completar(consulta);
    }

    public double total() {
        try {
            double quantity = 0.0;
            for (Venta_Detalle venta_Detalle : lista) {
                quantity += venta_Detalle.getCANVENDET() * venta_Detalle.getProducto().getPRECVENTPRO();
            }

            return quantity;
        } catch (Exception e) {
            System.out.println("Error en total: " + e.getMessage());
            return 0.0;
        }
    }

    public void descargarPDF() throws Exception {
        Reporte report;
        try {
            report = new Reporte();
            report.exportPrograma("ventas.pdf", "boleta");
        } catch (Exception e) {
            System.out.println("Advertencia!, No tiene ningún registro para mostrar");
        }
    }

}
