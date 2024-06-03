package ClasesObjetos;

public class Productos
{
    private int numReferencia;
    private String descripcion;
    private int cantidad;
    private float precio;
    private String almacen;
    private String URLfoto;

    public Productos(int numReferencia, String descripcion, int cantidad, float precio, String almacen, String URLfoto) {
        this.numReferencia = numReferencia;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.almacen = almacen;
        this.URLfoto = URLfoto;
    }

    public Productos(String descripcion, int cantidad, float precio, String almacen, String URLfoto) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.almacen = almacen;
        this.URLfoto = URLfoto;
    }

    public int getNumReferencia() {return numReferencia;}
    public String getDescripcion() {return descripcion;}
    public int getCantidad() {return cantidad;}
    public float getPrecio() {return precio;}
    public String getAlmacen() {return almacen;}
    public String getURLfoto() {return URLfoto;}

    public void setNumReferencia(int numReferencia) {this.numReferencia = numReferencia;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
    public void setPrecio(float precio) {this.precio = precio;}
    public void setAlmacen(String almacen) {this.almacen = almacen;}
    public void setURLfoto(String URLfoto) {this.URLfoto = URLfoto;}

}
