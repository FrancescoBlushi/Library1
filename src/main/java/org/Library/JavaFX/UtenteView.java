package org.Library.JavaFX;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.Library.Controller.UtenteController;
import org.Library.DaoModels.*;
import org.Library.Main;
import org.Library.models.Books;
import org.Library.models.Loans;
import org.Library.models.Users;

import java.time.LocalDate;

//  View dopo il Log in

public class UtenteView {

    private Stage primaryStage;

    public UtenteView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public Scene createStage(String username, String cartId) {

        // Layout centrale per visualizzare i libri
        FirstView firstView = new FirstView();
        TableView<Books> tableView = firstView.createBooksTableView();

        // Creazione del layout per la ricerca dei libri, in mezzo alla scena
        VBox searchPane = createSearchPane(tableView, cartId);

        //A destra della scena
        VBox vbox = new VBox(searchPane);

        // Layout principale che contiene le tre sezioni
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(createVbox(username));
        mainLayout.setCenter(tableView);
        mainLayout.setRight(vbox);

        Scene loginScene = new Scene(mainLayout, 1400, 600);
        primaryStage.setTitle("Biblioteca");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        return loginScene;
    }

    public VBox createVbox(String username) {
        UtenteDao utenteDao = new UtenteDao();
        Users users = utenteDao.usersCall(username);
        String name = users.getName();
        String lastname = users.getLastname();
        String cartId = users.getCartId();

        Label benvenutoLabel = new Label("Benvenuto nel tuo profilo : ");
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold");

        Label lastNameLabel = new Label(lastname);
        lastNameLabel.setStyle("-fx-font-weight: bold");

        Button cronologiaButton = new Button("Libri in Prestito");
        cronologiaButton.setStyle("-fx-background-color: #56b656; -fx-text-fill: white");

        //Cronologia Libri Action
        cronologiaButton.setOnAction(event -> {
            Scene scene = createStage(username, cartId);
            LoanView loanView = new LoanView(primaryStage, scene);
            loanView.createLoan(cartId);

        });

        Button esciButton = new Button("Esci");
        esciButton.setStyle("-fx-background-color: #a63845; -fx-text-fill: white;");

        //Esci Button Action
        esciButton.setOnAction(event -> {
            Main main = new Main();
            main.start(primaryStage);
        });

        //Spazio necessario
        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);

        //Vbox che serve per
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(10);

        vbox.getChildren().addAll(benvenutoLabel, nameLabel, lastNameLabel, space, cronologiaButton, esciButton);

        return vbox;
    }

    public VBox createSearchPane(TableView<Books> tableView, String cartId) {
        VBox searchPane = new VBox(10);
        searchPane.setPadding(new Insets(20));

        Label durationLabel = new Label("Seleziona la durata del prestito");
        durationLabel.setStyle("-fx-font-weight: bold;");

        Button unMeseButton = new Button("Un mese");

        Button dueMeseButton = new Button("Due mesi");

        Button treMeseButton = new Button("Tre mesi");

        final int[] durataScelta = {0};

        unMeseButton.setOnAction(event -> durataScelta[0] = 1);
        dueMeseButton.setOnAction(event -> durataScelta[0] = 2);
        treMeseButton.setOnAction(event -> durataScelta[0] = 3);

        Label searchLabel = new Label("Cerca libro:");
        TextField searchField = new TextField();
        searchField.setId("searchField");
        searchField.setPromptText("Inserisci il titolo del libro");

        Button searchButton = new Button("Cerca");
        searchButton.setId("searchButton");

        Button prendiInPrestito = new Button("Prendi in Prestito");
        prendiInPrestito.setStyle("-fx-background-color: #9c922f; -fx-text-fill: white");

        //Search Button Action
        searchButton.setOnAction(event -> {
            String search = searchField.getText().trim();
            FirstView.booksView(search, tableView);

        });


        // Prendi in Prestito Button
        Label messageLabel = new Label();
        prendiInPrestito.setOnAction(event1 -> {
            String Field = searchField.getText();
            LocalDate currentDate = LocalDate.now();
            LocalDate duelDate = currentDate.plusMonths(durataScelta[0]);
            UtenteController utenteController = new UtenteController();
            utenteController.prendiPrestito(Field, cartId, currentDate, duelDate,messageLabel);

        });

        Button restituisciButton = new Button("Restituisci Libro");
        TextField restituisciField = new TextField();
        restituisciField.setPromptText("Inserisci iban del Libro");
        Label messageRestituisci = new Label();

        restituisciButton.setOnAction(event -> {
            String isbn = restituisciField.getText().trim();
            UtenteController utenteController = new UtenteController();

            Boolean find = utenteController.restituisciLibro(isbn, cartId);
            if(find) {
                MessageView.mostraMessaggio(messageRestituisci,"Restituzione andata a buon fine","-fx-background-color: #26ff00; -fx-text-fill: white");
            }else{
                MessageView.mostraMessaggio(messageRestituisci,"Libro non trovato","-fx-background-color: #ff1500; -fx-text-fill: white");
            }


        });

        searchPane.getChildren().addAll(searchLabel, searchField, searchButton, unMeseButton, dueMeseButton, treMeseButton, prendiInPrestito, messageLabel,restituisciButton,restituisciField,messageRestituisci);

        return searchPane;
    }














}