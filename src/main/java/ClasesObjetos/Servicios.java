package ClasesObjetos;

public class Servicios
{
    private int idServicio;
    private String descripcion;
    private String fecha; ;
    private float precio;
    private String idVehiculo;
    public Servicios(int idServicio, String descripcion, String fecha, float precio, String idVehiculo)
    {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.precio = precio;
        this.idVehiculo = idVehiculo;
    }

    public int getIdServico() {return idServicio;}
    public String getDescripcion() {return descripcion;}
    public float getPrecio() {return precio;}
    public String getFecha() {return fecha;}
    public String getIdVehiculo() {return idVehiculo;}

    public void setIdServicio(int idServicio) {this.idServicio = idServicio;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setPrecio(float precio) {this.precio = precio;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public void setIdVehiculo(String idVehiculo) {this.idVehiculo = idVehiculo;}

}
