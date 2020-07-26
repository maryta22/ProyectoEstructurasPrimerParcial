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
public class DobleCircular<E> implements List<E> {

    private Node<E> last;
    private int efectivo;

    public DobleCircular() {
        this.last = new Node<>(null);
        this.efectivo = 0;
    }

    public Node<E> getLast() {
        return last.getPrevious();
    }

    public void setLast(Node<E> last) {
        this.last.setPrevious(last);
    }

    @Override
    public boolean addFirst(E e) {
        if (e != null) {
            Node<E> nuevo = new Node<>(e);
            if (this.isEmpty()) {
                last.setNext(nuevo);
                last.setPrevious(nuevo);
                nuevo.setNext(last);
                nuevo.setPrevious(last);
                efectivo++;
                return true;
            } else {
                nuevo.setNext(last.getNext());
                last.getNext().setPrevious(nuevo);
                last.setNext(nuevo);
                nuevo.setPrevious(last);
                efectivo++;
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean addLast(E e) {
        if (e != null) {
            Node<E> nuevo = new Node<>(e);
            if (this.isEmpty()) {
                last.setNext(nuevo);
                last.setPrevious(nuevo);
                nuevo.setNext(last);
                nuevo.setPrevious(last);
                efectivo++;
                return true;
            } else {
                nuevo.setPrevious(last.getPrevious());
                last.getPrevious().setNext(nuevo);
                nuevo.setNext(last);
                last.setPrevious(nuevo);
                efectivo++;
                return true;
            }
        } else {
            return false;
        }

    }

    @Override
    public E removeFirst() throws IndexOutOfBoundsException {
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException();
        } else {
            if (efectivo == 1) {
                Node<E> unico = last.getPrevious();
                E eliminado = unico.getData();
                unico.setNext(null);
                unico.setPrevious(null);
                unico.setData(null);
                last.setNext(null);
                last.setPrevious(null);

                efectivo--;
                return eliminado;
            } else {
                Node<E> siguiente = last.getNext().getNext();

                E eliminado = last.getNext().getData();

                last.getNext().setData(null);
                last.getNext().setNext(null);
                last.setNext(siguiente);
                last.getNext().setPrevious(null);
                siguiente.setPrevious(last);

                efectivo--;
                return eliminado;
            }

        }
    }

    @Override
    public E removeLast() throws IndexOutOfBoundsException {
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException();
        } else {
            if (efectivo == 1) {
                Node<E> unico = last.getPrevious();
                E eliminado = unico.getData();
                unico.setNext(null);
                unico.setPrevious(null);
                unico.setData(null);
                last.setNext(null);
                last.setPrevious(null);

                efectivo--;
                return eliminado;
            } else {
                Node<E> anterior = last.getPrevious().getPrevious();

                E eliminado = last.getPrevious().getData();

                last.getPrevious().setData(null);
                last.getPrevious().setNext(null);
                anterior.setNext(last);
                last.getPrevious().setPrevious(null);
                last.setPrevious(anterior);

                efectivo--;
                return eliminado;
            }

        }
    }

    @Override
    public int size() {
        return efectivo;
    }

    @Override
    public boolean isEmpty() {
        return efectivo == 0;
    }

    @Override
    public void clear() {
        last.getPrevious().setNext(null);
        last.getNext().setPrevious(null);
        last.setNext(null);
        last.setPrevious(null);
    }

    @Override
    public void add(int index, E element) throws IllegalArgumentException, IndexOutOfBoundsException {
        int cont = 0;
        if (element != null) {
            if (index >= 0 && index <= efectivo) {
                if (index == 0) {
                    addFirst(element);
                } else if (index == efectivo) {
                    addLast(element);
                } else {
                    Node<E> nuevo = new Node<>(element);
                    for (Node<E> n = last.getNext(); n.getData() != null; n = n.getNext()) {
                        if (cont == index) {
                            Node<E> anterior = n.getPrevious();
                            n.setPrevious(nuevo);
                            nuevo.setPrevious(anterior);
                            anterior.setNext(nuevo);
                            nuevo.setNext(n);

                            efectivo++;

                        }
                        cont++;
                    }
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
        if (index < efectivo && index >= 0) {
            int cont = 0;
            if (index == 0) {
                return removeFirst();
            } else if (index == efectivo - 1) {
                return removeLast();
            } else {
                for (Node<E> n = last.getNext(); n.getData() != null; n = n.getNext()) {
                    if (cont == index) {
                        E eliminado = n.getData();

                        Node<E> prev = n.getPrevious();
                        Node<E> after = n.getNext();

                        n.setData(null);
                        n.setPrevious(null);
                        after.setPrevious(prev);
                        n.setNext(null);
                        prev.setNext(after);

                        efectivo--;

                        return eliminado;
                    }
                    cont++;
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
        return null;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < efectivo && index >= 0) {
            int cont = 0;
            if (index == 0) {
                return last.getNext().getData();
            } else if (index == efectivo - 1) {
                return last.getPrevious().getData();
            } else {
                for (Node<E> n = last.getNext(); n.getData() != null; n = n.getNext()) {
                    if (cont == index) {
                        return n.getData();
                    }
                    cont++;
                }
            }

        } else {
            throw new IndexOutOfBoundsException();
        }
        return null;
    }

    @Override
    public E set(int index, E element) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (element != null) {
            if (index < efectivo && index >= 0) {
                int cont = 0;
                if (index == efectivo - 1) {
                    E cambiado = last.getPrevious().getData();
                    last.getPrevious().setData(element);
                    return cambiado;
                } else {
                    for (Node<E> n = last.getNext(); n.getData() != null; n = n.getNext()) {
                        if (cont == index) {
                            E cambiado = n.getData();
                            n.setData(element);
                            return cambiado;
                        }
                        cont++;
                    }
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }

    @Override
    public Iterator<E> iterador() {
        Iterator<E> it = new Iterator<E>() {
            private Node<E> n = last.getNext(); //first

            @Override
            public boolean hasNext() {
                if (!isEmpty()) {
                    return n.getData() != null;
                }
                return false;
            }

            @Override
            public E next() {
                E dato = n.getData();
                n = n.getNext();
                if (dato == null) {
                    dato = n.getData();
                    n = n.getNext();
                }

                return dato;
            }

        };
        return it;
    }

    public Iterator<E> iteradorReverse() {
        Iterator<E> it = new Iterator<E>() {
            private Node<E> p = last.getPrevious();

            @Override
            public boolean hasNext() {
                if (!isEmpty()) {
                    return p.getData() != null;
                }
                return false;
            }

            @Override
            public E next() {
                E dato = p.getData();
                p = p.getPrevious();
                if (dato == null) {
                    dato = p.getData();
                    p = p.getPrevious();
                }

                return dato;
            }

        };
        return it;
    }

    /*@Override
        public String toString() {
        StringBuilder sb = new StringBuilder();
        int cont = 0;
        for (Node<E> n = last.getNext(); cont < efectivo; n = n.getNext()) {
            if (n == last.getNext() && n == last) {
                sb.append("[");
                sb.append(last.getNext().getData());
                sb.append("]");
            } else if (n == last.getNext()) {
                sb.append("[");
                sb.append(last.getNext().getData());
                sb.append(",");
            } else if (n == last) {
                sb.append(last.getData());
                sb.append("]");
            } else {
                sb.append(n.getData());
                sb.append(",");
            }
            cont++;
        }
        return sb.toString();
    }*/
}
