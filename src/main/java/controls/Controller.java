package controls;

import java.awt.event.PaintEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.PersonDAO;
import entity.Person;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.PersonDAOImpl;

public class Controller {

    private Person selectedItem;

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    ObservableList<Person> personObservableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Person> TabView;

    @FXML
    private TableColumn<Person, Integer> PersonId;

    @FXML
    private TableColumn<Person, String> PersonFLP;

    @FXML
    private TableColumn<Person, Integer> PersonAge;

    @FXML
    void ButtonAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Person");
        stage.setScene(new Scene(root));
        stage.show();

        }


    @FXML
    void ButtonDelete(ActionEvent event) {
        Person person = TabView.getSelectionModel().getSelectedItem();
        TabView.getItems().remove(person);

        PersonDAO personDAO = new PersonDAOImpl(factory);
        personDAO.delete(person);

    }

    @FXML
    void ButtonUpdate(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateWindow.fxml"));
        Parent root = loader.load();
        stage.setTitle("Update Window");
        stage.setScene(new Scene(root));
        stage.show();
        UpdateWindowController updateWindowController = loader.getController();
        updateWindowController.setData(selectedItem);


    }
    @FXML
    void refresh(ActionEvent event) {
        initDateBase();

    }
    public void initDateBase(){
        PersonDAO personDAO = new PersonDAOImpl(factory);
        List<Person> personList = personDAO.findByAll();
        personObservableList.addAll(personList);
    }

    @FXML
    void initialize() {

        TabView.setItems(personObservableList);
        TabView.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> selectedItem = newValue);
        PersonId.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getId()));
        PersonFLP.setCellValueFactory(p -> new SimpleObjectProperty<String>(p.getValue().getFlp()));
        PersonAge.setCellValueFactory(p -> new SimpleObjectProperty<Integer>(p.getValue().getAge()));

        initDateBase();

    }
}
