package com.example.akirucrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //agregarUsuario();
        conecta();
    }

    public Connection conexionBD() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy((policy));
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            /*conexion = DriverManager.getConnection(""
                    + "jdbc:jtds:sqlserver://192.168.1.143/northwind;instance=SQLEXPRESS;"
                    + "user=PEPE;password=1234;");*/
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.43.108;databaseName=HolaMundo;user=sa;password=76076015;");
            Toast.makeText(getApplicationContext(), "holaaaaaa", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return conexion;
    }

    public Connection conecta(){
        Connection cnn=null;
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.43.108;databaseName=HolaMundo;user=PEPE;password=1234;");
            Toast.makeText(getApplicationContext(), "holaaaaaa", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return cnn;
    }

    public void agregarUsuario() {
        try {
            PreparedStatement pst = conexionBD().prepareStatement("insert into Empleados (idEmpleado,Nombre) values (43,'Pepe')");
            //pst.setString(1, "45");
            //pst.setString(2,"Loquendo");
            pst.executeUpdate();

            Toast.makeText(this, "Se ha insertado el registro",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(this, "ERROR en el registro", Toast.LENGTH_SHORT).show();
        }
    }
}