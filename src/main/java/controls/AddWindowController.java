package controls;

import dao.PassportDAO;
import dao.PersonDAO;
import entity.Passport;
import entity.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.PassportDAOImpl;
import service.PersonDAOImpl;

import java.io.IOException;

public class AddWindowController {


    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    ObservableList<Person> personObservableList = FXCollections.observableArrayList();
    ObservableList<Passport> passportObservableList = FXCollections.observableArrayList();
    private Passport item;



    @FXML
    private TextField AddFLP;

    @FXML
    private TextField AddAge;

    @FXML
    private ComboBox<Passport> AddPass;

    @FXML
    private TextField passSeries;

    @FXML
    private TextField passNumber;

    @FXML
    void AddPassport(ActionEvent event) {
        Passport passport = new Passport();

        passport.setSeries(passSeries.getText());
        passport.setNumber(Integer.parseInt(passNumber.getText()));

        PassportDAO passportDAO = new PassportDAOImpl(factory);
        passportDAO.create(passport);
        ref();
    }

    public void ref(){
        passportObservableList.clear();
        personObservableList.clear();
        initialize();
    }


    @FXML
    void AddPerson(ActionEvent event) throws IOException {

        Person person = new Person();

        person.setFlp(AddFLP.getText());
        person.setAge(Integer.parseInt(AddAge.getText()));
        person.setPassport(AddPass.getValue());

        PersonDAO personDAO = new PersonDAOImpl(factory);
        personDAO.create(person);
        ref();
    }

    @FXML
    void initialize() {
        PassportDAO passportDAO= new PassportDAOImpl(factory);
        passportObservableList.addAll(passportDAO.findByAll());

        AddPass.setItems(passportObservableList);
        AddPass.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> {
            item = newValue; });
    }

}
