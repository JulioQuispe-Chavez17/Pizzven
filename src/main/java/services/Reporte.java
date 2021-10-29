package services;

import dao.Conexion;
import java.io.File;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class Reporte extends Conexion{
    public void exportPrograma(String namefile, String url) throws JRException, IOException, Exception {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/" + url + ".jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), null, this.conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + namefile);
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
