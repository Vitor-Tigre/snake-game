import java.util.ArrayList;
import java.util.List;

public class ListaLigada {
    private Node head;
    private Node tail;

    public ListaLigada() {
        head = new Node(10, 10);
        tail = head;
    }

    public void adicionaCabeca(int x, int y) {
        Node novo = new Node(x, y);
        novo.setNext(head);
        head = novo;
    }

    public int[] removerTail() {
        if (head == tail) {
            return null;
        }

        Node atual = head;
        while (atual.getNext().getNext() != null) {
            atual = atual.getNext();
        }
        int[] ultimaParte = {tail.getX(), tail.getY()};
        atual.setNext(null);
        tail = atual;

        return ultimaParte;
    }

    public boolean colidiuCorpo(int x, int y) {
        Node atual = head;
        while (atual.getNext() != null) {
            if (atual.getX() == x && atual.getY() == y) {
                return true;
            }
            atual = atual.getNext();
        }
        return false;
    }

    public int[] getCabeca() {
        return new int[]{head.getX(), head.getY()};
    }

    public List<int[]> getCorpoCobra() {
        List<int[]> corpoCobra = new ArrayList<int[]>();
        Node atual = head;

        while (atual != null) {
            corpoCobra.add(new int[]{atual.getX(), atual.getY()});
            atual = atual.getNext();
        }
        return corpoCobra;
    }
}
