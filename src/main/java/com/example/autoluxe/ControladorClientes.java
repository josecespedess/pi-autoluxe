package com.example.autoluxe;

import ClasesObjetos.BDautoluxe;
import ClasesObjetos.Clientes;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorClientes implements Initializable {
    @FXML
    private Label btnCorreo;
    private String correoUsuario = "";
    @FXML
    private AnchorPane contenedor;
    @FXML
    private ImageView btnCerrarSesion;
    //Panel añadir cliente
    @FXML
    private Pane panelCuerpo;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfDNI;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfBastidor;

    @FXML
    private TextField tfMarca;

    @FXML
    private TextField tfModelo;

    @FXML
    private TextField tfColor;

    @FXML
    private ChoiceBox<String> cbCombustible;
    private String[] tiposCombustibles = {"Gasolina", "Diesel"};

    @FXML
    private ChoiceBox<String> cbOpcion;
    private String[] opcionesBuscarCliente = {"General","DNI", "Nombre","Apellidos"};

    @FXML
    private TextField tfBuscar;

    //Panel editar cliente
    @FXML
    private Pane panelCuerpo1;

    @FXML
    private TextField tfBuscarDNI;

    @FXML
    private TextField tfBuscar2;

    @FXML
    private TextField tfNombre2;

    @FXML
    private TextField tfApellidos2;

    @FXML
    private TextField tfTelefono2;

    @FXML
    private TextField tfEmail2;

    @FXML
    private TextField tfDNI2;

    @FXML
    private Button acBorrar;

    @FXML
    private Button acEditar;
    @FXML
    private ChoiceBox<String> cbOpcion2;
    //Panel añadir coche
    @FXML
    private Pane panelCuerpo2;

    @FXML
    private TextField tfBuscarDNI1;

    @FXML
    private TextField tfMatricula1;

    @FXML
    private TextField tfBastidor1;

    @FXML
    private TextField tfMarca1;

    @FXML
    private TextField tfModelo1;

    @FXML
    private TextField tfColor1;

    @FXML
    private TextField tfBuscar3;

    @FXML
    private ChoiceBox<String> cbCombustible1;

    @FXML
    private ChoiceBox<String> cbOpcion3;
    private String[] opcionesBuscarVehiculos={"General","Matrícula","NumBastidor","Marca","Color"};

    @FXML
    private Button acEditar1;
    //Panel editar coche
    @FXML
    private Pane panelCuerpo3;

    @FXML
    private TextField tfBuscarMatricula;

    @FXML
    private TextField tfMatricula2;

    @FXML
    private TextField tfBastidor2;

    @FXML
    private TextField tfMarca2;

    @FXML
    private TextField tfModelo2;

    @FXML
    private TextField tfColor2;

    @FXML
    private TextField tfBuscar4;

    @FXML
    private ChoiceBox<String> cbCombustible2;

    @FXML
    private ChoiceBox<String> cbOpcion4;

    @FXML
    private Button acBorrar1;

    @FXML
    private Button acEditar2;

    //Tabla Buscar Clientes[Añadir Clientes]
    @FXML
    private TableView<Clientes> tablaClientes;

    @FXML
    private TableColumn<Clientes, String> colApellidos;

    @FXML
    private TableColumn<Clientes, String> colCorreo;

    @FXML
    private TableColumn<Clientes, String> colDNI;

    @FXML
    private TableColumn<Clientes, String> colNombre;

    @FXML
    private TableColumn<Clientes, String> colTelefono;

    //Tabla Cliente Añadido
    @FXML
    private TableView<Clientes> tablaCliente;

    @FXML
    private TableColumn<Clientes, String> colNombre1;

    @FXML
    private TableColumn<Clientes, String> colApellidos1;

    @FXML
    private TableColumn<Clientes, String> colDNI1;

    @FXML
    private TableColumn<Clientes, String> colTelefono1;

    @FXML
    private TableColumn<Clientes, String> colCorreo1;

    //Tabla Editar Clientes[Editar Clientes]
    @FXML
    private TableView<Clientes> tablaClientes2;

    @FXML
    private TableColumn<Clientes, String> colApellidos2;

    @FXML
    private TableColumn<Clientes, String> colCorreo2;

    @FXML
    private TableColumn<Clientes, String> colDNI2;

    @FXML
    private TableColumn<Clientes, String> colNombre2;

    @FXML
    private TableColumn<Clientes, String> colTelefono2;
    //Tabla Buscar Vehículos[Añadir vehículos]
    @FXML
    private TableView<Vehiculos> tablaVehiculos;

    @FXML
    private TableColumn<Vehiculos,String> colMatricula;

    @FXML
    private TableColumn<Vehiculos,String> colBastidor;

    @FXML
    private TableColumn<Vehiculos,String> colMarca;

    @FXML
    private TableColumn<Vehiculos,String> colModelo;

    @FXML
    private TableColumn<Vehiculos,String> colDNICliente;

    @FXML
    private TableColumn<Vehiculos,String> colColor;

    @FXML
    private TableColumn<Vehiculos,String> colCombustible;
    //Tabla Vehículo Añadido
    @FXML
    private TableView<Vehiculos> tablaVehiculo;

    @FXML
    private TableColumn<Vehiculos,String> colMatricula1;

    @FXML
    private TableColumn<Vehiculos,String> colBastidor1;

    @FXML
    private TableColumn<Vehiculos,String> colMarca1;

    @FXML
    private TableColumn<Vehiculos,String> colModelo1;

    @FXML
    private TableColumn<Vehiculos,String> colDNICliente1;

    @FXML
    private TableColumn<Vehiculos,String> colColor1;

    @FXML
    private TableColumn<Vehiculos,String> colCombustible1;
    //Tabla Buscar Vehículos[Editar vehículos]
    @FXML
    private TableView<Vehiculos> tablaVehiculos2;

    @FXML
    private TableColumn<Vehiculos,String> colMatricula2;

    @FXML
    private TableColumn<Vehiculos,String> colBastidor2;

    @FXML
    private TableColumn<Vehiculos,String> colMarca2;

    @FXML
    private TableColumn<Vehiculos,String> colModelo2;

    @FXML
    private TableColumn<Vehiculos,String> colDNICliente2;

    @FXML
    private TableColumn<Vehiculos,String> colColor2;

    @FXML
    private TableColumn<Vehiculos,String> colCombustible2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BDautoluxe.conectar();
            //Aplicamos la lista al ChoiceBox
            cbOpcion.getItems().addAll(opcionesBuscarCliente);
            cbOpcion.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbOpcion2.getItems().addAll(opcionesBuscarCliente);
            cbOpcion2.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbOpcion3.getItems().addAll(opcionesBuscarVehiculos);
            cbOpcion3.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbOpcion4.getItems().addAll(opcionesBuscarVehiculos);
            cbOpcion4.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbCombustible.getItems().addAll(tiposCombustibles);
            cbCombustible.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbCombustible1.getItems().addAll(tiposCombustibles);
            cbCombustible1.getSelectionModel().selectFirst();
            //Aplicamos la lista al ChoiceBox
            cbCombustible2.getItems().addAll(tiposCombustibles);
            cbCombustible2.getSelectionModel().selectFirst();
            //Aplicamos las listas en las tablas
            establecerClientes();
            establecerVehiculos();
            iniciarColumnas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void introducirClienteBD() throws SQLException, ClassNotFoundException {
        if (tfNombre.getText().isEmpty() || tfDNI.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfEmail.getText().isEmpty() || tfTelefono.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (tfNombre.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Nombre.");
            } else if (tfApellidos.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Apellidos.");
            } else if (tfTelefono.getText().length() > 12) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Teléfono.");
            } else if (tfDNI.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El campo DNI debe de tener 9 caracteres.");
            } else if (!tfEmail.getText().contains("@") || tfEmail.getText().length() > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Formato no válido", "El correo no contiene [@] o ha excedido de los 100 caracteres disponibles.");
            } else {
                if (!BDautoluxe.dniExisteCliente(tfDNI.getText()) && !BDautoluxe.correoExisteCliente(tfEmail.getText())) {
                    Clientes cliente = new Clientes(tfDNI.getText(), tfNombre.getText(), tfApellidos.getText(), tfTelefono.getText(), tfEmail.getText());
                    BDautoluxe.insertarCliente(cliente);
                    establecerClienteAnadido(cliente);
                    establecerClientes();
                    if (tfMatricula.getText().isEmpty() || tfBastidor.getText().isEmpty() || tfMarca.getText().isEmpty() || tfModelo.getText().isEmpty() || tfColor.getText().isEmpty()) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente sin vehículo");
                    }
                    else
                    {
                        if(!BDautoluxe.matriculaExisteVehiculo(tfMatricula.getText()))
                        {
                            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente con vehículo");
                            Vehiculos vehiculo = new Vehiculos(tfMatricula.getText(), tfBastidor.getText(), tfMarca.getText(), tfModelo.getText(), cbCombustible.getValue(), tfColor.getText(), tfDNI.getText());
                            BDautoluxe.insertarVehiculo(vehiculo);
                        }
                        else
                        {
                            mostrarAlerta(Alert.AlertType.WARNING, "Matrícula existente", "La matrícula ya existe en la base de datos.");
                        }
                    }
                    limpiarCampos();
                }
                else
                {
                    mostrarAlerta(Alert.AlertType.WARNING, "DNI o correo existente", "El DNI o correo ingresado ya existe en la base de datos.");
                }
            }
        }
    }
    @FXML
    private void actualizarCliente() throws SQLException, ClassNotFoundException {
        if (tfNombre2.getText().isEmpty() || tfDNI2.getText().isEmpty() || tfApellidos2.getText().isEmpty() || tfEmail2.getText().isEmpty() || tfTelefono2.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (tfNombre2.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Nombre.");
            } else if (tfApellidos2.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Apellidos.");
            } else if (tfTelefono2.getText().length() > 12) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Teléfono.");
            } else if (tfDNI2.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de caracteres", "El campo DNI debe de tener 9 caracteres.");
            } else if (!tfEmail2.getText().contains("@") || tfEmail2.getText().length() > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Formato no válido", "El correo no tiene las credenciales de AutoLuxe [@autoluxe.com] o ha excedido de los 100 caracteres disponibles.");
            } else {
                Clientes cliente = new Clientes(tfDNI2.getText(), tfNombre2.getText(), tfApellidos2.getText(), tfTelefono2.getText(), tfEmail2.getText());
                BDautoluxe.actualizarClienteBD(cliente);
                establecerClienteAnadido(cliente);

                limpiarCampos1();

                tfBuscarDNI.clear();
                acBorrar.setDisable(true);
                acEditar.setDisable(true);

                establecerClientes();
            }
        }
    }
    @FXML
    private void introducirVehiculoBD() throws SQLException, ClassNotFoundException {
        if (tfMatricula1.getText().isEmpty()||tfBastidor1.getText().isEmpty()||tfMarca1.getText().isEmpty()||tfModelo1.getText().isEmpty()||tfColor1.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else{
            if (tfMatricula1.getText().length() > 15) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Matrícula.");
            }else if (tfBastidor1.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Número Bastidor.");
            }else if (tfMarca1.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Marca.");
            }else if (tfModelo1.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Modelo.");
            }else if (tfColor1.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Matrícula.");
            }else {
                if(!BDautoluxe.matriculaExisteVehiculo(tfMatricula1.getText()))
                {
                    Vehiculos vehiculo=new Vehiculos(tfMatricula1.getText(),tfBastidor1.getText(),tfMarca1.getText(),tfModelo1.getText(),cbCombustible1.getValue(),tfColor1.getText(),tfBuscarDNI1.getText());
                    BDautoluxe.insertarVehiculo(vehiculo);
                    establecerVehiculoAnadido(vehiculo);

                    limpiarCampos2();

                    tfBuscarDNI1.clear();
                    acEditar.setDisable(true);

                    establecerVehiculos();
                }
                else
                {
                    mostrarAlerta(Alert.AlertType.WARNING, "Matrícula existente", "La matrícula ya existe en la base de datos.");
                }
            }
        }
    }
    @FXML
    private void actualizarVehiculo() throws SQLException, ClassNotFoundException {
        if (tfMatricula2.getText().isEmpty()||tfBastidor2.getText().isEmpty()||tfMarca2.getText().isEmpty()||tfModelo2.getText().isEmpty()||tfColor2.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else{
            if (tfMatricula2.getText().length() > 15) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Matrícula.");
            }else if (tfBastidor2.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Número Bastidor.");
            }else if (tfMarca2.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Marca.");
            }else if (tfModelo2.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Modelo.");
            }else if (tfColor2.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Matrícula.");
            }else {
                Vehiculos vehiculo=new Vehiculos(tfMatricula2.getText(),tfBastidor2.getText(),tfMarca2.getText(),tfModelo2.getText(),cbCombustible2.getValue(),tfColor2.getText(),BDautoluxe.obtenerDNIVehiculo(tfMatricula2.getText()));
                BDautoluxe.actualizarVehiculoBD(vehiculo);
                establecerVehiculoAnadido(vehiculo);

                limpiarCampos3();

                tfBuscarMatricula.clear();
                acBorrar1.setDisable(true);
                acEditar2.setDisable(true);

                establecerVehiculos();
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
        TextField[] campos = {tfNombre, tfApellidos, tfDNI, tfTelefono, tfEmail, tfMatricula, tfBastidor, tfMarca, tfModelo,tfColor};
        for (TextField campo : campos) {
            campo.clear();
        }
    }
    private void limpiarCampos1() {
        TextField[] campos = {tfNombre2, tfApellidos2, tfDNI2, tfTelefono2, tfEmail2};
        for (TextField campo : campos) {
            campo.clear();
            campo.setDisable(true);
        }
    }
    private void limpiarCampos2() {
        TextField[] campos = {tfMatricula1, tfBastidor1, tfMarca1, tfModelo1,tfColor1};
        for (TextField campo : campos) {
            campo.clear();
            campo.setDisable(true);
        }
    }
    private void limpiarCampos3() {
        TextField[] campos = {tfMatricula2, tfBastidor2, tfMarca2, tfModelo2,tfColor2};
        for (TextField campo : campos) {
            campo.clear();
            campo.setDisable(true);
        }
        cbCombustible2.setDisable(true);
    }
    public void establecerClientes() throws SQLException, ClassNotFoundException {
        tablaClientes.getItems().clear();
        tablaClientes2.getItems().clear();
        List<Clientes> listaClientes=BDautoluxe.listadoClientesBD();
        tablaClientes.setItems((ObservableList<Clientes>) listaClientes);
        tablaClientes2.setItems((ObservableList<Clientes>)listaClientes);
    }
    public void establecerClienteAnadido(Clientes cliente)
    {
        tablaCliente.getItems().clear();
        ObservableList<Clientes> listaCliente = FXCollections.observableArrayList();
        listaCliente.add(cliente);
        tablaCliente.setItems(listaCliente);

    }
    public void establecerVehiculos() throws SQLException, ClassNotFoundException {
        tablaVehiculos.getItems().clear();
        tablaVehiculos2.getItems().clear();
        List<Vehiculos> listaVehiculos=BDautoluxe.listadoVehiculosBD();
        tablaVehiculos.setItems((ObservableList<Vehiculos>) listaVehiculos);
        tablaVehiculos2.setItems((ObservableList<Vehiculos>)listaVehiculos);
    }
    public void establecerVehiculoAnadido(Vehiculos vehiculo)
    {
        tablaVehiculo.getItems().clear();
        ObservableList<Vehiculos> listaVehiculo = FXCollections.observableArrayList();
        listaVehiculo.add(vehiculo);
        tablaVehiculo.setItems(listaVehiculo);

    }
    //Método para buscar en la tabla Clientes
    @FXML
    public void buscarDatosTablaClientes() throws SQLException, ClassNotFoundException {
        tablaClientes.getItems().clear();
        String opcion=cbOpcion.getValue();
        switch(opcion)
        {
            case "General"-> establecerClientes();
            default ->{
                List<Clientes> listaClientes=BDautoluxe.listadoClientesBD(opcion,tfBuscar.getText());
                tablaClientes.setItems((ObservableList<Clientes>)listaClientes);
            }
        }
    }
    //Método para buscar en la tabla Clientes 2
    @FXML
    public void buscarDatosTablaClientes2() throws SQLException, ClassNotFoundException {
        tablaClientes2.getItems().clear();
        String opcion=cbOpcion2.getValue();
        switch(opcion)
        {
            case "General"-> establecerClientes();
            default ->{
                List<Clientes> listaClientes=BDautoluxe.listadoClientesBD(opcion,tfBuscar2.getText());
                tablaClientes2.setItems((ObservableList<Clientes>)listaClientes);
            }
        }
    }
    //Método para buscar en la tabla Vehiculos
    @FXML
    public void buscarDatosTablaVehiculos() throws SQLException, ClassNotFoundException {
        tablaVehiculos.getItems().clear();
        String opcion=cbOpcion3.getValue();
        switch(opcion)
        {
            case "General"-> establecerClientes();
            default ->{
                List<Vehiculos> listaVehiculos=BDautoluxe.listadoVehiculosBD(opcion,tfBuscar3.getText());
                tablaVehiculos.setItems((ObservableList<Vehiculos>)listaVehiculos);
            }
        }
    }
    //Método para buscar en la tabla Vehiculos 2
    @FXML
    public void buscarDatosTablaVehiculos2() throws SQLException, ClassNotFoundException {
        tablaVehiculos2.getItems().clear();
        String opcion=cbOpcion4.getValue();
        switch(opcion)
        {
            case "General"-> establecerClientes();
            default ->{
                List<Vehiculos> listaVehiculos=BDautoluxe.listadoVehiculosBD(opcion,tfBuscar4.getText());
                tablaVehiculos2.setItems((ObservableList<Vehiculos>)listaVehiculos);
            }
        }
    }
    //Método para buscar Cliente
    @FXML
    public void buscarCliente() throws  ClassNotFoundException {
        try
        {
            Clientes cliente=BDautoluxe.obtenerClienteDNI(tfBuscarDNI.getText());
            if(cliente==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
            }
            else
            {
                tfNombre2.setText(cliente.getNombre());
                tfApellidos2.setText(cliente.getApellidos());
                tfDNI2.setText(cliente.getDNI());
                tfTelefono2.setText(cliente.getTelefono());
                tfEmail2.setText(cliente.getCorreo());

                tfNombre2.setDisable(false);
                tfApellidos2.setDisable(false);
                tfTelefono2.setDisable(false);
                tfEmail2.setDisable(false);
                acBorrar.setDisable(false);
                acEditar.setDisable(false);
            }

            if(tfBuscarDNI.getText().isEmpty()) {
                TextField [] campos = {tfNombre2, tfApellidos2, tfDNI2, tfTelefono2, tfEmail2};

                for(TextField campo : campos) {
                    campo.clear();
                    campo.setDisable(true);
                }
                acBorrar.setDisable(true);
                acEditar.setDisable(true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //Método para activar los campos para añadir vehiculo a cliente
    @FXML
    public void buscarClienteVehiculo() throws  ClassNotFoundException {
        try
        {
            Clientes cliente=BDautoluxe.obtenerClienteDNI(tfBuscarDNI1.getText());
            if(cliente==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("DNI no existente");
                alert.showAndWait();
            }
            else
            {
                tfMatricula1.setDisable(false);
                tfBastidor1.setDisable(false);
                tfMarca1.setDisable(false);
                tfModelo1.setDisable(false);
                tfColor1.setDisable(false);
                cbCombustible1.setDisable(false);
                acEditar1.setDisable(false);
            }

            if(tfBuscarDNI1.getText().isEmpty()) {
                TextField [] campos = {tfMatricula1, tfBastidor1, tfMarca1, tfModelo1,tfColor1};

                for(TextField campo : campos) {
                    campo.clear();
                    campo.setDisable(true);
                }
                cbCombustible1.setDisable(true);
                acEditar1.setDisable(true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //Método para buscar Vehiculo
    @FXML
    public void buscarVehiculo() throws  ClassNotFoundException {
        try
        {
            Vehiculos vehiculo=BDautoluxe.obtenerVehiculoMatricula(tfBuscarMatricula.getText());
            if(vehiculo==null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diálogo de Alerta");
                alert.setHeaderText("Matrícula no existente");
                alert.showAndWait();
            }
            else
            {
                tfMatricula2.setText(vehiculo.getMatricula());
                tfBastidor2.setText(vehiculo.getNumBastidor());
                tfMarca2.setText(vehiculo.getMarca());
                tfModelo2.setText(vehiculo.getModelo());
                tfColor2.setText(vehiculo.getColor());
                cbCombustible2.setValue(vehiculo.getCombustible());

                tfBastidor2.setDisable(false);
                tfMarca2.setDisable(false);
                tfModelo2.setDisable(false);
                tfColor2.setDisable(false);
                cbCombustible2.setDisable(false);
                acBorrar1.setDisable(false);
                acEditar2.setDisable(false);
            }

            if(tfBuscarMatricula.getText().isEmpty()) {
                TextField [] campos = {tfMatricula2, tfBastidor2, tfMarca2, tfModelo2,tfColor2};

                for(TextField campo : campos) {
                    campo.clear();
                    campo.setDisable(true);
                }
                cbCombustible2.setDisable(true);
                acBorrar1.setDisable(true);
                acEditar2.setDisable(true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Método para borrar Clientes
    @FXML
    public void borrarCliente() throws ClassNotFoundException {
        try {
            BDautoluxe.borrarClienteBD(tfDNI2.getText());
            tfNombre2.clear();
            tfApellidos2.clear();
            tfDNI2.clear();
            tfTelefono2.clear();
            tfEmail2.clear();

            tfNombre2.setDisable(true);
            tfApellidos2.setDisable(true);
            tfDNI2.setDisable(true);
            tfTelefono2.setDisable(true);
            tfEmail2.setDisable(true);

            acBorrar.setDisable(true);
            acEditar.setDisable(true);

            tfBuscarDNI.clear();

            establecerClientes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para borrar Vehiculo
    @FXML
    public void borrarVehiculo() throws ClassNotFoundException {
        try {
            BDautoluxe.borrarVehiculoBD(tfBuscarMatricula.getText());
            tfMatricula2.clear();
            tfBastidor2.clear();
            tfMarca2.clear();
            tfModelo2.clear();
            tfColor2.clear();
            cbCombustible2.setValue("");

            tfMatricula2.setDisable(true);
            tfBastidor2.setDisable(true);
            tfMarca2.setDisable(true);
            tfModelo2.setDisable(true);
            tfColor2.setDisable(true);
            cbCombustible2.setDisable(true);

            acBorrar1.setDisable(true);
            acEditar2.setDisable(true);

            tfBuscarMatricula.clear();

            establecerVehiculos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para actualizar las tablas
    @FXML
    public void actualizarTablas() throws SQLException, ClassNotFoundException {
        establecerClientes();
        establecerVehiculos();

        tablaCliente.getItems().clear();
        tablaVehiculo.getItems().clear();
    }

    //Método para inicializar las columnas y que se vean
    private void iniciarColumnas() {
        colDNI.setCellValueFactory(new PropertyValueFactory<Clientes, String>("DNI"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidos"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));

        colDNI1.setCellValueFactory(new PropertyValueFactory<Clientes, String>("DNI"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre"));
        colApellidos1.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidos"));
        colCorreo1.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correo"));
        colTelefono1.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));

        colDNI2.setCellValueFactory(new PropertyValueFactory<Clientes, String>("DNI"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre"));
        colApellidos2.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidos"));
        colCorreo2.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correo"));
        colTelefono2.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));

        colMatricula.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("matricula"));
        colBastidor.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("numBastidor"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("modelo"));
        colDNICliente.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("DNI_cliente"));
        colColor.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("color"));
        colCombustible.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("combustible"));

        colMatricula1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("matricula"));
        colBastidor1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("numBastidor"));
        colMarca1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("marca"));
        colModelo1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("modelo"));
        colDNICliente1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("DNI_cliente"));
        colColor1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("color"));
        colCombustible1.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("combustible"));

        colMatricula2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("matricula"));
        colBastidor2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("numBastidor"));
        colMarca2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("marca"));
        colModelo2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("modelo"));
        colDNICliente2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("DNI_cliente"));
        colColor2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("color"));
        colCombustible2.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("combustible"));
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
    private void abrirAñadirCliente() {
        panelCuerpo.setVisible(true);
        panelCuerpo1.setVisible(false);
        panelCuerpo2.setVisible(false);
        panelCuerpo3.setVisible(false);
    }

    @FXML
    private void abrirEditarCliente() {
        panelCuerpo.setVisible(false);
        panelCuerpo1.setVisible(true);
        panelCuerpo2.setVisible(false);
        panelCuerpo3.setVisible(false);
    }

    @FXML
    private void abrirAñadirVehiculo() {
        panelCuerpo.setVisible(false);
        panelCuerpo1.setVisible(false);
        panelCuerpo2.setVisible(true);
        panelCuerpo3.setVisible(false);
    }

    @FXML
    private void abrirEditarVehiculo() {
        panelCuerpo.setVisible(false);
        panelCuerpo1.setVisible(false);
        panelCuerpo2.setVisible(false);
        panelCuerpo3.setVisible(true);
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