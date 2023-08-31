package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GameSetupScene {
    private Scene scene;
    private Runnable onLogoutHandle;
    
    private boolean isTournament;
    
    private boolean isFirstInviaClick = true;
    private String currentFileName = "";
    private String title = "";
	
	public GameSetupScene(boolean isTournament) {
		this.isTournament = isTournament;
		this.interfaceSetup();
		
        // Create UI components
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> onLogoutHandle.run());
        
        Label playerInputLabel = new Label("Enter Player Name:");
        TextField playerInputField = new TextField();
        Button addButton = new Button("Add Player");
        ListView<String> playerListView = new ListView<>();
        
        addButton.setOnAction(e -> addPlayer(playerInputField, playerListView));
        
        Button doneButton = new Button("Done");
        doneButton.setOnAction(event -> this.generateDeckAndUserCard());

        VBox layout = new VBox(20, titleLabel, logoutButton, playerInputLabel, playerInputField, addButton, playerListView, doneButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.scene = new Scene(layout, 900, 600);
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public String getFileName() {
		return this.currentFileName.substring(0, this.currentFileName.length() - 4);
	}
	
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
    }
    
    public void interfaceSetup() {
    	if(this.isTournament == false) {
    		title = "CREATE SIMPLE MATCH";
    	} else {
    		title = "CREATE TOURNAMENT";
    	}
    }
	
    private void addPlayer(TextField playerInputField, ListView<String> playerListView) {
        String playerName = playerInputField.getText();
        if (!playerName.isEmpty()) {
            playerListView.getItems().add(playerName);
            playerInputField.clear();
        }
        
        if (isFirstInviaClick) {
            currentFileName = generateRandomFileName();
            isFirstInviaClick = false;
        }
        
        String directoryPath = "./data/";
        String fullPath = "";
        String randomFilename = "";

        if(this.isFirstInviaClick) {
            randomFilename = generateRandomFileName();

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
            
            writer.write(playerName + ",");
            writer.close();
            
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
