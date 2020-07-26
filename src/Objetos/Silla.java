
package Objetos;

import javafx.scene.shape.Rectangle;

public class Silla {
    private String nombre;
    private int numero;
    Rectangle r;

    public Silla(String nombre, int numero, int radio) {
        this.nombre = nombre;
        this.numero = numero;
        r = new Rectangle(350,300,radio,radio);
    }
  
    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }

    public Rectangle getR() {
        return r;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    
    
}
