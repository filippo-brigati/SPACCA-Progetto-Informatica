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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameSetupScene {
    private Scene scene;
    private Runnable onLogoutHandle;
    
    private boolean isTournament;
    
    private boolean isFirstInviaClick = true;
    
    private String currentFileName = null;
    private String title = "";
    private Integer playerForRow = 0;
	
	public GameSetupScene(boolean isTournament) {
		this.isTournament = isTournament;
		this.interfaceSetup();
		
        // Create UI components
        //Label titleLabel = new Label(title);
        //titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        ImageView titleLabel = new ImageView(new Image(getClass().getClassLoader().getResource(this.title).toExternalForm()));
        titleLabel.setFitWidth(290);
        titleLabel.setFitHeight(40);
        
        //Button logoutButton = new Button("Logout");
        //logoutButton.setOnAction(event -> onLogoutHandle.run());
        
        ImageView logoutButton = new ImageView(new Image(getClass().getClassLoader().getResource("logout.png").toExternalForm()));
        logoutButton.setFitWidth(90);
        logoutButton.setFitHeight(50);
        
        logoutButton.setOnMouseClicked(event -> onLogoutHandle.run());
        
        //Label playerInputLabel = new Label("Enter Player Name:");
        
        ImageView playerInputLabel = new ImageView(new Image(getClass().getClassLoader().getResource("enter_player_name.png").toExternalForm()));
        playerInputLabel.setFitWidth(280);
        playerInputLabel.setFitHeight(30);        
        
        TextField playerInputField = new TextField();
        
        ListView<String> playerListView = new ListView<>();
        
        //Button addButton = new Button("Add Player");
        //addButton.setOnAction(e -> addPlayer(playerInputField, playerListView));
        
        ImageView addButton = new ImageView(new Image(getClass().getClassLoader().getResource("add_player.png").toExternalForm()));
        addButton.setFitWidth(160);
        addButton.setFitHeight(50);
        
        addButton.setOnMouseClicked(e -> addPlayer(playerInputField, playerListView));
        
        //Button doneButton = new Button("Done");
        //doneButton.setOnAction(event -> this.generateDeckAndUserCard());
        
        ImageView doneButton = new ImageView(new Image(getClass().getClassLoader().getResource("create_game.png").toExternalForm()));
        doneButton.setFitWidth(160);
        doneButton.setFitHeight(50); 
        
        doneButton.setOnMouseClicked(event -> this.generateDeckAndUserCard());

        VBox layout = new VBox(20, titleLabel, logoutButton, playerInputLabel, playerInputField, addButton, playerListView, doneButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: green;");

        this.scene = new Scene(layout, 1100, 700);
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public String getFileName() {
		if(this.currentFileName != null) {
			return this.currentFileName.substring(0, this.currentFileName.length() - 4);	
		} else {
			return null;
		}
	}
	
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
    }
    
    public void interfaceSetup() {
    	if(this.isTournament == false) {
    		title = "create_single_game_white.png";
    	} else {
    		title = "create_tournament_white.png";
    	}
    }
	
    private void addPlayer(TextField playerInputField, ListView<String> playerListView) {
        String playerName = playerInputField.getText();
        if (!playerName.isEmpty()) {
            playerListView.getItems().add(playerName);
            playerInputField.clear();
        }
        
        if (isFirstInviaClick) {
        	if(this.isTournament == false) {
                this.currentFileName = generateRandomFileName();
                isFirstInviaClick = false;	
        	} else {
        		this.currentFileName = "tr-" + generateRandomFileName();
        		isFirstInviaClick = false;
        	}
        }
        
        String directoryPath = "./data/";
        String fullPath = "";

        fullPath = directoryPath + this.currentFileName;
        
        System.out.println("FILE PATH: " + fullPath);
        File file = new File(fullPath);
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));
            
        	if(this.isTournament == false) {
            	boolean isEmpty = file.length() == 0;

                if (!isEmpty) {
                    writer.newLine();
                }
                
                writer.write(playerName + ",");	
        	} else {
        		if(this.playerForRow % 2 == 0 && this.playerForRow != 0) {
        			writer.newLine();
        			writer.write(playerName + ",");
        		} else {
        			if(this.playerForRow == 0) { writer.write(playerName + ","); }
        			else { writer.write(playerName); }
        		}
        		
        		this.playerForRow = this.playerForRow + 1;
        	}
            writer.close();
            
            System.out.println("File create or edited: " + fullPath);
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
    	if(this.isTournament == false) {
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
                
                String fileDeck = "DECK:";
                String currentCard = "CURRENT:back";
                
                for (Integer card : deck) {
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
    	} else {
    		if(this.playerForRow % 2 == 0) {
        		onLogoutHandle.run();	
    		} else {
    			System.out.println("You have to add another player!");
    		}
    	}
    }
}
