package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

public class InstructionScene {
	private Scene scene;
	
	private Runnable onGoBackHomeHandler;
	
	public InstructionScene() {
        ImageView homeImage = new ImageView(new Image(new File("./assets/home.png").toURI().toString()));
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
                "   - Il gioco inizia con un mazzo di carte completo.\n" +
                "   - Ogni giocatore riceve un numero uguale di carte all'inizio della partita.\n\n" +
                "2. Turno di gioco:\n" +
                "   - I giocatori giocano in senso orario o antiorario, a seconda delle preferenze.\n" +
                "   - Inizia il giocatore 1 e il turno procede in ordine numerico o nell'ordine in cui i giocatori sono seduti.\n" +
                "   - Durante il suo turno, un giocatore può fare una delle seguenti azioni:\n" +
                "     - Scartare una carta: Il giocatore può scartare una carta che abbia un valore consecutivo rispetto a quella attualmente presente sul tavolo e con un valore superiore. Ad esempio, se sul tavolo c'è una '7', il giocatore può scartare solo una '8'.\n" +
                "     - Pescare una carta: Se un giocatore non può scartare una carta valida, deve pescare una carta dal mazzo. Dopo aver pescato, il suo turno termina e passa immediatamente al giocatore successivo.\n" +
                "     - Carta Cambio Verso: Se un giocatore scarta una carta 'Cambio Verso', il senso delle carte giocabili viene invertito. Se prima poteva giocare solo una carta con valore superiore, ora può giocare solo una carta con valore inferiore rispetto a quella sul tavolo, e viceversa.\n" +
                "     - Carta Stop: Se un giocatore scarta una carta 'Stop', il prossimo avversario salta il proprio turno. Il giocatore successivo a quello che ha giocato la carta 'Stop' prende il suo turno.\n" +
                "\n3. Vittoria:\n" +
                "   - Il primo giocatore a finire tutte le sue carte è dichiarato vincitore della partita.\n\n" +
                "4. Calcolo del punteggio:\n" +
                "   - Il vincitore della partita riceve 100 punti.\n" +
                "   - Gli altri giocatori ricevono un punteggio basato sul numero di carte rimaste in mano:\n" +
                "     - Punteggio = 100 - 10 * (numero di carte rimaste in mano).\n\n" +
                "5. Fine della partita:\n" +
                "   - Il gioco termina quando un giocatore vince\n" +
                "\n6. Nuova partita:\n" +
                "   - Gli stessi giocatori possono iniziare una nuova partita o nuovi giocatori possono unirsi al tavolo per una nuova partita."
                +
                "\n\nTORNEI:\n-La modalità tornei suddivide i giocatori in coppie, facendoli scontrare uno contro l'altro in partite singole, il vincitore della partita verrà accoppiato col vincitore di un'altro girone. la finale è tra i due giocatori rimamenti e si decide in una partita singola."
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
