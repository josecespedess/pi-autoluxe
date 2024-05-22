package ClasesObjetos;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.List;

public class BDautoluxe
{
    // -----------------------------------------------------------------------------------------------------------------
    // CONEXIÓN Y DESCONEXIÓN CON LA BASE DE DATOS
    // -----------------------------------------------------------------------------------------------------------------
    private String url="jdbc:mysql://monorail.proxy.rlwy.net:23659/railway";
    private String user="root";
    private String password="VqtxFIxDedWebvmMbtzzWNwtdWlSGmAF";
    public static Connection connection=null;

    //Método para conectar con la base de datos
    public void conectar() throws SQLException, ClassNotFoundException
    {
        if (connection == null || connection.isClosed())
        {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("BD conectada");
        }
    }
    //Método para desconectar de la base de datos
    public void desconectar() throws SQLException
    {
        if (connection != null && !connection.isClosed())
        {
            connection.close();
            System.out.println("BD desconectada");
        }
    }
    //Método para obtener la conexión
    public Connection getConnection()
    {
        return connection;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // EMPLEADOS
    // -----------------------------------------------------------------------------------------------------------------
    //Método añadir empleados a la base de datos
    public static void altaEmpleadoBD(@NotNull Empleados empleado)
    {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("INSERT INTO empleados (DNI, nombre, apellidos, telefono, rol, correo, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)");

            st.setString(1, empleado.getDNI());
            st.setString(2, empleado.getNombre());
            st.setString(3, empleado.getApellidos());
            st.setString(4, empleado.getTelefono());
            st.setString(5, empleado.getRol());
            st.setString(6, empleado.getCorreo());
            st.setString(7, empleado.getContrasena());

            st.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado añadido.", "");
        }
        catch (SQLException e)
        {
            mostrarAlerta(Alert.AlertType.WARNING, "DNI existente", "");
        }
    }

    // Método para comprobar si existe el dni
    public static boolean dniExisteEmpleado(String dni) {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean existe = false;
        try {
            st = connection.prepareStatement("SELECT COUNT(*) FROM empleados WHERE DNI = ?");
            st.setString(1, dni);
            rs = st.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return existe;
    }

    //Método para comprobar el correo
    public static boolean correoExisteEmpleado(String correo) {
        boolean existe = false;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) FROM empleados WHERE correo=?";
            st = connection.prepareStatement(query);
            st.setString(1, correo);
            rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                existe = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return existe;
    }

    // Método para actualizar un empleado
    public static void actualizarEmpleadoBD(@NotNull Empleados empleado) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("UPDATE empleados SET nombre = ?, apellidos = ?, telefono = ?, rol = ?, correo = ?, contraseña = ? WHERE DNI = ?");

            st.setString(1, empleado.getNombre());
            st.setString(2, empleado.getApellidos());
            st.setString(3, empleado.getTelefono());
            st.setString(4, empleado.getRol());
            st.setString(5, empleado.getCorreo());
            st.setString(6, empleado.getContrasena());
            st.setString(7, empleado.getDNI());

