/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDAs;

import java.util.Iterator;

/**
 *
 * @author Deja mi lapto ¬¬...!
 */
public class DobleCircular<E> implements List<E>{
    
    private Node<E> last;
    private int efectivo;

    public DobleCircular(){
        this.last=null;
        this.efectivo=0;
    }

    public Node<E> getLast() {
        return last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    @Override
    public boolean addFirst(E e) {
        if (e == null) {
            return false;
        }
        Node<E> nuevo = new Node<>(e);
        if (this.isEmpty()) {
            last = nuevo;
            this.efectivo++;
            last.setNext(nuevo);
            last.setPrevious(nuevo);
            return true;
        } else {
            nuevo.setNext(last.getNext());
            nuevo.setPrevious(last);
            last.getNext().setPrevious(nuevo);
            last.setNext(nuevo);
            this.efectivo++;
            return true;
        }
    }

    @Override
    public boolean addLast(E e) {
        if (e == null) {
            return false;
        }
        Node<E> nuevo = new Node<>(e);
        if (this.isEmpty()) {
            last = nuevo;
            this.efectivo++;
            last.setNext(nuevo);
            last.setPrevious(nuevo);
            return true;
        } else {
            nuevo.setPrevious(last);
            nuevo.setNext(last.getNext());
            last.getNext().setPrevious(nuevo);
            last.setNext(nuevo);
            setLast(nuevo);
            this.efectivo++;
            return true;
        }
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else if (last== last.getNext()) { // En caso que haya un solo nodo
            E eliminado= last.getData();
            last.setData(null);
            last = null;  //Segun yo con eso todo se hace null, no creo que sea necesario setear next, previous con null
            efectivo--;
            return eliminado;
        } else {
            E eliminado= last.getNext().getData();
            Node<E> temp = last.getNext().getNext(); //Obtengo el siguiente del "primero"
            last.getNext().setData(null);
            last.getNext().setNext(null);
            last.getNext().setPrevious(null);
            
            last.setNext(temp);
            temp.setPrevious(last);
            
            this.efectivo--;
            return eliminado;
        }
    }

    @Override
    public E removeLast() {
        if (this.isEmpty()) {
            return null;

        } else if (last.getNext()==last) { //En caso que haya un solo nodo
            E eliminado= last.getData();
            last.setData(null);
            last=null; //Segun yo con eso todo se hace null, no creo que sea necesario setear next, previous con null
            this.efectivo--;
            return eliminado;
        } else {
            E eliminado = last.getData();
            Node<E> previo = last.getPrevious();           
            
            previo.setNext(last.getNext());
            last.getNext().setPrevious(previo);
            
            last.setPrevious(null);
            last.setNext(null);
            
            setLast(previo);
            
            this.efectivo--;
            return eliminado;
        }
    }

    @Override
    public int size() {
        return efectivo;
    }

    @Override
    public boolean isEmpty() {
        return efectivo==0;
    }

    @Override
    public void clear() {
        setLast(null); //Con esto se pierden todos los enlaces y el gargabe collection se encarga de lo demas.
    }

    @Override
    public void add(int index, E element) {
        int cont=1;
        if(element==null){
            return;
        }else if(index==efectivo){
            addLast(element); 
            return;
        }else if(index==0){
            addFirst(element);
            return;
        }
        Node<E> first= last.getNext();
        for (Node<E> n = first.getNext(); n != last; n = n.getNext()) {
            if (cont == index) {
                Node<E> prev = n.getPrevious();
                Node<E> nuevo = new Node<>(element);
                prev.setNext(nuevo);
                nuevo.setPrevious(prev);
                nuevo.setNext(n);
                n.setPrevious(nuevo);
                efectivo++;
                return;

            }
            cont++;
        }
    }

    @Override
    public E remove(int index) {
        int cont = 1;
        if (index >= efectivo || index < 0) {
            return null;
        } else if (index == 0) {
            return removeFirst();
        } else if (index == this.efectivo - 1) {
            return removeLast();
        }
        Node<E> first = last.getNext();
        for (Node<E> n = first.getNext(); n != last; n = n.getNext()) {
            if (cont == index) {
                E eliminado= n.getData();
                Node<E> prev = n.getPrevious();
                Node<E> after = n.getNext();
                n.setData(null);
                n.setNext(null);
                n.setPrevious(null);
                after.setPrevious(prev);
                prev.setNext(after);
                efectivo--;
                return eliminado;

            }
            cont++;
        }
        return null;
    }

    @Override
    public E get(int index) {
        int cont=1;
        if(index>=efectivo || index<0){
            return null;
        }else if(index==0){
            return last.getNext().getData();
        }else if(index==efectivo-1){
            return last.getData();
        }
        Node<E> first= last.getNext();
        for(Node<E> n=first.getNext(); n!=last; n=n.getNext()){
            if(cont==index){
                return n.getData();
            }
            cont++;
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        int cont=0;
        if(element==null || (index >= efectivo || index < 0)){
            return null;
        } else if(index== efectivo-1){
            E cambiado= last.getData();
            last.setData(element);
            return cambiado;
        }
        
        for(Node<E> n=last.getNext(); n!=last; n= n.getNext()){
            if(cont==index){
                E cambiado= n.getData();
                n.setData(element);
                return cambiado;
            }
        }
        return null;
    }

    @Override
    public Iterator<E> iterador() {
        Iterator<E> it = new Iterator<E>() {
            private int contador = 0;
            private int contReverso= efectivo-1;
            private Node<E> n = last.getNext(); //first
            private Node<E> p= last; 

            @Override
            public boolean hasNext() {
                return contador != efectivo;
            }

            @Override
            public E next() {
                E dato = n.getData();
                n = n.getNext();
                contador++;
                return dato;
            }
            
            public boolean hasPrevious(){
                return contReverso!= -1;
            }
            
            public E previous(){
                E dato =p.getData();
                p=p.getPrevious();
                contReverso--;
                return dato;
            }

        };
        return it;
    }
    
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        int cont=0;
        for(Node<E> n=last.getNext(); cont<efectivo;n=n.getNext()){
            if(n==last.getNext() && n==last){
                sb.append("[");
                sb.append(last.getNext().getData());
                sb.append("]");
            }else if(n==last.getNext()){
                sb.append("[");
                sb.append(last.getNext().getData());
                sb.append(",");
            }else if(n==last){
                sb.append(last.getData());
                sb.append("]");
            }else{
                sb.append(n.getData());
                sb.append(",");
            }
            cont++;
        }
        return sb.toString();
    }
    
}
