
package Movimientos;

import Objetos.Persona;
import TDAs.LinkedList;


public class MovimientoPersonas {
    private LinkedList<Persona> personas;

    public MovimientoPersonas() {
        personas = new LinkedList<>();
    }
    
    public void rellenarLista(int numero){
        for(int i = 0 ; i < numero ; i++){
            personas.add(i,new Persona("Persona"+String.valueOf(i)));
        }
    }
   
    public LinkedList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(LinkedList<Persona> personas) {
        this.personas = personas;
    }
    
    
    
}
