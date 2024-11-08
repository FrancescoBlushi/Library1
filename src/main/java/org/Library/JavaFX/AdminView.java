package org.Library.JavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Scene;
import org.Library.Controller.AdminController;
import org.Library.DaoModels.*;
import org.Library.Main;

import org.Library.Pattern.Observer;
import org.Library.models.Users;
import org.Library.models.Utente;



public class AdminView implements Observer {

    private Stage stage;
    private String username;
    private Scene scena;
    private AdminController adminController;
    private TableView<Users> tableView;



    public AdminView(Stage stage,String username,AdminController adminController) {
        this.stage =  stage;
        this.username = username;
        this.adminController = adminController;
        this.adminController.addObserver(this);

    }



     //Creazione layout sinistro della scena
    public VBox createLeft(String username){

        //Nome di admin dopo il Log in
        UserDao userDao = new UserDao();
        Users users = userDao.usersCall(username);
        String name = users.getName();
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold;");

        // Cognome di admin dopo il log in
        String lastname = users.getLastname();
        Label lastnameLabel = new Label(lastname);
        lastnameLabel.setStyle("-fx-font-weight: bold;");

        //Spazio per migliorare la visibilita
        Region space = new Region();
        space.setMinHeight(10);

        //Azione dopo il clic su Aggiungi, Ti porta alla scena in cui permette di aggiungere un altro libro.
        Button aggiungiLibro = new Button("Gestione Libri");
        aggiungiLibro.setOnAction(event -> {
            scena = stage.getScene();
            String title = stage.getTitle();
            addLibroView(scena, title);
        });

        //Button di uscita porta al main
        Button esciButton = new Button("Esci");
        esciButton.setStyle("-fx-background-color: #a63845; -fx-text-fill: white;");
        esciButton.setOnAction(e -> {
            Main main = new Main();
            main.start(stage);
        });

        //Button di Gestione Utente
        Button controlloUtente = new Button("Gestione Utenti");
        controlloUtente.setOnAction(event3 -> {
            scena = stage.getScene();
            String titolo = stage.getTitle();
            controlloUtenteView(scena,titolo);
        });

        //Messaggio di benvenuto
        Label benvenuto = new Label("Benvenuto nel tuo profilo : ");
        benvenuto.setStyle("-fx-font-weight: bold;");

        Region space1 = new Region();
        VBox.setVgrow(space1, Priority.ALWAYS);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(10);

        vbox.getChildren().addAll(benvenuto,space,nameLabel,lastnameLabel,aggiungiLibro, controlloUtente,space1,esciButton);
        return vbox;
    }

    public VBox addLibroView(Scene scene,String title){
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label isbnLabel = new Label("Isbn");
        isbnLabel.setStyle("-fx-font-weight: bold;");
        TextField isbnText = new TextField();

        Label titoloLabel = new Label("Titolo");
        titoloLabel.setStyle("-fx-font-weight: bold;");
        TextField titoloText = new TextField();

        Label autoreLabel = new Label("Autore");
        autoreLabel.setStyle("-fx-font-weight: bold;");
        TextField autoreText = new TextField();

        Label generoLabel = new Label("Genere");
        generoLabel.setStyle("-fx-font-weight: bold;");
        TextField generoText = new TextField();

        Label linguaLabel = new Label("Lingua");
        linguaLabel.setStyle("-fx-font-weight: bold;");
        TextField linguaText = new TextField();

        Label disponibiliLabel = new Label("Disponibili");
        disponibiliLabel.setStyle("-fx-font-weight: bold;");
        TextField disponibiliText = new TextField();

        Label edizioneLabel = new Label("Edizione");
        edizioneLabel.setStyle("-fx-font-weight: bold;");
        TextField edizioneText = new TextField();


        Label messaggioLabel1 = new Label();
        messaggioLabel1.setStyle("-fx-text-fill: #a89132; -fx-font-scale: white");
        messaggioLabel1.setText("Per eliminare un libro scrivere solo il codice ISBN");

        Button indietro = new Button("Indietro");
        indietro.setOnAction(event -> {
            stage.setTitle(title);
            stage.setScene(scene);
        });

        Button eliminaLibro = new Button("Elimina Libro");

        Label messaggioLabel = new Label();

        adminController = new AdminController();

        eliminaLibro.setOnAction(event -> {
            adminController.addBookController(isbnText,titoloText,messaggioLabel);
        });

        Button aggiunigiLibro = new Button("Aggiungi Libro");
        aggiunigiLibro.setOnAction(event -> {
            adminController.removeBookController(isbnText,autoreText,titoloText,generoText,linguaText,edizioneText,disponibiliText,messaggioLabel);

        });

        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(aggiunigiLibro,indietro,eliminaLibro,messaggioLabel1);


        vbox.getChildren().addAll(isbnLabel,isbnText,titoloLabel,titoloText,autoreLabel,autoreText,generoLabel,generoText,
                linguaLabel,linguaText,disponibiliLabel,disponibiliText,edizioneLabel,edizioneText,hbox,messaggioLabel);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(vbox);

        Scene loginScene = new Scene(mainLayout, 1400, 600);
        stage.setTitle("Aggiungi un libro");
        stage.setScene(loginScene);
        stage.show();

        return vbox;

    }