            st.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado actualizado.", "");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error al actualizar el empleado.", "");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Método mostrar Empleados
    public static List<Empleados> listadoEmpleadosBD()
    {
        List<Empleados> listaEmpleados = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM empleados");

            rs=st.executeQuery();
            while (rs.next()) {
                String DNI = rs.getString("DNI");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String rol = rs.getString("rol");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contraseña");
                Empleados empleado = new Empleados(DNI, nombre, apellidos,telefono,rol,correo,contrasena);
                listaEmpleados.add(empleado);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return listaEmpleados;
    }

    //Método mostrar Empleados segun la opcion
    public static List<Empleados> listadoEmpleadosBD(String opcion, String busqueda)
    {
        List<Empleados> listaEmpleados = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        if(opcion=="Nombre")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM empleados WHERE nombre='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String rol = rs.getString("rol");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String contrasena = rs.getString("contraseña");
                    Empleados empleado = new Empleados(DNI, nombre, apellidos,telefono,rol,correo,contrasena);
                    listaEmpleados.add(empleado);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="Apellidos")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM empleados WHERE apellidos='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String rol = rs.getString("rol");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String contrasena = rs.getString("contraseña");
                    Empleados empleado = new Empleados(DNI, nombre, apellidos,telefono,rol,correo,contrasena);
                    listaEmpleados.add(empleado);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="Rol")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM empleados WHERE rol='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String rol = rs.getString("rol");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String contrasena = rs.getString("contraseña");
                    Empleados empleado = new Empleados(DNI, nombre, apellidos,telefono,rol,correo,contrasena);
                    listaEmpleados.add(empleado);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="DNI")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM empleados WHERE DNI='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String rol = rs.getString("rol");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String contrasena = rs.getString("contraseña");
                    Empleados empleado = new Empleados(DNI, nombre, apellidos,telefono,rol,correo,contrasena);
                    listaEmpleados.add(empleado);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return listaEmpleados;
    }

    //Método para obtener un Empleado[DNI]
    public static Empleados obtenerEmpleadoDNI(String busqueda)
    {
        Empleados e = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM empleados WHERE DNI='"+busqueda+"'");

            rs=st.executeQuery();
            while (rs.next()) {
                String DNI = rs.getString("DNI");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String rol = rs.getString("rol");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contraseña");
                e = new Empleados(DNI, nombre, apellidos,telefono,rol,correo,contrasena);
                
            }
        }
        catch (SQLException i)
        {
            i.printStackTrace();
        }
        return e;
    }

    //Método para borrar un Empleado[DNI]
    public static void borrarEmpleadoBD(String busqueda) {
        PreparedStatement st = null;
        try {
            String query = "DELETE FROM empleados WHERE DNI=?";
            st = connection.prepareStatement(query);
            st.setString(1, busqueda);
            st.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado eliminado", "El empleado ha sido eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar empleado", "Ha ocurrido un error al intentar eliminar el empleado.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void mostrarAlerta(Alert.AlertType tipo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Diálogo de Alerta");
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // CLIENTES
    // -----------------------------------------------------------------------------------------------------------------
    // Método para insertar un cliente en la base de datos
    public static void insertarCliente(Clientes cliente) {
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO clientes (DNI, nombre, apellidos, telefono, correo) VALUES (?, ?, ?, ?, ?)";
            st = connection.prepareStatement(query);
            st.setString(1, cliente.getDNI());
            st.setString(2, cliente.getNombre());
            st.setString(3, cliente.getApellidos());
            st.setString(4, cliente.getTelefono());
            st.setString(5, cliente.getCorreo());
            st.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente insertado correctamente", "El cliente se ha insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al insertar cliente", "Ha ocurrido un error al intentar insertar el cliente.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para editar un cliente en la base de datos
    public static void actualizarClienteBD(Clientes cliente) {
        PreparedStatement st = null;
        try {
            String query = "UPDATE clientes SET nombre=?, apellidos=?, telefono=?, correo=? WHERE DNI=?";
            st = connection.prepareStatement(query);
            st.setString(1, cliente.getNombre());
            st.setString(2, cliente.getApellidos());
            st.setString(3, cliente.getTelefono());
            st.setString(4, cliente.getCorreo());
            st.setString(5, cliente.getDNI());
            int filasActualizadas = st.executeUpdate();
            if (filasActualizadas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente actualizado correctamente", "Los datos del cliente se han actualizado correctamente.");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Error al actualizar cliente", "No se pudo encontrar el cliente para actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar cliente", "Ha ocurrido un error al intentar actualizar el cliente.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para eliminar un cliente de la base de datos
    public static void borrarClienteBD(String dni) {
        PreparedStatement st = null;
        try {
            String query = "DELETE FROM vehiculos WHERE DNI_cliente=?";
            st = connection.prepareStatement(query);
            st.setString(1, dni);
            st.executeUpdate();

            query = "DELETE FROM clientes WHERE DNI=?";
            st = connection.prepareStatement(query);
            st.setString(1, dni);
            st.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente eliminado", "El cliente ha sido eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar cliente", "Ha ocurrido un error al intentar eliminar el cliente.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Método mostrar Clientes
    public static List<Clientes> listadoClientesBD()
    {
        List<Clientes> listaClientes = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM clientes");

            rs=st.executeQuery();
            while (rs.next()) {
                String DNI = rs.getString("DNI");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                Clientes cliente = new Clientes(DNI, nombre, apellidos, telefono, correo);
                listaClientes.add(cliente);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return listaClientes;
    }
    //Método mostrar Empleados segun la opcion
    public static List<Clientes> listadoClientesBD(String opcion, String busqueda)
    {
        List<Clientes> listaClientes = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        if(opcion=="Nombre")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM clientes WHERE nombre='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    Clientes cliente = new Clientes(DNI, nombre, apellidos,telefono,correo);
                    listaClientes.add(cliente);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="Apellidos")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM clientes WHERE apellidos='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    Clientes cliente = new Clientes(DNI, nombre, apellidos,telefono,correo);
                    listaClientes.add(cliente);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="DNI")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM clientes WHERE DNI='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String DNI = rs.getString("DNI");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    Clientes cliente = new Clientes(DNI, nombre, apellidos,telefono,correo);
                    listaClientes.add(cliente);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return listaClientes;
    }
    // Método para verificar si un DNI ya existe en la base de datos
    public static boolean dniExisteCliente(String dni) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM clientes WHERE DNI = ?";
            st = connection.prepareStatement(query);
            st.setString(1, dni);
            rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    // Método para verificar si un correo ya existe en la base de datos
    public static boolean correoExisteCliente(String correo) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM clientes WHERE correo = ?";
            st = connection.prepareStatement(query);
            st.setString(1, correo);
            rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    //Método para obtener un Empleado[DNI]
    public static Clientes obtenerClienteDNI(String busqueda)
    {
        Clientes c = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM clientes WHERE DNI='"+busqueda+"'");

            rs=st.executeQuery();
            while (rs.next()) {
                String DNI = rs.getString("DNI");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                c = new Clientes(DNI, nombre, apellidos,telefono,correo);
            }
        }
        catch (SQLException i)
        {
            i.printStackTrace();
        }
        return c;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // VEHÍCULOS
    // -----------------------------------------------------------------------------------------------------------------
    // Método para insertar un vehiculo en la base de datos
    public static void insertarVehiculo(Vehiculos vehiculo) {
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO vehiculos (matricula, numBastidor, marca, modelo, combustible,color,DNI_cliente) VALUES (?, ?, ?, ?, ?,?,?)";
            st = connection.prepareStatement(query);
            st.setString(1, vehiculo.getMatricula());
            st.setString(2, vehiculo.getNumBastidor());
            st.setString(3, vehiculo.getMarca());
            st.setString(4,vehiculo.getModelo());
            st.setString(5, vehiculo.getCombustible());
            st.setString(6, vehiculo.getColor());
            st.setString(7, vehiculo.getDNI_cliente());
            st.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Vehículo insertado correctamente", "El vehículo se ha insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al insertar vehículo", "Ha ocurrido un error al intentar insertar el vehículo.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Método mostrar Todos los Vehiculos
    public static List<Vehiculos> listadoVehiculosBD()
    {
        List<Vehiculos> listaVehiculos = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM vehiculos");

            rs=st.executeQuery();
            while (rs.next()) {
                String matricula = rs.getString("matricula");
                String bastidor = rs.getString("numBastidor");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String DNI_Cliente = rs.getString("DNI_cliente");
                String color = rs.getString("color");
                String combustible = rs.getString("combustible");
                Vehiculos vehiculo = new Vehiculos(matricula,bastidor,marca,modelo,combustible,color,DNI_Cliente);
                listaVehiculos.add(vehiculo);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return listaVehiculos;
    }
    //Método mostrar Empleados segun la opcion
    public static List<Vehiculos> listadoVehiculosBD(String opcion, String busqueda)
    {
        List<Vehiculos> listaVehiculos = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        if(opcion=="Matrícula")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM vehiculos WHERE matricula='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String matricula = rs.getString("matricula");
                    String bastidor = rs.getString("numBastidor");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    String DNI_Cliente = rs.getString("DNI_cliente");
                    String color = rs.getString("color");
                    String combustible = rs.getString("combustible");
                    Vehiculos vehiculo = new Vehiculos(matricula,bastidor,marca,modelo,combustible,color,DNI_Cliente);
                    listaVehiculos.add(vehiculo);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="NumBastidor")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM vehiculos WHERE numBastidor='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String matricula = rs.getString("matricula");
                    String bastidor = rs.getString("numBastidor");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    String DNI_Cliente = rs.getString("DNI_cliente");
                    String color = rs.getString("color");
                    String combustible = rs.getString("combustible");
                    Vehiculos vehiculo = new Vehiculos(matricula,bastidor,marca,modelo,combustible,color,DNI_Cliente);
                    listaVehiculos.add(vehiculo);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="Marca")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM vehiculos WHERE marca='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String matricula = rs.getString("matricula");
                    String bastidor = rs.getString("numBastidor");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    String DNI_Cliente = rs.getString("DNI_cliente");
                    String color = rs.getString("color");
                    String combustible = rs.getString("combustible");
                    Vehiculos vehiculo = new Vehiculos(matricula,bastidor,marca,modelo,combustible,color,DNI_Cliente);
                    listaVehiculos.add(vehiculo);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else if(opcion=="Color")
        {
            try
            {
                st=connection.prepareStatement("SELECT * FROM vehiculos WHERE color='"+busqueda+"'");

                rs=st.executeQuery();
                while (rs.next()) {
                    String matricula = rs.getString("matricula");
                    String bastidor = rs.getString("numBastidor");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    String DNI_Cliente = rs.getString("DNI_cliente");
                    String color = rs.getString("color");
                    String combustible = rs.getString("combustible");
                    Vehiculos vehiculo = new Vehiculos(matricula,bastidor,marca,modelo,combustible,color,DNI_Cliente);
                    listaVehiculos.add(vehiculo);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return listaVehiculos;
    }
    //Método para obtener el DNI del propietario de un Vehiculo
    public static String obtenerDNIVehiculo(String busqueda)
    {
        String dni = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM vehiculos WHERE matricula='"+busqueda+"'");

            rs=st.executeQuery();
            while (rs.next()) {
                String DNI = rs.getString("DNI_cliente");
                dni = DNI;
            }
        }
        catch (SQLException i)
        {
            i.printStackTrace();
        }
        return dni;
    }
    // Método para editar un vehiculo en la base de datos
    public static void actualizarVehiculoBD(Vehiculos vehiculo) {
        PreparedStatement st = null;
        try {
            String query = "UPDATE vehiculos SET matricula=?, numBastidor=?, marca=?, modelo=?,combustible=?,color=?,DNI_cliente=? WHERE matricula=?";
            st = connection.prepareStatement(query);
            st.setString(1, vehiculo.getMatricula());
            st.setString(2, vehiculo.getNumBastidor());
            st.setString(3, vehiculo.getMarca());
            st.setString(4,vehiculo.getModelo());
            st.setString(5, vehiculo.getCombustible());
            st.setString(6, vehiculo.getColor());
            st.setString(7, vehiculo.getDNI_cliente());
            st.setString(8, vehiculo.getMatricula());
            int filasActualizadas = st.executeUpdate();
            if (filasActualizadas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Vehículo actualizado correctamente", "Los datos del vehículo se han actualizado correctamente.");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Error al actualizar vehículo", "No se pudo encontrar el vehículo para actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar vehículo", "Ha ocurrido un error al intentar actualizar el vehículo.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // Método para eliminar un vehiculo de la base de datos
    public static void borrarVehiculoBD(String matricula) {
        PreparedStatement st = null;
        try {
            String query = "DELETE FROM vehiculos WHERE matricula=?";
            st = connection.prepareStatement(query);
            st.setString(1, matricula);
            st.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Vehículo eliminado", "El vehículo ha sido eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar vehículo", "Ha ocurrido un error al intentar eliminar el vehículo.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Método para obtener el Vehiculo mediante la matrícula
    public static Vehiculos obtenerVehiculoMatricula(String busqueda)
    {
        Vehiculos v = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st=connection.prepareStatement("SELECT * FROM vehiculos WHERE matricula='"+busqueda+"'");

            rs=st.executeQuery();
            while (rs.next()) {;
                String bastidor = rs.getString("numBastidor");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String DNI_Cliente = rs.getString("DNI_cliente");
                String color = rs.getString("color");
                String combustible = rs.getString("combustible");
                Vehiculos vehiculo = new Vehiculos(busqueda,bastidor,marca,modelo,combustible,color,DNI_Cliente);
                v=vehiculo;
            }
        }
        catch (SQLException i)
        {
            i.printStackTrace();
        }
        return v;
    }
    // Método para verificar si una Matrícula ya existe en la base de datos
    public static boolean matriculaExisteVehiculo(String matricula) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) AS count FROM vehiculos WHERE matricula = ?";
            st = connection.prepareStatement(query);
            st.setString(1, matricula);
            rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    // -----------------------------------------------------------------------------------------------------------------
}
