import javax.swing.JOptionPane;

public class Simple {

    private Node head;
    private Node tail;
    private int size;
    private String sign;

    public Simple() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.sign = "+";
    }

    /**
     * Retorna el tamaño de esta lista.
     * 
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * Retorna el signo del número que esta lista representa.
     * 
     * @return String
     */
    public String sign() {
        return sign;
    }

    /**
     * Cambia el signo de la lista.
     * 
     * @param sign
     */
    public void sign(String sign) {
        if (sign == "+" | sign == "-") {
            this.sign = sign;
        }
    }

    /**
     * Imprime el valor de cada nodo por consola.
     */
    public void print() {
        if (head != null) {
            Node nodo = head;
            if (nodo.info() != 0) {
                System.out.print(sign + " ");
            }
            do {
                System.out.print(nodo.info());
                if (nodo.link() != null) {
                    System.out.print(" -> ");
                } else {
                    System.out.println(" -> null\n");
                }
                nodo = nodo.link();
            } while (nodo != null);
        } else {
            System.out.println("La lista está vacía.");
        }
    }

    /**
     * Retorna la información de un nodo determinado.
     * 
     * @param index
     * @return int
     */
    public int info(int index) {
        if (index <= 0 | index > size) {
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

    /**
     * Obtiene la referencia de un nodo; se utiliza para saber si un nodo existe.
     * 
     * @param index
     * @return Node
     */
    public Node get(int index) {
        if (index <= 0 | index > size) {
            return null;
        } else {
            Node nodo = head;
            for (int i = 1; i < index; i++) {
                nodo = nodo.link();
            }
            return nodo;
        }
    }

    /**
     * Reemplaza la información del nodo en el «index» con el «value».
     * 
     * @param index
     * @param value
     */
    public void set(int index, int value) {
        if (index <= 0 | index > size) {
            JOptionPane.showMessageDialog(null, "Not(set).\n");
        } else {
            Node nodo = head;
            for (int i = 1; i < index; i++) {
                nodo = nodo.link();
            }
            nodo.info(value);
        }
    }

    /**
     * Añade un nodo con una cierta información antes de todos los demás.
     * 
     * @param value
     */
    // add after all, en realidad
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
        tail.link(null);
        size++;
    }

    /**
     * Añade antes de primero un nodo con un valor establecido.
     * 
     * @param value
     */
    public void add_before_all(int value) {
        if (head == null) {
            add(value);
        } else {
            Node nodo = new Node();
            nodo.info(value);
            nodo.link(head);
            head = nodo;
            size++;
        }
    }

    /**
     * Elimina un nodo en una posición conocida.
     * 
     * @param index
     */
    public void delete(int index) {
        if (index <= 0 | index > size) {
            JOptionPane.showMessageDialog(null, "Not(delete).\n");
        } else {
            Node AP = null;
            Node P = head;
            for (int i = 1; i < index; i++) {
                AP = P;
                P.link();
            }
            if (P == head) {
                head = P.link();
            } else {
                AP.link(P.link());
            }
            P.release();
            P = null;
            size--;
        }
    }

    /**
     * 
     * Retorna el número que el nodo representa.
     * 
     * @return int
     */
    public int number() {
        int number = 0;
        if (head != null) {
            Node nodo = head;
            String cadena = "" + nodo.info();
            while (nodo.link() != null) {
                nodo.link();
                cadena = cadena + nodo.info();
            }
            number = Integer.valueOf(cadena);
        }
        return number;
    }
}
