package model;

import java.util.Date;
import lombok.Data;

@Data
public class Venta {
    private  int IDVEN, IDVEND, IDCLI;
    private  Date FECVEN;
    private  String ESTVEN, VENDPER, CLIPER;
    private  Persona empleado;
}
