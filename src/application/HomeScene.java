package application;

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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HomeScene {
    private Scene scene;
    private Runnable onLogoutHandle;
    @SuppressWarnings("unused")
	private Stage primaryStage;
    
    private boolean isFirstInviaClick = true;
    private String currentFileName = "";
    private TextField usernameField;
    private TextFlow usernameList;

    public HomeScene(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> onLogoutHandle.run());

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
        Button inviaButton = new Button("Add Player");
        inviaButton.setOnAction(event -> this.handleInviaButtonClick());
        
        usernameList = new TextFlow();
        usernameList.setTextAlignment(TextAlignment.CENTER);
        usernameList.setPrefWidth(900);
        
        Button nextStepButton = new Button("Next");
        nextStepButton.setOnAction(event -> this.generateDeckAndUserCard());

        centerPane.getChildren().addAll(usernameField, inviaButton, usernameList, nextStepButton);
        rootPane.setCenter(centerPane);

        Scene scene1 = new Scene(rootPane, 900, 600);

        this.scene = scene1;
    }

    public Scene getScene() {
        return scene;
    }
    
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
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
        	boolean isEmpty = file.length() == 0;
        	
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));

            if (!isEmpty) {
                writer.newLine();
            }
            
            writer.write(username + ",");
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
    
    private void generateDeckAndUserCard() {
        ArrayList<Integer> deck = new ArrayList<>();
    	String fullPath = "./data/" + this.currentFileName;
        
        for (int number = 1; number <= 9; number++) {
            for (int count = 0; count < 4; count++) {
                deck.add(number);
            }
        }
        
        Collections.shuffle(deck);
        
        ArrayList<String> usernames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("DECK:")) {
                    usernames.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, false))) {
            for (String username : usernames) {
                writer.write(username);
                for (int i = 0; i < 5; i++) {
                    int randomIndex = random.nextInt(deck.size());
                    int card = deck.get(randomIndex);
                    writer.write(String.valueOf(card));
                    /*
                    if (i < 4) {
                        writer.write(",");
                    }
                    */
                    deck.remove(randomIndex);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Created deck: " + deck);
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));

            //writer.newLine();
            
            String fileDeck = "DECK:";
            String currentCard = "CURRENT:back";
            
            for (Integer card : deck) {
                //fileDeck += card + ",";
                fileDeck += card;
            }
            
            writer.write(fileDeck);
            writer.newLine();
            writer.write(currentCard);
            writer.close();
            
            onLogoutHandle.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}