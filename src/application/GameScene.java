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

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameScene {
    private Scene scene;
    private Runnable onLogoutHandle;
    @SuppressWarnings("unused")
	private Stage primaryStage;
    private String gameCode;
    
    private String deckCard;
    
    private ArrayList<String> playerArray = new ArrayList<>();
    private ArrayList<Integer> playerCardArray = new ArrayList<>();
    private String gameCard;
    
    private String currentPlayer = "Loading";
    private Integer currentPlayerIndex = 0;
    
    private ImageView gameImage;
    private HBox bottomImages;
    private BorderPane rootPane;
    private Label gameInfoLabel;
    private VBox topPane;
    private Button logoutButton;

    public GameScene(Stage primaryStage, String gameCode) {
        this.primaryStage = primaryStage;
        this.gameCode = gameCode;
        this.rootPane = new BorderPane();
        
        this.gameCard = "back";
        
        
        this.setUpGame();
        
        
        this.logoutButton = new Button("Logout");
        this.logoutButton.setOnAction(event -> onLogoutHandle.run());
        
        this.gameInfoLabel = new Label("Game Code: " + this.gameCode + " | Current Player: " + this.currentPlayer);
        this.gameInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        BorderPane.setAlignment(this.gameInfoLabel, Pos.CENTER);
        BorderPane.setMargin(this.gameInfoLabel, new Insets(20, 0, 0, 0));

        this.topPane = new VBox(20);
        this.topPane.getChildren().addAll(this.logoutButton, this.gameInfoLabel);
        BorderPane.setAlignment(this.topPane, Pos.CENTER);
        this.topPane.setAlignment(Pos.CENTER);
        this.rootPane.setTop(this.topPane);

        // Center: Image
		this.gameImage = new ImageView(new Image(new File("./assets/" + this.gameCard + ".png").toURI().toString()));
		
        this.gameImage.setFitWidth(120);
        this.gameImage.setFitHeight(200);
        this.rootPane.setCenter(this.gameImage);
        
        // Bottom: Horizontal Images
        this.bottomImages = new HBox(10);
        this.bottomImages.setAlignment(Pos.CENTER);

        for (Integer playerCard : playerCardArray) {
        	System.out.println("CARD:" + playerCard);
            ImageView imageView = new ImageView(new Image(new File("./assets/" + playerCard + ".png").toURI().toString()));
            imageView.setFitWidth(120);
            imageView.setFitHeight(200);
            
            imageView.setOnMouseClicked(event -> this.onImageClick(playerCard, (ImageView) event.getSource()));
            
            this.bottomImages.getChildren().add(imageView);
        }

        BorderPane.setMargin(gameInfoLabel, new Insets(0, 0, 0, 20));
        this.rootPane.setBottom(this.bottomImages);

        Scene scene1 = new Scene(this.rootPane, 900, 600);

        this.scene = scene1;
    }

    public Scene getScene() {
        return scene;
    }
    
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
    }

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	
	private void getPlayerCard() {
		String fileName = "./data/" + this.gameCode + ".txt";
		
		this.playerCardArray.clear();
		
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].toString().equals(this.currentPlayer)) {
                	String numberPart = parts[1].trim();
                    for (char digitChar : numberPart.toCharArray()) {
                        try {
                            int digit = Integer.parseInt(String.valueOf(digitChar));
                            System.out.println("DIGIT: " +  digit);
                            this.playerCardArray.add(digit);
                        } catch (NumberFormatException e) {
                            // Parsing error here
                        	System.out.println(e);
                        }
                    }
                }
            }
            
            this.bottomImages = new HBox(10);
            this.bottomImages.setAlignment(Pos.CENTER);
            this.bottomImages.getChildren().clear();
            
            System.out.println("PLAYER CARD: "  + this.playerCardArray);
            
            for (Integer playerCard : playerCardArray) {
            	System.out.println("CARD:" + playerCard);
                ImageView imageView = new ImageView(new Image(new File("./assets/" + playerCard + ".png").toURI().toString()));
                imageView.setFitWidth(120);
                imageView.setFitHeight(200);
                
                imageView.setOnMouseClicked(event -> this.onImageClick(playerCard, (ImageView) event.getSource()));
                
                this.bottomImages.getChildren().add(imageView);
            }
            
            this.logoutButton = new Button("Logout");
            this.logoutButton.setOnAction(event -> onLogoutHandle.run());
            
            this.gameInfoLabel = new Label();
            this.gameInfoLabel = new Label("Game Code: " + this.gameCode + " | Current Player: " + this.currentPlayer);
            this.gameInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            BorderPane.setAlignment(this.gameInfoLabel, Pos.CENTER);
            BorderPane.setMargin(this.gameInfoLabel, new Insets(20, 0, 0, 0));
            
            BorderPane.setMargin(this.gameInfoLabel, new Insets(0, 0, 0, 20));
            this.rootPane.setBottom(this.bottomImages);
            
            this.topPane = new VBox(20);
            this.topPane.getChildren().addAll(this.logoutButton, this.gameInfoLabel);
            BorderPane.setAlignment(this.topPane, Pos.CENTER);
            this.topPane.setAlignment(Pos.CENTER);
            this.rootPane.setTop(this.topPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void setUpGame() {
    	String fileName = "./data/" + this.gameCode + ".txt";
    	
		try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("DECK:")) {
                    int commaIndex = line.indexOf(',');
                    if (commaIndex != -1) {
                        String extractedData = line.substring(0, commaIndex).trim();
                        this.playerArray.add(extractedData);
                        
                        System.out.println(extractedData);
                    }
                } else {
                	int doubleDotIndex = line.indexOf(':');
                	this.deckCard = line.toString().substring(doubleDotIndex+1, line.length()).trim();
                }
            }
            
            this.currentPlayer = this.playerArray.get(0);
            this.currentPlayerIndex = 0;
            
            System.out.println(this.deckCard);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.getPlayerCard();
	}
	
	private void removeCardFromFile(Integer cardToRemove) {
	    String fileName = "./data/" + this.gameCode + ".txt";
	    ArrayList<String> lines = new ArrayList<>();
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith(this.currentPlayer + ",")) {
	            	System.out.println(this.currentPlayer);
	                // Remove the card from the line
	                String[] parts = line.split(",");
	                StringBuilder updatedLine = new StringBuilder(parts[0] + ",");
	                System.out.println("PARTE 1: " + parts[1].length());
                    for(int y = 0; y < parts[1].length(); y++) {
                    	if(!(parts[1].charAt(y) == cardToRemove.toString().charAt(0))) {
                    		updatedLine.append(parts[1].charAt(y));
                    	}
                    }
                    System.out.println(updatedLine);
	                lines.add(updatedLine.toString());
	            } else {
	                lines.add(line);
	            }
	        }
	        
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // Write the modified contents back to the file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        for (String line : lines) {
	            writer.write(line);
	            writer.newLine();
	        }
	        
	        writer.close();
	        
	        if(this.currentPlayerIndex < this.playerArray.size()) {
	        	this.currentPlayerIndex = this.currentPlayerIndex + 1;
	        } else {
	        	this.currentPlayerIndex = 0;
	        }
	        
	        System.out.println(this.currentPlayerIndex + " ---- " + this.playerArray.get(this.currentPlayerIndex));
	        this.currentPlayer = this.playerArray.get(this.currentPlayerIndex);
	        this.getPlayerCard();
	        
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void onImageClick(Integer card, ImageView imageView) {
		String fileName = "./data/" + this.gameCode + ".txt";
		
		Integer valid = 0;
		if(this.gameCard != "back") {
			valid = Integer.parseInt(this.gameCard) + 1;
		}
		if(this.gameCard == "9") { valid = 1; }
		System.out.println(card + " - IS EQUAL - " + valid);
		if(this.gameCard.equals("back") || card == valid) {
			this.gameCard = card.toString();
			
			this.gameImage.setImage(new Image(new File("./assets/" + this.gameCard + ".png").toURI().toString()));
			//this.bottomImages.getChildren().removeIf(node -> node instanceof ImageView && node.getId().equals(card.toString()));
			
			this.bottomImages.getChildren().clear();
			
			this.playerCardArray.remove(card);
	        for (Integer playerCard : playerCardArray) {
	        	System.out.println("CARD:" + playerCard);
	            ImageView newImageView = new ImageView(new Image(new File("./assets/" + playerCard + ".png").toURI().toString()));
	            newImageView.setFitWidth(120);
	            newImageView.setFitHeight(200);
	            
	            newImageView.setOnMouseClicked(event -> this.onImageClick(playerCard, (ImageView) event.getSource()));
	            
	            this.bottomImages.getChildren().add(newImageView);
	        }
	        
	        this.removeCardFromFile(card);
		} else {
        	ArrayList<String> updatedLines = new ArrayList<>();
        	Integer deckNumber = 0;
        	
	        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("DECK:")) {
	                    String deckRow = line.substring(5);
	                    deckNumber = Integer.parseInt(deckRow.substring(0, 1));
	                    
	                    String newDeck = deckRow.substring(1, deckRow.length());
	                    
	                    updatedLines.add("DECK:" + newDeck);
	                    
	                    this.playerCardArray.add(deckNumber);
	                    ImageView newImageView = new ImageView(new Image(new File("./assets/" + deckNumber + ".png").toURI().toString()));
	    	            newImageView.setFitWidth(120);
	    	            newImageView.setFitHeight(200);
	                    
	                    this.bottomImages.getChildren().add(newImageView);
	                } else {
	                	updatedLines.add(line);
	                }
	            }
	            
	            for (int i = 0; i < updatedLines.size(); i++) {
	            	String l = updatedLines.get(i);
                	if(l.startsWith(this.currentPlayer + ",")) {
                		l = l + deckNumber.toString();
                		updatedLines.set(i, l);
                	}
                }
	        } catch (IOException | NumberFormatException e) {
	            e.printStackTrace();
	        }
	        
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
	            for (String updatedLine : updatedLines) {
	                writer.write(updatedLine);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
}
