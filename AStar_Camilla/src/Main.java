
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Location start = new Location();
        Nodo initialNode = start.chooseStart("San Giorgio");
        Nodo finalNode = start.chooseEnd("Napoli");

        int rows = 6;
        int cols = 7;

        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        int[][] blocksParking = new int[][]{{0,2}, {0,3}, {1,4}};
        aStar.setBlocchi(blocksArray);
        aStar.setParking(blocksParking);
        List<Nodo> path = aStar.ricercaPercorso();
        for (Nodo node : path) {
            System.out.println(node);
            if(node.isPark())
                System.out.println("Parcheggio trovato");
        }

        //Search Area
        //      0   1   2   3   4   5   6
        // 0    -   -   P   P   -   -   -
        // 1    -   -   -   B   P   -   -
        // 2    -   I   -   B   -   F   -
        // 3    -   -   -   B   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -

        //Expected output with diagonals
        //Node [row=2, col=1]
        //Node [row=1, col=2]
        //Node [row=0, col=3]
        //Node [row=1, col=4]
        //Node [row=2, col=5]

        //Search Path with diagonals
        //      0   1   2   3   4   5   6
        // 0    -   -   -   *   -   -   -
        // 1    -   -   *   B   *   -   -
        // 2    -   I*  -   B   -  *F   -
        // 3    -   -   -   B   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -

        //Expected output without diagonals
        //Node [row=2, col=1]
        //Node [row=2, col=2]
        //Node [row=1, col=2]
        //Node [row=0, col=2]
        //Node [row=0, col=3]
        //Node [row=0, col=4]
        //Node [row=1, col=4]
        //Node [row=2, col=4]
        //Node [row=2, col=5]

        //Search Path without diagonals
        //      0   1   2   3   4   5   6
        // 0    -   -   *   *   *   -   -
        // 1    -   -   *   B   *   -   -
        // 2    -   I*  *   B   *  *F   -
        // 3    -   -   -   B   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -
    }
}


/*
Viene creato un oggetto Nodo iniziale con coordinate (2,1) e un oggetto Nodo finale con coordinate (2,5).
Viene inoltre definita una griglia di dimensioni 6x7, e vengono inseriti dei blocchi nella griglia.
Successivamente viene chiamato il metodo ricercaPercorso dell'oggetto AStar per trovare il percorso migliore
tra il nodo iniziale e quello finale.
Infine, viene stampato il percorso trovato iterando attraverso la lista di nodi e stampando le coordinate di
ciascuno di essi. Vengono inoltre mostrati due possibili output, uno con la considerazione dei nodi
diagonalmente adiacenti e l'altro senza. Per ciascuno dei due casi, viene mostrata la griglia iniziale e la
griglia finale con il percorso evidenziato con un asterisco (*)
 */