package PrimaImplementazione;

public class Main {
    public static void main(String[] args) {

        /* Creiamo un'istanza della classe AStar con i seguenti parametri:
                la larghezza e l'altezza della griglia (5x5)
                le coordinate iniziali (0,0) e quelle finali (3,2)
                una matrice 2D che rappresenta gli ostacoli nella griglia (posizioni non percorribili) */

        AStar aStar = new AStar(5, 5, 0,0, 3, 2,
                new int[][]{
                        {0,4},{2,2},{3,1},{3,3},{2,1},{2,3}
                }
        );
        aStar.display();
        aStar.process(); //Applica l'algoritmo A*
        aStar.displayScores();//Visualizziamo i punteggi sulla griglia
        aStar.displaySolution(); // Soluzione Path

    }
}