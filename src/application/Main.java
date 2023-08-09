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
    private boolean isFirstInviaClick = true;
    private String currentFileName = "";
    private TextField usernameField;
    private TextFlow usernameList;

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
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> switchToLoginView());

        BorderPane rootPane = new BorderPane();

        StackPane topRightPane = new StackPane(logoutButton);
        BorderPane.setAlignment(topRightPane, Pos.TOP_RIGHT);
        rootPane.setTop(topRightPane);

        // Create an HBox for center alignment
        VBox centerPane = new VBox(10);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setMaxWidth(500);

        // Username input with 50% width
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(0.5 * 900); // 50% of screen width

        // "Invia" button
        Button inviaButton = new Button("Invia");
        inviaButton.setOnAction(event -> this.handleInviaButtonClick());
        
        usernameList = new TextFlow();
        usernameList.setTextAlignment(TextAlignment.CENTER);
        usernameList.setPrefWidth(900);

        centerPane.getChildren().addAll(usernameField, inviaButton, usernameList);
        rootPane.setCenter(centerPane);

        // Set the home view as the root of the scene
        primaryStage.setTitle("HOME - SPACCA");
        primaryStage.setScene(new Scene(rootPane, 900, 600));
    }

    // Method to switch to the home view
    private void switchToHomeView() {
        createHomeView();
    }

    // Method to switch to the login view
    private void switchToLoginView() {
        createLoginView();
    }
    
    private void handleInviaButtonClick() {
        String username = usernameField.getText();
        if (isFirstInviaClick) {
            currentFileName = generateRandomFileName();
            isFirstInviaClick = false;
        }
        
        // Specify the directory where you want to create the file
        String directoryPath = "./data/";
        String fullPath = "";
        String randomFilename = "";

        if(this.isFirstInviaClick) {
            // Generate a random filename with 9 lowercase letters
            randomFilename = generateRandomFileName();

            // Construct the full path of the file
            fullPath = directoryPath + randomFilename;  
            this.currentFileName = randomFilename;
        } else {
        	fullPath = directoryPath + this.currentFileName;
        }
        
        System.out.println("FILE PATH: " + fullPath);
        File file = new File(fullPath);
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));

            if (file.exists()) {
                writer.newLine();
            }

            writer.write(username);
            writer.close();
            
            updateUsernameList(username);
            
            System.out.println("File created: " + fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateRandomFileName() {
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder fileName = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int randomIndex = random.nextInt(characters.length());
            fileName.append(characters.charAt(randomIndex));
        }
        return fileName.toString() + ".txt";
    }
    
    private void updateUsernameList(String newUsername) {
        if (!newUsername.isEmpty()) {
            Text usernameText = new Text(newUsername + ", ");
            usernameList.getChildren().add(usernameText);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



