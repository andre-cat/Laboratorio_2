import javax.swing.JOptionPane;

public class Circular {

    private Node head; 
    private Node tail;
    private int size;

    public Circular() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Retorna el número de nodos de la lista.
     * 
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * Añade un nodo a la lista al final de todos los elementos.
     * 
     * @param value
     */
    public void add(int value) {
        Node nodo = new Node();
        nodo.info(value);
        if (head == null) {
            head = nodo;
            tail = nodo;
        } else {
            tail.link(nodo);
            tail = nodo;
        }
        size++;
        tail.link(head);
    }

    /**
     * Imprime toda la lista por consola con representación de flechas.
     */
    public void print() {
        if (head != null) {
            Node nodo = head;
            do {
                System.out.print(nodo.info());
                if (nodo.link() != head) {
                    System.out.print(" -> ");
                } else {
                    System.out.println(" -> Head\n");
                }
                nodo = nodo.link();
            } while (nodo != head);
        } else {
            System.out.println("La lista está vacía.");
        }
    }

    /**
     * Retorna el valor de un nodo determinado.
     * 
     * @param index
     * @return int
     */
    public int info(int index) {
        if (index < 0 | index > size | head == null) {
            JOptionPane.showMessageDialog(null, "Not(info).\n");
            return 0;
        } else {
            Node nodo = head;
            for (int i = 1; i < index; i++) {
                nodo = nodo.link();
            }
            return nodo.info();
        }
    }

}