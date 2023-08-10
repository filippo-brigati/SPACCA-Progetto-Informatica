package application;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameScene {
    private Scene scene;
    private Runnable onLogoutHandle;
    @SuppressWarnings("unused")
	private Stage primaryStage;

    public GameScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        BorderPane rootPane = new BorderPane();
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> onLogoutHandle.run());
        
        Label gameInfoLabel = new Label("Game Code: ABC123 | Current Player: John");
        gameInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        BorderPane.setAlignment(gameInfoLabel, Pos.CENTER);
        BorderPane.setMargin(gameInfoLabel, new Insets(20, 0, 0, 0));

        VBox topPane = new VBox(20);
        topPane.getChildren().addAll(logoutButton, gameInfoLabel);
        BorderPane.setAlignment(topPane, Pos.CENTER);
        topPane.setAlignment(Pos.CENTER);
        rootPane.setTop(topPane);

        // Center: Image
        ImageView gameImage;
		gameImage = new ImageView(new Image(new File("./assets/back.png").toURI().toString()));
		
        gameImage.setFitWidth(120);
        gameImage.setFitHeight(200);
        rootPane.setCenter(gameImage);
        
        // Bottom: Horizontal Images
        HBox bottomImages = new HBox(10);
        ImageView image1 = new ImageView(new Image(new File("./assets/1.png").toURI().toString()));
        image1.setFitWidth(120);
        image1.setFitHeight(200);
        ImageView image2 = new ImageView(new Image(new File("./assets/2.png").toURI().toString()));
        image2.setFitWidth(120);
        image2.setFitHeight(200);
        ImageView image3 = new ImageView(new Image(new File("./assets/3.png").toURI().toString()));
        image3.setFitWidth(120);
        image3.setFitHeight(200);
        bottomImages.getChildren().addAll(image1, image2, image3);
        bottomImages.setAlignment(Pos.CENTER);
        BorderPane.setMargin(gameInfoLabel, new Insets(0, 0, 0, 20));
        rootPane.setBottom(bottomImages);

        Scene scene1 = new Scene(rootPane, 900, 600);

        this.scene = scene1;
    }

    public Scene getScene() {
        return scene;
    }
    
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
    }
}
