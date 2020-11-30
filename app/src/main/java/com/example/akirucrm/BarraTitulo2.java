package com.example.akirucrm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.sql.SQLException;
import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class BarraTitulo2 extends ConstraintLayout {
    private TextView company, empleado, tvTitulo;
    private Spinner spMesa, spComensales;
    private TabLayout tabs, tabsFamilia;
    private TabItem tabComida, tabBebida, tabBuscar;
    private static final int REQUEST_CODE = 1;
    private static Intent i;
    private BarraTitulo2 context = this;
    private static AutoCompleteTextView autoText;
    private HorizontalScrollView layoutTabs;

    public BarraTitulo2(@NonNull final Context context, @Nullable AttributeSet attrs) throws SQLException {
        super(context, attrs);
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_barratitulo2, this, true);

        company = findViewById(R.id.tvNombreEmpresa);
        empleado = findViewById(R.id.tvNombreEmpleado);
        tvTitulo = findViewById(R.id.tvTitulo);
        spMesa = findViewById(R.id.spMesa);
        tvTitulo.setText(this.getResources().getString(R.string.nuevopedido));
        tabComida = findViewById(R.id.tabComida);
        tabBuscar = findViewById(R.id.tabBuscar);
        tabs = findViewById(R.id.tabsOpcion);
        tabsFamilia = findViewById(R.id.tabsFamilia);

        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabs.getChildAt(0)).getChildAt(2));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.25f; // e.g. 0.5f
        layout.setLayoutParams(layoutParams);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              /**
               * Called when a tab enters the selected state.
               *
               * @param tab The tab that was selected
               */
              @Override
              public void onTabSelected(TabLayout.Tab tab) {
                  try {
                      if(tab.getText()==null){
                          mostrarBusqueda();
                      }else {
                          changeTabs(tab.getText().toString());
                      }
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }

              /**
               * Called when a tab exits the selected state.
               *
               * @param tab The tab that was unselected
               */
              @Override
              public void onTabUnselected(TabLayout.Tab tab) {

              }

              /**
               * Called when a tab that is already selected is chosen again by the user. Some applications may
               * use this action to return to the top level of a category.
               *
               * @param tab The tab that was reselected.
               */
              @Override
              public void onTabReselected(TabLayout.Tab tab) {

              }
          });

        changeTabs("comida");

        tabsFamilia.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /**
             * Called when a tab enters the selected state.
             *
             * @param tab The tab that was selected
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!tab.getText().equals(">")){
                    i = new Intent(tabsFamilia.getContext(), Detalle.class );
                    i.putExtra("familia", tab.getText().toString());
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                }
            }

            /**
             * Called when a tab exits the selected state.
             *
             * @param tab The tab that was unselected
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            /**
             * Called when a tab that is already selected is chosen again by the user. Some applications may
             * use this action to return to the top level of a category.
             *
             * @param tab The tab that was reselected.
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        String[] numMesas = {"LOCAL 1", "LOCAL 2", "LOCAL 3", "LOCAL 4", "TERRAZA 1", "TERRAZA 2", "TERRAZA 3", "TERRAZA 4"};

        /*Spinner spMesa = (Spinner)findViewById(R.id.spMesa);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.layout_spinner_row, R.id.spMesa, numMesas);
        spMesa.setAdapter(adapter);*/
    }

    private void mostrarBusqueda() {
        autoText = (AutoCompleteTextView)findViewById(R.id.autoTextView);
        autoText.setVisibility(VISIBLE);
        layoutTabs.setVisibility(GONE);

    }


    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setCompany(String comp){
        company.setText(comp);
    }

    public void setEmpleado(String emp){
        empleado.setText(emp);
    }

    public String getEmpleado(){
        return empleado.getText().toString();
    }

    public void changeTabs(String opcion) throws SQLException {
        tabs = findViewById(R.id.tabsFamilia);
        layoutTabs = findViewById(R.id.layoutFamilias);

        autoText = (AutoCompleteTextView)findViewById(R.id.autoTextView);
        autoText.setVisibility(GONE);
        layoutTabs.setVisibility(VISIBLE);

        ArrayList<String> familias = new ArrayList<String>();
        tabs.removeAllTabs();


        if(opcion.equalsIgnoreCase("comida") || opcion.equalsIgnoreCase("bebida")){

            familias = Conexion.getFamilias(opcion);
            tabs.addTab(tabs.newTab().setText(">"));

            for (String f: familias) {
                tabs.addTab(tabs.newTab().setText(f.toString()));
            }
        }else{
        }

    }
}
