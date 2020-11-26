package com.example.akirucrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends AppCompatActivity implements OnClickListener {
    Connection conex = Conexion.getConexion();
    private Button btnConsultarStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Empleado emp = getIntent().getParcelableExtra("empleado");
        BarraTitulo d = (BarraTitulo)findViewById(R.id.view2);


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

        Toast.makeText(getApplicationContext(), Empleado.nombre.toString(), Toast.LENGTH_SHORT).show();
        //d.setEmpleado(emp.getNombre());
        //Toast.makeText(getApplicationContext(), d.getEmpleado(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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