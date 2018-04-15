package pract.es.deusto.wake_up;

import java.io.Serializable;

/**
 * Created by Jon_Acha on 14/04/2018.
 *
 * CREATE TABLE IF NOT EXISTS usuario (cod_usuario INTEGER primary key  AUTOINCREMENT NOT NULL,
 * nombre TEXT not null UNIQUE, descripcion TEXT not null ,altura INTEGER,
 * peso INTEGER,telefono INTEGER , residencia TEXT,  cod_enf INTEGER not null);
 */

public class usuario  implements Serializable{
    private String nombre;
    private String descripcion;
    private int altura;
    private int peso;
    private String residencia;
    private int telefono;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public usuario(String nombre, String descripcion, int altura, int peso, String residencia, int telefono) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.altura = altura;
        this.peso = peso;
        this.residencia = residencia;
        this.telefono = telefono;
    }
}
