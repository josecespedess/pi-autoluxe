package ClasesObjetos;

import javafx.scene.control.Button;

public class ProductosServiciosFacturas
{
    private String descripcion;
    private int cantidad;
    private float precioU;
    private float precioT;
    private Button borrar;
    public ProductosServiciosFacturas(String descripcion, int cantidad, float precioU, Button borrar)
    {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioU = precioU;
        this.precioT=precioU*cantidad;
        this.borrar = borrar;
    }

    public String getDescripcion() {return descripcion;}
    public int getCantidad() {return cantidad;}
    public float getPrecioU() {return precioU;}
    public float getPrecioT() {return precioT;}
    public Button getBorrar() {return borrar;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
    public void setPrecioU(float precioU) {this.precioU = precioU;}
    public void setBorrar(Button borrar) {this.borrar = borrar;}
}
