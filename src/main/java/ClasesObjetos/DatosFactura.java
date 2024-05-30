package ClasesObjetos;

import javafx.scene.control.Button;

public class DatosFactura
{
    private String descripcion;
    private int cantidad;
    private float precioU;
    private float precioT;
    private Button borrar;
    public DatosFactura(String descripcion, int cantidad, float precioU, float precioT,Button borrar)
    {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioU = precioU;
        this.precioT = precioT;
        this.borrar = borrar;
    }

    public String getDescripcion() {return descripcion;}
    public int getCantidad() {return cantidad;}
    public float getPrecioU() {return precioU;}
    public float getPrecioT() {return precioT;}
    public Button getBorrar() {return borrar;}

    public void setBorrar(Button borrar) {this.borrar = borrar;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
    public void setPrecioU(float precioU) {this.precioU = precioU;}
    public void setPrecioT(float precioT) {this.precioT = precioT;}
}
