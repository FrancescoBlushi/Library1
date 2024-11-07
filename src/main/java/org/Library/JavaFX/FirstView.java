package org.Library.JavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.Library.Controller.AccessController;
import org.Library.DaoModels.AdminDao;
import org.Library.DaoModels.BookDao;
import org.Library.DaoModels.UtenteDao;
import org.Library.models.Books;
import org.Library.models.Users;
import org.Library.models.Utente;

import java.util.List;

public class FirstView {


    private Stage stage;
    private RecuperaPs recuperaPs;
    private RegisterView registerView;
    private BookDao bookDao;
    private List<Books> allBooksList;
    private AccessController accessController;

    public FirstView( Stage stage ) {

        this.stage = stage;

    }

    //Crea la prima Pagina
    public Stage creaStage() {

        //Layout sinistro
        VBox creaLogin = createLogin();

        // Layout centrale per visualizzare i libri
        TableView<Books> tableView = createBooksTableView();

        // Creazione del layout per la ricerca dei libri
        VBox searchPane = createSearchPane(tableView);

        // Layout principale che contiene le tre sezioni
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(creaLogin);
        mainLayout.setCenter(tableView);
        mainLayout.setRight(searchPane);


        Scene loginScene = new Scene(mainLayout, 1400, 600);
        stage.setTitle("Sistema di gestione della Biblioteca");
        stage.setScene(loginScene);
        stage.show();


        recuperaPs = new RecuperaPs(stage);
        Button recuperaButton = (Button) loginScene.lookup("#recuperaButton");
        recuperaButton.setOnAction(event -> {
            Scene recuperScena = recuperaPs.createRecuperoScene();
            stage.setScene(recuperScena);
        });

        registerView = new RegisterView(stage);

        // Imposta il bottone di registrazione per mostrare la pagina di registrazione
        Button registerButton = (Button) loginScene.lookup("#registerButton");
        registerButton.setOnAction(e -> {
            Scene registrationScene = registerView.createRegisterScene();
            stage.setScene(registrationScene);
        });

        return stage;

    }

    public FirstView() {
    }

    //Crea la tabella centrale per visualizzare tutti i libri
    public TableView<Books> createBooksTableView() {
        TableView<Books> tableView = new TableView<>();

        TableColumn<Books, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setMinWidth(150);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Books, String> titleColumn = new TableColumn<>("Titolo");
        titleColumn.setMinWidth(250);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Books, String> authorColumn = new TableColumn<>("Autore");
        authorColumn.setMinWidth(250);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("autore"));

        TableColumn<Books, String> genreColumn = new TableColumn<>("Genere");
        genreColumn.setMinWidth(150);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Books, String> linguaColumn = new TableColumn<>("Lingua");
        linguaColumn.setMinWidth(150);
        linguaColumn.setCellValueFactory(new PropertyValueFactory<>("lingua"));

        TableColumn<Books, Integer> disponibiliColumn = new TableColumn<>("Disponibili");
        disponibiliColumn.setMinWidth(150);
        disponibiliColumn.setCellValueFactory(new PropertyValueFactory<>("disponibili"));

        tableView.getColumns().addAll(isbnColumn, titleColumn,authorColumn, genreColumn, linguaColumn, disponibiliColumn);

        bookDao = new BookDao();
        allBooksList = bookDao.getBooks();
        ObservableList<Books> allBooksObservableList = FXCollections.observableArrayList(allBooksList);
        tableView.setItems(allBooksObservableList);
        return tableView;
    }

    // Metodo che permette la ricerca chiama altri metodi di BookDao.
    public static void booksView(String searchTerm, TableView<Books> tableView) {

        BookDao bookDao = new BookDao();
        List<Books> booksList;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            booksList = bookDao.getBooks();
        } else {
            booksList = bookDao.findBooksByTitle(searchTerm.trim());
        }

        if (!booksList.isEmpty()) {
            ObservableList<Books> booksObservableList = FXCollections.observableArrayList(booksList);
            tableView.setItems(booksObservableList);
        } else {
            tableView.setItems(FXCollections.observableArrayList());
            tableView.setPlaceholder(new Label("Nessun libro trovato con questo titolo."));
        }
    }

    //Parte sinistra della prima pagina in cui hai il Log in User e Admin
    public VBox createLogin() {
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(20));

        ToggleGroup userTypeGroup = new ToggleGroup();

        RadioButton rbUser = new RadioButton("User");
        rbUser.setToggleGroup(userTypeGroup);
        rbUser.setSelected(true);

        RadioButton rbAdmin = new RadioButton("Admin");
        rbAdmin.setToggleGroup(userTypeGroup);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Inserisci username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Inserisci password");

        Button loginButton = new Button("Login");

        Button registerButton = new Button("Registrazione");
        registerButton.setId("registerButton");

        Label messaglabel = new Label();

        Button recuperButton = new Button("Password Dimenticata");
        recuperButton.setId("recuperaButton");

        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);

        loginPane.getChildren().addAll(rbUser, rbAdmin, usernameLabel, usernameField, passwordLabel, passwordField, loginButton,
                registerButton,messaglabel,space,recuperButton);

        //Log in Button con azioni diversi Per Admin e User.
        loginButton.setOnAction(event -> {
            accessController = new AccessController();
            //User Log in
            if(rbUser.isSelected()) {
                accessController.accessUser(usernameField,passwordField,messaglabel,stage);
             // Admin Login
            }else if (rbAdmin.isSelected() ){
                accessController.accesAdmin(usernameField,passwordField,messaglabel,stage);

            }
        });




        return loginPane;
    }

    public  VBox createSearchPane(TableView<Books> tableView) {
        VBox searchPane = new VBox(10);
        searchPane.setPadding(new Insets(20));

        Label searchLabel = new Label("Cerca libro:");
        TextField searchField = new TextField();
        searchField.setId("searchField");
        searchField.setPromptText("Inserisci il titolo del libro");

        Button searchButton = new Button("Cerca");
        searchButton.setId("searchButton");

        searchButton.setOnAction(event -> {
            String search = searchField.getText().trim();
                booksView(search,tableView);

        });

        searchPane.getChildren().addAll(searchLabel, searchField,searchButton);

        return searchPane;
    }



}
