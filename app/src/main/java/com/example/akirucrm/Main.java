package com.example.akirucrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends AppCompatActivity {
    Connection conex = Conexion.getConexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Empleado emp = getIntent().getParcelableExtra("empleado");
        BarraTitulo d = (BarraTitulo)findViewById(R.id.vBarraTitulo);

        //Toast.makeText(getApplicationContext(), emp.getNombre(), Toast.LENGTH_LONG).show();

        // COGER MEDIA PEDIDO + ULTIMO PEDIDO
        try {
            String nombreAp = emp.getNombre() + " " + emp.getApellido();
            String compName = Conexion.getEmpresa();
            cambiar(nombreAp, compName, 0,0);
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error al cargar los datos!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //d.setEmpleado(emp.getNombre());
        //Toast.makeText(getApplicationContext(), d.getEmpleado(), Toast.LENGTH_LONG).show();
    }

    public void cambiar(String emp, String comp, double med, double ult){
        BarraTitulo d = (BarraTitulo) findViewById(R.id.vBarraTitulo);
        d.setEmpleado(emp);
        d.setCompany(comp);
        d.setMedia(med);
        d.setUltimo(ult);
    }




}