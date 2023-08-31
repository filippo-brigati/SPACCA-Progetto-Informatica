package application;

import javafx.scene.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminScene {
    private Runnable onLogoutHandle;
    private Runnable onCreateGameHandle;
	
    private Scene scene;
    
    private boolean isTournament;
	
	public AdminScene() {
        BorderPane root = new BorderPane();
		
        Scene scene1 = new Scene(root, 900, 600);
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> onLogoutHandle.run());
        
        HBox topLeftBox = new HBox(logoutButton);
        topLeftBox.setPadding(new Insets(10));
        root.setLeft(topLeftBox);

        Button createTournamentButton = new Button("Create Tournament");
        createTournamentButton.setOnAction(event -> createGame(true));
        Button createSimpleMatchButton = new Button("Create Simple Match");
        createSimpleMatchButton.setOnAction(event -> createGame(false));
        HBox topRightBox = new HBox(createTournamentButton, createSimpleMatchButton);
        
        
        topRightBox.setSpacing(10);
        topRightBox.setPadding(new Insets(10));
        topRightBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        root.setRight(topRightBox);

        Label registeredMatchesLabel = new Label("Registered Matches");
        ListView<String> matchesListView = new ListView<>();
        ObservableList<String> matchesList = FXCollections.observableArrayList();
        matchesListView.setItems(matchesList);
        VBox centerBox = new VBox(registeredMatchesLabel, matchesListView);
        centerBox.setSpacing(10);
        centerBox.setPadding(new Insets(10));
        root.setCenter(centerBox);

        this.scene = scene1;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public boolean getIsTournament() {
		return this.isTournament;
	}
	
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
    }
    
    public void setOnCreateGameHandle(Runnable handler) {
    	onCreateGameHandle = handler;
    }
    
    public void createGame(boolean isTournament) {
    	this.isTournament = isTournament;
    	
    	onCreateGameHandle.run();
    	
    }
}
