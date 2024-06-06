package com.example.autoluxe;

import ClasesObjetos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorServicios implements Initializable {
    @FXML
    private Button btnEmpleados;
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
    private TextField tfPrecio;

    @FXML
    private ComboBox<String> cbAlmacen;
    @FXML
    private TextField tfBuscar;

    @FXML
    private Label btnCorreo;
    private String correoUsuario;
    private String[] almacenes = {"ALM-1", "ALM-2", "ALM-3"};

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

    @FXML
    private ComboBox<String> cbOpciones;
    private String[] opcionesBuscarProducto = {"General","ID", "Descripción"};

    @FXML
    private TableView<Vehiculos> tablaVehiculosServicios;

    @FXML
    private TableColumn<Vehiculos, String> colCliente2;
    @FXML
    private TableColumn<Servicios, String> colDescripcion2;
    @FXML
    private TableColumn<Servicios, String> colFecha2;
    @FXML
    private TableColumn<Servicios, Integer> colIdServicio2;
    @FXML
    private TableColumn<Vehiculos, String> colMarca2;
    @FXML
    private TableColumn<Vehiculos, String> colMatricula2;
    @FXML
    private TableColumn<Vehiculos, String> colColor2;
    @FXML
    private TableColumn<Vehiculos, String> colModelo2;
    @FXML
    private TableColumn<Vehiculos, String> colNBastidor2;
    @FXML
    private TableColumn<Vehiculos, String> colCombustible2;
    @FXML
    private TableColumn<Servicios, Float> colPrecio2;
    @FXML
    private TableColumn<Servicios, String> colMatriculaServicio2;
    @FXML
    private TextField tfMatricula2;
    @FXML
    private TextField tfReparacion;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField tfPrecio2;
    @FXML
    private TableView<Servicios> tablaServicios;
    @FXML
    private Button btnAnadir2;
    @FXML
    private ComboBox<String> cbBuscarServicios;
    String [] opcionesServicios = {"General", "ID", "Matricula", "Descripcion"};
    @FXML
    private TextField tfBuscarServicio;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BDautoluxe.conectar();
            //Aplicamos la lista al ChoiceBox
            cbAlmacen.getItems().addAll(almacenes);
            cbAlmacen.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbOpciones.getItems().addAll(opcionesBuscarProducto);
            cbOpciones.getSelectionModel().selectFirst();

            cbBuscarServicios.getItems().addAll(opcionesServicios);
            cbBuscarServicios.getSelectionModel().selectFirst();

            establecerProductos();
            iniciarColumnas();
            establecerVehiculos();
            establecerServicios();

            tableStock.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // Verificar si se hizo doble clic
                    // Obtener el elemento seleccionado
                    Productos productoSeleccionado = tableStock.getSelectionModel().getSelectedItem();

                    if (productoSeleccionado != null) {
                        // Aquí abrirías un modal para mostrar los detalles del producto seleccionado
                        mostrarDetallesProducto(productoSeleccionado);
                    }
                }
            });

            tablaServicios.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // Verificar si se hizo doble clic
                    // Obtener el elemento seleccionado
                    Servicios servicioSeleccionado = tablaServicios.getSelectionModel().getSelectedItem();

                    if (servicioSeleccionado != null) {
                        // Aquí abrirías un modal para mostrar los detalles del producto seleccionado
                        mostrarDetallesServicio(servicioSeleccionado);
                    }
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void bloquearEmpleados()
    {
        Empleados empleado= BDautoluxe.obtenerEmpleadoCorreo(btnCorreo.getText());
        if(!empleado.getRol().contains("Administrador"))
        {
            btnEmpleados.setDisable(true);
        }
    }
    //Método para buscar en la tabla Productos
    @FXML
    public void buscarDatosTablaProductos() throws SQLException, ClassNotFoundException {
        tableStock.getItems().clear();
        String opcion = cbOpciones.getValue();
        switch(opcion) {
            case "General"-> establecerProductos();
            default -> {
                List<Productos> listaProductos=BDautoluxe.listadoProductosBD(opcion,tfBuscar.getText());
                tableStock.setItems((ObservableList<Productos>)listaProductos);
            }
        }
    }

    //Método para buscar en la tabla Productos
    @FXML
    public void buscarDatosTablaServicios() throws SQLException, ClassNotFoundException {
        tablaServicios.getItems().clear();
        String opcion = cbBuscarServicios.getValue();
        switch(opcion) {
            case "General"-> establecerServicios();
            default -> {
                List<Servicios> listaServicios=BDautoluxe.listadoServiciosBD(opcion,tfBuscarServicio.getText());
                tablaServicios.setItems((ObservableList<Servicios>)listaServicios);
            }
        }
    }

    // Método para inicializar las columnas
    private void iniciarColumnas() {
        colNReferencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("numReferencia"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Productos, Float>("precio"));
        colAlmacen.setCellValueFactory(new PropertyValueFactory<Productos, String>("almacen"));

        colIdServicio2.setCellValueFactory(new PropertyValueFactory<Servicios, Integer>("idServicio"));
        colDescripcion2.setCellValueFactory(new PropertyValueFactory<Servicios, String>("descripcion"));
        colPrecio2.setCellValueFactory(new PropertyValueFactory<Servicios, Float>("precio"));
        colFecha2.setCellValueFactory(new PropertyValueFactory<Servicios, String>("fecha"));
        colMatriculaServicio2.setCellValueFactory(new PropertyValueFactory<Servicios, String>("idVehiculo"));

        colFecha2.setCellFactory(column -> {
            return new TableCell<Servicios, String>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        LocalDate date = LocalDate.parse(item); // assuming your date string is in ISO_LOCAL_DATE format
                        setText(formatter.format(date));
                    }
                }
            };
        });

        colMatricula2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("matricula"));
        colNBastidor2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("numBastidor"));
        colMarca2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("marca"));
        colModelo2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("modelo"));
        colCliente2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("DNI_cliente"));
        colColor2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("color"));
        colCombustible2.setCellValueFactory(new PropertyValueFactory<Vehiculos, String>("combustible"));
    }

    private void establecerProductos() {
        tableStock.getItems().clear();
        List<Productos> listaProductos = BDautoluxe.listadoProductosBD();
        tableStock.setItems((ObservableList<Productos>) listaProductos);
    }

    @FXML
    private void agregarProducto() {
        // Obtener los valores de los campos
        String descripcion = tfDescripcion.getText();
        String almacen = cbAlmacen.getValue();
        String cantidadText = tfCantidad.getText();
        String precioText = tfPrecio.getText();

        // Verificar si alguno de los campos está vacío
        if (descripcion.isEmpty() || almacen == null || cantidadText.isEmpty() || precioText.isEmpty()) {
            // Mostrar mensaje de error
            mostrarAlerta("Error", "Todos los campos son obligatorios");
        } else {
            try {
                // Convertir los valores de cantidad y precio a los tipos correspondientes
                int cantidad = Integer.parseInt(cantidadText);
                float precio = Float.parseFloat(precioText);

                // Crear el objeto Producto y agregarlo a la base de datos
                Productos producto = new Productos(descripcion, cantidad, precio, almacen, "");
                BDautoluxe.altaProductoBD(producto);

                // Limpiar los campos y actualizar la lista de productos
                limpiarCampos();
                establecerProductos();
            } catch (NumberFormatException e) {
                // Mostrar mensaje de error si la conversión falla (por ejemplo, si el usuario ingresa letras en lugar de números)
                mostrarAlerta("Error", "Cantidad y precio deben ser números válidos");
            }
        }
    }


    @FXML
    private void agregarServicio() throws SQLException, ClassNotFoundException {
        String descripcion = tfReparacion.getText();
        String fecha = dpFecha.getValue() != null ? dpFecha.getValue().toString() : null; // Verificar si la fecha no es nula
        Float precio = !tfPrecio2.getText().isEmpty() ? Float.parseFloat(tfPrecio2.getText()) : null; // Verificar si el campo de precio no está vacío
        String idVehiculo = tfMatricula2.getText();

        if (descripcion.isEmpty() || fecha == null || precio == null || idVehiculo.isEmpty()) {
            mostrarAlerta("Error", "Por favor, complete todos los campos.");
            return; // Salir del método si algún campo está vacío
        }

        Servicios servicio = new Servicios(descripcion, fecha, precio, idVehiculo);
        BDautoluxe.altaServicioBD(servicio);

        tfReparacion.clear();
        tfMatricula2.clear();
        dpFecha.setValue(null);
        tfPrecio2.clear();
        tfReparacion.setDisable(true);
        dpFecha.setDisable(true);
        tfPrecio2.setDisable(true);
        btnAnadir2.setDisable(true);

        establecerServicios();
    }

    private void establecerServicios() throws SQLException, ClassNotFoundException {
        tablaServicios.getItems().clear();
        List<Servicios> listaServicios = BDautoluxe.listadoServiciosBD();
        tablaServicios.setItems((ObservableList<Servicios>) listaServicios);
    }

    //Método para buscar Vehiculo
    @FXML
    private void buscarVehiculo() throws  ClassNotFoundException {
        try {
            Vehiculos vehiculo = BDautoluxe.obtenerVehiculoMatricula(tfMatricula2.getText());
            if(vehiculo == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("Matrícula no existente");
                alert.showAndWait();
            } else {
                tfReparacion.setDisable(false);
                dpFecha.setDisable(false);
                tfPrecio2.setDisable(false);
                btnAnadir2.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void establecerVehiculos() throws SQLException, ClassNotFoundException {
        tablaVehiculosServicios.getItems().clear();
        List<Vehiculos> listaVehiculos=BDautoluxe.listadoVehiculosBD();
        tablaVehiculosServicios.setItems((ObservableList<Vehiculos>) listaVehiculos);
    }

    //Método para actualizar las tablas
    @FXML
    public void actualizarTablas() throws SQLException, ClassNotFoundException {
        establecerProductos();
        establecerServicios();
    }

    private void limpiarCampos() {
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
            nuevaVentana.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/LogoAutoLuxe.png")));
            Stage ventanaActual = (Stage) btnCerrarSesion.getScene().getWindow();
            ventanaActual.close();nuevaVentana.show();
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
        bloquearEmpleados();
    }

    // Método para mostrar los detalles del producto en un modal
    private void mostrarDetallesProducto(Productos producto) {
        // Crear un nuevo Stage para el modal
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Detalles del Producto");

        // Crear un nuevo VBox para el contenido del modal
        VBox modalContent = new VBox();
        modalContent.setSpacing(10);
        modalContent.setAlignment(Pos.CENTER_LEFT);
        modalContent.setPadding(new Insets(20)); // Añadir márgenes

        // Crear TextField para editar los detalles del producto
        TextField tfReferencia = new TextField(String.valueOf(producto.getNumReferencia()));
        tfReferencia.setEditable(false);
        TextField tfDescripcion = new TextField(producto.getDescripcion());
        TextField tfCantidad = new TextField(String.valueOf(producto.getCantidad()));
        TextField tfPrecio = new TextField(String.valueOf(producto.getPrecio()));
        ComboBox<String> tfAlmacen = new ComboBox<>(FXCollections.observableArrayList(almacenes));
        tfAlmacen.setValue(producto.getAlmacen());


        // Crear etiquetas para los TextField
        Label lblReferencia = new Label("Referencia:");
        lblReferencia.setAlignment(Pos.CENTER_LEFT);
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setAlignment(Pos.CENTER_LEFT);
        Label lblCantidad = new Label("Cantidad:");
        lblCantidad.setAlignment(Pos.CENTER_LEFT);
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setAlignment(Pos.CENTER_LEFT);
        Label lblAlmacen = new Label("Almacén:");
        lblAlmacen.setAlignment(Pos.CENTER_LEFT);

        // Agregar las etiquetas y TextField al VBox
        modalContent.getChildren().addAll(
                lblReferencia, tfReferencia,
                lblDescripcion, tfDescripcion,
                lblCantidad, tfCantidad,
                lblPrecio, tfPrecio,
                lblAlmacen, tfAlmacen
        );

        // Crear un HBox para los botones de editar y cerrar
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT); // Alinear a la derecha

        // Crear botón para editar
        Button btnEditar = new Button("EDITAR");
        btnEditar.setStyle("-fx-background-color: #FFA423; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold"); // Estilo CSS para color rojo
        btnEditar.setOnAction(e -> {
            // Crear un nuevo objeto Productos con los valores modificados
            Productos productoModificado = new Productos(producto.getNumReferencia(), tfDescripcion.getText(),
                    Integer.parseInt(tfCantidad.getText()), Float.parseFloat(tfPrecio.getText()),
                    tfAlmacen.getValue(), ""
            );
            BDautoluxe.modificarProductoPorReferencia(productoModificado);

            modalStage.close();
            establecerProductos();
        });

        // Crear botón para eliminar
        Button btnEliminar = new Button("ELIMINAR");
        btnEliminar.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold"); // Estilo CSS para color rojo
        btnEliminar.setOnAction(e -> {
            // Obtener el número de referencia del producto
            int numReferencia = producto.getNumReferencia();
            // Llamar al método en BDautoluxe para eliminar el producto por su número de referencia
            BDautoluxe.eliminarProductoPorReferencia(numReferencia);
            // Cerrar el modal y actualizar la tabla de productos
            modalStage.close();
            establecerProductos();
        });

        // Crear botón para cerrar
        Button btnCerrar = new Button("CERRAR");
        btnCerrar.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold"); // Estilo CSS para color rojo
        btnCerrar.setOnAction(e -> modalStage.close());

        // Agregar los botones al HBox
        buttonsBox.getChildren().addAll(btnEditar, btnEliminar, btnCerrar);

        // Agregar el HBox al VBox
        modalContent.getChildren().add(buttonsBox);

        // Crear una nueva escena con el VBox como raíz y ajustar el tamaño
        Scene modalScene = new Scene(modalContent, 450, 400); // Tamaño ajustado

        // Establecer la escena en el Stage y mostrar el modal
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    // Método para mostrar los detalles del servicio en un modal
    private void mostrarDetallesServicio(Servicios servicio) {
        // Crear un nuevo Stage para el modal
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Detalles del Servicio");

        // Crear un nuevo VBox para el contenido del modal
        VBox modalContent = new VBox();
        modalContent.setSpacing(10);
        modalContent.setAlignment(Pos.CENTER_LEFT);
        modalContent.setPadding(new Insets(20)); // Añadir márgenes



        // Crear TextField para mostrar los detalles del servicio
        TextField tfIdServicio = new TextField(String.valueOf(servicio.getIdServicio()));
        tfIdServicio.setEditable(false);
        TextField tfDescripcion = new TextField(servicio.getDescripcion());
        TextField tfPrecio = new TextField(String.valueOf(servicio.getPrecio()));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.parse(servicio.getFecha())); // Aquí parseamos la fecha del servicio a un objeto LocalDate
        TextField tfMatricula = new TextField(servicio.getIdVehiculo());

        // Crear etiquetas para los TextField
        Label lblIdServicio = new Label("ID del Servicio:");
        lblIdServicio.setAlignment(Pos.CENTER_LEFT);
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setAlignment(Pos.CENTER_LEFT);
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setAlignment(Pos.CENTER_LEFT);
        Label lblFecha = new Label("Fecha:");
        lblFecha.setAlignment(Pos.CENTER_LEFT);
        Label lblMatricula = new Label("Matrícula:");
        lblMatricula.setAlignment(Pos.CENTER_LEFT);

        // Agregar las etiquetas y TextField al VBox
        modalContent.getChildren().addAll(
                lblIdServicio, tfIdServicio,
                lblDescripcion, tfDescripcion,
                lblPrecio, tfPrecio,
                lblMatricula, tfMatricula,
                lblFecha, datePicker
        );

        // Crear un HBox para los botones de editar, eliminar y cerrar
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT); // Alinear a la derecha

        // Crear botón para editar
        Button btnEditar = new Button("EDITAR");
        btnEditar.setStyle("-fx-background-color: #FFA423; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold"); // Estilo CSS para color naranja
        btnEditar.setOnAction(e -> {

            // Suponiendo que item es el objeto del que obtienes la fecha
            LocalDate fechaSeleccionada = datePicker.getValue();

            // Convertir LocalDate a String en el formato deseado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaFormateada = fechaSeleccionada.format(formatter);

            // Crear un nuevo objeto Servicio con los valores modificados
            Servicios servicioModificado = new Servicios (
                    servicio.getIdServicio(), // Mantener el mismo ID del servicio
                    tfDescripcion.getText(), // Obtener la descripción del TextField
                    fechaFormateada, // Usar la fecha formateada
                    Float.parseFloat(tfPrecio.getText()), // Obtener el precio del TextField
                    tfMatricula.getText() // Obtener la matrícula del TextField
            );

            // Llamar al método en BDautoluxe para modificar el servicio por su ID
            BDautoluxe.modificarServicioPorId(servicioModificado);

            // Cerrar el modal y actualizar la tabla de servicios
            modalStage.close();
            try {
                establecerServicios();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Crear botón para eliminar
        Button btnEliminar = new Button("ELIMINAR");
        btnEliminar.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold"); // Estilo CSS para color rojo
        btnEliminar.setOnAction(e -> {
            // Obtener el ID del servicio
            int idServicio = servicio.getIdServicio();
            // Llamar al método en BDautoluxe para eliminar el servicio por su ID
            BDautoluxe.eliminarServicioPorId(idServicio);
            // Cerrar el modal y actualizar la tabla de servicios
            modalStage.close();
            try {
                establecerServicios();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Crear botón para cerrar
        Button btnCerrar = new Button("CERRAR");
        btnCerrar.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold"); // Estilo CSS para color negro
        btnCerrar.setOnAction(e -> modalStage.close());

        // Agregar los botones al HBox
        buttonsBox.getChildren().addAll(btnEditar, btnEliminar, btnCerrar);

        // Agregar el HBox al VBox
        modalContent.getChildren().add(buttonsBox);

        // Crear una nueva escena con el VBox como raíz y ajustar el tamaño
        Scene modalScene = new Scene(modalContent, 450, 400); // Tamaño ajustado

        // Establecer la escena en el Stage y mostrar el modal
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }


}
