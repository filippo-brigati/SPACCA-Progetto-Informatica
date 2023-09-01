package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;

import java.io.File;

public class LoginScene {
    private Scene scene;
    private Runnable onLoginSuccess;
    private Runnable onGameStart;
    private Runnable onTournamentLobby;
    
    private String gameCode;
    
    private Label errorLabel;
    private Label fileNotExistLabel;
    
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public LoginScene(String fileCode) {
        StackPane root = new StackPane();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        Text titleLabel = new Text("Login");
        titleLabel.setStyle("-fx-font-size: 24;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> handleLoginButtonClick(usernameField.getText(), passwordField.getText()));
        
        // Create the error label
        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        
        fileNotExistLabel = new Label();
        fileNotExistLabel.setTextFill(Color.RED);
        
        Text gameCodeLabel = new Text("Enter game code:");
        
        TextField gameCodeField = new TextField();
        Button enterGameButton = new Button("Enter");
        enterGameButton.setOnAction(event -> checkExistringMatch(gameCodeField.getText()));

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(usernameField, 0, 1);
        gridPane.add(passwordField, 0, 2);
        gridPane.add(loginButton, 0, 3);
        gridPane.add(errorLabel, 1, 3);
        
        GridPane.setMargin(gameCodeLabel, new Insets(30, 0, 0, 0));
        
        Text codeLabel = new Text("Game code: " + fileCode);
        
        if(fileCode != null) {
        	gridPane.add(codeLabel, 0, 4);
        }
        
        gridPane.add(gameCodeLabel, 0, 4);
        gridPane.add(gameCodeField, 0, 5);
        gridPane.add(enterGameButton, 1, 5);
        
        
        
        gridPane.add(fileNotExistLabel, 1, 3);

        root.getChildren().add(gridPane);

        Scene scene1 = new Scene(root, 900, 600);

        this.scene = scene1;
    }

    public void setOnLoginSuccess(Runnable handler) {
        onLoginSuccess = handler;
    }
    
    public void setOnGameStart(Runnable handler) {
        onGameStart = handler;
    }
    
    public void setOnTournamentLobby(Runnable handler) {
        onTournamentLobby = handler;
    }

    public Scene getScene() {
        return scene;
    }
    
    public String getGameCode() {
    	return this.gameCode;
    }

    private void handleLoginButtonClick(String username, String password) {
        if (onLoginSuccess != null) {
        	if(username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                onLoginSuccess.run();
        	} else {
        		System.out.println("non sei un admin");
        		errorLabel.setText("Invalid username or password!");
        	}
        }
    }
    
    private void checkExistringMatch(String fileName) {
    	File file = new File("./data/" + fileName + ".txt");
    	
    	if(file.exists()) {
    		this.gameCode = fileName;
    		
    		if(fileName.startsWith("tr-")) {
    			onTournamentLobby.run();
    		} else {
        		onGameStart.run();
    		}
    	} else {
    		System.out.println("il file" + fileName + " non esiste");
    		fileNotExistLabel.setText("Game code does not exist");
    	}
    }
}