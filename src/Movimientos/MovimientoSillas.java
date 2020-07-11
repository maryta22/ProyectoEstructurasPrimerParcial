
package Movimientos;

import Objetos.Silla;
import TDAs.ArrayList;

public class MovimientoSillas {
    private ArrayList<Silla> sillas ;

    public MovimientoSillas() {
       sillas = new ArrayList<>();
    }
    
    public void rellenarLista(int numero){
        for(int i = 0 ; i < numero ; i++){
            sillas.add(i,new Silla("Silla"+String.valueOf(i)));
        }
    }

    public ArrayList<Silla> getSillas() {
        return sillas;
    }

    public void setSillas(ArrayList<Silla> sillas) {
        this.sillas = sillas;
    }
      
    
    
}
