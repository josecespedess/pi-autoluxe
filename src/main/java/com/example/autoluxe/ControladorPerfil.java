package com.example.autoluxe;

import ClasesObjetos.BDautoluxe;
import ClasesObjetos.Empleados;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladorPerfil implements Initializable {
    // Controlador base de datos
    BDautoluxe bd = new BDautoluxe();

    // Datos del usuario
    String dni, nombre, apellidos, telefono, rol, correo, password;
    @FXML
    private AnchorPane contenedor;

    // Imagen
    @FXML
    private ImageView iconoSetting;

    // Label
    @FXML
    private Label lblNombre;

    @FXML
    private Label lblApellidos;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblDNI;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblROL;

    // TextField
    @FXML
    private TextField tvApellidos;

    @FXML
    private TextField tvDNI;

    @FXML
    private TextField tvEmail;

    @FXML
    private TextField tvNombre;

    @FXML
    private TextField tvRecuperarPass;

    @FXML
    private TextField tvTelefono;

    // Paneles
    @FXML
    private Pane panelCuenta;

    @FXML
    private Pane panelCuenta2;

    // Botones
    @FXML
    private ImageView btnCerrarSesion;

    @FXML
    private Label lbCorreo;
    private String correoUsuario = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            bd.conectar();
            establecerDatos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        panelCuenta.setVisible(true);
        panelCuenta2.setVisible(false);
    }

    @FXML
    private void abrirMiCuenta() {
        panelCuenta.setVisible(true);
        panelCuenta2.setVisible(false);

    }

    @FXML
    private void abrirEditarCuenta() {
        panelCuenta.setVisible(false);
        panelCuenta2.setVisible(true);

    }

    private void establecerDatos() {
        if (correoUsuario != null && !correoUsuario.isEmpty()) {
            Empleados empleado = BDautoluxe.obtenerEmpleadoCorreo(correoUsuario);
            if (empleado != null) {
                lblDNI.setText(empleado.getDNI());
                this.dni = empleado.getDNI();
                lblNombre.setText(empleado.getNombre());
                this.nombre = empleado.getNombre();
                lblApellidos.setText(empleado.getApellidos());
                this.apellidos = empleado.getApellidos();
                lblTelefono.setText(empleado.getTelefono());
                this.telefono = empleado.getTelefono();
                lblROL.setText(empleado.getRol());
                this.rol = empleado.getRol();
                lblEmail.setText(empleado.getCorreo());
                this.correo = empleado.getCorreo();
                this.password = empleado.getContrasena();

                tvNombre.setText(empleado.getNombre());
                tvApellidos.setText(empleado.getApellidos());
                tvDNI.setText(empleado.getDNI());
                tvEmail.setText(empleado.getCorreo());
                tvTelefono.setText(empleado.getTelefono());
            } else {
                // Manejar caso donde no se encuentra el empleado
                System.out.println("Empleado no encontrado.");
            }
        }
    }

    @FXML
    private void actualizarEmpleado() throws SQLException, ClassNotFoundException {
        if (tvNombre.getText().isEmpty() || tvApellidos.getText().isEmpty() || tvEmail.getText().isEmpty() ||tvTelefono.getText().isEmpty() || tvEmail.getText().isEmpty() ) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese todos los campos.");
        } else {
            if (tvNombre.getText().length() > 25) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Nombre.");
            } else if (tvApellidos.getText().length() > 50) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Apellidos.");
            } else if (tvTelefono.getText().length() > 12) {
                mostrarAlerta(Alert.AlertType.WARNING, "Exceso de caracteres en Teléfono.");
            } else if (!tvEmail.getText().contains("@autoluxe.com") || tvEmail.getText().length() > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Formato no válido", "El correo no tiene las credenciales de AutoLuxe [@autoluxe.com] o ha excedido de los 100 caracteres disponibles.");
            } else {
                Empleados empleado = new Empleados(this.dni, tvNombre.getText(), tvApellidos.getText(), tvTelefono.getText(), this.rol, tvEmail.getText(), password);
                BDautoluxe.actualizarEmpleadoBD2(empleado);

            }
        }
    }

    // Método para mostrar alertas
    private static void mostrarAlerta(Alert.AlertType tipo, String encabezado) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Diálogo de Alerta");
        alert.setHeaderText(encabezado);
        alert.showAndWait();
    }

    private static void mostrarAlerta(Alert.AlertType tipo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Diálogo de Alerta");
        alert.setHeaderText(encabezado);
        alert.showAndWait();
    }


    @FXML
    private void cerrarVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_InicioSesion.fxml"));
            Parent root = loader.load();
//          Stage nuevaVentana = new Stage();
//          nuevaVentana.setTitle("AutoLuxe"); // Puedes establecer el título
//          nuevaVentana.setScene(new Scene(root,1920,1000));
//          Stage ventanaActual = (Stage) btnCerrarSesion.getScene().getWindow();
//          ventanaActual.close();
//          nuevaVentana.show();
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

    @FXML
    private void abrirPopupCambiarContrasena() {
        // Crear un nuevo Stage para el popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Cambiar Contraseña");

        // Crear los componentes del formulario
        Label lblAntiguaContrasena = new Label("Introduce la contraseña antigua:");
        PasswordField pfAntiguaContrasena = new PasswordField();

        Label lblNuevaContrasena = new Label("Introduce la nueva contraseña:");
        PasswordField pfNuevaContrasena = new PasswordField();

        Label lblConfirmarContrasena = new Label("Confirma la nueva contraseña:");
        PasswordField pfConfirmarContrasena = new PasswordField();

        Button btnCambiarContrasena = new Button("Cambiar contraseña");

        // Añadir la acción al botón
        btnCambiarContrasena.setOnAction(e -> {
            String antiguaContrasena = pfAntiguaContrasena.getText();
            String nuevaContrasena = pfNuevaContrasena.getText();
            String confirmarContrasena = pfConfirmarContrasena.getText();

            if (nuevaContrasena.length() >= 8) {
                if (nuevaContrasena.equals(confirmarContrasena)) {
                    Empleados empleado = BDautoluxe.obtenerEmpleadoCorreo(correoUsuario);
                    if (empleado != null && empleado.getContrasena().equals(antiguaContrasena)) {
                        empleado.setContrasena(nuevaContrasena);
                        BDautoluxe.actualizarContrasena(empleado);
                        popupStage.close();
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "La contraseña antigua no es correcta.");
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Las nuevas contraseñas no coinciden.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "La nueva contraseña debe tener al menos 8 caracteres.");
            }
        });

        // Crear el layout y añadir los componentes
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.getChildren().addAll(lblAntiguaContrasena, pfAntiguaContrasena, lblNuevaContrasena, pfNuevaContrasena, lblConfirmarContrasena, pfConfirmarContrasena, btnCambiarContrasena);

        // Crear la escena y mostrar el popup
        Scene scene = new Scene(layout, 400, 300);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }



    public void setCorreoUsuario(String correo) {
        this.correoUsuario = correo;
        this.lbCorreo.setText(correo);
        establecerDatos();
    }
}
