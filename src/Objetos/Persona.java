
package Objetos;

public class Persona {
    private String nombre;
    private int numero;

    public Persona(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    
    
    
}
