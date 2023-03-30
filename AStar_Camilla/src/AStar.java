
import java.util.*;
public class AStar {

    private static int DEFAULT_HV_COST = 10; // Costo Orizzontale e Verticale
    private static int DEFAULT_DIAGONAL_COST = 14; //Costo Diagionale

    private int HVCosto;
    private int costoDiagonale;

    private Nodo[][] searchArea; //Rappresenta la griglia rettangolare di nodi in cui viene effettuata la ricerca.
    private PriorityQueue<Nodo> openList; //Una PriorityQueue di nodi aperti/openset che vengono ancora valutati dalla ricerca
    private Set<Nodo> closedSet; //Un Set di nodi che sono stati già valutati dalla ricerca e che non sarnno più prelevati
    private Nodo nodoIniziale;
    private Nodo nodoFinale;

    /*
        Il codice fornisce due costruttori per la classe AStar:
         1.Uno che accetta tutti i parametri necessari,
         2.Uno che utilizza i valori predefiniti per i costi orizzontali/verticali e diagonali.
    */

    //Primo Costruttore:
    public AStar(int rows, int cols,  Nodo nodoIniziale, Nodo nodoFinale ,int HVCosto, int costoDiagonale) {
        this.HVCosto = HVCosto;
        this.costoDiagonale = costoDiagonale;
        setNodoIniziale(nodoIniziale);
        setNodoFinale(nodoFinale);
        this.searchArea = new Nodo[rows][cols];
        this.openList = new PriorityQueue<Nodo>(new Comparator<Nodo>() {
            @Override
            public int compare(Nodo nodo0, Nodo nodo1) {
                return Integer.compare(nodo0.getF(),nodo1.getF());
            }
        });
        setNodi();
        this.closedSet = new HashSet<>();
    }

    //Secondo Costruttore
    public AStar(int rows, int cols, Nodo nodoIniziale, Nodo nodoFinale) {
        this(rows, cols, nodoIniziale, nodoFinale, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
    }

    /*  SetNodi():
         Viene utilizzato per creare e impostare la matrice di nodi searchArea.
         Questa matrice rappresenta la griglia rettangolare di nodi in cui viene
         effettuata la ricerca.
         All'interno del metodo viene creato un nodo per ogni elemento della
         matrice searchArea e viene calcolata l'euristica per ogni nodo in base
         al nodo finale. Inoltre, il nodo viene inserito nella posizione corrispondente
         della matrice searchArea.
     */
    private void setNodi() {
        for (int i = 0; i < searchArea.length; i++){
            for (int j = 0; j < searchArea[0].length; j++){
                Nodo nodo = new Nodo(i,j);
                nodo.calcoloEuristica(getNodoFinale());
                this.searchArea[i][j] = nodo;
            }
        }

    }

    /*  setBlocchi():
        Viene utilizzato per impostare i nodi bloccati nella griglia di ricerca. (Quindi gli ostacoli).
        Prende in input una matrice di coordinate (riga, colonna) che rappresentano i nodi bloccati
        nella griglia. In altre parole, questi sono i nodi attraverso i quali il percorso non può passare.
     */
    public void setBlocchi(int[][] blocksArray){
        for (int i = 0; i < blocksArray.length; i++){
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row,col);
        }
    }


    public void setParking(int[][] blocksParking){
        for(int i = 0; i < blocksParking.length; i++){
            int row = blocksParking[i][0];
            int col = blocksParking[i][1];
            setPark(row, col);
        }
    }

    /*
        ricercaPercorso():
        Viene utilizzato per eseguire l'algoritmo di ricerca del percorso A*.
        Inizia con l'aggiunta del nodo iniziale all'elenco aperto (openList).
        Successivamente, viene eseguito un ciclo finché openList non è vuoto.
        Durante il ciclo, viene estratto il nodo con il punteggio f più basso
        dall'elenco aperto e viene aggiunto all'elenco chiuso (closedSet).
        Se il nodo estratto è il nodo finale, il percorso viene restituito
        chiamando il metodo getPath. Altrimenti, vengono aggiunti i nodi
        adiacenti al nodo estratto all'elenco aperto.
     */
    public List<Nodo> ricercaPercorso() {
        openList.add(nodoIniziale);
        while (!isEmpty(openList)) {
            Nodo nodoCorrente = openList.poll();
            closedSet.add(nodoCorrente);
            if (isFinalNode(nodoCorrente)) {
                return getPercorso(nodoCorrente);
            } else {
                addNodiAdiacente(nodoCorrente);
            }
        }
        return new ArrayList<Nodo>();
    }

    /*
        getPercorso():
        Restituisce il percorso dal nodo finale al nodo iniziale.
        Inizia aggiungendo il nodo corrente alla lista di percorso (List<Nodo> percorso)
        e successivamente aggiungendo il padre (parent) del nodo corrente, e così via
        fino a raggiungere il nodo iniziale.
     */
    private List<Nodo> getPercorso(Nodo nodoCorrente){
        List<Nodo> percorso = new ArrayList<Nodo>();
        percorso.add(nodoCorrente);
        Nodo parent;
        while ((parent = nodoCorrente.getParent()) != null){
            percorso.add(0,parent);
            nodoCorrente = parent;
        }
        return percorso;
    }

    private void addNodiAdiacente(Nodo nodoCorrente){
        addAdjacentUpperRow(nodoCorrente);
        addAdjacentMiddleRow(nodoCorrente);
        addAdjacentLowerRow(nodoCorrente);
    }


