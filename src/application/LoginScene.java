package application;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.io.File;

public class LoginScene {
    private Scene scene;
    private Runnable onLoginSuccess;
    private Runnable onGameStart;
    private Runnable onTournamentLobby;
    
    private String gameCode;
    
    private ImageView errorLabel;
    private ImageView fileNotExistLabel;
    
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public LoginScene(String fileCode) {
        StackPane root = new StackPane();
        
        ImageView imageView = new ImageView(new Image(new File("./assets/logo.png").toURI().toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(300);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        
        gridPane.setMinWidth(600);
        gridPane.setPrefWidth(600);
        gridPane.setMaxWidth(600);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        ImageView loginImage = new ImageView(new Image(new File("./assets/login.png").toURI().toString()));
        loginImage.setFitWidth(100);
        loginImage.setFitHeight(50);
        
        loginImage.setOnMouseClicked(event -> handleLoginButtonClick(usernameField.getText(), passwordField.getText()));
        
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefWidth(600);
        
        hbox.getChildren().addAll(usernameField, passwordField);
        
        /*
        this.errorLabel = new ImageView(new Image(new File("").toURI().toString()));
        this.errorLabel.setFitWidth(100);
        this.errorLabel.setFitHeight(50);
        
        this.fileNotExistLabel = new ImageView(new Image(new File("").toURI().toString()));
        this.fileNotExistLabel.setFitWidth(100);
        this.fileNotExistLabel.setFitHeight(50);
        */
        
        this.errorLabel = new ImageView();
        this.fileNotExistLabel = new ImageView();
        
        TextField gameCodeField = new TextField();
        gameCodeField.setPrefWidth(600);
        
        //Button enterGameButton = new Button("");
        ImageView buttonImage = new ImageView(new Image(new File("./assets/play.png").toURI().toString()));
        buttonImage.setFitWidth(100);
        buttonImage.setFitHeight(50);
        
        buttonImage.setOnMouseClicked(event -> checkExistringMatch(gameCodeField.getText()));
        
        //enterGameButton.setGraphic(buttonImage);
        //enterGameButton.setOnAction(event -> checkExistringMatch(gameCodeField.getText()));
        
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(imageView, gameCodeField, buttonImage, hbox, loginImage, this.errorLabel, this.fileNotExistLabel);
        
        gridPane.add(vbox, 0, 0);
        
        Text codeLabel = new Text("Game code: " + fileCode);
        
        if(fileCode != null) {
        	gridPane.add(codeLabel, 0, 4);
        }
        
        gridPane.add(fileNotExistLabel, 1, 3);

        root.getChildren().add(gridPane);
        root.setStyle("-fx-background-color: green;");

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
        		
                this.errorLabel = new ImageView(new Image(new File("./assets/wrong_credential.png").toURI().toString()));
                this.errorLabel.setFitWidth(100);
                this.errorLabel.setFitHeight(50);
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
    		
            this.fileNotExistLabel = new ImageView(new Image(new File("./assets/game_not_found.png").toURI().toString()));
            this.fileNotExistLabel.setFitWidth(100);
            this.fileNotExistLabel.setFitHeight(50);
    	}
    }
}