public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // Nodi
            Nodo partenza = new Nodo("Partenza");
            Nodo arrivo = new Nodo("Arrivo");
            Nodo parcheggio = new Nodo("Parcheggio");

        // Archi partenza -> parcheggio e parcheggio -> arrivo
            Arco arco1 = new Arco(partenza, parcheggio, 2.5);
            Arco arco2 = new Arco(parcheggio, arrivo, 3.0);

            partenza.aggiungiArco(arco1);
            parcheggio.aggiungiArco(arco2);

            grafo.aggiungiNodo(partenza);
            grafo.aggiungiNodo(parcheggio);
            grafo.aggiungiNodo(arrivo);
    }
}
