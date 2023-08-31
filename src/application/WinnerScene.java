package application;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class WinnerScene {
    private Scene scene;
    private String gameCode;
    
    private Map<String, Integer> playerScores = new HashMap<>();

    public WinnerScene(String gameCode) {
        this.gameCode = gameCode;

        StackPane root = new StackPane();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        Text titleLabel = new Text("WE HAVE A WINNER");
        titleLabel.setStyle("-fx-font-size: 24;");

        gridPane.add(titleLabel, 0, 0, 2, 1);

        // Read user data from file and calculate scores
        try (BufferedReader br = new BufferedReader(new FileReader("./data/" + this.gameCode + ".txt"))) {
            String line;
            int rowIndex = 1; // Start from row index 1
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("DECK:") && !line.startsWith("CURRENT:")) {
                    String[] parts = line.split(",");
                    String username = parts[0];
                    int score = parts.length == 1 ? 100 : 100 - (parts[1].length() * 10);

                    Label userLabel = new Label(username);
                    Label scoreLabel = new Label("Score: " + score);
                    
                    playerScores.put(username, score);

                    gridPane.add(userLabel, 0, rowIndex);
                    gridPane.add(scoreLabel, 1, rowIndex);

                    rowIndex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.getChildren().add(gridPane);

        Scene scene1 = new Scene(root, 900, 600);

        this.scene = scene1;
        
        this.writeNewScore();
    }

    public Scene getScene() {
        return scene;
    }
    
    public void writeNewScore() {
    	Map<String, Integer> playerScores = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/leaderboard.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                int score = Integer.parseInt(parts[1].trim());

                updateScore(playerScores, username, score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated scores back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/leaderboard.txt"))) {
            for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
                bw.write(entry.getKey() + ", " + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void updateScore(Map<String, Integer> scores, String username, int pointsToAdd) {
        if (scores.containsKey(username)) {
            int currentScore = scores.get(username);
            scores.put(username, currentScore + pointsToAdd);
        } else {
            scores.put(username, pointsToAdd);
        }
    }
}

