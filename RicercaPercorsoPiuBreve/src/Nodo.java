import java.util.*;
public class Nodo {
    private String nome;
    private List<Arco> archi;

    public Nodo(String nome) {
        this.nome = nome;
        archi = new ArrayList<>();
    }

    public void aggiungiArco(Arco arco) {
        archi.add(arco);
    }

    public List<Arco> getArchi() {
        return archi;
    }

    public String getNome() {
        return nome;
    }
}