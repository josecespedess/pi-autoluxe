package com.example.autoluxe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControladorInicio
{
    @FXML
    private Label btnCorreo;
    private String correoUsuario;
    @FXML
    private AnchorPane contenedor;
    @FXML
    private ImageView btnCerrarSesion;
    //Panel de Añadir Empleado
    @FXML
    private Pane panelCuerpo;
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
        System.out.println(correo);
        this.correoUsuario = correo;
        this.btnCorreo.setText(correo);
        System.out.println(correoUsuario);
    }
}
