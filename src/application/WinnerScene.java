package application;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    
    private Runnable onBackHandle;
    private Runnable onHomeHandle;
    
    private String mainFilePath = "";
    
    private Map<String, Integer> playerScores = new HashMap<>();

    public WinnerScene(String gameCode) {
        this.gameCode = gameCode;
        this.updateInterface();

        StackPane root = new StackPane();
        
        ImageView imageView = new ImageView(new Image(new File("./assets/winner.png").toURI().toString()));
        imageView.setFitWidth(500);
        imageView.setFitHeight(300);
        
        this.gridPane.add(imageView, 0, 0, 2, 1);
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setHgap(10);
        this.gridPane.setVgap(10);
        this.gridPane.setPadding(new Insets(20));

        root.getChildren().add(this.gridPane);
        root.setStyle("-fx-background-color: green;");

        Scene scene1 = new Scene(root, 1100, 700);

        this.scene = scene1;
    }

    public Scene getScene() {
        return scene;
    }
    
    public void setOnBackHandle(Runnable handle) {
    	onBackHandle = handle;
    }
    
    public void setOnHomeHandle(Runnable handle) {
    	onHomeHandle = handle;
    }
    
    public String getMainFilePath() {
    	String flag = this.mainFilePath.replace("./data/", "");
    	System.out.println(flag);
    	return flag.replace(".txt", "");
    }
    
    private void updateInterface() {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/" + this.gameCode + ".txt"))) {
        	String line;
        	
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("DECK:") && !line.startsWith("CURRENT:")) {
                    String[] parts = line.split(",");
                    String username = parts[0];
                    int score = parts.length == 1 ? 100 : 100 - (parts[1].length() * 10);
                    
                    this.playerScores.put(username, score);
                }
            }
            
            br.close();
            
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
            	this.mainFilePath = mainTournamentFilePath;
            } else {
                System.out.println("No hyphen found in the input string.");
            }
            
            System.out.println("MAIN FILE PATH: " + mainTournamentFilePath);
            
            try (BufferedReader buffer = new BufferedReader(new FileReader(mainTournamentFilePath))) {
                String l;
                while ((l = buffer.readLine()) != null) {
                    lines.add(l);
                }
                
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            System.out.println(lowestScorer);
            System.out.println(lines);
            
            System.out.println("----------------");
            Integer foundedIndex = 0;
            for(int i = 0; i < lines.size(); i++) {
            	String li = lines.get(i);
            	if(li.contains(",")) {
                	String[] usernames = li.split(",");
                	
                	System.out.println("first: " + usernames[0] + " || " + "second: " + usernames[1]);
                	
                	if(usernames[0].equals(lowestScorer)) {
                		li = usernames[1];
                	} else if(usernames[1].equals(lowestScorer)) {
                		li = usernames[0];
                	}
                	foundedIndex = i;
                	System.out.println(li);
                	lines.set(i, li);
            	}
            }
            
            System.out.println("----------------");
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(mainTournamentFilePath))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    System.out.println(updatedLine);
                    bw.newLine();
                }
                
                if(lines.size() == 1 && !lines.get(0).contains(",")) {
                	ImageView imageView = new ImageView(new Image(new File("./assets/home.png").toURI().toString()));
                    imageView.setFitWidth(120);
                    imageView.setFitHeight(40);
                    
                    imageView.setOnMouseClicked(event -> onHomeHandle.run());
                	
                	Label userLabel = new Label(lines.get(0) + " WON THE TOURNAMENT!");
                    userLabel.setTextFill(Color.WHITE);
                    userLabel.setAlignment(Pos.CENTER);
                    userLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                	
                    this.gridPane.add(userLabel, 0, 1);
                    this.gridPane.add(imageView, 0, 2);
                } else {
                	ImageView imageView = new ImageView(new Image(new File("./assets/previous.png").toURI().toString()));
                    imageView.setFitWidth(120);
                    imageView.setFitHeight(40);
                    
                    imageView.setOnMouseClicked(event -> onBackHandle.run());
                	
                	Label userLabel = new Label(lines.get(foundedIndex) + " WON THE MATCH!");
                    userLabel.setTextFill(Color.WHITE);
                    userLabel.setAlignment(Pos.CENTER);
                    userLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                	
                    this.gridPane.add(userLabel, 0, 1);
                    this.gridPane.add(imageView, 0, 2);
                }
                
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader("./data/" + this.gameCode + ".txt"))) {
                String line;
                int rowIndex = 1;
                while ((line = br.readLine()) != null) {
                    if (!line.startsWith("DECK:") && !line.startsWith("CURRENT:")) {
                        String[] parts = line.split(",");
                        String username = parts[0];
                        
                        System.out.println("USERNAME: " + username + " - SECOND PART: " + parts[1]);
                        
                        int score = 0;
                        
                        if(parts[1].length() == 1) {
                        	score = 100;
                        	
                        	ImageView imageView = new ImageView(new Image(new File("./assets/home.png").toURI().toString()));
                            imageView.setFitWidth(120);
                            imageView.setFitHeight(40);
                            
                            imageView.setOnMouseClicked(event -> onHomeHandle.run());
                        	
                            Label userLabel = new Label(username + " WON THE MATCH!");
                            userLabel.setTextFill(Color.WHITE);
                            userLabel.setAlignment(Pos.CENTER);
                            userLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                        	
                            this.gridPane.add(userLabel, 0, rowIndex);
                            this.gridPane.add(imageView, 0, rowIndex + 1);
                            this.gridPane.setAlignment(Pos.CENTER);
                            this.gridPane.setHgap(10);
                            this.gridPane.setVgap(10);
                            this.gridPane.setPadding(new Insets(10));
                        } else {
                        	score = 100 - (parts[1].length() * 10);
                        }
                        this.playerScores.put(username, score);

                        rowIndex++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}