package com.example.akirucrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cambiar(View view2){
        BarraTitulo d = (BarraTitulo) findViewById(R.id.view2);
        d.setEmpleado("Michael Scott");
        d.setCompany("DUNDER MIFFLIN Corp.");
        d.setMedia(134.56);
        d.setUltimo(56.79);


    }
}