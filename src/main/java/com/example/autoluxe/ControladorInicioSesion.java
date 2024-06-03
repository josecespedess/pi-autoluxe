package com.example.autoluxe;

import ClasesObjetos.BDautoluxe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControladorInicioSesion implements Initializable {
    BDautoluxe bd = new BDautoluxe();
    @FXML
    private AnchorPane contenedor;
    @FXML
    private TextField edEmail;
    @FXML
    private TextField edContraseña;
    @FXML
    private Button bt1;
    @FXML
    private Label msgError2;
    @FXML
    private Label msgError1;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void accionBt1() throws IOException, SQLException, ClassNotFoundException {
        bd.conectar();
        msgError1.setVisible(false);
        msgError2.setVisible(false);
        if(edEmail.getText().isEmpty()||edContraseña.getText().isEmpty()||edEmail.getText().contains("@autoluxe.com")==false)
        {
            if (edEmail.getText().isEmpty()||edEmail.getText().contains("@")==false)
            {
                msgError1.setVisible(true);
            }
            if(edContraseña.getText().isEmpty())
            {
                msgError2.setVisible(true);
            }
        }
        else
        {
            String correo="";
            String contrasena="";
            try(Connection conexion=bd.getConnection())
            {
                String query="SELECT correo,contraseña FROM empleados WHERE correo='"+edEmail.getText()+"'";
                Statement st=conexion.createStatement();
                ResultSet rs=st.executeQuery(query);
                while (rs.next())
                {
                    correo=rs.getString("correo");
                    contrasena=rs.getString("contraseña");
                }
                if(correo!="")
                {
                    if(contrasena.equals(edContraseña.getText()))
                    {
                        System.out.println("Antes de llamar a abrir aplicacion"+correo);
                        abrirAplicacion(correo);
                    }
                    else
                    {
                        mostrarAlerta(Alert.AlertType.ERROR, "Información Incorrecta", "Contraseña incorrecta, estos datos no son válidos.");
                    }
                }
                else
                {
                    mostrarAlerta(Alert.AlertType.ERROR, "Información Incorrecta", "Correo incorrecto, estos datos no son existentes.");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void abrirAplicacion(String correo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista_inicio.fxml"));
            Parent root = loader.load();

            System.out.println("Metodo abrir aplicacion"+correo);
            // Obtener el controlador de la nueva vista y pasar el correo
            ControladorInicio controlador = loader.getController();
            controlador.setCorreoUsuario(correo);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("AutoLuxe"); // Puedes establecer el título
            nuevaVentana.setScene(new Scene(root,1920,1080));
            nuevaVentana.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/LogoAutoLuxe.png")));
            Stage ventanaActual = (Stage) bt1.getScene().getWindow();
            ventanaActual.close();
            nuevaVentana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirGmail()
    {
        String url = "https://www.google.com/intl/es/gmail/about/";
        try {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirInstagram()
    {
        String url = "https://www.instagram.com/auto_luxe_js/";
        try {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void abrirFacebook()
    {
        String url = "https://www.facebook.com/profile.php?id=61558487721041";
        try {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
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


}