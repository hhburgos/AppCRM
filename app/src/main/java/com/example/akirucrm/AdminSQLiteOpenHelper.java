package com.example.akirucrm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "akiru.db";
    private static final int VERSION_DB = 1;
    private static final String TABLA_PEDIDOS = "pedidos";
    private static final String CREA_TABLA_PEDIDOS = "create table pedidos (idPedido integer primary key, idEmpleado integer, idFecha date, entregado boolean, precio integer)";

    public AdminSQLiteOpenHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREA_TABLA_PEDIDOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLA_PEDIDOS);
    }
}
