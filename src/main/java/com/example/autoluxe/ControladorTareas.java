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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;


public class ControladorTareas
{
    @FXML
    private Button btnEmpleados;
    @FXML
    private Label btnCorreo;
    private String correoUsuario = "";
    @FXML
    private AnchorPane contenedor;
    @FXML
    private ImageView btnCerrarSesion;
    //Panel de Añadir Tarea
    @FXML
    private Pane panelCuerpo;
    @FXML
    private TextField tfIDEmpleado;
    @FXML
    private TextField tfAsunto;
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ChoiceBox<String> cbOpcion;
    private String[] listaOpciones={"General","ID Tarea","ID Empleado","Asunto"};
    @FXML
    private Button acAnadir;
    //Panel de Editar Tarea
    @FXML
    private Pane panelCuerpo1;
    @FXML
    private TextField tfBuscar2;
    @FXML
    private TextField tfIDEmpleado2;
    @FXML
    private TextField tfAsunto2;
    @FXML
    private TextArea tfDescripcion2;
    @FXML
    private TextField tfBuscar3;
    @FXML
    private ChoiceBox<String> cbOpcion2;
    @FXML
    private Button acBorrar;
    @FXML
    private Button acEditar;

    //Tabla Tareas [Añadir tarea]
    @FXML
    private TableView<Tareas> tablaTareas;
    @FXML
    private TableColumn<Tareas, String> colIDTarea;
    @FXML
    private TableColumn<Tareas, String> colIDEmpleado;
    @FXML
    private TableColumn<Tareas, String> colAsunto;
    @FXML
    private TableColumn<Tareas, String> colDescripcion;
    //Tabla Tarea [Añadir tarea]
    @FXML
    private TableView<Tareas> tablaTarea;
    @FXML
    private TableColumn<Tareas, String> colIDTarea1;
    @FXML
    private TableColumn<Tareas, String> colIDEmpleado1;
    @FXML
    private TableColumn<Tareas, String> colAsunto1;
    @FXML
    private TableColumn<Tareas, String> colDescripcion1;
    //Tabla Tareas [Editar tarea]
    @FXML
    private TableView<Tareas> tablaTareas2;
    @FXML
    private TableColumn<Tareas, String> colIDTarea2;
    @FXML
    private TableColumn<Tareas, String> colIDEmpleado2;
    @FXML
    private TableColumn<Tareas, String> colAsunto2;
    @FXML
    private TableColumn<Tareas, String> colDescripcion2;
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException
    {
        BDautoluxe.conectar();
        //Aplicamos el ChoiceBox
        cbOpcion.getItems().addAll(listaOpciones);
        cbOpcion.getSelectionModel().selectFirst();
        //Aplicamos el ChoiceBox
        cbOpcion2.getItems().addAll(listaOpciones);
        cbOpcion2.getSelectionModel().selectFirst();
        //Iniciar columnas
        iniciarColumnas();
        //Establecer datos existentes
        establecerTareas();
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
    public void bloquearEmpleados()
    {
        Empleados empleado= BDautoluxe.obtenerEmpleadoCorreo(btnCorreo.getText());
        if(!empleado.getRol().contains("Administrador"))
        {
            btnEmpleados.setDisable(true);
        }
    }
    @FXML
    private void abrirAñadirTarea()
    {
        panelCuerpo.setVisible(true);
        panelCuerpo1.setVisible(false);
    }
    @FXML
    private void abrirEditarTarea()
    {
        panelCuerpo.setVisible(false);
        panelCuerpo1.setVisible(true);
    }
    @FXML
    private void introducirTareaBD() throws SQLException, ClassNotFoundException {
        if (tfIDEmpleado.getText().isEmpty() || tfAsunto.getText().isEmpty() || tfDescripcion.getText().isEmpty() ) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (tfIDEmpleado.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El campo ID Empleado debe de tener 9 caracteres.");
            } else if (tfAsunto.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El asunto tiene más de 50 caracteres.");
            } else if (tfDescripcion.getText().length() > 300) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El asunto tiene más de 300 caracteres.");
            } else {
                Tareas tarea = new Tareas(0, tfIDEmpleado.getText(), tfAsunto.getText(), tfDescripcion.getText());
                BDautoluxe.altaTareaBD(tarea);
                establecerTareaAnadido(tarea);
                establecerTareas();
                limpiarCampos();
            }
        }
    }
    @FXML
    private void actualizarTarea() throws SQLException, ClassNotFoundException {
        if (tfIDEmpleado2.getText().isEmpty() || tfAsunto2.getText().isEmpty() || tfDescripcion2.getText().isEmpty() ) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (tfIDEmpleado2.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El campo ID Empleado debe de tener 9 caracteres.");
            } else if (tfAsunto2.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El asunto tiene más de 50 caracteres.");
            } else if (tfDescripcion2.getText().length() > 300) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El asunto tiene más de 300 caracteres.");
            } else {
                if(BDautoluxe.dniExisteEmpleado(tfIDEmpleado2.getText()))
                {
                    Tareas tarea = new Tareas(Integer.parseInt(tfBuscar2.getText()), tfIDEmpleado2.getText(), tfAsunto2.getText(), tfDescripcion2.getText());
                    BDautoluxe.actualizarTareaBD(tarea);
                    establecerTareaAnadido(tarea);

                    limpiarCampos1();

                    tfBuscar2.clear();
                    acBorrar.setDisable(true);
                    acEditar.setDisable(true);

                    establecerTareas();
                }
                else
                {
                    mostrarAlerta(Alert.AlertType.WARNING, "DNI no existente", "Introduce un DNI de empleado.");
                }
            }
        }
    }
    //Método para actualizar las tablas
    @FXML
    public void actualizarTablas() throws SQLException, ClassNotFoundException {
        establecerTareas();

        tablaTarea.getItems().clear();
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
        TextField[] campos = {tfIDEmpleado, tfAsunto};
        for (TextField campo : campos) {
            campo.clear();
        }
        tfAsunto.setDisable(true);
        tfDescripcion.clear();
        tfDescripcion.setDisable(true);
        acAnadir.setDisable(true);
    }

    private void limpiarCampos1() {
        TextField[] campos = {tfBuscar2,tfIDEmpleado2, tfAsunto2};
        for (TextField campo : campos) {
            campo.clear();
        }
        tfIDEmpleado2.setDisable(true);
        tfAsunto2.setDisable(true);
        tfDescripcion2.clear();
        tfDescripcion2.setDisable(true);
        acAnadir.setDisable(true);
        acBorrar.setDisable(true);

    }


    public void establecerTareas() throws SQLException, ClassNotFoundException {
        tablaTareas.getItems().clear();
        tablaTareas2.getItems().clear();
        List<Tareas> listaTareas= BDautoluxe.listadoTareasBD();
        tablaTareas.setItems((ObservableList<Tareas>) listaTareas);
        tablaTareas2.setItems((ObservableList<Tareas>) listaTareas);
    }

    public void establecerTareaAnadido(Tareas tarea)
    {
        tablaTarea.getItems().clear();
        ObservableList<Tareas> listaTarea = FXCollections.observableArrayList();
        listaTarea.add(tarea);
        tablaTarea.setItems(listaTarea);
    }

    //Método para buscar en la tabla Tareas
    @FXML
    public void buscarDatosTablaTareas() throws SQLException, ClassNotFoundException {
        tablaTareas.getItems().clear();
        String opcion=cbOpcion.getValue();
        switch(opcion)
        {
            case "General"-> establecerTareas();
            default ->{
                List<Tareas> listaTareas=BDautoluxe.listadoTareasBD(opcion,tfBuscar.getText());
                tablaTareas.setItems((ObservableList<Tareas>)listaTareas);
            }
        }
    }
    //Método para buscar en la tabla Tareas 2
    @FXML
    public void buscarDatosTablaTareas2() throws SQLException, ClassNotFoundException {
        tablaTareas2.getItems().clear();
        String opcion=cbOpcion2.getValue();
        switch(opcion)
        {
            case "General"-> establecerTareas();
            default ->{
                List<Tareas> listaTareas=BDautoluxe.listadoTareasBD(opcion,tfBuscar3.getText());
                tablaTareas2.setItems((ObservableList<Tareas>)listaTareas);
            }
        }
    }
    //Método para buscar Empleado
    @FXML
    public void buscarEmpleado() throws  ClassNotFoundException {
        try {
            Empleados empleado = BDautoluxe.obtenerEmpleadoDNI(tfIDEmpleado.getText());
            if (empleado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
            } else {
                tfAsunto.setDisable(false);
                tfDescripcion.setDisable(false);
                acAnadir.setDisable(false);
            }

            if (tfIDEmpleado.getText().isEmpty()) {
                TextField[] campos = {tfAsunto};

                for (TextField campo : campos) {
                    campo.clear();
                    campo.setDisable(true);
                }
                tfDescripcion.setDisable(true);
                tfDescripcion.clear();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Método para buscar Tarea
    @FXML
    public void buscarTarea() throws  ClassNotFoundException {
        try {
            Tareas tarea = BDautoluxe.obtenerTareaID(tfBuscar2.getText());
            if (tarea == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("Tarea no existente");
                alert.showAndWait();
            } else {
                tfIDEmpleado2.setText(tarea.getIdEmpleado());
                tfAsunto2.setText(tarea.getAsunto());
                tfDescripcion2.setText(tarea.getDescripcion());

                tfIDEmpleado2.setDisable(false);
                tfAsunto2.setDisable(false);
                tfDescripcion2.setDisable(false);
                acBorrar.setDisable(false);
                acEditar.setDisable(false);
            }

            if (tfBuscar2.getText().isEmpty()) {
                TextField[] campos = {tfAsunto,tfIDEmpleado2};

                for (TextField campo : campos) {
                    campo.clear();
                    campo.setDisable(true);
                }
                tfDescripcion2.setDisable(true);
                tfDescripcion2.clear();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Método para borrar Tare
    @FXML
    public void borrarTarea() throws ClassNotFoundException {
        try
        {
            BDautoluxe.borrarTareaBD(tfBuscar2.getText());
            tfIDEmpleado2.clear();
            tfAsunto2.clear();
            tfDescripcion2.clear();
            tfBuscar2.clear();

            tfIDEmpleado2.setDisable(true);
            tfAsunto2.setDisable(true);
            tfDescripcion2.setDisable(true);
            acBorrar.setDisable(true);
            acEditar.setDisable(true);

            establecerTareas();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Método para inicializar las columnas y que se vean
    private void iniciarColumnas() {
        colIDTarea.setCellValueFactory(new PropertyValueFactory<Tareas, String>("idTarea"));
        colIDEmpleado.setCellValueFactory(new PropertyValueFactory<Tareas, String>("idEmpleado"));
        colAsunto.setCellValueFactory(new PropertyValueFactory<Tareas, String>("asunto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Tareas, String>("descripcion"));

        colIDTarea1.setCellValueFactory(new PropertyValueFactory<Tareas, String>("idTarea"));
        colIDEmpleado1.setCellValueFactory(new PropertyValueFactory<Tareas, String>("idEmpleado"));
        colAsunto1.setCellValueFactory(new PropertyValueFactory<Tareas, String>("asunto"));
        colDescripcion1.setCellValueFactory(new PropertyValueFactory<Tareas, String>("descripcion"));

        colIDTarea2.setCellValueFactory(new PropertyValueFactory<Tareas, String>("idTarea"));
        colIDEmpleado2.setCellValueFactory(new PropertyValueFactory<Tareas, String>("idEmpleado"));
        colAsunto2.setCellValueFactory(new PropertyValueFactory<Tareas, String>("asunto"));
        colDescripcion2.setCellValueFactory(new PropertyValueFactory<Tareas, String>("descripcion"));
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
}
