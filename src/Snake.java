import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Snake extends Application {
    // throws exception because of bufferedwriter in board
    public void start(Stage stage) throws Exception {
        SimpleBooleanProperty started = new SimpleBooleanProperty(false);
        VBox aiData = new VBox();
        Button ai = new Button("Click to watch HAL play");
        aiData.getChildren().add(ai);
        TextField input = new TextField();
        BorderPane borderPane = new BorderPane();
        Board board = new Board(16);
        borderPane.setLeft(input);
        borderPane.setRight(new ScoreBoard(ai));
        borderPane.setCenter(board);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
        ai.setOnMouseClicked(event -> {
            board.processAIButton();
        });
        input.setOnKeyPressed(event -> {
            // TODO: figure out better way to accept input
            input.setText("");
            if (!started.get() && event.getCode().toString().equals("RIGHT") || event.getCode().toString().equals("UP")
                    || event.getCode().toString().equals("DOWN")){
                started.setValue(true);
                board.startGame();
            }
            if (event.getCode().toString().equals("ESCAPE")) System.exit(-4);
            if (event.getCode().toString().equals("P")) board.pause = !board.pause;
            board.setOnBoardDirection(event.getCode().toString());

        });

    }
}
