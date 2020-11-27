package com.example.akirucrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends AppCompatActivity implements OnClickListener {
    Connection conex = Conexion.getConexion();
    private Button btnConsultarStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarraTitulo d = (BarraTitulo)findViewById(R.id.view2);
        try {
            Conexion.recogeInfoEmpresa();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        inicializar();

        // COGER MEDIA PEDIDO + ULTIMO PEDIDO
        try {
            String nombreAp = Empleado.nombre + " " + Empleado.apellido;
            String compName = Empleado.nEmpresa;
            cambiar(nombreAp, compName, 0,0);
        } catch (Exception e) {
            Empleado.mensaje("Error al cargar los datos!",this);
            Empleado.mensaje(e.getMessage(),getApplication());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConsultarStock: abreActivityStock(); break;
        }
    }

    public void abreActivityStock () {
        try {
            Intent aStock = new Intent(this, Stock.class);
            startActivity(aStock);
        }
        catch (Exception e) {
            Empleado.mensaje(e.getMessage(),this);
        }
    }

    public void cambiar(String emp, String comp, double med, double ult){
        BarraTitulo d = (BarraTitulo) findViewById(R.id.view2);
        d.setEmpleado(emp);
        d.setCompany(comp);
        d.setMedia(med);
        d.setUltimo(ult);
    }

    public void inicializar () {
        //btnModificarPedido = findViewById(R.id.btnListarProducto);
        btnConsultarStock = findViewById(R.id.btnConsultarStock);
        btnConsultarStock.setOnClickListener(this);
        //btnHacerPedido = findViewById(R.id.btnBuscarProducto);
    }
}