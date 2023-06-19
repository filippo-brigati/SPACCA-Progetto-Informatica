package application;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class LoginScene {
    private Scene scene;
    private Runnable onLoginSuccess;
    @SuppressWarnings("unused")
	private Stage primaryStage;
    
    private Label errorLabel;
    
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public LoginScene(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Group group1 = new Group();

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

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(usernameField, 0, 1);
        gridPane.add(passwordField, 0, 2);
        gridPane.add(loginButton, 0, 3);
        gridPane.add(errorLabel, 1, 3);

        group1.getChildren().add(gridPane);

        Scene scene1 = new Scene(group1, 900, 600);

        this.scene = scene1;
    }

    public void setOnLoginSuccess(Runnable handler) {
        onLoginSuccess = handler;
    }

    public Scene getScene() {
        return scene;
    }

    private void handleLoginButtonClick(String username, String password) {
        // Perform login validation
        // If login is successful, trigger the onLoginSuccess event
        if (onLoginSuccess != null) {
        	if(username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                onLoginSuccess.run();
        	} else {
        		System.out.println("non sei un admin");
        		errorLabel.setText("Invalid username or password!");
        	}
        }
    }
}



