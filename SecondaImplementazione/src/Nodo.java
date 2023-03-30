public class Nodo {

    private int g, f, h , row, col;
    private boolean isBlock;
    private Nodo parent;


    //Costruttore
    public Nodo(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }


    /* Questo metodo calcola l'euristica del nodo in base alla posizione
    del nodo finale specificato. L'euristica viene calcolata come la
    distanza di Manhattan tra la posizione corrente del nodo e la posizione finale.
    */

    public void calcoloEuristica(Nodo nodoFinale){
        this.h = Math.abs(nodoFinale.getRow() - getRow()) + Math.abs(nodoFinale.getCol() - getCol());
    }


    public void calcoloCostoFinale(){
        int costoFinale = getG() + getH();
        setF(costoFinale);
    }

    /*  setNodoData(Nodo currentNodo, int cost):
        Questo metodo imposta i dati del nodo (costo g, padre, costo totale f)
        in base al nodo corrente e al costo specificati. Il costo g viene calcolato
        come la somma del costo g del nodo corrente e del costo specificato.
        Il genitore del nodo viene impostato come il nodo corrente. Infine, il
        metodo chiama il metodo calculateFinalCost per calcolare il costo totale f.
     */
    public void setNodoData(Nodo currentNodo, int cost){
        int gCost = currentNodo.getG() + cost;
        setParent(currentNodo);
        setG(gCost);
        calcoloCostoFinale();
    }


    /*  checkBetterPath(Nodo nodoCorrente, int cost):
        Questo metodo verifica se il percorso corrente per raggiungere
        il nodo ha un costo inferiore al percorso precedente per
        raggiungere lo stesso nodo. In caso affermativo, imposta i dati
        del nodo con i valori del nodo corrente e del costo specificato.
     */
    public boolean checkBetterPath(Nodo nodoCorrente, int cost){
        int gCost = nodoCorrente.getG() + cost;

        if (gCost < getG()){
            setNodoData(nodoCorrente, cost);
            return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object arg0){
        Nodo other = (Nodo) arg0;
        return this.getRow() == other.getRow() && this.getCol() == other.getCol();

    }

    @Override
    public String toString() {
        return "Nodo [row=" + row + ", col=" + col + "]";
    }


    //Metodi di Set & Get
    public void setG(int g) {
        this.g = g;
    }

    public int getG() {
        return g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Nodo getParent() {
        return parent;
    }

    public void setParent(Nodo parent) {
        this.parent = parent;
    }


}
