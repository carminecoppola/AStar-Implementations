import java.util.ArrayList;
import java.util.List;

public class Location {

    private List<Nodo> start = new ArrayList<>();
    private List<Nodo> end = new ArrayList<>();

    public Location(){
        setStart();
        setEnd();
    }

    void setStart(){
        Nodo nodo1 = new Nodo(2,1);
        nodo1.setIndirizzo("San Giorgio");

        Nodo nodo2 = new Nodo(1,1);
        nodo2.setIndirizzo("Portici");

        Nodo nodo3 = new Nodo(0,1);
        nodo3.setIndirizzo("Pomigliano");

        this.start.add(nodo1);
        this.start.add(nodo2);
        this.start.add(nodo3);
    }

    public void setEnd() {
        Nodo nodo1 = new Nodo(2,5);
        nodo1.setIndirizzo("Napoli");

        Nodo nodo2 = new Nodo(3,6);
        nodo2.setIndirizzo("Caserta");

        Nodo nodo3 = new Nodo(5,5);
        nodo3.setIndirizzo("Casoria");

        this.end.add(nodo1);
        this.end.add(nodo2);
        this.end.add(nodo3);
    }

    public Nodo chooseStart(String indirizzo){

        for (Nodo position : this.start) {
            if(indirizzo.equals(position.getIndirizzo())){
                return position;
            }
        }
        return null;
    }

    public Nodo chooseEnd(String indirizzo){

        for (Nodo position : this.end) {
            if(indirizzo.equals(position.getIndirizzo())){
                return position;
            }
        }
        return null;
    }
}
