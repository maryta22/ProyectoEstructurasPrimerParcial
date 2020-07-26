package Objetos;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Persona {

    private String nombre;
    private int numero;
    Circle circle;

    public Persona(String nombre, int numero,int radio) {
        this.nombre = nombre;
        this.numero = numero;
        
        circle = new Circle(350,300,radio,generarColor());
        circle.setAccessibleText(nombre);
    }
    
    public  Color generarColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        Color customColor = Color.rgb(red, green, blue);
        return customColor;
    }
    
    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public Circle getCircle() {
        return circle;
    } 

    @Override
    public String toString() {
        return nombre;
    }
 

}
