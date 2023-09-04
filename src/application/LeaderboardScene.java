package application;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class LeaderboardScene {
    private Scene scene;
	
	public LeaderboardScene() {
        StackPane root = new StackPane();
        
        Scene scene1 = new Scene(root, 1100, 700);
        this.scene = scene1;
	}
	
    public Scene getScene() {
        return scene;
    }
}
