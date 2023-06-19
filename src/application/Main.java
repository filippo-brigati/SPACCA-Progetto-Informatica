package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.layout.StackPane;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the initial view
        createLoginView();

        primaryStage.setTitle("SPACCA");
        primaryStage.show();
    }

    // Method to create the login view
    private void createLoginView() {
        LoginScene login = new LoginScene(primaryStage);
        login.setOnLoginSuccess(() -> switchToHomeView());

        // Set the login view as the root of the scene
        primaryStage.setTitle("LOGIN - SPACCA");
        primaryStage.setScene(login.getScene());
    }

    // Method to create the home view
    private void createHomeView() {
        StackPane homePane = new StackPane();
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> switchToLoginView());

        homePane.getChildren().add(logoutButton);

        // Set the home view as the root of the scene
        primaryStage.setTitle("HOME - SPACCA");
        primaryStage.setScene(new Scene(homePane, 900, 600));
    }

    // Method to switch to the home view
    private void switchToHomeView() {
        createHomeView();
    }

    // Method to switch to the login view
    private void switchToLoginView() {
        createLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



