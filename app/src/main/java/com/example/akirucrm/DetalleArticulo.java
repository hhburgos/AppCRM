package com.example.akirucrm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.TransitionManager;

import java.sql.SQLException;


public class DetalleArticulo extends AppCompatActivity {

    private TextView nom;
    private EditText cantidad;
    private Articulo art;
    private ImageView img;

    private Button btnmas, btnmenos, btnVolver, btnAnadir;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_articulo);

        Bundle bundle = getIntent().getExtras();
        try {
            art = Conexion.getArticulo(bundle.getString("articulo"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        img = findViewById(R.id.img_articulo);
        cantidad = findViewById(R.id.popup_etCantidad);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        cantidad.requestFocus();
        cantidad.setText("0");

        //ImageView imgArticulo = (ImageView)findViewById(R.id.imgArticulo);
        String src = "com.example.akirucrm:drawable/" + art.getCodigo().toLowerCase();
        int id = getResources().getIdentifier(src, null, null);
        img.setImageResource(id);

        nom = findViewById(R.id.tvNombre);

        nom.setText(art.getNombre());

        btnmas = (Button)findViewById(R.id.btnMas);
        btnmenos = (Button)findViewById(R.id.btnMenos);

        btnmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidad.getText().length() == 0) {
                    cantidad.setText("1");
                }else{

                    if(Integer.parseInt(cantidad.getText().toString()) < art.getStock()){
                        cantidad.setText(String.valueOf(Integer.parseInt(cantidad.getText().toString()) + 1));
                    }else{
                        Toast.makeText(btnmas.getContext(), "No se pueden añadir más unidades", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidad.getText().length() == 0) {
                    cantidad.setText("0");
                }else{
                    if (Integer.parseInt(cantidad.getText().toString()) > 0) {
                        cantidad.setText(String.valueOf(Integer.parseInt(cantidad.getText().toString()) - 1));
                    }
                }
            }
        });

        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(cantidad.getText().toString()) > art.getStock()){
                    Toast.makeText(btnAnadir.getContext(), "El límite de existencias para este artículo es de " + art.getStock(), Toast.LENGTH_LONG).show();
                }else{
                    if(cantidad.getText().toString().isEmpty()){
                        Toast.makeText(btnAnadir.getContext(), "¡Introduce una cantidad!", Toast.LENGTH_LONG).show();
                    }else{
                        art.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                        activity_nuevopedido.pedido.add(art);
                    }
                }
            }
        });

    }
}
