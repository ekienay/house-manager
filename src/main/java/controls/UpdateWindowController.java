package controls;

import dao.PersonDAO;
import entity.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.PersonDAOImpl;

public class UpdateWindowController {
   private SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @FXML
    private TextField UpdateFLP;

    @FXML
    private TextField UpdateAge;

    @FXML
    private Label UpdateID;

    public void setData(Person person){
        if(person != null){
            UpdateID.setText(String.valueOf(person.getId()));
            UpdateFLP.setText(person.getFlp());
            UpdateAge.setText(String.valueOf(person.getAge()));
        }else
        {
            UpdateID.setText("");
            UpdateFLP.setText("");
            UpdateAge.setText("");
        }
    }
    public Person getData(){
        Person person = new Person();
        person.setId(Integer.parseInt(UpdateID.getText()));
        person.setFlp(UpdateFLP.getText());
        person.setAge(Integer.parseInt(UpdateAge.getText()));
        return person;
    }

    @FXML
    void Update(ActionEvent event) {
        PersonDAO personDAO = new PersonDAOImpl(factory);
        personDAO.update(getData());
    }
}
