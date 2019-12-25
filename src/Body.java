import java.util.ArrayList;

public class Body {
    ArrayList<Position> body;
    Position head;
    Position tail;
    Body(){
        this.body = new ArrayList<>();
        for (int i=0; i<3; i++){
            body.add(new Position(i+1, 1));
        }
        this.head = body.get(this.body.size()-1);
        this.tail = body.get(0);
    }

}
