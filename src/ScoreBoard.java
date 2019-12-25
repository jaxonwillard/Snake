import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreBoard extends VBox{
    ScoreBoard(Button ai) throws FileNotFoundException {
        Text UserScoreCard = makeScoreCard("UserScoreCard");
        Text AIScorecard = makeScoreCard("AIScoreCard");
        this.getChildren().addAll(ai, AIScorecard, UserScoreCard);

    }

    Text makeScoreCard(String fileName) throws FileNotFoundException {
        ArrayList<Integer> userScoreList = getFileScores(fileName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileName.equals("AIScoreCard") ? "HAL's High Scores" : "User High Scores  ");
//        stringBuilder.append("\n");
        for (Integer i : userScoreList){
            stringBuilder.append("\n" + i);
        }
        stringBuilder.append("\n\n");
        return new Text(stringBuilder.toString());

    }







    ArrayList<Integer> getFileScores(String file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new FileReader(file));
        ArrayList<Integer> scoresList = new ArrayList<>();
        ArrayList<Integer> topThree = new ArrayList<>();
        while(fileScanner.hasNextInt()){ scoresList.add(fileScanner.nextInt()); }
        System.out.println(scoresList);
        scoresList.sort(Integer::compareTo);
        System.out.println(scoresList);
        for (int i = 1; i < 4; i++){
            if (scoresList.size() > 0) topThree.add(scoresList.remove(scoresList.size()-1));}
        System.out.println(topThree + "\n\n");
        return topThree;
    }

}
