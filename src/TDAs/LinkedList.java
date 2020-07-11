package TDAs;

public class LinkedList<E> implements List<E> {

    private NodeList<E> header;
    private NodeList<E> last;

    public LinkedList() {
        header = null;
        last = null;
    }

    public NodeList<E> getHeader() {
        return header;
    }

    public void setHeader(NodeList<E> header) {
        this.header = header;
    }

    public NodeList<E> getLast() {
        return last;
    }

    public void setLast(NodeList<E> last) {
        this.last = last;
    }

    @Override
    public boolean addFirst(E e) {

        if (e != null) {
            NodeList<E> nuevo = new NodeList(e);
            if (isEmpty()) {
                setHeader(nuevo);
                setLast(nuevo);
            } else {
                nuevo.setNext(header);
                setHeader(nuevo);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addLast(E e) {
        if (e != null) {
            NodeList<E> nuevo = new NodeList(e);
            if (isEmpty()) {
                setHeader(nuevo);
                setLast(nuevo);
            } else {
                last.setNext(nuevo);
                setLast(nuevo);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E removeFirst() throws IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        } else {
            NodeList<E> primero = header;
            if (header.getNext() != null) {
                header.setContent(null);
                setHeader(header.getNext());               
            } else {
                header = null;
                last = null;
            }
            return primero.getContent();
        }

    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        } else {
            NodeList<E> ultimo = last;
            if (header.getNext() != null) {
                NodeList<E> n;
                for (n = header; n != null; n = n.getNext()) {
                    if (n.getNext() == last) {
                        setLast(n);
                        last.setNext(null);
                    }
                }
            } else {
                header = null;
                last = null;
            }
            return ultimo.getContent();
        }
    }

    @Override
    public int size() {
        int contador = 0;
        NodeList<E> n;
        for (n = header; n != null; n = n.getNext()) {
            contador++;
        }
        return contador;
    }

    @Override
    public boolean isEmpty() {
        return header == null;
    }

    @Override
    public void clear() {
        header = null;
        last = null;
    }

    /*devuelve el nodo en el index indicado*/
    public NodeList<E> buscarNodo(int index) {
        int contador = 0;
        NodeList<E> n;
        for (n = header; contador <= index ; n = n.getNext()) {
            if (contador == index) {
                return n;
            }
            contador++;
        }
        return null;
    }

    @Override
    public void add(int index, E element) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (element != null) {
            if (index <= size()) {
                if (index == size()) {
                    addLast(element);
                } else if (index == 0) {
                    addFirst(element);
                } else {
                    NodeList<E> nuevo = new NodeList(element); 
                    nuevo.setNext(buscarNodo(index));
                    buscarNodo(index - 1).setNext(nuevo);     
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < size()) {
            if (index == size() - 1) {
                return removeLast();
            } else if (index == 0) {
                return removeFirst();
            } else {
                E eliminado = buscarNodo(index).getContent();
                buscarNodo(index).setContent(null);
                buscarNodo(index - 1).setNext(buscarNodo(index + 1));
                return eliminado;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E get(int index) {
        return buscarNodo(index).getContent();
    }

    @Override
    public E set(int index, E element) {
        if (element != null) {
            if (index < size()) {
                if (index == size() - 1) {
                    E reemplazado = last.getContent();
                    last.setContent(element);  
                    return reemplazado;
                } else if (index == 0) {
                    E reemplazado = header.getContent();
                    header.setContent(element);
                    return reemplazado;
                } else {
                    E reemplazado = buscarNodo(index).getContent();
                    buscarNodo(index).setContent(element);  
                    return reemplazado;
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        String result = "";
        NodeList<E> n;
        for (n = header; n != null; n = n.getNext()) {
            result += n.getContent().toString() + ",";
        }
        return result;
    }

}
