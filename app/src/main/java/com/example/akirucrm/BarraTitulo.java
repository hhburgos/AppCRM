package com.example.akirucrm;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BarraTitulo extends ConstraintLayout {
    private TextView company;
    private TextView empleado;
    private TextView titulo;

    public BarraTitulo(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        this.company = findViewById(R.id.tvNombreEmpresa);
        this.empleado = findViewById(R.id.tvNombreEmpleado);
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_barratitulo, this, true);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @see View(Context, AttributeSet)
     */
    public BarraTitulo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.company = findViewById(R.id.tvNombreEmpresa);
        this.empleado = findViewById(R.id.tvNombreEmpleado);
    }

    public void onCreate(Bundle savedInstanceState) {
        //LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        this.company.setText("HOLA MUNDO");
        this.empleado.setText("Michael Scott");


        //...

    }
}
