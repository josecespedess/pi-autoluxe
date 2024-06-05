package com.example.autoluxe;

import ClasesObjetos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

import static java.lang.Float.*;

public class ControladorFacturas
{
    @FXML
    private Label btnCorreo;
    private String correoUsuario = "";
    @FXML
    private AnchorPane contenedor;
    @FXML
    private ImageView btnCerrarSesion;
    @FXML
    public TextField precioTotalPagar;
    public static TextField precioTotalPagar1;
    @FXML
    public TextField precioSubtotal;
    @FXML
    private TextField descuento;
    @FXML
    private TextField IVA;
    @FXML
    private TextField cambio;
    @FXML
    private TextField efectivo;
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
    SpinnerValueFactory<Integer> vf2=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
    //Tabla de los productos/servicios que se añaden a la factura
    @FXML
    private TableView<ProductosServiciosFacturas> tablaFactura;
    private static TableView<ProductosServiciosFacturas> tablaFactura1;
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
        cantidadServicio.setValueFactory(vf2);
        estblecerDatosFactura();
        iniciarColumnas();
        tablaFactura1=tablaFactura;
        precioTotalPagar1=precioTotalPagar;
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
            listaIDs.add(String.valueOf(servicios.getIdServicio()));
        }
        cbServicio.setItems(listaIDs);
    }

    @FXML
    public void generarfactura() {
        if((cbCliente.getValue()=="Seleccione cliente"||cbCliente.getValue()==null||cbCliente.getValue()=="")||(cbEmpleado.getValue()=="Seleccione empleado"||cbEmpleado.getValue()==null||cbEmpleado.getValue()=="")||tablaFactura.getItems().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,"Error al generar facturar.");
            estblecerDatosFactura();
        } else {
            ventaPDF ventaPDF = new ventaPDF();
            ventaPDF.DatosCliente(cbCliente.getValue().toString());
            ventaPDF.DatosEmpleado(cbEmpleado.getValue().toString());
            ventaPDF.generarFactura();
        }
    }

    public void estblecerDatosFactura() {
        precioSubtotal.setText(Float.toString(establecerPrecioSubtotal()));
        IVA.setText("21.00");
        descuento.setText("0.00");
        cambio.setText("0.00");
        efectivo.setText("0.00");
        if(descuento.getText().equals("0.00")) {
            precioTotalPagar.setText(Float.toString(establecerPrecioSubtotal()*1.21f));
        }
    }

    public float establecerPrecioSubtotal() {
        float total = 0.0f;
        for (ProductosServiciosFacturas item : tablaFactura.getItems()) {
            total += item.getPrecioT();
        }
        return total;
    }

    @FXML
    public void calcularEfectivo() {
        if(efectivo.getText().equals("0.00")||efectivo.getText().equals("0")) {
            cambio.setText(precioTotalPagar.getText());
        } else {
            cambio.setText(String.valueOf(Float.parseFloat(precioTotalPagar.getText())-Float.parseFloat(efectivo.getText())));
        }

    }

    @FXML
    public void establecerDescuento() {
        if(descuento.getText().equals("0.00")||descuento.getText().equals("0")) {
            precioTotalPagar.setText(Float.toString(establecerPrecioSubtotal()*1.21f));
        } else {
            float calculo=parseFloat(precioTotalPagar.getText())*(1-parseFloat(descuento.getText())/100);
            precioTotalPagar.setText(String.valueOf(calculo));
        }
    }

    @FXML
    public void anadirProducto() {
        if(cbProducto.getValue()==""||cbProducto.getValue()=="Seleccione producto") {
            mostrarAlerta(Alert.AlertType.WARNING,"Especifique el id del producto");
        } else {
            String idProducto= (String) cbProducto.getValue();
            Productos producto=BDautoluxe.obtenerProductoID(idProducto);
            int cantidad=cantidadProducto.getValue();
            Button clearButton = new Button("Borrar");
            clearButton.setLayoutX(100);
            clearButton.setLayoutY(100);
            clearButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            ProductosServiciosFacturas prod=new ProductosServiciosFacturas(producto.getDescripcion(),cantidad,producto.getPrecio(),clearButton);
            prod.getBorrar().setOnAction(event -> tablaFactura.getItems().remove(prod));
            tablaFactura.getItems().add(prod);
            cbProducto.setValue("Seleccione producto");
            estblecerDatosFactura();
        }
    }

    @FXML
    public void anadirServicios() {
        if(cbServicio.getValue()==""||cbServicio.getValue()=="Seleccione servicio") {
            mostrarAlerta(Alert.AlertType.WARNING,"Especifique el id del servicio");
        } else {
            String idServicio= (String) cbServicio.getValue();
            Servicios servicio=BDautoluxe.obtenerServicioID(idServicio);
            int cantidad=cantidadServicio.getValue();
            Button clearButton = new Button("Borrar");
            clearButton.setLayoutX(100);
            clearButton.setLayoutY(100);
            clearButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            ProductosServiciosFacturas ser=new ProductosServiciosFacturas(servicio.getDescripcion(),cantidad,servicio.getPrecio(),clearButton);
            ser.getBorrar().setOnAction(event -> tablaFactura.getItems().remove(ser));
            tablaFactura.getItems().add(ser);
            cbServicio.setValue("Seleccione servicio");
            estblecerDatosFactura();
        }
    }

    //Método para buscar Cliente
    @FXML
    public void buscarCliente() throws  ClassNotFoundException {
        try {
            Clientes cliente= BDautoluxe.obtenerClienteDNI(tfBuscarCliente.getText());
            if(cliente==null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
                tfBuscarCliente.setText("");
                cbCliente.setValue("Seleccione cliente");
            } else {
                cbCliente.setValue(cliente.getDNI());
            }
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para buscar Empleado
    @FXML
    public void buscarEmpleado() throws  ClassNotFoundException {
        try {
            Empleados empleado= BDautoluxe.obtenerEmpleadoDNI(tfBuscarEmpleado.getText());
            if(empleado==null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
                tfBuscarEmpleado.setText("");
                cbEmpleado.setValue("Seleccione empleado");
            } else {
                cbEmpleado.setValue(empleado.getDNI());
            }
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para buscar Producto
    @FXML
    public void buscarProducto() throws  ClassNotFoundException {
        try {
            Productos producto= BDautoluxe.obtenerProductoID(tfBuscarProducto.getText());
            if(producto==null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("ID no existente");
                alert.showAndWait();
                tfBuscarProducto.setText("");
                cbProducto.setValue("Seleccione producto");
            } else {
                cbProducto.setValue(producto.getNumReferencia());
            }
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para buscar Producto
    @FXML
    public void buscarServicio() throws  ClassNotFoundException {
        try {
            Servicios servicio= BDautoluxe.obtenerServicioID(tfBuscarServicio.getText());
            if(servicio==null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("ID no existente");
                alert.showAndWait();
                tfBuscarServicio.setText("");
                cbServicio.setValue("Seleccione producto");
            } else {
                cbServicio.setValue(servicio.getIdServicio());
            }
            limpiarCampos();
        } catch (Exception e) {
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
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("AutoLuxe");
            nuevaVentana.setScene(new Scene(root,1920,1000));
            Stage ventanaActual = (Stage) btnCerrarSesion.getScene().getWindow();
            ventanaActual.close();nuevaVentana.show();
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
        tablaFactura.setItems(FXCollections.observableArrayList());
    }

    public static TableView<ProductosServiciosFacturas> getTablaFacturas() {return tablaFactura1;}

    public static String getTotalAPagar() {return precioTotalPagar1.getText();}
    /*
    MENU 8/8
     */
    @FXML
    private void abrirTareas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_tareas.fxml"));
            Parent root = loader.load();
            ControladorTareas controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirEmpleadosYRoles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_empleadosyroles.fxml"));
            Parent root = loader.load();
            ControladorEmpleadosYRoles controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_inicio.fxml"));
            Parent root = loader.load();
            ControladorInicio controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_clientes.fxml"));
            Parent root = loader.load();
            ControladorClientes controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirFacturas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_facturas.fxml"));
            Parent root = loader.load();
            ControladorFacturas controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_perfil.fxml"));
            Parent root = loader.load();
            ControladorPerfil controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTaller() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_taller.fxml"));
            Parent root = loader.load();
            ControladorTaller controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirServicios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_servicios.fxml"));
            Parent root = loader.load();
            ControladorServicios controlador = loader.getController();
            controlador.setCorreoUsuario(correoUsuario);
            contenedor.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setCorreoUsuario(String correo) {
        this.correoUsuario = correo;
        this.btnCorreo.setText(correo);
    }
}
