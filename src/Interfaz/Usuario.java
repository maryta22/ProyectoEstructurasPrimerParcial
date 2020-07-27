/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import TDAs.ArrayList;

/**
 *
 * @author Deja mi lapto ¬¬...!
 */
public class Usuario {
    private String usuario;
    private String password;
    private String nombre;
    private String edad;
    public static ArrayList<Usuario> usuarios;

    public Usuario(String usuario, String password, String nombre, String edad) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.edad = edad;
        usuarios= new ArrayList<Usuario>();
    }
    
    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        usuarios= new ArrayList<Usuario>();
    }
    
    public Usuario(){
        
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
    
}
