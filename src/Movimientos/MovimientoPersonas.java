
package Movimientos;

import Objetos.Persona;
import TDAs.DobleCircular;


public class MovimientoPersonas {
    private DobleCircular<Persona> personas;

    public MovimientoPersonas() {
        personas = new DobleCircular<>();
    }
    
    public void rellenarLista(int numero){
        for(int i = 0 ; i < numero ; i++){
            personas.add(i,new Persona("Persona"+String.valueOf(i+1),i));
        }
    }
   
    public DobleCircular<Persona> getPersonas() {
        return personas;
    }

    
}
