import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    Position position;
    Rectangle background;
    Tile(Position p){
        this.setHeight(15);
        this.setWidth(15);
        this.position = p;
        this.background = new Rectangle(this.getWidth(), this.getHeight());
        this.getChildren().add(background);
        background.setStroke(Color.BLACK);
        background.setFill(Color.AQUAMARINE);
    }
    void addBody(){
        this.background.setFill(Color.BLACK);
    }
    void subtractBody(){
        this.background.setFill(Color.WHITE);
    }

}
