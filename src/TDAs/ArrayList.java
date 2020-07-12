package TDAs;

public class ArrayList<E> implements List<E> {

    //arreglo de elementos
    //en Java no se puede crear un arreglo así
    //E[] elements = new E[100];
    //so...
    private int capacidad = 100;
    private E[] elementos = null;
    private int ocupados;  // cuanto del arreglo ha sido ocupado.
    private E[] copia = null;

    /* constructor */
    public ArrayList() {
        this.elementos = (E[]) (new Object[capacidad]);
        this.ocupados = 0;
    }

    /* mueve todos los elementos un espacio a la izquierda */
    private void moverIzquierda(int n) {
        for (int i = n; i < ocupados; i++) {
            elementos[i - 1] = elementos[i];
        }
        elementos[ocupados - 1] = null;

        ocupados--;
    }

    /*mueve todos los elementos un espacio a la derecha.*/
    private void moverDerecha(int n, int limite) {
        try {
            for (int i = limite; i >= n; i--) {
                elementos[i + 1] = elementos[i];
            }
            ocupados++;
        } catch (ArrayIndexOutOfBoundsException excepcion) {
            addCapacity();
            moverDerecha(n,limite);
        }
        
    }
        /* crea una lista del doble a la capacidad anterior, 
    guarda los elementos de elements 
    y se le asigna a elements el nuevo arreglo*/
    private void addCapacity() {
        copia = (E[]) (new Object[capacidad * 2]);

        for (int i = 0; i < elementos.length; i++) {
            copia[i] = elementos[i];
        }
        elementos = copia;
        capacidad = capacidad * 2;
    }

    /* retorna true si elements está lleno */
    private boolean isFull() {
        return elementos.length == ocupados;
    }

    public boolean addFirstJunior(E e) {
        if (ocupados != 0) {
            moverDerecha(ocupados - 1, 0);
        } else {
            ocupados++;
        }
        elementos[0] = e;
        return true;
    }

    /*añade un elemento al inicio del arreglo*/
    @Override
    public boolean addFirst(E e) {
        if (e == null) {
            return false;
        } else {
            if (this.isFull()) {
                addCapacity();
                return addFirstJunior(e);
            } else {
                return addFirstJunior(e);
            }
        }

    }

    /*añade un elemento al final del arreglo*/
    @Override
    public boolean addLast(E e) {
        if (e == null) {
            return false;
        } else {
            if (this.isFull()) {
                addCapacity();
            }
        }
        elementos[ocupados] = e;
        ocupados++;
        return true;
    }

    /**
     * remueve el primer elemento del arreglo y lo devuelve*
     */
    @Override
    public E removeFirst() {
        if (ocupados != 0) {
            E elemento = elementos[0];
            moverIzquierda(1);
            return elemento;
        } else {
            return null;
        }

    }

    /**
     * remueve el último elemento del arreglo y lo devuelve*
     */
    @Override
    public E removeLast() {
        if (ocupados != 0) {
            E elemento = elementos[ocupados - 1];
            elementos[ocupados - 1] = null;
            ocupados--;
            return elemento;
        } else {
            return null;
        }

    }

    /* se eliminan todos los datos del arreglo */
    @Override
    public void clear() {
        for (int i = 0; i < elementos.length; i++) {
            elementos[i] = null;
        }
        ocupados = 0;
    }

    /* devuelve el tamaño del arreglo */
    @Override
    public int size() {
        return ocupados;
    }

    /* retorna true si el arreglo está vacio */
    @Override
    public boolean isEmpty() {
        return ocupados == 0;
    }

    /*reemplaza el valor encontrado en ese índice y devuelve el valor reemplazado*/
    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (index >= ocupados) {
            throw new IndexOutOfBoundsException();
        } else if (element == null) {
            throw new IllegalArgumentException();
        } else {
            E reemplazado = elementos[index];
            elementos[index] = element;
            return reemplazado;
        }
    }

    /*devuelve el elemento en la posicion index del arreglo*/
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= ocupados) {
            throw new IndexOutOfBoundsException();
        } else {
            return elementos[index];
        }
    }

    /*elimina el valor en la posicion index del arreglo*/
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index >= ocupados) {
            throw new IndexOutOfBoundsException();
        } else {
            E eliminado = elementos[index];
            moverIzquierda(index + 1);
            return eliminado;
        }
    }

    /*añade el elemento element al arreglo en la posición index */
    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (index > ocupados) {
            throw new IndexOutOfBoundsException();
        } else if (element == null) {
            throw new IllegalArgumentException();
        } else if (isEmpty()) {
            addFirst(element);
        } else {
            moverDerecha(ocupados - 1, index);
            elementos[index] = element;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < ocupados; i++) {
            result += elementos[i].toString() + ",";
        }
        return result;
    }

}
