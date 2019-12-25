public class HamiltonCycle {
    final int V;
    Board board;
    HamiltonCycle(Board board){
        V = 5;
        int path[];
        this.board = board;

    }
    boolean isSafe(int v, int[][] graph, int[] path, int pos){
        /*
        check if this vertex is an adjacent vertex of the previously added vertex
         */
        if (graph[path[pos-1]][v] == 0){
            return false;
        }
        /*
        check if the vertex has already been included.
         */
        for (int i=0; i < pos; i++){
            if (path[i] == v){
                return false;
            }
        }

        return true;
    }

    boolean hamiltonCycleUtil(int[][] graph, int[] path, int pos){
        if (pos == this.V){
            if (graph[path[pos-1]][path[0]] == 1){
                return true;
            }
            else return false;
        }
        for (int v = 1; v < V; v++){
            if (isSafe(v, graph, path, pos)){
                path[pos] = v;
                if (hamiltonCycleUtil(graph, path, pos +1)){
                    return true;
                }
                path[pos] = -1;
            }
        }

        return false;

    }

    int hamCycle(int[][] graph){
        int[] path = new int[V];
        for (int i = 0; i < V; i++){
            path[i] = -1;
        }
        path[0] = 0;
        if (!hamiltonCycleUtil(graph, path, 1)){
            System.out.println("\n Solution does not exist");
            return 0;
        }
        printSolution(path);
        return 1;
    }

    void printSolution(int[] path){
        for (int i = 0; i < V; i++){
            System.out.print(" " + path[i] + " ");
        }
        System.out.println(" " + path[0] + " ");
    }

    void createGraph(Tile[][] tiles){
        int[][] graph = new int [tiles.length * tiles.length][tiles.length * tiles.length];
        for (int[] tileInfos : graph){
            for (int info : tileInfos){
                info = 0;
            }
        }

        for (int i = 0; i<tiles.length*tiles.length; i++){
            Position p = createPosition(i, tiles.length);
            if (isValidRight(p) && i < tiles.length*tiles.length-1) graph[i][i+1] = 1;
        }




//            for (int[] tileInfos : graph){
//            for (int info : tileInfos){
//
//                System.out.print(info + " ");
//            }
//            System.out.println();
//        }
    }

    boolean isValidRight(Position position){
        if (!this.board.positionInBody(new Position(position.i+1, position.j))) return true;
        return false;
    }

    Position createPosition(int graphPosition, int graphSize){
        return new Position(graphPosition % graphSize, graphPosition / graphSize);
    }




//    public static void main(String[] args) {
//        HamiltonCycle hamiltonCycle = new HamiltonCycle();
//        int[][] graph1 = new int[][]{
//                {0,1,0,1,0},
//                {1,0,1,1,1},
//                {0,1,0,0,1},
//                {1,1,0,0,1},
//                {0,1,1,1,0}};
////        hamiltonCycle.hamCycle(graph1);
//        int [][] graph2 = new int[][]{
//                {0,1,0,1,0},
//                {1,0,1,1,1},
//                {0,1,0,0,1},
//                {1,1,0,0,0},
//                {0,1,1,0,0}};
////        hamiltonCycle.hamCycle(graph2);
//        ;
//
//    }
}