    //Metodo chiamata da Admin Per gestire e controllare gli utenti
    public  void controlloUtenteView(Scene scene,String titolo){

        tableView = new TableView<>();

        TableColumn<Users, String> cartid = new TableColumn<>("CartId");
        cartid.setMinWidth(150);
        cartid.setCellValueFactory(new PropertyValueFactory<>("cartId"));

        TableColumn<Users, String> name = new TableColumn<>("Nome");
        name.setMinWidth(150);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Users, String> lastname = new TableColumn<>("cognome");
        lastname.setMinWidth(150);
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        tableView.getColumns().addAll(cartid,name,lastname);

        update();



        Button eliminaUtente = new Button("Elimina Utente");
        TextField eliminaField = new TextField();
        eliminaField.setPromptText("Inserire CartId Utente");

        TextField searchField = new TextField();
        searchField.setPromptText("Inserisce Id Utente");

        Button search = new Button("Search");

        adminController = new AdminController();

        search.setOnAction(event3 ->{
            String searchText = searchField.getText().trim();
            adminController.clientSearch(searchText,tableView);
        } );


        //Rimuovi Utente
        Label messaggio = new Label();
        eliminaUtente.setOnAction(event5 -> {
            adminController.eliminaUtenteController(eliminaField,messaggio);

        });

        Button indietro = new Button("Indietro");
        indietro.setOnAction(event1 -> {
            stage.setTitle(titolo);
            stage.setScene(scene);
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(tableView);

        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(10));
        vbox1.setSpacing(10);
        vbox1.getChildren().addAll(eliminaField,eliminaUtente,indietro,messaggio);


        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(10));
        vbox2.setSpacing(10);
        vbox2.getChildren().addAll(search,searchField);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(vbox);
        mainLayout.setLeft(vbox1);
        mainLayout.setRight(vbox2);

        Scene loginScene = new Scene(mainLayout, 1250, 500);
        stage.setTitle("Gestisci Utenti");
        stage.setScene(loginScene);
        stage.show();

    }


    public Scene mainLayout(){
        FirstView firstView = new FirstView();
        TableView tableView = firstView.createBooksTableView();
        VBox leftBox = createLeft(username);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(leftBox);
        mainLayout.setCenter(tableView);

        Scene loginScene = new Scene(mainLayout, 1450, 500);
        stage.setTitle("Tutti i libri della biblioteca");
        stage.setScene(loginScene);
        stage.show();

        return loginScene;
    }

    @Override
    public void update(){
        UtenteDao utenteDao = new UtenteDao();
        List<Utente> utenti = utenteDao.findAllUtenti();
        List<Users> users =  utenti.stream().map(Utente::getUsers).collect(Collectors.toList());
        ObservableList<Users> allUsersList = FXCollections.observableArrayList(users);
        tableView.setItems(allUsersList);

    }





}



