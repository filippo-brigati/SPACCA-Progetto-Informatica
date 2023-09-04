package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the initial view
        createLoginView(null);

        primaryStage.setTitle("SPACCA");
        primaryStage.show();
    }

    // Method to create the login view
    private void createLoginView(String fileCode) {
        LoginScene login = new LoginScene(fileCode);
        login.setOnLoginSuccess(() -> switchToHomeView());
        login.setOnGameStart(() -> switchToGameView(login.getGameCode()));
        login.setOnTournamentLobby(() -> switchToTournamentLobbyView(login.getGameCode()));

        // Set the login view as the root of the scene
        primaryStage.setTitle("LOGIN - SPACCA");
        primaryStage.setScene(login.getScene());
    }

    // Method to create the adminScene view
    private void createHomeView() {
    	AdminScene adminScene = new AdminScene();
    	adminScene.setOnLogoutHandle(() -> switchToLoginView(null));
    	adminScene.setOnCreateGameHandle(() -> switchToGameSetupView(adminScene.getIsTournament()));
    	
    	primaryStage.setTitle("ADMIN - SPACCA");
    	primaryStage.setScene(adminScene.getScene());
    }
    
    private void createGameView(String gameCode) {
    	GameScene game = new GameScene(primaryStage, gameCode);
    	game.setOnLogoutHandle(() -> switchToLoginView(null));
    	game.setOnGameWinHandle(() -> switchToWinView(gameCode));
    	
    	primaryStage.setTitle("GAME - SPACCA");
    	primaryStage.setScene(game.getScene());
    }
    
    private void createWinView(String gameCode) {
    	WinnerScene winner = new WinnerScene(gameCode);
    	winner.setOnBackHandle(() -> switchToTournamentLobbyView(winner.getMainFilePath()));
    	winner.setOnHomeHandle(() -> switchToLoginView(null));
    	
    	primaryStage.setTitle("GAME WINNER - SPACCA");
    	primaryStage.setScene(winner.getScene());
    }
    
    private void createGameSetupView(boolean isTournament) {
    	GameSetupScene gameSetup = new GameSetupScene(isTournament);
    	gameSetup.setOnLogoutHandle(() -> switchToLoginView(gameSetup.getFileName()));
    	
    	String title = "";
    	if(isTournament == false) { title = "SIMPLE MATCH SETUP - SPACCA"; }
    	else { title = "TOURNAMENT SETUP - SPACCA"; }
    	
    	primaryStage.setTitle(title);
    	primaryStage.setScene(gameSetup.getScene());
    }
    
    private void createTournamentLobbyView(String gameCode) {
    	TournamentLobby trLobby = new TournamentLobby(gameCode);
    	trLobby.setOnLogoutHandle(() -> switchToLoginView(null));
    	trLobby.setOnStartGameHandle(() -> switchToGameView(trLobby.getFullChildPath()));
    	
    	primaryStage.setTitle("TOURNAMENT " + gameCode + " - SPACCA");
    	primaryStage.setScene(trLobby.getScene());
    }

    // Method to switch to the home view
    private void switchToHomeView() {
        createHomeView();
    }

    // Method to switch to the login view
    private void switchToLoginView(String fileCode) {
        createLoginView(fileCode);
    }
    
    private void switchToGameView(String gameCode) {
    	createGameView(gameCode);
    }
    
    private void switchToWinView(String gameCode) {
    	createWinView(gameCode);
    }
    
    private void switchToGameSetupView(boolean isTournament) {
    	createGameSetupView(isTournament);
    }
    
    private void switchToTournamentLobbyView(String gameCode) {
    	createTournamentLobbyView(gameCode);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