    /*
        addAdjacentLowerRow():
        controlla la riga inferiore della griglia rispetto al nodo corrente
        e aggiunge i nodi adiacenti se presenti. Come in precedenza, per ogni
        nodo adiacente, viene verificato che sia all'interno della griglia e
        che non sia un blocco (ostacolo). Se tutte le condizioni sono soddisfatte,
        viene calcolato il costo di movimento per raggiungere il nodo adiacente
        dal nodo corrente e il nodo adiacente viene aggiunto alla coda di priorità
        openList se non è già presente in closedSet.
     */
    private void addAdjacentLowerRow(Nodo nodoCorrente) {
        int row = nodoCorrente.getRow();
        int col = nodoCorrente.getCol();
        int rigaLow = row + 1;
        if (rigaLow < getSearchArea().length){
            if (col - 1  >= 0){
                checkNodo(nodoCorrente, col - 1, rigaLow, getCostoDiagonale());
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNodo(nodoCorrente, col + 1, rigaLow, getCostoDiagonale()); // Comment this line if diagonal movements are not allowed
            }
            checkNodo(nodoCorrente, col, rigaLow, getHVCosto());
        }
    }

    /*
        addAdjacentMiddleRow():
        Controlla la riga corrente della griglia rispetto al nodo corrente
        e aggiunge i nodi adiacenti se presenti. Anche in questo caso, per
        ogni nodo adiacente, viene verificato che sia all'interno della
        griglia e che non sia un blocco. Se tutte le condizioni sono
        soddisfatte, viene calcolato il costo di movimento per raggiungere
        il nodo adiacente dal nodo corrente e il nodo adiacente viene aggiunto
        alla coda di priorità openList se non è già presente in closedSet.
     */

    private void addAdjacentMiddleRow(Nodo nodoCorrente) {
        int row = nodoCorrente.getRow();
        int col = nodoCorrente.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNodo(nodoCorrente, col - 1, middleRow, getHVCosto());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNodo(nodoCorrente, col + 1, middleRow, getHVCosto());
        }
    }

    /*
        addAdjacentUpperRow():
        Controlla la riga superiore della griglia rispetto al nodo corrente
        e aggiunge i nodi adiacenti se presenti. In particolare, per ogni
        nodo adiacente, viene verificato che sia all'interno della griglia
        e che non sia un blocco (ovvero un nodo che non può essere attraversato).
        Se tutte le condizioni sono soddisfatte, viene calcolato il costo di
        movimento per raggiungere il nodo adiacente dal nodo corrente e il nodo
        adiacente viene aggiunto alla coda di priorità openList se non è
        già presente in closedSet.
     */
    private void addAdjacentUpperRow(Nodo nodoCorrente) {
        int row = nodoCorrente.getRow();
        int col = nodoCorrente.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNodo(nodoCorrente, col - 1, upperRow, getCostoDiagonale()); // Comment this if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNodo(nodoCorrente, col + 1, upperRow, getCostoDiagonale()); // Comment this if diagonal movements are not allowed
            }
            checkNodo(nodoCorrente, col, upperRow, getHVCosto());
        }
    }


    /*
         checkNodo():
         è una funzione che prende in input un oggetto nodo e controlla
         se questo soddisfa determinati criteri. In particolare, la funzione
         verifica se il nodo è già stato visitato (cioè se nodo.visitato è uguale a true)
         e se il nodo ha una distanza maggiore di 1 rispetto al nodo di partenza
         (cioè se nodo.distanza è maggiore di 1). Questi controlli sono importanti perché
         consentono di evitare cicli e di assicurarsi che la distanza calcolata per il nodo
         sia sempre quella minima rispetto al nodo di partenza. Se il nodo non soddisfa
         questi criteri, la funzione restituisce false, altrimenti restituisce true.
     */
    private void checkNodo(Nodo nodoCorrente, int col, int row, int cost) {
        Nodo adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodoData(nodoCorrente, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(nodoCorrente, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }

    }


    //Metodi di Set& Get
    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    private void setPark(int row, int col) {
        this.searchArea[row][col].setPark(true);
    }


    private boolean isFinalNode(Nodo currentNode) {
        return currentNode.equals(nodoFinale);
    }

    private boolean isEmpty(PriorityQueue<Nodo> openList) {
        return openList.size() == 0;
    }

    public int getHVCosto() {
        return HVCosto;
    }

    public void setHVCosto(int HVCosto) {
        this.HVCosto = HVCosto;
    }

    public int getCostoDiagonale() {
        return costoDiagonale;
    }

    public void setCostoDiagonale(int costoDiagonale) {
        this.costoDiagonale = costoDiagonale;
    }

    public Nodo[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Nodo[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Nodo> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Nodo> openList) {
        this.openList = openList;
    }

    public Set<Nodo> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Nodo> closedSet) {
        this.closedSet = closedSet;
    }

    public Nodo getNodoIniziale() {
        return nodoIniziale;
    }

    public void setNodoIniziale(Nodo nodoIniziale) {
        this.nodoIniziale = nodoIniziale;
    }

    public Nodo getNodoFinale() {
        return nodoFinale;
    }

    public void setNodoFinale(Nodo nodoFinale) {
        this.nodoFinale = nodoFinale;
    }
}