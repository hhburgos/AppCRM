package com.example.akirucrm;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class Empleado implements Parcelable {
    int idEmp, idCat;
    String nombre, apellido, DNI, correo, pass;
    LocalDateTime fNacimiento;
    String estado;

    public Empleado(int idEmp, int idCat, String nombre, String apellido) {
        this.idEmp = idEmp;
        this.idCat = idCat;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    protected Empleado(Parcel in) {
        idEmp = in.readInt();
        idCat = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        DNI = in.readString();
        correo = in.readString();
        pass = in.readString();
        estado = in.readString();
    }

    public static final Creator<Empleado> CREATOR = new Creator<Empleado>() {
        @Override
        public Empleado createFromParcel(Parcel in) {
            return new Empleado(in);
        }

        @Override
        public Empleado[] newArray(int size) {
            return new Empleado[size];
        }
    };

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idEmp);
        dest.writeInt(idCat);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(DNI);
        dest.writeString(correo);
        dest.writeString(pass);
        dest.writeString(estado);
    }
}
