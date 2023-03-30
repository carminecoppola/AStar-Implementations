import java.util.PriorityQueue;

public class AStar {

    //Costo per gli spostamenti diagonali, orizzontali e verticali
    public static final int DIAGONAL_COST = 14; //costo degli spostamenti diagonali
    public static final int V_H_COST = 10; //costo degli spostamenti verticali e orizzontali


    private Cell[][] grid;//Una matrice di oggetti di tipo Cell che rappresentano le celle della griglia
    //Definizione della priority queue per aprire le celle
    //Apertura delle celle: OPEN SET
    //mettiamo prima le celle che hanno il costo minore

    private PriorityQueue<Cell> openCells;//una coda di priorità che contiene le celle aperte, ordinate in base al costo finale del percorso


    //CLOSED SET: set di nodi gia valutati
    private boolean[][] closedCells; //una matrice booleana che indica se una cella è stata valutata o meno

    //Cella di partenza & Cella di arrivo
    private int startI, startJ, endI, endJ;

    /*Costruttore:
        Inizializza la griglia con la larghezza e l'altezza specificate, creando un oggetto Cell per ogni cella.
        Inizializza la coda di priorità e le variabili startI, startJ, endI ed endJ.
        Assegna l'euristica di ogni cella come la distanza euclidea tra la cella e la cella di arrivo.
        Inizializza il costo finale della cella di partenza a zero.
        Aggiunge eventuali blocchi alla griglia.
    */
    public AStar(int width, int height, int si, int sj, int ei, int ej, int [][] blocks) {
        grid = new Cell[width][height];

        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<Cell>((Cell c1, Cell c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
        });

        startCell(si, sj);
        endCell(ei, ej);

        //Inizia euristica e le celle
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ); // Distanza euclidea
                grid[i][j].solution = false;
            }
        }
        grid[startI][startJ].finalCost = 0;

        //Mettiamo i blocchi nella griglia

        for (int i = 0; i < blocks.length; i++) {
            addBlockOnCell(blocks[i][0], blocks[i][1]); // Prendiamo la prima riga
        }
    }
        public void addBlockOnCell(int i, int j){
            grid[i][j] = null;
        }

        public void startCell(int i, int j){
            startI = i;
            startJ = j;
        }

        public void endCell(int i, int j){
            endI = i;
            endJ = j;
        }

        //Aggiungiamo il costo se necessario
        public void updateCostIfNeeded(Cell current, Cell t, int cost){
            if (t == null || closedCells[t.i][t.j])
                return;

            int tFinalCost = t.heuristicCost + cost;
            boolean isOpen = openCells.contains(t);
            if (!isOpen || tFinalCost < t.finalCost) {
                t.finalCost = tFinalCost;
                t.parent = current;
                if (!isOpen)
                    openCells.add(t);
            }
        }


        /* Process:
            Aggiunge la cella di partenza alla coda di priorità.
            Cicla finché la coda di priorità NON è vuota:
                1.Rimuove la cella con il costo finale minore dalla coda di priorità.
                2.Marca la cella come valutata.
                3.Se la cella è la cella di arrivo, termina la ricerca.
                4.Altrimenti, esamina le celle adiacenti alla cella corrente e aggiorna i loro costi finali
                  se necessario.
            Se la coda di priorità è vuota e la cella di arrivo non è stata trovata, la ricerca è fallita.
         */
        public void process(){
            //Aggiungiamo la posizione di partenza
            openCells.add(grid[startI][startJ]);
            Cell current;

            while (true){
                current = openCells.poll();

                if (current == null){
                    break;
                }

                closedCells[current.i][current.j] = true;

                if (current.equals(grid[endI][endJ]))
                    return;

                Cell t;
                if (current.i - 1 >= 0){
                    t = grid[current.i - 1][current.j];
                    updateCostIfNeeded(current,t,current.finalCost + V_H_COST);

                    //Costo diagonale
                    if (current.j - 1 >= 0){
                        t = grid[current.i - 1][current.j - 1];
                        updateCostIfNeeded(current,t,current.finalCost + DIAGONAL_COST);
                    }

                    //Costo
                    if (current.j + 1 < grid[0].length){
                        t = grid[current.i - 1][current.j + 1];
                        updateCostIfNeeded(current,t,current.finalCost + DIAGONAL_COST);
                    }
                }

                if (current.j - 1 >= 0){
                    t = grid[current.i][current.j - 1];
                    updateCostIfNeeded(current,t,current.finalCost + V_H_COST);

                }

                if (current.j + 1 < grid[0].length){
                    t = grid[current.i][current.j + 1];
                    updateCostIfNeeded(current,t,current.finalCost + V_H_COST);
                }

                if (current.i + 1 < grid.length){
                    t = grid[current.i + 1][current.j];
                    updateCostIfNeeded(current,t,current.finalCost + V_H_COST);

                    if (current.j - 1 >= 0) {
                        t = grid[current.i + 1][current.j - 1];
                        updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
                    }

                    if (current.j + 1 < grid[0].length) {
                        t = grid[current.i + 1][current.j + 1];
                        updateCostIfNeeded(current, t, current.finalCost + DIAGONAL_COST);
                    }
                }
            }


        }


        /* Display:
             Stampa a schermo la griglia e la soluzione del percorso trovato,
             se esiste. Viene stampato un SO per indicare la cella di
             partenza e un DE per la cella di destinazione.
             Le celle aperte e chiuse vengono stampate rispettivamente con il
             carattere OP e XX, mentre le celle che non sono state ancora
             visitate verranno stampate con il carattere __.
         */
        public void display(){
            System.out.println("Grid :");

            for (int i = 0; i < grid.length; i++){
                for (int j = 0; j < grid[i].length; j++){
                    if (i == startI && j == startJ)
                        System.out.print("SO  "); //Cella sorgente
                    else if (i == endI && j == endJ)
                        System.out.print("DE  ");  //Cella di destinazione
                    else if(grid[i][j] != null)
                        System.out.printf("%-3d", 0);
                    else
                        System.out.print("BL  "); //Cella di blocco
                }
                System.out.println("");
            }
            System.out.println();
        }


        /*DisplayScores:
            Serve a stampare il costo finale di ogni cella della griglia,
            dopo che la ricerca A* è stata completata. Il metodo
            attraversa la griglia e stampa il costo finale di ogni cella,
            utilizzando il carattere " " per le celle non bloccate e "#"
            per le celle bloccate.
         */
        public void displayScores(){
            System.out.println("\nScores for cells:");

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] != null)
                        System.out.printf("%-3d", grid[i][j].finalCost);
                    else
                        System.out.print("BL  ");
                }
                System.out.println();
            }
            System.out.println();
        }

        /* DisplaySolution:
                Serve a stampare il percorso dalla cella di partenza
                alla cella di arrivo, dopo che la ricerca A* è stata
                completata. Il metodo segna le celle che fanno parte
                del percorso con il carattere "*". Inizia dalla
                cella di arrivo e segue i puntatori ai genitori fino
                ad arrivare alla cella di partenza.
         */
        public void displaySolution(){
            if (closedCells[endI][endJ]) {
                //Ripercorriamo il percorso
                System.out.println("Path: ");
                Cell current = grid[endI][endJ];
                System.out.println(current);
                grid[current.i][current.j].solution = true;


                while (current.parent != null) {
                    System.out.print("-->" + current.parent);
                    grid[current.parent.i][current.parent.j].solution = true;
                    current = current.parent;
                }

                System.out.println("\n");

                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (i == startI && j == startJ)
                            System.out.print("SO  "); //Source cell
                        else if (i == endI && j == endJ)
                            System.out.print("DE  "); //Cella di destinazione
                        else if (grid[i][j] != null)
                            System.out.printf("%-3s", grid[i][j].solution ? "X" : "0");
                        else
                            System.out.print("BL  "); //Cella di blocco

                    }
                    System.out.println();
                }
                System.out.println();
            }else
                System.out.println("No possible path");
            }


}

