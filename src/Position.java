public class Position {
    int i;
    int j;
    Position(int i, int j){
        this.i = i;
        this.j = j;
    }
    boolean equals(Position other){
        return this.i == other.i && this.j == other.j;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[" + this.i + ", " + this.j + "]");
        return sb.toString();
    }
}
