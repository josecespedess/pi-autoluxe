package com.example.autoluxe;

import ClasesObjetos.BDautoluxe;
import ClasesObjetos.Productos;
import ClasesObjetos.Vehiculos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorServicios implements Initializable {
    @FXML
    private ImageView btnCerrarSesion;
    @FXML
    private AnchorPane contenedor;

    @FXML
    private Pane panelGestorServicios;

    @FXML
    private Pane panelGestorStock;

    // TextField
    @FXML
    private TextField tfCantidad;

    @FXML
    private TextField tfDescripcion;

    @FXML
    private TextField tfNReferencia;

    @FXML
    private TextField tfPrecio;

    @FXML
    private ComboBox<String> cbAlmacen;

    @FXML
    private Label btnCorreo;
    private String correoUsuario;
    private String[] almacenes = {"Almacen 1", "Almacen 2", "Almacen 3"};

    @FXML
    private TableColumn<Productos, String> colAlmacen;

    @FXML
    private TableColumn<Productos, Integer> colCantidad;

    @FXML
    private TableColumn<Productos, String> colDescripcion;

    @FXML
    private TableColumn<Productos, Integer> colNReferencia;

    @FXML
    private TableColumn<Productos, Float> colPrecio;

    @FXML
    private TableView<Productos> tableStock;

    private List<Productos> productos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {
            BDautoluxe.conectar();
            //Aplicamos la lista al ChoiceBox
            cbAlmacen.getItems().addAll(almacenes);
            cbAlmacen.getSelectionModel().selectFirst();

            establecerProductos();
            iniciarColumnas();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para inicializar las columnas
    private void iniciarColumnas() {
        colNReferencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("numReferencia"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Productos, Float>("precio"));
        colAlmacen.setCellValueFactory(new PropertyValueFactory<Productos, String>("almacen"));
    }

    private void establecerProductos() {
        tableStock.getItems().clear();
        List<Productos> listaProductos = BDautoluxe.listadoProductosBD();
        tableStock.setItems((ObservableList<Productos>) listaProductos);
    }

    @FXML
    private void agregarProducto() {
        int cantidad = Integer.parseInt(tfCantidad.getText());
        String descripcion = tfDescripcion.getText();
        String almacen = cbAlmacen.getValue();
        float precio = Float.parseFloat(tfPrecio.getText());

        if (descripcion.isEmpty() || almacen == null || tfCantidad.getText().isEmpty() || tfPrecio.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        Productos producto = new Productos(descripcion, cantidad, precio, almacen,"");
        productos.add(producto);
        limpiarCampos();
    }

    private void limpiarCampos() {
        tfNReferencia.clear();
        tfCantidad.clear();
        tfDescripcion.clear();
        cbAlmacen.setValue(null);
        tfPrecio.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void vistaGestorStock() {
        panelGestorStock.setVisible(true);
        panelGestorServicios.setVisible(false);
    }

    @FXML
    private void vistaGestorServicios() {
        panelGestorStock.setVisible(false);
        panelGestorServicios.setVisible(true);
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
