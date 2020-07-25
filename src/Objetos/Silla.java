
package Objetos;

public class Silla {
    private String nombre;
    private int numero;

    public Silla(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }
  
    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    
    
}
