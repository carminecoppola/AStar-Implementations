import java.util.*;
public class Arco {
    private Nodo partenza;
    private Nodo arrivo;
    private double peso;

    public Arco(Nodo partenza, Nodo arrivo, double peso) {
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.peso = peso;
    }

    public Nodo getPartenza() {
        return partenza;
    }

    public Nodo getArrivo() {
        return arrivo;
    }

    public double getPeso() {
        return peso;
    }
}
