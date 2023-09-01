package application;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;

import java.util.HashMap;
import java.util.Map;

public class WinnerScene {
    private Scene scene;
    private String gameCode;
    private GridPane gridPane = new GridPane();
    
    private Map<String, Integer> playerScores = new HashMap<>();

    public WinnerScene(String gameCode) {
        this.gameCode = gameCode;
        this.updateInterface();

        StackPane root = new StackPane();

        
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setHgap(10);
        this.gridPane.setVgap(10);
        this.gridPane.setPadding(new Insets(20));
        
        ImageView imageView = new ImageView(new Image(new File("./assets/winner.png").toURI().toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        
        this.gridPane.add(imageView, 0, 0, 2, 1);

        root.getChildren().add(this.gridPane);

        Scene scene1 = new Scene(root, 900, 600);

        this.scene = scene1;
    }

    public Scene getScene() {
        return scene;
    }
    
    public void updateInterface() {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/" + this.gameCode + ".txt"))) {
            String line;
            int rowIndex = 1;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("DECK:") && !line.startsWith("CURRENT:")) {
                    String[] parts = line.split(",");
                    String username = parts[0];
                    int score = parts.length == 1 ? 100 : 100 - (parts[1].length() * 10);

                    Label userLabel = new Label(username);
                    Label scoreLabel = new Label("Score: " + score);
                    
                    this.playerScores.put(username, score);

                    this.gridPane.add(userLabel, 0, rowIndex);
                    this.gridPane.add(scoreLabel, 1, rowIndex);

                    rowIndex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(this.gameCode.startsWith("tr-")) {
            String lowestScorer = null;
            int lowestScore = Integer.MAX_VALUE;

            for (Map.Entry<String, Integer> entry : this.playerScores.entrySet()) {
                String username = entry.getKey();
                int score = entry.getValue();

                if (score < lowestScore) {
                    lowestScore = score;
                    lowestScorer = username;
                }
            }
            
            System.out.println(this.playerScores);
            
            List<String> lines = new ArrayList<>();
            String mainTournamentFilePath = "./data/";
            
            int indexOfHyphen = (this.gameCode + ".txt").indexOf('_');

            if (indexOfHyphen != -1) {
                // Extract the substring before the first hyphen
            	mainTournamentFilePath = mainTournamentFilePath + (this.gameCode + ".txt").substring(0, indexOfHyphen) + ".txt";
            } else {
                System.out.println("No hyphen found in the input string.");
            }
            
            System.out.println("MAIN FILE PATH: " + mainTournamentFilePath);
            
            try (BufferedReader br = new BufferedReader(new FileReader(mainTournamentFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            System.out.println(lowestScorer);
            System.out.println(lines);
            
            System.out.println("----------------");
            
            for(int i = 0; i < lines.size(); i++) {
            	String line = lines.get(i);
            	if(line.contains(",")) {
                	String[] usernames = line.split(",");
                	
                	System.out.println("first: " + usernames[0] + " || " + "second: " + usernames[1]);
                	
                	if(usernames[0].equals(lowestScorer)) {
                		line = usernames[1];
                	} else if(usernames[1].equals(lowestScorer)) {
                		line = usernames[0];
                	}
                	
                	System.out.println(line);
                	lines.set(i, line);            		
            	}
            }
            
            System.out.println("----------------");
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(mainTournamentFilePath))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    System.out.println(updatedLine);
                    bw.newLine();
                }
                
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}