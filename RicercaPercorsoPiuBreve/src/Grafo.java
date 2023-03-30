import java.util.*;

/*
    Rappresenta un grafo non orientato, dove ogni nodo viene identificato
    da una stringa e i suoi archi hanno un peso associato, rappresentato
    da un valore intero.
    Il grafo viene implementato utilizzando una mappa (o dizionario) che
    mappa ogni nodo alle sue liste di adiacenza, dove ogni elemento della
    lista rappresenta un arco uscente dal nodo corrente.
 */
public class Grafo {
    private List<Nodo> nodi;

    //Costruttore: inizializza la mappa con un insieme vuoto di nodi e nessun arco
    public Grafo() {
        nodi = new ArrayList<>();
    }

    //Aggiunge un nodo alla mappa, associandogli una lista di adiacenza vuota, se non è già presente nella mappa
    public void aggiungiNodo(Nodo nodo) {
        nodi.add(nodo);
    }

    public List<Nodo> getNodi() {
        return nodi;
    }
}

