package PrimaImplementazione;

public class Cell {

    //Coordinate

    public int i,j;

    //Oggetto di tipo Cell per il percorso al padre
    public Cell parent;

    //Costo euristico della cella corrente
    public int heuristicCost;

    //Costo finale della soluzione
    public int finalCost; //G + H with
    //G(n) è il costo del percorso dal nodo di partenza a n
    //e H(n) è l'euristica che stima in maniera ottimale il costo del percorso meno costosoda n al targhet


    public boolean solution; // Se le celle fanno parte del percorso della soluzione

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }


    @Override
    public String toString() {
        return "["+ i +"," + j + "]";
    }
}
