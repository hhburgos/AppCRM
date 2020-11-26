package com.example.akirucrm;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;

public class Stock extends AppCompatActivity implements OnClickListener {
    Connection conex = Conexion.getConexion();
    private Button btnBuscarProducto, btnListarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Empleado emp = getIntent().getParcelableExtra("empleado");
        BarraTitulo d = (BarraTitulo) findViewById(R.id.view2);

        inicializar();

        //Toast.makeText(getApplicationContext(), emp.getNombre(), Toast.LENGTH_LONG).show();

        // COGER MEDIA PEDIDO + ULTIMO PEDIDO
        try {
            String nombreAp = Empleado.nombre + " " + Empleado.apellido;
            String compName = Empleado.nEmpresa;
            cambiar(nombreAp, compName, 0, 0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al cargar los datos!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //d.setEmpleado(emp.getNombre());
        //Toast.makeText(getApplicationContext(), d.getEmpleado(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConsultarStock:
                break;
        }
    }

    public void cambiar(String emp, String comp, double med, double ult) {
        BarraTitulo d = (BarraTitulo) findViewById(R.id.view2);
        d.setEmpleado(emp);
        d.setCompany(comp);
        d.setMedia(med);
        d.setUltimo(ult);
    }

    public void inicializar() {
        btnBuscarProducto = findViewById(R.id.btnBuscarProducto);
        btnBuscarProducto.setOnClickListener(this);
        btnListarProducto = findViewById(R.id.btnListarProducto);
        btnListarProducto.setOnClickListener(this);
    }
}