package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class HomeScene {
    private Scene scene;
    private EventHandler<ActionEvent> onLogout;
    
    public HomeScene() {
        StackPane homePane = new StackPane();
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(this::handleLogoutButtonClick);

        homePane.getChildren().add(logoutButton);
        scene = new Scene(homePane, 400, 300);
    }

    public void setOnLogout(EventHandler<ActionEvent> handler) {
        onLogout = handler;
    }

    public Scene getScene() {
        return scene;
    }

    private void handleLogoutButtonClick(ActionEvent event) {
        // Perform logout operations
        // Trigger the onLogout event
        if (onLogout != null) {
            onLogout.handle(event);
        }
    }
}