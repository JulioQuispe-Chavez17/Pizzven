package model;

import java.util.Date;
import lombok.Data;

@Data
public class Historial {
    private int IDHIS, IDPRO, IDPER, CANTHIST;
    private Date FECHIST;
    private String TIPHIST;
}
