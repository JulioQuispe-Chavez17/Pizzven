package model;

import lombok.Data;

@Data
public class Venta_Detalle {
   private  int IDVENDET,CANVENDET,IDVEN, IDPRO;
   private  float TOTVENTDET;
   private  Venta venta;
   private  Producto producto;
}
