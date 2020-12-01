package com.example.akirucrm;

import android.os.Build;
import android.os.StrictMode;
import androidx.annotation.RequiresApi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.threeten.bp.LocalDate;
import java.util.ArrayList;


public class Conexion {
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy((policy));
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //LOCAL

            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.179; Integrated Security=False;", "AccesoDatos", "Aa1234");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.13; Integrated Security=False;", "AccesoDatos", "Aa1234");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.18; Integrated Security=False;", "AccesoDatos", "Aa1234");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public static ResultSet sqlSelect(String select) throws SQLException {
        Connection conex = getConexion();
        Statement stmt = conex.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery(select);
        conex.close();
        return rs;
    }

    public static void actualizarStock() throws SQLException {
        ArrayList<Articulo> arts = new ArrayList<Articulo>();
        arts.clear();
        Connection conex = getConexion();
        Statement stmt = conex.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * from HolaMundo.dbo.Articulo");
        while(rs.next()){
            String cod = rs.getString("codArticulo");
            String nom = rs.getString("Nombre");
            boolean tipo = rs.getBoolean("Tipo");
            Double precio = rs.getDouble("Precio");
            int stock = rs.getInt("Stock");
            String fam = rs.getString("Familia");
            Articulo aux = new Articulo(stock, precio, tipo, nom, cod, fam);
            arts.add(aux);
        }

        for (Articulo a: arts){
            ResultSet res;
            res = stmt.executeQuery("SELECT SUM(Movimiento) from HolaMundo.dbo.Movimiento WHERE codArticulo = '" + a.getCodigo().trim() + "'");
            if(res.next()){
                int stock = res.getInt(1);
                stmt.executeUpdate("update HolaMundo.dbo.Articulo" +
                                            " SET Stock = " + stock +
                                            " WHERE codArticulo = '" + a.getCodigo().trim() + "'");
            }else{
                stmt.executeUpdate("update HolaMundo.dbo.Articulo" +
                        " SET Stock = 0 " +
                        "WHERE codArticulo = '" + a.getCodigo().trim() + "'");
            }
        }
    }

    public static String getEmpresa() throws SQLException {
        Connection conex = getConexion();
        Statement stmt = conex.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * from HolaMundo.dbo.CompanyInfo where idEmpresa = 1");
        rs.next();
        String comp = rs.getString("Nombre");
        return comp;
    }

    public static ArrayList<String> getFamilias(String tipo) throws SQLException {
        ArrayList<String> familias = new ArrayList<String>();
        Connection conex = getConexion();
        Statement stmt = conex.createStatement();
        ResultSet rs;
        String opc;

        if(tipo.equalsIgnoreCase("comida")){
            opc = "false";
        }else{
            opc = "true";
        }

        rs = stmt.executeQuery("SELECT * from HolaMundo.dbo.Familia WHERE Tipo = '" + opc + "'");
        while(rs.next()){
            familias.add(rs.getString("Descripcion"));
        };
        return familias;
    }

    public static ArrayList<Articulo> getArticulos(String familia, boolean mostrarAgotados) throws SQLException {
        ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();
        Connection conex = getConexion();
        Statement stmt = conex.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * from HolaMundo.dbo.Articulo WHERE Familia in (" +
                                        "SELECT idFamilia from HolaMundo.dbo.Familia WHERE Descripcion = '" + familia + "')");

        while(rs.next()){
            String cod = rs.getString("codArticulo");
            String nom = rs.getString("Nombre");
            boolean tipo = rs.getBoolean("Tipo");
            Double precio = rs.getDouble("Precio");
            int stock = rs.getInt("Stock");
            String fam = rs.getString("Familia");
            Articulo aux = new Articulo(stock, precio, tipo, nom, cod, fam);
            if(mostrarAgotados){
                listaArticulos.add(aux);
            }else{
                if(stock>0) {
                     listaArticulos.add(aux);
                }
            }
        }
        return listaArticulos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean nuevoPedidoVenta(ArrayList<Articulo> pedido) throws SQLException {
        LocalDate fecha = LocalDate.now();
        String strfecha = "" + fecha.getYear() + "" + fecha.getMonthValue() + "" + fecha.getDayOfMonth();
        try{
            Connection conex = getConexion();
            Statement stmt = conex.createStatement();
            ResultSet rs;

            Double total = Double.parseDouble(activity_nuevopedido.calcularTotalPedido());
            stmt.executeUpdate("INSERT INTO HolaMundo.dbo.Ventas" +
                                            "(idCliente, Fecha, Precio_total, idEmpleado)" +
                                            "VALUES(0, '" + strfecha + "'," + total + ", 4)");

            rs = stmt.executeQuery("SELECT max(NPedido) FROM HolaMundo.dbo.Ventas");
            int numpedido = 0;
            while(rs.next()) {
                numpedido = rs.getInt(1);
            }

            for (Articulo a: pedido) {
                stmt.executeUpdate("INSERT INTO HolaMundo.dbo.LineaVenta" +
                                            " VALUES(" + numpedido + ",'" + a.getCodigo().trim() +
                                            "'," + a.getCantidad() + "," + (a.getPrecio() * a.getCantidad()) +")");
                String sql = "INSERT INTO HolaMundo.dbo.Movimiento" +
                                            "(codArticulo, Movimiento, idPedido) " +
                                            "VALUES('" + a.getCodigo().trim() + "', -"
                                            + a.getCantidad() + ", " + numpedido + ")";
                stmt.executeUpdate(sql);
            }
            return true;
        }catch(Exception e){
            e.getMessage();
            return false;
        }
    }
}



