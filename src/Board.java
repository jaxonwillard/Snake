import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Board extends GridPane {
    boolean pause;
    int size;
    Tile[][] tiles;
    Body body;
    Position apple;
    String currentDirection;
    String onBoardDirection;
    int rate;
    Timeline timeLine;
    boolean ai;
    HamiltonCycle hamiltonCycle;
    BufferedWriter userScoreBufferedWriter;
    BufferedWriter AIScoreBufferedWriter;
    FileWriter userScoreFileWriter;
    FileWriter AIScoreFileWriter;
    //throws exception because of bufferedwriter
    Board(int size) throws Exception{
        this.userScoreFileWriter = new FileWriter("UserScoreCard", true);
        this.AIScoreFileWriter =   new FileWriter("AIScoreCard", true);
        this.userScoreBufferedWriter = new BufferedWriter(this.userScoreFileWriter);
        this.AIScoreBufferedWriter =   new BufferedWriter(this.AIScoreFileWriter);
        this.pause = false;
        addTiles(size);
        this.rate = 0;
        this.onBoardDirection = "RIGHT";
        this.currentDirection = "RIGHT";
        this.size = size;
        this.body = new Body();
        addBody();
        setApple();
        this.hamiltonCycle = new HamiltonCycle(this);

        this.timeLine = new Timeline(new KeyFrame(Duration.millis(100), ae -> {
            if (this.ai){
                setDirection();

            }



            if (!pause){
            increaseRate();
            // try/catch because of bufferedwriter
                try {
                    moveSnake();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        timeLine.setCycleCount(Animation.INDEFINITE);

        hamiltonCycle.createGraph(this.tiles);




    }

    void addTiles(int size){
        this.tiles = new Tile[size][size];
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Tile t = new Tile(new Position(i,j));
                this.tiles[i][j] = t;
                this.add(this.tiles[i][j],i, j);
            }
        }
    }
    void addBody(){
        for (int i=0; i<this.body.body.size(); i++){
            this.tiles[this.body.body.get(i).i][this.body.body.get(i).j].addBody();
        }
    }
    void setApple(){
        if (this.apple != null){
        this.tiles[this.apple.i][this.apple.j].background.setFill(Color.BLACK);}
        int i = (int) (Math.random() * this.size);
        int j = (int) (Math.random() * this.size);
        Position a = new Position(i,j);
        if (!appleInBody(a)){
            this.apple = a;
            this.tiles[i][j].background.setFill(Color.RED);
        }
        else {setApple();}
    }
    boolean appleInBody(Position p){
        for (Position body : this.body.body){
            if (body.equals(p)) return true;
        }
        return false;
    }
    boolean positionInBody(Position p){
        for (int i=0; i<this.body.body.size()-1; i++){
            if (this.body.body.get(i).equals(p)) {
                return true;
            }
        }
        return false;
    }
    void moveSnake() throws IOException {
        if (this.onBoardDirection.equals("DOWN")){
            if (this.body.head.j == this.size-1) endGame(-2);
            if (this.body.head.j < this.size-1){
                this.body.body.add( new Position(this.body.head.i, this.body.head.j+1));
                this.body.head = this.body.body.get(this.body.body.size()-1);
                this.tiles[this.body.head.i][this.body.head.j].background.setFill(Color.BLACK);
            }
        }
        if (this.onBoardDirection.equals("RIGHT")){
            if (this.body.head.i == this.size-1) endGame(-2);
            if (this.body.head.i < this.size-1){
                this.body.body.add( new Position(this.body.head.i+1, this.body.head.j));
                this.body.head = this.body.body.get(this.body.body.size()-1);
                this.tiles[this.body.head.i][this.body.head.j].background.setFill(Color.BLACK);
            }
        }

        if (this.onBoardDirection.equals("LEFT")){
            if (this.body.head.i == 0) endGame(-2);
            if (this.body.head.i > 0){
                this.body.body.add( new Position(this.body.head.i-1, this.body.head.j));
                this.body.head = this.body.body.get(this.body.body.size()-1);
                this.tiles[this.body.head.i][this.body.head.j].background.setFill(Color.BLACK);
            }
        }

        if (this.onBoardDirection.equals("UP")){
            if (this.body.head.j == 0) endGame(-2);
            if (this.body.head.j > 0){
                this.body.body.add( new Position(this.body.head.i, this.body.head.j-1));
                this.body.head = this.body.body.get(this.body.body.size()-1);
                this.tiles[this.body.head.i][this.body.head.j].background.setFill(Color.BLACK);
            }
        }
        this.currentDirection = this.onBoardDirection;

        if (positionInBody(this.body.head)){
            endGame(-3);
        }
        if (body.body.size() == this.size * this.size){
            endGame(-4);
        }


        if (!appleInBody(this.apple)){
            this.tiles[this.body.tail.i][this.body.tail.j].background.setFill(Color.CADETBLUE);
            this.body.body.remove(0);
            this.body.tail = this.body.body.get(0);
        }
        else {
            increaseRate();
            setApple();
        }
    }
    void setOnBoardDirection(String event){
        if (event.equals("LEFT")  && !this.currentDirection.equals("RIGHT")){
            this.onBoardDirection = event;
        }
        if (event.equals("RIGHT") && !this.currentDirection.equals("LEFT")){
            this.onBoardDirection = event;
        }
        if (event.equals("UP")    && !this.currentDirection.equals("DOWN")){
            this.onBoardDirection = event;
        }
        if (event.equals("DOWN")  && !this.currentDirection.equals("UP")){
            this.onBoardDirection = event;
        }

    }

    void increaseRate() {
//        this.rate += 1.7;
//        this.timeLine.setRate(this.rate);
        if (this.body.body.size() > this.size / 3){
            this.timeLine.setRate(1);
        }
//        if (this.body.body.size() > this.size / 2){
//            this.timeLine.setRate(2);
//        }
    }
    void processAIButton(){
        this.ai = true;
        startGame();
    }

    void startGame(){
        this.timeLine.play();
        }


    void setDirection(){



        /*
        left, right, up, down
         */
        int[] directions = new int[]{0,0,0,0};


        if (this.currentDirection.equals("LEFT"))       { directions[0] = -1; directions[1] = -1;}
        else if (this.currentDirection.equals("RIGHT")) { directions[1] = -1; directions[0] = -1;}
        else if (this.currentDirection.equals("UP"))    { directions[2] = -1; directions[3] = -1;}
        else if (this.currentDirection.equals("DOWN"))  { directions[3] = -1; directions[2] = -1;}

        if (getDistanceLeft() > 0) directions[0] = 1;
        if (getDistanceRight() > 0) directions[1] = 1;
        if (getDistanceUp() > 0) directions[2] = 1;
        if (getDistanceDown() > 0) directions[3] = 1;

        if (this.body.head.i == 0 ||                   positionInBody(new Position(this.body.head.i-1, this.body.head.j)))     {directions[0] = -2;}
        if (this.body.head.i == this.tiles.length-1 || positionInBody(new Position(this.body.head.i+1, this.body.head.j)))     {directions[1] = -2;}
        if (this.body.head.j == 0 ||                   positionInBody(new Position(this.body.head.i, this.body.head.j-1)))     {directions[2] = -2;}
        if (this.body.head.j == this.tiles.length-1 || positionInBody(new Position(this.body.head.i, this.body.head.j+1)))     {directions[3] = -2;}





        int maxIndex = getMaxIndex(directions);

        if (directions[maxIndex] > 0 || detectBorderCross() || detectBodyCross()){
        if (maxIndex == 0) setOnBoardDirection("LEFT");
        else if (maxIndex == 1) setOnBoardDirection("RIGHT");
        else if (maxIndex == 2) setOnBoardDirection("UP");
        else setOnBoardDirection("DOWN");
        }

//        else if (detectBorderCross()){
//            System.out.println("getZeroIndex(directions) = " + getZeroIndex(directions));
//            int zeroIndex = getZeroIndex(directions);
//            if (zeroIndex == 0) setOnBoardDirection("LEFT");
//            else if (zeroIndex == 1) setOnBoardDirection("RIGHT");
//            else if (zeroIndex == 2) setOnBoardDirection("UP");
//            else setOnBoardDirection("DOWN");
//
//        }

//        System.out.println(directions[0] + " " + directions[1] + " " + directions[2] + " " + directions[3]);
//        System.out.println("maxIndex = " + maxIndex);
//        System.out.println("getMinIndex(directions) = " + getMinIndex(directions));
//        System.out.println("directions[index] = " + directions[maxIndex]);



    }

    int getDistanceRight(){
        boolean isApple = false;
        int distance = 0;
        for (int i=this.body.head.i; i<this.tiles.length; i++){
            if (new Position(i, this.body.head.j).equals(this.apple)){
                isApple = true;
                break; }
            distance++; }
        if (isApple){
            return distance;}
        return distance * -1;
    }

    int getDistanceLeft(){
        boolean isApple = false;
        int distance = 0;
        for (int i=this.body.head.i; i>=0; i--){
            if (new Position(i, this.body.head.j).equals(this.apple)){
                isApple = true;
                break; }
            distance++; }
        if (isApple){
            return distance;}
        return distance * -1;
    }

    int getDistanceDown(){
        boolean isApple = false;
        int distance = 0;
        for (int j=this.body.head.j; j<this.tiles.length; j++){
            if (new Position(this.body.head.i, j).equals(this.apple)){
                isApple = true;
                break; }
            distance++; }
        if (isApple){
            return distance;}
        return distance * -1;
    }

    int getDistanceUp(){
        boolean isApple = false;
        int distance = 0;
        for (int j=this.body.head.j; j>= 0; j--){
            if (new Position(this.body.head.i, j).equals(this.apple)){
                isApple = true;
                break; }
            distance++; }
        if (isApple){
            return distance;}
        return distance * -1;
    }

    int getMaxIndex(int[] array){
        int maxIndex = 0;
        for (int i=0; i<array.length; i++){
            if (array[i] > array[maxIndex]){
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    int getMinIndex(int[] array){
        int minIndex = 0;
        for (int i=0; i<array.length; i++){
            if (array[i] < array[minIndex]){
                minIndex = i;
            }
        }
        return minIndex;
    }

    int getZeroIndex(int[] array){
        int zeroIndex = 0;
        for (int i=0; i<array.length; i++){
            if (array[i] == 0){
                zeroIndex = i;
                break;
            }
        }
        return zeroIndex;
    }
    boolean detectBorderCross(){
        if (this.currentDirection.equals("RIGHT") && this.body.head.i == this.tiles.length-1) return true;
        if (this.currentDirection.equals("LEFT") && this.body.head.i == 0) return true;
        if (this.currentDirection.equals("UP") && this.body.head.j == 0) return true;
        if (this.currentDirection.equals("DOWN") && this.body.head.j == this.tiles.length-1) return true;
        return false;


    }
    boolean detectBodyCross(){
        if (this.onBoardDirection.equals("LEFT") && positionInBody(new Position(this.body.head.i-1, this.body.head.j))) return true;
        else if (this.onBoardDirection.equals("RIGHT") && positionInBody(new Position(this.body.head.i+1, this.body.head.j))) return true;
        else if (this.onBoardDirection.equals("UP") && positionInBody(new Position(this.body.head.i, this.body.head.j-1))) return true;
        else return this.onBoardDirection.equals("DOWN") && positionInBody(new Position(this.body.head.i, this.body.head.j + 1));
    }

    void endGame(int exitCode) throws IOException {
        if (this.size == 16){
        StringBuilder str = new StringBuilder();
        str.append(this.body.body.size());
        str.append("\n");
        if (this.ai) {
            this.AIScoreBufferedWriter.append(str.toString());
            this.AIScoreBufferedWriter.close();
        }
        else {
            this.userScoreBufferedWriter.append(str.toString());
            this.userScoreBufferedWriter.close();}

        }



        System.exit(exitCode);
    }


}

