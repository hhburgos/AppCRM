package com.example.akirucrm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class activity_nuevopedido extends AppCompatActivity {

    Connection conex;
    public static ArrayList<Articulo> pedido = new ArrayList<Articulo>();
    private Button btnmas, btnmenos, btnRedo, btnGrabar;
    private ImageButton btnDel;
    private TabLayout tabs;
    public static activity_nuevopedido.AdaptadorArticulos adaptador;
    public static TextView tvTotal;
    static AlertDialog.Builder builder;
    static LinearLayout layoutNewPedido, layoutTotalPedido;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevopedido);
        layoutNewPedido = findViewById(R.id.layoutNuevoPedido);
        layoutTotalPedido = findViewById(R.id.layoutTotalPedido);

        try {
            Connection conex = Conexion.getConexion();
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //Empleado emp = getIntent().getParcelableExtra("empleado");
        BarraTitulo d = (BarraTitulo) findViewById(R.id.vBarraTitulo);

        /*Spinner spMesa = (Spinner) findViewById(R.id.spMesa);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numeromesas, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        spMesa.setAdapter(adapter);*/

        adaptador = new AdaptadorArticulos(this);
        ListView lv1 = (ListView) findViewById(R.id.listaPedido);
        lv1.setAdapter(adaptador);

        tvTotal = findViewById(R.id.tvTotal);
        tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");

        btnRedo = findViewById(R.id.btnRedo);

        btnRedo.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if(pedido.isEmpty()){
                    Toast.makeText(btnmas.getContext(), "No hay productos en el pedido...", Toast.LENGTH_LONG).show();
                }else{
                    pedido.remove(pedido.size()-1);
                    adaptador.notifyDataSetChanged();
                    tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                }
            }
        });

        btnGrabar = findViewById(R.id.btnGrabar);
        btnGrabar.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if(pedido.isEmpty()){
                    Toast.makeText(btnmas.getContext(), "No hay productos en el pedido...", Toast.LENGTH_LONG).show();
                }else{
                    builder = new AlertDialog.Builder(btnmenos.getContext());
                    builder.setMessage("¿Grabar pedido?");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                if(Conexion.nuevoPedidoVenta(pedido)){
                                    Toast.makeText(btnGrabar.getContext(), "Pedido grabado!", Toast.LENGTH_LONG).show();
                                    pedido.clear();
                                    adaptador.notifyDataSetChanged();
                                    tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                                    try {
                                        Conexion.actualizarStock();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    //finish();
                                }else{

                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        try {
            Conexion.actualizarStock();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static String calcularTotalPedido(){
        Double totalPedido = 0.0;
        for (Articulo a: pedido) {
            totalPedido = totalPedido + (a.getPrecio() * a.getCantidad());
        }
        return String.format("%.2f", totalPedido);
    }

    class AdaptadorArticulos extends ArrayAdapter<Articulo> {
        AppCompatActivity appCompatActivity;

        AdaptadorArticulos(AppCompatActivity context) {
            super(context, R.layout.layout_lista_articulos, pedido);
            appCompatActivity = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.layout_lista_articulos_pedidos, null);

            ImageView imgArticulo = (ImageView)item.findViewById(R.id.imgArticulo);
            String src = "com.example.akirucrm:drawable/" + pedido.get(position).getCodigo().toLowerCase();
            int id = getResources().getIdentifier(src, null, null);
            imgArticulo.setImageResource(id);

            TextView tvNombre = (TextView)item.findViewById(R.id.tvNombre);
            tvNombre.setText(pedido.get(position).getNombre());

            TextView tvPrecio = (TextView)item.findViewById(R.id.tvPrecio);
            tvPrecio.setText(pedido.get(position).getPrecio() + "€");

            final TextView tvCantidad = (TextView)item.findViewById(R.id.tvCantidad);
            tvCantidad.setText(String.valueOf(pedido.get(position).getCantidad()));

            btnDel = (ImageButton)item.findViewById(R.id.btnDelete);
            btnmas = (Button)item.findViewById(R.id.btnMas);
            btnmenos = (Button)item.findViewById(R.id.btnMenos);

            btnmas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pedido.get(position).getCantidad() < pedido.get(position).getStock()){
                        pedido.get(position).sumaCantidad();
                        tvCantidad.setText(String.valueOf(pedido.get(position).getCantidad()));
                    }else{
                        Toast.makeText(btnmas.getContext(), "No se pueden añadir más unidades", Toast.LENGTH_LONG).show();
                    }
                    tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                }
            });

            btnmenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pedido.get(position).getCantidad()>1) {
                        pedido.get(position).restaCantidad();
                        tvCantidad.setText(String.valueOf(pedido.get(position).getCantidad()));
                        tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                    }else{
                        builder = new AlertDialog.Builder(btnmenos.getContext());
                        builder.setMessage("¿Borrar " + pedido.get(position).getNombre() + "?");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                pedido.remove(pedido.get(position));
                                tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                                adaptador.notifyDataSetChanged();

                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pedido.get(position).setCantidad(1);
                                tvCantidad.setText(String.valueOf(pedido.get(position).getCantidad()));
                                tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(btnDel.getContext());
                    builder.setMessage("¿Borrar " + pedido.get(position).getNombre() + "?");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            pedido.remove(pedido.get(position));
                            tvTotal.setText(String.valueOf(calcularTotalPedido()) + "€");
                            adaptador.notifyDataSetChanged();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            return(item);
        }
    }


}