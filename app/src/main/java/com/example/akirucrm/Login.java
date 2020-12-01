package com.example.akirucrm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {
    EditText etLogin;
    static Empleado emp;
    static Intent i;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        i =new Intent(this,activity_nuevopedido.class);

        etLogin = findViewById(R.id.editTextNumberPassword);

        etLogin.setOnKeyListener(new View.OnKeyListener() {
            /**
             * Called when a hardware key is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             * <p>Key presses in software keyboards will generally NOT trigger this method,
             * although some may elect to do so in some situations. Do not assume a
             * software input method has to be key-based; even if it is, it may use key presses
             * in a different way than you expect, so there is no way to reliably catch soft
             * input key presses.
             *
             * @param v       The view the key has been dispatched to.
             * @param keyCode The code for the physical key that was pressed
             * @param event   The KeyEvent object containing full information about
             *                the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if(validar(etLogin)){
                        try {
                            if(loginOK()){
                                i.putExtra("empleado", emp);
                                startActivity(i);
                                ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom);
                            }else{
                                Toast.makeText(getApplicationContext(), "Usuario no válido", Toast.LENGTH_LONG).show();
                            }
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "¡Error de conexión!", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        //agregarUsuario();
        //conexionBD();
        //conecta();
    }

    private boolean loginOK() throws SQLException {
        Connection conex = conexionBD();
        String nombre, apellido;
        int idEmp, idCat;

        Statement stmt = conex.createStatement();
        ResultSet rs;

        //Toast.makeText(getApplicationContext(), "SELECT * FROM HolaMundo.dbo.Empleados WHERE idEmpleado = " + etLogin.getText(), Toast.LENGTH_LONG).show();
        rs = stmt.executeQuery("SELECT * FROM HolaMundo.dbo.Empleados WHERE idEmpleado = " + etLogin.getText());

        if (rs.next()) {
            idEmp = rs.getInt("idEmpleado");
            idCat = rs.getInt("idCat");
            nombre = rs.getString("Nombre");
            apellido = rs.getString("Apellido");

            emp = new Empleado(idEmp, idCat, nombre, apellido);
        } else {
            return false;
        }
        conex.close();
        return true;
    }

    private boolean validar(EditText etLogin) {
        if(etLogin.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Introduce tu clave de acceso", Toast.LENGTH_LONG).show();
            return false;
        }else{

            return true;
        }
    }

    public Connection conexionBD() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy((policy));
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //REMOTO kevin
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.143; Integrated Security=False;", "PEPE", "1234");

            //LOCAL
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.179; Integrated Security=False;", "AccesoDatos", "Aa1234");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.13; Integrated Security=False;", "AccesoDatos", "Aa1234");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.18; Integrated Security=False;", "AccesoDatos", "Aa1234");


        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return conexion;
    }

    /*public Connection conecta(){
        Connection cnn=null;
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.43.108:1433;databaseName=CINE;user=PEPE;password=1234;");
            Toast.makeText(getApplicationContext(), "holaaaaaa", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return cnn;
    }*/

    public void agregarUsuario() {
        Connection conexion = null;

        try {
            PreparedStatement pst = conexionBD().prepareStatement("insert into HolaMundo.dbo.Empleados (Nombre) values (?)");
            //pst.setInt(1, 45);
            pst.setString(1,"Pruebafirewall");
            pst.executeUpdate();

            Toast.makeText(this, "Se ha insertado el registro",Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e) {
            Toast.makeText(this, "ERROR en el registro", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}