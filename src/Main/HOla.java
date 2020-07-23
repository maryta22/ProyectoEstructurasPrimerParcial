
package Main;

import TDAs.DobleCircular;
import TDAs.LinkedList;
import java.util.Iterator;


public class HOla  {
    
   
    public static void main(String[] args) {
        
        LinkedList<String> personas = new LinkedList<String>();
        personas.add(0, "gsdd");
        personas.add(1, "gfadsfdd");
        personas.add(2, "iuytrd");
        personas.add(3, "gnvnd");
        personas.add(4, "g213d");
        personas.add(3, "dgsdf");

        Iterator it = personas.iterador();
        while(it.hasNext()){
            System.out.println(it.next());
        }

    }
    
}
