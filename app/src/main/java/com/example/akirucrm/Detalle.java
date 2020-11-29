package com.example.akirucrm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.SQLException;
import java.util.ArrayList;
import androidx.transition.TransitionManager;
import androidx.transition.Transition;
import androidx.transition.Fade;

public class Detalle extends AppCompatActivity {
    //public static final String RESULT_KEY = "resultado";
    public static final String FAMILIA = "familia";
    //AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, MainActivity.dbName, null, 1);
    ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();
    private Button btnmas, btnmenos, btnAceptar, btnCancelar;
    BottomNavigationView layoutAceptar;
    private Context context = this;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        layoutAceptar = findViewById(R.id.layoutAceptar);
        TextView tvTitulo = findViewById(R.id.tvFamilia);

        ListView lista = findViewById(R.id.list1);
        final ArrayList<Articulo> articulos = new ArrayList<>();

        Articulo aux;

        Bundle bundle = getIntent().getExtras();
        String fami = bundle.getString("familia");
        tvTitulo.setText(fami);

        try {
            listaArticulos = Conexion.getArticulos(fami, false);
            if(listaArticulos.isEmpty()){
                Toast.makeText(this, "No hay articulos en stock!", Toast.LENGTH_LONG).show();
                this.finish();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final AdaptadorArticulos adaptador = new AdaptadorArticulos(this);

        lista.setAdapter(adaptador);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnAceptar = findViewById(R.id.btnAnadir);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Articulo a: listaArticulos) {
                    if(a.getCantidad()!=0) {
                        activity_nuevopedido.pedido.add(a);
                    }
                }
                ListView lv1 = (ListView)findViewById(R.id.listaPedido);
                activity_nuevopedido.adaptador.notifyDataSetChanged();
                activity_nuevopedido.tvTotal.setText(String.valueOf(activity_nuevopedido.calcularTotalPedido()) + "€");
                finish();
                ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
            }
        });
    }

    public void exit(View view){
        finish();
    }



    class AdaptadorArticulos extends ArrayAdapter<Articulo> {

        AppCompatActivity appCompatActivity;

        AdaptadorArticulos(AppCompatActivity context) {
            super(context, R.layout.layout_lista_articulos, listaArticulos);
            appCompatActivity = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.layout_lista_articulos, null);

            TextView tvNombre = (TextView)item.findViewById(R.id.tvNombre);
            tvNombre.setText(listaArticulos.get(position).getNombre());

            TextView tvPrecio = (TextView)item.findViewById(R.id.tvPrecio);
            tvPrecio.setText(listaArticulos.get(position).getPrecio() + "€/Ud.");

            TextView tvStock = (TextView)item.findViewById(R.id.tvStock);
            tvStock.setText(listaArticulos.get(position).getStock() + "uds.");

            final TextView tvCantidad = (TextView)item.findViewById(R.id.tvCantidad);
            tvCantidad.setText(String.valueOf(listaArticulos.get(position).getCantidad()));

            btnmas = (Button)item.findViewById(R.id.btnMas);
            btnmenos = (Button)item.findViewById(R.id.btnMenos);

            btnmas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listaArticulos.get(position).getCantidad() < listaArticulos.get(position).getStock()){
                        listaArticulos.get(position).sumaCantidad();
                        tvCantidad.setText(String.valueOf(listaArticulos.get(position).getCantidad()));
                        TransitionManager.beginDelayedTransition(layoutAceptar);
                        btnAceptar.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(btnmas.getContext(), "No se pueden añadir más unidades", Toast.LENGTH_LONG).show();
                    }

                }
            });

            btnmenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listaArticulos.get(position).getCantidad()>0){
                        listaArticulos.get(position).restaCantidad();
                        tvCantidad.setText(String.valueOf(listaArticulos.get(position).getCantidad()));
                    }
                }
            });

            return(item);
        }
    }



}