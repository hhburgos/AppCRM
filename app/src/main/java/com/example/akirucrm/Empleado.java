package com.example.akirucrm;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.time.LocalDateTime;

public class Empleado {

    public static int idEmp, idCat;
    public static String nombre, apellido, DNI, correo, pass, nEmpresa;
    public static LocalDateTime fNacimiento;
    public static String estado;

    public static void mensaje (String m, Context c) {
        Toast.makeText(c,m,Toast.LENGTH_LONG).show();
    }
}
