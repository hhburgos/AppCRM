package com.example.akirucrm;

import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy((policy));
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            //REMOTO kevin
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.143; Integrated Security=False;", "PACO", "1234");

            //LOCAL UNAI
            //SERVER=WINSERVER\SQLEXPRESS;  Integrated Security=False; DATABASE = CINE; user=AccesoDatos; password='Aa1234'
            //Toast.makeText(getApplicationContext(), "holaaaaaa", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            e.getMessage();
        }
        return conexion;
    }

    public static ResultSet sqlSelect(String select) throws SQLException {
        Connection conex = getConexion();
        Statement stmt = conex.createStatement();
        ResultSet rs;

        //Toast.makeText(getApplicationContext(), "SELECT * FROM HolaMundo.dbo.Empleados WHERE idEmpleado = " + etLogin.getText(), Toast.LENGTH_LONG).show();
        rs = stmt.executeQuery(select);
        conex.close();
        return rs;
    }

    public static void recogeInfoEmpresa() throws SQLException {
            Connection conex = getConexion();
            Statement stmt = conex.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT * from HolaMundo.dbo.CompanyInfo where idEmpresa = 1");
            rs.next();
            Empleado.nEmpresa = rs.getNString("Nombre");
}
}
