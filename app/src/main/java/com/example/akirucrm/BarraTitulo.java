package com.example.akirucrm;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BarraTitulo extends ConstraintLayout {
    private TextView company, empleado, tvMedia, tvUltimo;

    public BarraTitulo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_barratitulo, this, true);

        company = findViewById(R.id.tvNombreEmpresa);
        empleado = findViewById(R.id.tvNombreEmpleado);
        tvMedia = findViewById(R.id.tvMediaPedido);
        tvUltimo = findViewById(R.id.tvUltimoPedido);

        /*TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BarraTitulo);
        company.setText(attributes.getString(R.styleable.BarraTitulo_empresa));
        empleado.setText(attributes.getString(R.styleable.BarraTitulo_empleado));
        attributes.recycle();*/

    }

    public void onCreate(Bundle savedInstanceState) {
        //company = findViewById(R.id.tvNombreEmpresa);
        //empleado = findViewById(R.id.tvNombreEmpleado);
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

    public void setMedia(double med){
        String mediapedido = "" + med + "€";
        tvMedia.setText(mediapedido);
    }

    public void setUltimo(double ult){
        String ultimopedido = "" + ult + "€";
        tvUltimo.setText(ultimopedido);
    }

}
