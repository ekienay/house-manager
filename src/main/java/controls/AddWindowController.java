package controls;

import dao.PersonDAO;
import entity.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.PersonDAOImpl;

import java.io.IOException;
import java.util.List;

public class AddWindowController {


    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    ObservableList<Person> personObservableList = FXCollections.observableArrayList();



    @FXML
    private TextField AddFLP;

    @FXML
    private TextField AddAge;

    @FXML
    private TextField status;


    @FXML
    void AddPerson(ActionEvent event) throws IOException {

        Person person = new Person();
        person.setFlp(AddFLP.getText());
        person.setAge(Integer.parseInt(AddAge.getText()));
        PersonDAO personDAO = new PersonDAOImpl(factory);
        personDAO.create(person);

    }

    @FXML
    void initialize() {
        PersonDAO personDAO = new PersonDAOImpl(factory);
        List<Person> personList = personDAO.findByAll();
        personObservableList.addAll(personList);


    }

}
