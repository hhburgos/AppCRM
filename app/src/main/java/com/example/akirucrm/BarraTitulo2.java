package com.example.akirucrm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

public class BarraTitulo2 extends ConstraintLayout {
    private TextView company, empleado, tvTitulo;
    private Spinner spMesa, spComensales;
    private TabLayout tabs, tabsFamilia;
    private TabItem tabComida, tabBebida, tabBuscar;
    private static final int REQUEST_CODE = 1;
    private static Intent i;
    private BarraTitulo2 context = this;
    private static LinearLayout autoText;
    private static AutoCompleteTextView autoBusqueda;
    private HorizontalScrollView layoutTabs;
    ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();

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
        autoText = findViewById(R.id.layoutBusqueda);
        layoutTabs = findViewById(R.id.layoutFamilias);
        autoBusqueda = findViewById(R.id.autoBusqueda);

        this.autoBusqueda.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                setImeVisibility(hasFocus);
                try {
                    cargarAutoBusqueda();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

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
                          mostrarOcultar(autoText, layoutTabs);
                          autoBusqueda.requestFocus();

                      }else {
                          if(autoText.getVisibility() == VISIBLE){
                              mostrarOcultar(layoutTabs, autoText);
                              layoutTabs.requestFocus();
                          }
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


    static ArrayList<String> listaNombresArticulos = new ArrayList<String>();

    private void cargarAutoBusqueda() throws SQLException {
        listaNombresArticulos = Conexion.getFromBD("Nombre", "Articulo");
        listaNombresArticulos.addAll(Conexion.getFromBD("codArticulo", "Articulo"));

        //simple_dropdown_item_1line
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, listaNombresArticulos);
        autoBusqueda.setAdapter(adapter);

        autoBusqueda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(autoBusqueda.getContext(), autoBusqueda.getText(), Toast.LENGTH_LONG).show();
                //onButtonShowPopupWindowClick(autoBusqueda, autoBusqueda.getText().toString());
                i = new Intent(autoBusqueda.getContext(), DetalleArticulo.class );

                i.putExtra("articulo", autoBusqueda.getText().toString());
                autoBusqueda.getContext().startActivity(i);
                ((Activity) getContext()).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
            }
        });
    }

    private void mostrarOcultar(View v1, View v2) {
        //autoText = (LinearLayout)findViewById(R.id.layoutBusqueda);
        if (v1.getVisibility() == GONE && v2.getVisibility() == GONE) {
            slideDown(v1, VISIBLE);
            slideUp(v2, VISIBLE);
        } else {
            if (v1.getVisibility() == GONE && v2.getVisibility() == VISIBLE) {
                slideUp(v2, GONE);
                slideDown(v1, VISIBLE);
            } else {
                if (v1.getVisibility() == VISIBLE && v2.getVisibility() == GONE) {
                    slideUp(v1, GONE);
                    slideDown(v2, VISIBLE);
                }
            }
        }
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

        autoText = (LinearLayout) findViewById(R.id.layoutBusqueda);

        ArrayList<String> familias = new ArrayList<String>();
        tabs.removeAllTabs();


        if(opcion.equalsIgnoreCase("comida") || opcion.equalsIgnoreCase("bebida")){

            familias = Conexion.getFamilias(opcion);
            tabs.addTab(tabs.newTab().setText(">"));

            for (String f: familias) {
                tabs.addTab(tabs.newTab().setText(f.toString()));
            }
        }
    }

    // slide the view from below itself to the current position
    public void slideDown(View view, int visibility){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0 - view.getHeight(),               // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibility);

    }

    // slide the view from its current position to below itself
    public void slideUp(View view, int visibility){
        view.setVisibility(visibility);
        /*TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                tabs.getHeight(),                 // fromYDelta
                view.getHeight() - (view.getHeight()*2)); // toYDelta*/
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,               // fromYDelta
                0 - view.getHeight());                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);

    }

    private Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.showSoftInput(autoBusqueda, 0);
            }
        }
    };

    private void setImeVisibility(final boolean visible) {
        if (visible) {
            post(mShowImeRunnable);
        } else {
            removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }

}
