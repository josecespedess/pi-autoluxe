package com.example.autoluxe;

import ClasesObjetos.BDautoluxe;
import ClasesObjetos.Empleados;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorEmpleadosYRoles  {
    @FXML
    private Label btnCorreo;
    private String correoUsuario = "";
    //Clase de la base de datos
    BDautoluxe bd = new BDautoluxe();
    //Paneles
    @FXML
    private AnchorPane contenedor;
    @FXML
    private Pane panelCuerpo1;
    //Botón barra superior
    @FXML
    private ImageView btnCerrarSesion;
    //Panel de Añadir Empleado
    @FXML
    private Pane panelCuerpo;
    @FXML
    private ChoiceBox<String> choiceBoxA;
    private String[] roles = {"Administrador", "Empleado: Mecánico", "Empleado: Chapa y Pintura","Empleado: Operador"};
    @FXML
    private TextField edNombreA;
    @FXML
    private TextField edDNIA;
    @FXML
    private TextField edApellidosA;
    @FXML
    private TextField edCorreoA;
    @FXML
    private TextField edTlfnoA;
    @FXML
    private PasswordField edContrasenaA;
    @FXML
    private TextField edBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private ChoiceBox<String> cbBusquedaEmpleadosA;
    private String[] opcionesBuscarEmpleado = {"General","DNI", "Nombre","Apellidos", "Rol"};
    //Panel de Editar Empleado
    @FXML
    private TextField edNombreA1;
    @FXML
    private TextField edDNIA1;
    @FXML
    private TextField edApellidosA1;
    @FXML
    private TextField edCorreoA1;
    @FXML
    private TextField edTlfnoA1;
    @FXML
    private PasswordField edContrasenaA1;
    @FXML
    private TextField edBuscar1;
    @FXML
    private Button btnBuscar1;
    @FXML
    private TextField edBuscar2;
    @FXML
    private Button btnBuscar2;
    @FXML
    private ChoiceBox<String> choiceBoxA1;
    @FXML
    private ChoiceBox<String> cbBusquedaEmpleadosA1;
    @FXML
    private Button acBorrar;
    @FXML
    private Button acEditar;
    //Tabla Buscar Empleados [Añadir Empleados]
    @FXML
    private TableView<Empleados> vistaEmpleadosA;
    @FXML
    private TableColumn<Empleados, String> colDNIEmpleadoA;
    @FXML
    private TableColumn<Empleados, String> colNombreEmpleadoA;
    @FXML
    private TableColumn<Empleados, String> colApellidosEmpleadoA;
    @FXML
    private TableColumn<Empleados, String> colRolEmpleadoA;
    @FXML
    private TableColumn<Empleados, String> colCorreoEmpleadoA;
    @FXML
    private TableColumn<Empleados, String> colTelefonoEmpleadoA;
    //Tabla Empleado Añadido
    @FXML
    private TableView<Empleados> vistaEmpleadoAnadido;
    @FXML
    private TableColumn<Empleados, String> colDNIEmpleadoA1;
    @FXML
    private TableColumn<Empleados, String> colNombreEmpleadoA1;
    @FXML
    private TableColumn<Empleados, String> colApellidosEmpleadoA1;
    @FXML
    private TableColumn<Empleados, String> colRolEmpleadoA1;
    @FXML
    private TableColumn<Empleados, String> colCorreoEmpleadoA1;
    @FXML
    private TableColumn<Empleados, String> colTelefonoEmpleadoA1;
    //Tabla Buscar Empleados [Editar Empleados]
    @FXML
    private TableView<Empleados> vistaEmpleadosA2;
    @FXML
    private TableColumn<Empleados, String> colDNIEmpleadoA2;
    @FXML
    private TableColumn<Empleados, String> colNombreEmpleadoA2;
    @FXML
    private TableColumn<Empleados, String> colApellidosEmpleadoA2;
    @FXML
    private TableColumn<Empleados, String> colRolEmpleadoA2;
    @FXML
    private TableColumn<Empleados, String> colCorreoEmpleadoA2;
    @FXML
    private TableColumn<Empleados, String> colTelefonoEmpleadoA2;


    public void initialize() throws SQLException, ClassNotFoundException {
        bd.conectar();

        //Aplicamos la lista al ChoiceBox
        choiceBoxA.getItems().addAll(roles);
        choiceBoxA.getSelectionModel().select(1);
        //Aplicamos la lista al ChoiceBox
        cbBusquedaEmpleadosA.getItems().addAll(opcionesBuscarEmpleado);
        cbBusquedaEmpleadosA.getSelectionModel().select(0);
        //Aplicamos la lista al ChoiceBox
        choiceBoxA1.getItems().addAll(roles);
        choiceBoxA1.getSelectionModel().select(1);
        //Aplicamos la lista al ChoiceBox
        cbBusquedaEmpleadosA1.getItems().addAll(opcionesBuscarEmpleado);
        cbBusquedaEmpleadosA1.getSelectionModel().select(0);
        //Aplicamos la lista de todos los empleados en la Tabla Empleados
        establecerEmpleados();
        iniciarColumnas();

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
    @FXML
    private void abrirAñadirEmpleados()
    {
        panelCuerpo.setVisible(true);
        panelCuerpo1.setVisible(false);
    }
    @FXML
    private void abrirEditarEmpleados()
    {
        panelCuerpo.setVisible(false);
        panelCuerpo1.setVisible(true);
    }

    @FXML
    private void introducirEmpleadoBD() throws SQLException, ClassNotFoundException {
        if (edNombreA.getText().isEmpty() || edDNIA.getText().isEmpty() || edApellidosA.getText().isEmpty() || edCorreoA.getText().isEmpty() || edContrasenaA.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (edNombreA.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Nombre.");
            } else if (edApellidosA.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Apellidos.");
            } else if (edTlfnoA.getText().length() > 12) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Teléfono.");
            } else if (edDNIA.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El campo DNI debe de tener 9 caracteres.");
            } else if (edContrasenaA.getText().length() < 8 || edContrasenaA.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Contraseña poca segura", "La contraseña tiene que tener más de 8 caracteres y menos de 50.");
            } else if (!edCorreoA.getText().contains("@autoluxe.com") || edCorreoA.getText().length() > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Formato no válido", "El correo no tiene las credenciales de AutoLuxe [@autoluxe.com] o ha excedido de los 100 caracteres disponibles.");
            } else {
                if (!BDautoluxe.dniExisteEmpleado(edDNIA.getText()) && !BDautoluxe.correoExisteEmpleado(edCorreoA.getText())) {
                    Empleados empleado = new Empleados(edDNIA.getText(), edNombreA.getText(), edApellidosA.getText(), edTlfnoA.getText(), choiceBoxA.getValue(), edCorreoA.getText(), edContrasenaA.getText());
                    BDautoluxe.altaEmpleadoBD(empleado);
                    establecerEmpleadoAnadido(empleado);
                    establecerEmpleados();
                    limpiarCampos();
                } else if (BDautoluxe.dniExisteEmpleado(edDNIA.getText())) {
                    mostrarAlerta(Alert.AlertType.WARNING, "DNI existente", "El DNI ingresado ya existe en la base de datos.");
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Correo existente", "El correo electrónico ingresado ya existe en la base de datos.");
                }
            }
        }
    }


    @FXML
    private void actualizarEmpleado() throws SQLException, ClassNotFoundException {
        if (edNombreA1.getText().isEmpty() || edDNIA1.getText().isEmpty() || edApellidosA1.getText().isEmpty() || edCorreoA1.getText().isEmpty() || edContrasenaA1.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (edNombreA1.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Nombre.");
            } else if (edApellidosA1.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Apellidos.");
            } else if (edTlfnoA1.getText().length() > 12) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Teléfono.");
            } else if (edDNIA1.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El campo DNI debe de tener 9 caracteres.");
            } else if (edContrasenaA1.getText().length() < 8 || edContrasenaA1.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Contraseña poca segura", "La contraseña tiene que tener más de 8 caracteres y menos de 50.");
            } else if (!edCorreoA1.getText().contains("@autoluxe.com") || edCorreoA1.getText().length() > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Formato no válido", "El correo no tiene las credenciales de AutoLuxe [@autoluxe.com] o ha excedido de los 100 caracteres disponibles.");
            } else {
                Empleados empleado = new Empleados(edDNIA1.getText(), edNombreA1.getText(), edApellidosA1.getText(), edTlfnoA1.getText(), choiceBoxA1.getValue(), edCorreoA1.getText(), edContrasenaA1.getText());
                BDautoluxe.actualizarEmpleadoBD(empleado);
                establecerEmpleadoAnadido(empleado);

                limpiarCampos1();

                choiceBoxA1.setDisable(true);
                edBuscar1.clear();
                acBorrar.setDisable(true);
                acEditar.setDisable(true);

                establecerEmpleados();
            }
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
        TextField[] campos = {edNombreA, edApellidosA, edDNIA, edTlfnoA, edCorreoA, edContrasenaA};
        for (TextField campo : campos) {
            campo.clear();
        }
    }

    private void limpiarCampos1() {
        TextField[] campos = {edNombreA1, edApellidosA1, edDNIA1, edTlfnoA1, edCorreoA1, edContrasenaA1};
        for (TextField campo : campos) {
            campo.clear();
            campo.setDisable(true);
        }
    }


    public void establecerEmpleados() throws SQLException, ClassNotFoundException {
        vistaEmpleadosA.getItems().clear();
        List<Empleados> listaEmpleados=BDautoluxe.listadoEmpleadosBD();
        vistaEmpleadosA.setItems((ObservableList<Empleados>) listaEmpleados);
        vistaEmpleadosA2.setItems((ObservableList<Empleados>) listaEmpleados);
    }

    public void establecerEmpleadoAnadido(Empleados empleado)
    {
        vistaEmpleadoAnadido.getItems().clear();
        ObservableList<Empleados> listaEmpleado = FXCollections.observableArrayList();
        listaEmpleado.add(empleado);
        vistaEmpleadoAnadido.setItems(listaEmpleado);
    }

    //Método para buscar en la tabla Empleados
    @FXML
    public void buscarDatosTablaEmpleados() throws SQLException, ClassNotFoundException {
        vistaEmpleadosA.getItems().clear();
        String opcion=cbBusquedaEmpleadosA.getValue();
        switch(opcion)
        {
            case "General"-> establecerEmpleados();
            default ->{
                List<Empleados> listaEmpleados=BDautoluxe.listadoEmpleadosBD(opcion,edBuscar.getText());
                vistaEmpleadosA.setItems((ObservableList<Empleados>)listaEmpleados);
            }
        }
    }

    //Método para buscar en la tabla Empleados 2
    @FXML
    public void buscarDatosTablaEmpleados2() throws SQLException, ClassNotFoundException {
        vistaEmpleadosA2.getItems().clear();
        String opcion=cbBusquedaEmpleadosA1.getValue();
        switch(opcion) {
            case "General"-> establecerEmpleados();
            default -> {
                List<Empleados> listaEmpleados=BDautoluxe.listadoEmpleadosBD(opcion,edBuscar2.getText());
                vistaEmpleadosA2.setItems((ObservableList<Empleados>)listaEmpleados);
            }
        }
    }

    //Método para buscar Empleado
    @FXML
    public void buscarEmpleado() throws  ClassNotFoundException {
        try {
            Empleados empleado=BDautoluxe.obtenerEmpleadoDNI(edBuscar1.getText());
            if(empleado==null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
            } else {
                edNombreA1.setText(empleado.getNombre());
                edApellidosA1.setText(empleado.getApellidos());
                edDNIA1.setText(empleado.getDNI());
                edTlfnoA1.setText(empleado.getTelefono());
                choiceBoxA1.setValue(empleado.getRol());
                edCorreoA1.setText(empleado.getCorreo());
                edContrasenaA1.setText(empleado.getContrasena());

                edNombreA1.setDisable(false);
                edApellidosA1.setDisable(false);
                edTlfnoA1.setDisable(false);
                choiceBoxA1.setDisable(false);
                edCorreoA1.setDisable(false);
                edContrasenaA1.setDisable(false);
                acBorrar.setDisable(false);
                acEditar.setDisable(false);
            }

            if(edBuscar1.getText().isEmpty()) {
                TextField [] campos = {edNombreA1, edApellidosA1, edDNIA1, edTlfnoA1, edCorreoA1, edContrasenaA1};

                for(TextField campo : campos) {
                    campo.clear();
                    campo.setDisable(true);
                }
                choiceBoxA1.setDisable(true);
                acBorrar.setDisable(true);
                acEditar.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Método para borrar Empleado
    @FXML
    public void borrarEmpleado() throws ClassNotFoundException {
        try {
            BDautoluxe.borrarEmpleadoBD(edDNIA1.getText());
            edNombreA1.clear();
            edApellidosA1.clear();
            edDNIA1.clear();
            edTlfnoA1.clear();
            choiceBoxA1.setValue("");
            edCorreoA1.clear();
            edContrasenaA1.clear();

            edNombreA1.setDisable(true);
            edApellidosA1.setDisable(true);
            edDNIA1.setDisable(true);
            edTlfnoA1.setDisable(true);
            choiceBoxA1.setDisable(true);
            edCorreoA1.setDisable(true);
            edContrasenaA1.setDisable(true);
            acBorrar.setDisable(true);
            acEditar.setDisable(true);

            edBuscar1.clear();

            establecerEmpleados();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para actualizar las tablas
    @FXML
    public void actualizarTablas() throws SQLException, ClassNotFoundException {
        establecerEmpleados();

        vistaEmpleadoAnadido.getItems().clear();
    }

    //Método para inicializar las columnas y que se vean
    private void iniciarColumnas() {
        colDNIEmpleadoA.setCellValueFactory(new PropertyValueFactory<Empleados,String>("DNI"));
        colNombreEmpleadoA.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombre"));
        colApellidosEmpleadoA.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidos"));
        colRolEmpleadoA.setCellValueFactory(new PropertyValueFactory<Empleados,String>("rol"));
        colCorreoEmpleadoA.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correo"));
        colTelefonoEmpleadoA.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefono"));

        colDNIEmpleadoA1.setCellValueFactory(new PropertyValueFactory<Empleados,String>("DNI"));
        colNombreEmpleadoA1.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombre"));
        colApellidosEmpleadoA1.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidos"));
        colRolEmpleadoA1.setCellValueFactory(new PropertyValueFactory<Empleados,String>("rol"));
        colCorreoEmpleadoA1.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correo"));
        colTelefonoEmpleadoA1.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefono"));

        colDNIEmpleadoA2.setCellValueFactory(new PropertyValueFactory<Empleados,String>("DNI"));
        colNombreEmpleadoA2.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombre"));
        colApellidosEmpleadoA2.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidos"));
        colRolEmpleadoA2.setCellValueFactory(new PropertyValueFactory<Empleados,String>("rol"));
        colCorreoEmpleadoA2.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correo"));
        colTelefonoEmpleadoA2.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefono"));
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
