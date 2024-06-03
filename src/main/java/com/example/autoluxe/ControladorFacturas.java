package com.example.autoluxe;

import ClasesObjetos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;

public class ControladorFacturas
{
    @FXML
    private AnchorPane contenedor;
    @FXML
    private ImageView btnCerrarSesion;
    @FXML
    public static TextField tfTotalPagar;
    @FXML
    private DatePicker dpFechaActual;
    @FXML
    private TextField tfBuscarCliente;
    @FXML
    private ComboBox cbCliente;
    @FXML
    private TextField tfBuscarEmpleado;
    @FXML
    private ComboBox cbEmpleado;
    @FXML
    private TextField tfBuscarProducto;
    @FXML
    private ComboBox cbProducto;
    @FXML
    private TextField tfBuscarServicio;
    @FXML
    private ComboBox cbServicio;
    @FXML
    private Spinner<Integer> cantidadProducto;
    @FXML
    private Spinner<Integer> cantidadServicio;
    SpinnerValueFactory<Integer> vf=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
    //Tabla de los productos/servicios que se añaden a la factura
    @FXML
    public static TableView<?> tablaFactura;
    @FXML
    private TableColumn<ProductosServiciosFacturas, String> colDescripcion;
    @FXML
    private TableColumn<ProductosServiciosFacturas, Integer> colCantidad;
    @FXML
    private TableColumn<ProductosServiciosFacturas, Float> colPrecioU;
    @FXML
    private TableColumn<ProductosServiciosFacturas, Float> colPrecioT;
    @FXML
    private TableColumn<ProductosServiciosFacturas, Button> colBorrar;
    public void initialize() throws SQLException, ClassNotFoundException {
        BDautoluxe.conectar();
        dpFechaActual.setValue(LocalDate.now());
        establecerDNIClientes();
        establecerDNIEmpleados();
        establecerIDProductos();
        establecerIDServicios();
        cantidadProducto.setValueFactory(vf);
        cantidadServicio.setValueFactory(vf);
        iniciarColumnas();
    }
    // Establecer los DNIs en el ChoiceBox Clientes
    public void establecerDNIClientes() throws SQLException, ClassNotFoundException {
        ObservableList<Clientes> listado = (ObservableList<Clientes>) BDautoluxe.listadoClientesBD();
        ObservableList<String> listaDNIs = FXCollections.observableArrayList();
        for (Clientes cliente : listado) {
            listaDNIs.add(cliente.getDNI());
        }
        cbCliente.setItems(listaDNIs);
    }
    // Establecer los DNIs en el ChoiceBox Empleados
    public void establecerDNIEmpleados() throws SQLException, ClassNotFoundException {
        ObservableList<Empleados> listado = (ObservableList<Empleados>) BDautoluxe.listadoEmpleadosBD();
        ObservableList<String> listaDNIs = FXCollections.observableArrayList();
        for (Empleados empleado : listado) {
            listaDNIs.add(empleado.getDNI());
        }
        cbEmpleado.setItems(listaDNIs);
    }
    // Establecer los IDs en el ChoiceBox Productos
    public void establecerIDProductos() throws SQLException, ClassNotFoundException {
        ObservableList<Productos> listado = (ObservableList<Productos>) BDautoluxe.listadoProductosBD();
        ObservableList<String> listaIDs = FXCollections.observableArrayList();
        for (Productos producto : listado) {
            listaIDs.add(String.valueOf(producto.getNumReferencia()));
        }
        cbProducto.setItems(listaIDs);
    }
    // Establecer los IDs en el ChoiceBox Servicios
    public void establecerIDServicios() throws SQLException, ClassNotFoundException {
        ObservableList<Servicios> listado = (ObservableList<Servicios>) BDautoluxe.listadoServiciosBD();
        ObservableList<String> listaIDs = FXCollections.observableArrayList();
        for (Servicios servicios : listado) {
            listaIDs.add(String.valueOf(servicios.getIdServico()));
        }
        cbServicio.setItems(listaIDs);
    }
    @FXML
    public void anadirProducto()
    {

    }
    //Método para buscar Cliente
    @FXML
    public void buscarCliente() throws  ClassNotFoundException {
        try
        {
            Clientes cliente= BDautoluxe.obtenerClienteDNI(tfBuscarCliente.getText());
            if(cliente==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
                tfBuscarCliente.setText("");
                cbCliente.setValue("Seleccione cliente");
            }
            else
            {
                cbCliente.setValue(cliente.getDNI());
            }
            limpiarCampos();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Método para buscar Empleado
    @FXML
    public void buscarEmpleado() throws  ClassNotFoundException {
        try
        {
            Empleados empleado= BDautoluxe.obtenerEmpleadoDNI(tfBuscarEmpleado.getText());
            if(empleado==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
                tfBuscarEmpleado.setText("");
                cbEmpleado.setValue("Seleccione empleado");
            }
            else
            {
                cbEmpleado.setValue(empleado.getDNI());
            }
            limpiarCampos();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Método para buscar Producto
    @FXML
    public void buscarProducto() throws  ClassNotFoundException {
        try
        {
            Productos producto= BDautoluxe.obtenerProductoID(tfBuscarProducto.getText());
            if(producto==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("ID no existente");
                alert.showAndWait();
                tfBuscarProducto.setText("");
                cbProducto.setValue("Seleccione producto");
            }
            else
            {
                cbProducto.setValue(producto.getNumReferencia());
            }
            limpiarCampos();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Método para buscar Producto
    @FXML
    public void buscarServicio() throws  ClassNotFoundException {
        try
        {
            Servicios servicio= BDautoluxe.obtenerServicioID(tfBuscarServicio.getText());
            if(servicio==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("ID no existente");
                alert.showAndWait();
                tfBuscarServicio.setText("");
                cbServicio.setValue("Seleccione producto");
            }
            else
            {
                cbServicio.setValue(servicio.getIdServico());
            }
            limpiarCampos();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void mostrarAlerta(Alert.AlertType tipo, String encabezado) {
        mostrarAlerta(tipo, encabezado, null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Diálogo de Alerta");
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    private void limpiarCampos() {
        TextField[] campos = {tfBuscarCliente,tfBuscarEmpleado,tfBuscarProducto,tfBuscarServicio};
        for (TextField campo : campos) {
            campo.clear();
        }
    }
    @FXML
    private void cerrarVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_InicioSesion.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Método para inicializar las columnas y que se vean
    private void iniciarColumnas() {
        colDescripcion.setCellValueFactory(new PropertyValueFactory<ProductosServiciosFacturas, String>("descripcion"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<ProductosServiciosFacturas, Integer>("cantidad"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<ProductosServiciosFacturas, Float>("precioU"));
        colPrecioT.setCellValueFactory(new PropertyValueFactory<ProductosServiciosFacturas,Float>("precioT"));
        colBorrar.setCellValueFactory(new PropertyValueFactory<ProductosServiciosFacturas, Button>("borrar"));
    }
    /*
    MENU 8/8
     */
    @FXML
    private void abrirTareas()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_tareas.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirEmpleadosYRoles()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_empleadosyroles.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirInicio()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_inicio.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirClientes()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_clientes.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirFacturas()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_facturas.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_perfil.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirTaller()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_taller.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirServicios()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_servicios.fxml"));
            Parent root = loader.load();
            contenedor.getChildren().setAll(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
