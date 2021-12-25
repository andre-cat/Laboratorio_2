public class Node {

    private int data;
    private Node next;

    public Node() {
        this.data = 0;
        this.next = null;
    }

    /**
     * Retorna el valor de este nodo.
     * 
     * @return int
     */
    public int info() {
        return data;
    }

    /**
     * Cambia el valor actual del nodo por el indicado.
     * 
     * @param dato
     */
    public void info(int dato) {
        this.data = dato;
    }

    /**
     * Retorna la referencia al siguiente nodo de este nodo.
     * 
     * @return Node
     */
    public Node link() {
        return next;
    }

    /**
     * Cambia el apuntador del nodo por el indicado.
     * 
     * @param next
     */
    public void link(Node next) {
        this.next = next;
    }

    /**
     * Apunta al nodo hacia ningún otro nodo.
     * 
     * @param next
     */
    public void release() {
        next = null;
    }
}
