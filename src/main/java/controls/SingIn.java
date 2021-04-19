package controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.UserDAO;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.UserDAOImpl;

public class SingIn {

    private User item;
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    ObservableList<User> userObservableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private Button sing;

    @FXML
    private ComboBox<User> Combo;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label status;

    @FXML
    void ComboAction(ActionEvent event) {
        User select = Combo.getSelectionModel().getSelectedItem();
       loginField.setText(String.valueOf(select));

    }

    @FXML
    void singIn(ActionEvent event) throws IOException {
        for (User user : userObservableList){
            if (loginField.getText().trim().equals(user.getLogin()) && passwordField.getText().trim().equals(user.getPassword())){
                Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Panel");
                stage.setScene(new Scene(root));
                stage.show();
                sing.getScene().getWindow().hide();
            }else {
                status.setText("Wrong login or pass");

            }
        }

    }

    @FXML
    void initialize() {
        UserDAO userDAO = new UserDAOImpl(factory);
        userObservableList.addAll(userDAO.findByAll());
        Combo.setItems(userObservableList);
        Combo.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> {
         item = newValue; });

    }
}
