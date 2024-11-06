package org.Library.JavaFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.Library.DaoModels.BookDao;
import org.Library.DaoModels.ConnectionDB;
import org.Library.DaoModels.LoansDao;
import org.Library.DaoModels.UserDao;
import org.Library.Dto.LoanBook;
import org.Library.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


//Classe che permette agli utenti di visualizzare i libri in prestito
public class LoanView {

    public LoanView(Stage stage,Scene scene) {
        this.stage = stage;
        this.loginScene = scene;
    }

    private Stage stage;
    private Scene loginScene;
    private UserDao userDao = new UserDao();

   /* public TableView<Loans> loansTView(String cart) {

        LoansDao loansDao = new LoansDao();
        TableView<Loans> tableView = new TableView<>();

        TableColumn<Loans, LocalDate> loanDate = new TableColumn<>("LOAN DATE");
        loanDate.setMinWidth(150);
        loanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));;

        TableColumn<Loans, LocalDate> dueDate = new TableColumn<>("DUE DATE");
        dueDate.setMinWidth(150);
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Loans, LocalDate> returnDate = new TableColumn<>("RETURN DATE");
        returnDate.setMinWidth(170);
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        TableColumn<Loans, String> cartId = new TableColumn<>("CART ID");
        cartId.setMinWidth(150);
        cartId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getCartId()));

        TableColumn<Loans, String> isbn = new TableColumn<>("ISBN");
        isbn.setMinWidth(150);
        isbn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getIsbn()));

        TableColumn<Books,String> title = new TableColumn<>("Titolo");
        title.setMinWidth(150);
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        tableView.getColumns().addAll(cartId, isbn, loanDate, dueDate, returnDate);

        List<Loans> loansList = loansDao.loansByCartId(cart);

        ObservableList<Loans> observableList = FXCollections.observableArrayList(loansList);
        tableView.setItems(observableList);

        return tableView;


    }*/
    public TableView<LoanBook> loansTView1(String cart) {

        LoansDao loansDao = new LoansDao();
        TableView<LoanBook> tableView = new TableView<>();

        TableColumn<LoanBook, LocalDate> loanDate = new TableColumn<>("LOAN DATE");
        loanDate.setMinWidth(150);
        loanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));;

        TableColumn<LoanBook, LocalDate> dueDate = new TableColumn<>("DUE DATE");
        dueDate.setMinWidth(150);
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<LoanBook, LocalDate> returnDate = new TableColumn<>("RETURN DATE");
        returnDate.setMinWidth(170);
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        TableColumn<LoanBook, String> cartId = new TableColumn<>("CART ID");
        cartId.setMinWidth(150);
        cartId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCartId()));

        TableColumn<LoanBook, String> isbn = new TableColumn<>("ISBN");
        isbn.setMinWidth(150);
        isbn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        TableColumn<LoanBook,String> title = new TableColumn<>("Titolo");
        title.setMinWidth(150);
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitolo()));

        TableColumn<LoanBook,String> autore = new TableColumn<>("Autore");
        title.setMinWidth(150);
        autore.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getAutore()));


        tableView.getColumns().addAll(cartId, isbn,title,autore, loanDate, dueDate, returnDate);

        List<LoanBook> loansList = loansDao.getLibriLoan(cart);

        ObservableList<LoanBook> observableList = FXCollections.observableArrayList(loansList);
        tableView.setItems(observableList);

        return tableView;


    }



    public VBox CreatLeft(String id){

        VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(10));

        Label benvenutoLabel = new Label("Benvenuto nel tuo profilo : ");

        Label cronologia = new Label("Tutti i libri in prestito");
        cronologia.setStyle("-fx-font-weight: bold;");

        Users user = userDao.findUserByCartId(id);
        Label name = new Label(user.getName());
        name.setStyle("-fx-font-weight: bold;");

        Label lastname = new Label(user.getLastname());
        lastname.setStyle("-fx-font-weight: bold;");

        Button indietro = new Button("Indietro");
        indietro.setStyle("-fx-background-color: #56b656; -fx-text-fill: white");

        indietro.setOnAction(event -> {
            stage.setScene(loginScene);
        });

        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);

        leftBox.getChildren().addAll(cronologia,benvenutoLabel, name, lastname,space,indietro);

        return leftBox;


    }


    public void createLoan(String id){

        TableView<LoanBook> tableView = loansTView1(id);
        VBox leftBox = CreatLeft(id);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(leftBox);
        mainLayout.setCenter(tableView);

        Scene loginScene = new Scene(mainLayout, 1400, 600);
        stage.setTitle("Tutti i libri in prestito");
        stage.setScene(loginScene);
        stage.show();

    }

}
