package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

public class InstructionScene {
	private Scene scene;
	
	private Runnable onGoBackHomeHandler;
	
	public InstructionScene() {
        ImageView homeImage = new ImageView(new Image(getClass().getClassLoader().getResource("home.png").toExternalForm()));
        homeImage.setFitWidth(90);
        homeImage.setFitHeight(50);
        
		homeImage.setOnMouseClicked(event -> onGoBackHomeHandler.run());
        
        HBox hbox = new HBox(20);
        
        hbox.getChildren().add(homeImage);
        hbox.setAlignment(Pos.CENTER);
        
        HBox.setHgrow(homeImage, Priority.ALWAYS);
        
        VBox vbox = new VBox(20, hbox);
        vbox.setAlignment(Pos.CENTER_RIGHT);
        vbox.setStyle("-fx-background-color: green;");
		
		TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(
        		"Nome del gioco: SPACCA\n\n" +

                "Numero di giocatori: Da 2 a N giocatori (umani o robot)\n\n" +

                "Scopo del gioco: Il giocatore che finisce per primo tutte le sue carte vince la partita.\n\n" +

                "Punteggio:\n" +

                "- Il vincitore riceve 100 punti.\n" +

                "- Gli altri giocatori ricevono un punteggio calcolato come segue: 100 - 10 * (numero di carte che hanno in mano) punti.\n\n" +

                "Regole del gioco:\n" +

                "1. Inizio del gioco:\n" +

                "   - Il gioco inizia il mazzo contiene carte dal valore numerico da 1->7 + due carte speciali.\n" +

                "   - Ogni giocatore riceve un numero uguale di carte all'inizio della partita.\n\n" +

                "2. Turno di gioco:\n" +

                "   - I giocatori giocano con l’ordine inserito dall’admin.\n" +

                "   - La partita viene giocata un turno per volta alternando i giocatori.\n" +

                "   - Durante il suo turno, un giocatore può fare una delle seguenti azioni:\n" +

                "   - Scartare una carta: Il giocatore può scartare una carta che abbia un valore consecutivo rispetto a quella attualmente presente sul tavolo e con un valore superiore. \n     Ad esempio, se sul tavolo c'è una '3', il giocatore può scartare solo una '4'.\n" +

                "   - Pescare una carta: Se un giocatore non può scartare una carta valida, deve pescare una carta dal mazzo, cliccando su una carta a caso tra quelle che ha in mano. \n     Dopo aver pescato, il suo turno termina e passa immediatamente al giocatore successivo.\n" +

                "   - Carta Jolly: la carta ‘Jolly’ è una carta giocabile ogni turno e può assumere un qualsiasi valore numerico.\n" +

                "   - Carta Stop: Se un giocatore scarta una carta 'Stop', il prossimo avversario salta il proprio turno.\n" +

                "\n"+

                "   - Il primo giocatore a finire tutte le sue carte è dichiarato vincitore della partita.\n\n" +

                

                "3. Fine della partita:\n" +

                "   - Il gioco termina quando un giocatore vince\n" +

                "   -Vengono assegnati i punteggi e viene aggiornata la Leaderboard."

                +

                "\n\nTORNEI:\n-La modalità tornei suddivide i giocatori in coppie, facendoli scontrare uno contro l'altro in partite singole, il vincitore della partita verrà accoppiato col vincitore di un’altra partita. La finale è tra i due giocatori rimanenti e si decide in una partita singola.\n\n"

                +"ADMIN:\n"

                + "L’admin è in grado di creare partite e tornei, aggiungere giocatori e bot alle partite, modificare il nome ed il punteggio di un utente, eliminare le partite salvate. L’ordine con cui l’admin aggiunge i giocatori alle partite sarà l’ordine con cui i giocatori si troveranno a giocare in partita. \n"
        );
        textArea.setStyle("-fx-control-inner-background: green;");
 
        BorderPane borderPane = new BorderPane();
        
        borderPane.setTop(vbox);
        borderPane.setCenter(textArea);
        Scene scene1 = new Scene(borderPane, 1100, 700);

        this.scene = scene1;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public void setOnGoBackHomeHandler(Runnable handler) {
		onGoBackHomeHandler = handler;
	}
}
