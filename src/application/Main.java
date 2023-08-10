package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
    	HomeScene home = new HomeScene(primaryStage);
    	home.setOnLogoutHandle(() -> switchToLoginView());
    	
    	primaryStage.setTitle("HOME - SPACCA");
    	primaryStage.setScene(home.getScene());

        // Set the home view as the root of the scene
        primaryStage.setTitle("HOME - SPACCA");
        primaryStage.setScene(home.getScene());
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



