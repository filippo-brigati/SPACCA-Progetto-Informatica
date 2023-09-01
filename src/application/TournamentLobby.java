package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TournamentLobby {
    private Scene scene;
    private String gameCode;
    
    private Runnable onLogoutHandle;

    public TournamentLobby(String gameCode) {
        this.gameCode = gameCode;

        // Create a VBox to hold the elements vertically
        VBox root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        
		Button logoutButton = new Button("Logout");
		logoutButton.setOnAction(event -> onLogoutHandle.run());

        // Create a label for the title
        Label titleLabel = new Label("Tournament: " + this.gameCode);
        titleLabel.setStyle("-fx-font-size: 24px;");

        // Create a ListView for the list of strings
        ObservableList<String> matchList = readMatchesFromFile("./data/" + this.gameCode + ".txt");
        
        ListView<String> listView = new ListView<>(matchList);

        // Set a custom cell factory for the ListView
        listView.setCellFactory(param -> createMatchCell());

        // Add elements to the root VBox
        root.getChildren().addAll(logoutButton, titleLabel, listView);
        
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        VBox.setVgrow(listView, Priority.ALWAYS);
        listView.setMaxWidth(Double.MAX_VALUE);

        // Create a scene
        this.scene = new Scene(root, 900, 600);
    }

    public Scene getScene() {
        return this.scene;
    }
    
    public void setOnLogoutHandle(Runnable handler) {
        onLogoutHandle = handler;
    }
    
    private ObservableList<String> readMatchesFromFile(String filePath) {
        ObservableList<String> matchList = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] players = line.split(",");
                if (players.length == 2) {
                    String match = players[0] + " vs " + players[1];
                    matchList.add(match);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchList;
    }

    // Create a custom cell for the ListView
    private ListCell<String> createMatchCell() {
        return new ListCell<>() {
            private final Button playButton = new Button("Play");

            {
                playButton.setOnAction(event -> {
                    // Add your play button action here
                    String match = getItem();
                    System.out.println("Playing match: " + match);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(playButton);
                }
            }
        };
    }
}