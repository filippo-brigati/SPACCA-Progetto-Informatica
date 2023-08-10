package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

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
        login.setOnGameStart(() -> switchToGameView());

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
    }
    
    private void createGameView() {
    	GameScene game = new GameScene(primaryStage);
    	game.setOnLogoutHandle(() -> switchToLoginView());
    	
    	primaryStage.setTitle("GAME - SPACCA");
    	primaryStage.setScene(game.getScene());
    }

    // Method to switch to the home view
    private void switchToHomeView() {
        createHomeView();
    }

    // Method to switch to the login view
    private void switchToLoginView() {
        createLoginView();
    }
    
    private void switchToGameView() {
    	createGameView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



