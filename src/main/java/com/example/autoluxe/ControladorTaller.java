package com.example.autoluxe;

import ClasesObjetos.BDautoluxe;
import ClasesObjetos.Clientes;
import ClasesObjetos.Empleados;
import ClasesObjetos.Vehiculos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.html.CSS;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladorTaller  implements Initializable
{
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label btnCorreo;
    private String correoUsuario = "";
    @FXML
    private AnchorPane contenedor;
    @FXML
    private Pane panelEditarElevador;
    //Botones para editar el estado de los elevadores
    @FXML
    private Button acElevador1;
    @FXML
    private Button acElevador2;
    @FXML
    private Button acElevador3;
    @FXML
    private Button acElevador4;
    @FXML
    private Button acElevador5;
    @FXML
    private Button acElevador6;
    //Botones de acciones de editar
    @FXML
    private Button acRevision;
    @FXML
    private Button acLiberar;
    @FXML
    private Button acOcupar;
    @FXML
    private Button acAccion;
    @FXML
    private Button acCancelar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscar1;
    //TextField editar elevador
    @FXML
    private TextField buscar;
    @FXML
    private TextField buscar1;
    @FXML
    private TextField tvMatricula;
    @FXML
    private TextField tvDNICliente;
    @FXML
    private TextField tvNombreCliente;
    @FXML
    private TextField tvDNIEmpleado;
    @FXML
    private TextField tvNombreEmpleado;
    //Listas con los datos de cada elevador
    @FXML
    private TextField nombreCliente1;
    @FXML
    private TextField nombreCliente2;
    @FXML
    private TextField nombreCliente3;
    @FXML
    private TextField nombreCliente4;
    @FXML
    private TextField nombreCliente5;
    @FXML
    private TextField nombreCliente6;
    @FXML
    private TextField nombreEmpleado1;
    @FXML
    private TextField nombreEmpleado2;
    @FXML
    private TextField nombreEmpleado3;
    @FXML
    private TextField nombreEmpleado4;
    @FXML
    private TextField nombreEmpleado5;
    @FXML
    private TextField nombreEmpleado6;
    @FXML
    private TextField dniCliente1;
    @FXML
    private TextField dniCliente2;
    @FXML
    private TextField dniCliente3;
    @FXML
    private TextField dniCliente4;
    @FXML
    private TextField dniCliente5;
    @FXML
    private TextField dniCliente6;
    @FXML
    private TextField dniEmpleado1;
    @FXML
    private TextField dniEmpleado2;
    @FXML
    private TextField dniEmpleado3;
    @FXML
    private TextField dniEmpleado4;
    @FXML
    private TextField dniEmpleado5;
    @FXML
    private TextField dniEmpleado6;
    @FXML
    private TextField matricula1;
    @FXML
    private TextField matricula2;
    @FXML
    private TextField matricula3;
    @FXML
    private TextField matricula4;
    @FXML
    private TextField matricula5;
    @FXML
    private TextField matricula6;
    //Valores del panel editar elevador
    @FXML
    private TextField tvNumeroElevador;
    @FXML
    private TextField tvEstadoActual;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {
            BDautoluxe.conectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        acElevador1.setOnAction(event ->
        {
            abrirEditarElevador(1,acElevador1.getText());
        });
        acElevador2.setOnAction(event ->
        {
            abrirEditarElevador(2,acElevador2.getText());
        });
        acElevador3.setOnAction(event ->
        {
            abrirEditarElevador(3,acElevador3.getText());
        });
        acElevador4.setOnAction(event ->
        {
            abrirEditarElevador(4,acElevador4.getText());
        });
        acElevador5.setOnAction(event ->
        {
            abrirEditarElevador(5,acElevador5.getText());
        });
        acElevador6.setOnAction(event ->
        {
            abrirEditarElevador(6,acElevador6.getText());
        });
        establecerDatos();
    }
     public void establecerDatos()
     {
         TextField[] nombreCliente={nombreCliente1,nombreCliente2,nombreCliente3,nombreCliente4,nombreCliente5,nombreCliente6};
         TextField[] nombreEmpledo={nombreEmpleado1,nombreEmpleado2,nombreEmpleado3,nombreEmpleado4,nombreEmpleado5,nombreEmpleado6};
         TextField[] dniCliente={dniCliente1,dniCliente2,dniCliente3,dniCliente4,dniCliente5,dniCliente6};
         TextField[] dniEmpleado={dniEmpleado1,dniEmpleado2,dniEmpleado3,dniEmpleado4,dniEmpleado5,dniEmpleado6};
         TextField[] matricula={matricula1,matricula2,matricula3,matricula4,matricula5,matricula6};
         Button[] botones={acElevador1,acElevador2,acElevador3,acElevador4,acElevador5,acElevador6};
         for(int i=1;i<=6;i++) {
             String estado = BDautoluxe.estadoElevador(i);
             switch (estado) {
                 case "Libre" -> {
                     botones[i - 1].getStylesheets().remove(String.valueOf(getClass().getResource("/style/estilo_revision.css")));
                     botones[i - 1].getStylesheets().remove(String.valueOf(getClass().getResource("/style/estilo_ocupado.css")));
                     botones[i - 1].getStylesheets().add(String.valueOf(getClass().getResource("/style/estilo_libre.css")));
                     botones[i - 1].setText(estado);
                     matricula[i-1].setText("");
                     dniCliente[i-1].setText("");
                     nombreCliente[i-1].setText("");
                     dniEmpleado[i-1].setText("");
                     nombreEmpledo[i-1].setText("");
                 }
                 case "Ocupado" -> {
                     botones[i - 1].getStylesheets().remove(String.valueOf(getClass().getResource("/style/estilo_revision.css")));
                     botones[i - 1].getStylesheets().remove(String.valueOf(getClass().getResource("/style/estilo_libre.css")));
                     botones[i - 1].getStylesheets().add(String.valueOf(getClass().getResource("/style/estilo_ocupado.css")));
                     botones[i - 1].setText(estado);
                     try
                     {
                         Vehiculos vehiculo=BDautoluxe.obtenerVehiculoMatricula(BDautoluxe.vehiculoElevador(i));
                         Empleados empleado=BDautoluxe.obtenerEmpleadoDNI(BDautoluxe.empleadoElevador(i));
                         if(empleado!=null||vehiculo!=null)
                         {
                             if(vehiculo!=null)
                             {
                                 Clientes cliente=BDautoluxe.obtenerClienteDNI(vehiculo.getDNI_cliente());
                                 matricula[i-1].setText(vehiculo.getMatricula());
                                 dniCliente[i-1].setText(vehiculo.getDNI_cliente());
                                 nombreCliente[i-1].setText(cliente.getNombre());
                             }
                             if(empleado!=null)
                             {
                                 dniEmpleado[i-1].setText(empleado.getDNI());
                                 nombreEmpledo[i-1].setText(empleado.getNombre());
                             }
                         }
                         else
                         {
                             matricula[i-1].setText("");
                             dniCliente[i-1].setText("");
                             nombreCliente[i-1].setText("");
                             dniEmpleado[i-1].setText("");
                             nombreEmpledo[i-1].setText("");
                             BDautoluxe.establecerEstado(i,"Libre");
                         }
                     }
                     catch (Exception e)
                     {
                         e.printStackTrace();
                     }
                 }
                 case "En Revisión" -> {
                     botones[i - 1].getStylesheets().remove(String.valueOf(getClass().getResource("/style/estilo_libre.css")));
                     botones[i - 1].getStylesheets().remove(String.valueOf(getClass().getResource("/style/estilo_ocupado.css")));
                     botones[i - 1].getStylesheets().add(String.valueOf(getClass().getResource("/style/estilo_revision.css")));
                     botones[i - 1].setText(estado);
                     matricula[i-1].setText("");
                     dniCliente[i-1].setText("");
                     nombreCliente[i-1].setText("");
                     dniEmpleado[i-1].setText("");
                     nombreEmpledo[i-1].setText("");
                 }
             }
         }
     }
    private void abrirEditarElevador(int elevador,String estado)
    {
        panelEditarElevador.setVisible(true);
        tvNumeroElevador.setText(String.valueOf(elevador));
        tvEstadoActual.setText(estado);
        try
        {
            Vehiculos vehiculo=BDautoluxe.obtenerVehiculoMatricula(BDautoluxe.vehiculoElevador(elevador));
            Empleados empleado=BDautoluxe.obtenerEmpleadoDNI(BDautoluxe.empleadoElevador(elevador));
            if(vehiculo!=null&&empleado!=null)
            {
                Clientes cliente=BDautoluxe.obtenerClienteDNI(vehiculo.getDNI_cliente());
                tvMatricula.setText(vehiculo.getMatricula());
                tvDNICliente.setText(vehiculo.getDNI_cliente());
                tvNombreCliente.setText(cliente.getNombre());
                tvDNIEmpleado.setText(empleado.getDNI());
                tvNombreEmpleado.setText(empleado.getNombre());
            }
            else
            {
                tvMatricula.setText("");
                tvDNICliente.setText("");
                tvNombreCliente.setText("");
                tvDNIEmpleado.setText("");
                tvNombreEmpleado.setText("");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        acOcupar.setOnAction(event -> {camposOcuparVisibles();});
        acRevision.setOnAction(event ->{
            BDautoluxe.establecerDatos(Integer.parseInt(tvNumeroElevador.getText()),null,null);
            BDautoluxe.establecerEstado(Integer.parseInt(tvNumeroElevador.getText()),"En Revisión");
            establecerDatos();
            acCancelar.setVisible(false);
            acAccion.setVisible(false);
            buscar.setVisible(false);
            buscar1.setVisible(false);
            btnBuscar.setVisible(false);
            btnBuscar1.setVisible(false);
            panelEditarElevador.setVisible(false);
        });
        acLiberar.setOnAction(event ->{
            BDautoluxe.establecerDatos(Integer.parseInt(tvNumeroElevador.getText()),null,null);
            BDautoluxe.establecerEstado(Integer.parseInt(tvNumeroElevador.getText()),"Libre");
            establecerDatos();
            acCancelar.setVisible(false);
            acAccion.setVisible(false);
            buscar.setVisible(false);
            buscar1.setVisible(false);
            btnBuscar.setVisible(false);
            btnBuscar1.setVisible(false);
            panelEditarElevador.setVisible(false);
        });
    }
    private void camposOcuparVisibles()
    {
        acCancelar.setVisible(true);
        acAccion.setVisible(true);
        buscar.setVisible(true);
        buscar1.setVisible(true);
        btnBuscar.setVisible(true);
        btnBuscar1.setVisible(true);
    }
    @FXML
    private void registrarOcupacionElevador()
    {
        if(tvMatricula.getText().isEmpty()||tvDNIEmpleado.getText().isEmpty())
        {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos.", "Por favor rellene los campos para realizar la acción.");
        }
        else
        {
            BDautoluxe.establecerDatos(Integer.parseInt(tvNumeroElevador.getText()),tvMatricula.getText(),tvDNIEmpleado.getText());
            acCancelar.setVisible(false);
            acAccion.setVisible(false);
            buscar.setVisible(false);
            buscar1.setVisible(false);
            btnBuscar.setVisible(false);
            btnBuscar1.setVisible(false);
            panelEditarElevador.setVisible(false);
            tvMatricula.clear();
            tvDNIEmpleado.clear();
            tvNombreEmpleado.clear();
            tvNombreCliente.clear();
            tvDNICliente.clear();
            BDautoluxe.establecerEstado(Integer.parseInt(tvNumeroElevador.getText()),"Ocupado");
            establecerDatos();
        }
    }
    @FXML
    private void buscarVehiculo()
    {
        if(BDautoluxe.matriculaExisteVehiculo(buscar.getText()))
        {
            Vehiculos vehiculo=BDautoluxe.obtenerVehiculoMatricula(buscar.getText());
            tvMatricula.setText(vehiculo.getMatricula());
            tvDNICliente.setText(vehiculo.getDNI_cliente());
            Clientes cliente=BDautoluxe.obtenerClienteDNI(vehiculo.getDNI_cliente());
            tvNombreCliente.setText(cliente.getNombre());
            buscar.clear();
        }
        else
        {
            mostrarAlerta(Alert.AlertType.WARNING, "Matrícula no existente", "Por favor ingrese una matrícula existente.");
        }
    }
    @FXML
    private void buscarEmpleado()
    {
        if(BDautoluxe.dniExisteEmpleado(buscar1.getText()))
        {
            Empleados empleado=BDautoluxe.obtenerEmpleadoDNI(buscar1.getText());
            tvDNIEmpleado.setText(empleado.getDNI());
            tvNombreEmpleado.setText(empleado.getNombre());
            buscar1.clear();
        }
        else
        {
            mostrarAlerta(Alert.AlertType.WARNING, "DNI no existente", "Por favor ingrese un DNI existente.");
        }
    }
    @FXML
    private void cerrarEditarElevador()
    {
        panelEditarElevador.setVisible(false);
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
