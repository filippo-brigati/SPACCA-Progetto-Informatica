package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LeaderboardScene {
    private Scene scene;
    
    private Runnable onLogoutHandle;
    
	public LeaderboardScene() {
		List<Map<String, String>> playerData = readPlayerDataFromFile();

        VBox playerListVBox = new VBox(20);

        ImageView home = new ImageView(new Image(new File("./assets/home.png").toURI().toString()));
        home.setFitWidth(110);
        home.setFitHeight(50);
        home.setOnMouseClicked(event -> onLogoutHandle.run());

        ImageView headerImage = new ImageView(new Image(new File("./assets/leaderboard_title.png").toURI().toString()));
        headerImage.setFitWidth(600);
        headerImage.setFitHeight(80);
        headerImage.setPreserveRatio(true);

        for (int i = 0; i < playerData.size(); i++) {
            Map<String, String> player = playerData.get(i);

            HBox playerHBox = new HBox(10);
            playerHBox.setPrefWidth(300);
            playerHBox.setSpacing(20);
            playerHBox.setAlignment(Pos.CENTER); // Align horizontally at the center

            Label playerLabel = new Label(player.get("username") + " - " + player.get("score"));
            playerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");

            ImageView imageView;
            if (i < 3) {
                imageView = new ImageView(new Image(new File("./assets/leaderboard.png").toURI().toString()));
            } else {
                imageView = new ImageView(new Image(new File("./assets/minus.png").toURI().toString()));
            }
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);

            playerHBox.getChildren().addAll(imageView, playerLabel);
            playerListVBox.getChildren().add(playerHBox);
        }

        VBox vbox = new VBox(home, headerImage, playerListVBox);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPrefWidth(1100);
        vbox.setStyle("-fx-background-color: green;");

        Scene scene1 = new Scene(vbox, 1100, 700);
        
        this.scene = scene1;
	}
	
    public Scene getScene() {
        return scene;
    }
    
    public void setOnLogoutHandle(Runnable handle) {
    	onLogoutHandle = handle;
    }
    
    private List<Map<String, String>> readPlayerDataFromFile() {
        List<Map<String, String>> playerData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("./data/leaderboard.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
 
                Map<String, String> playerInfo = new HashMap<>();
                playerInfo.put("username", parts[0].trim());
                playerInfo.put("score", parts[1].trim());

                playerData.add(playerInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(playerData);
        return playerData;
    }
}
