package controls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import dao.PassportDAO;
import dao.PersonDAO;
import entity.Passport;
import entity.Person;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.PassportDAOImpl;
import service.PersonDAOImpl;

public class MainPanelController {

    private Person selectedItem;

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    ObservableList<Person> personObservableList = FXCollections.observableArrayList();
    ObservableList<Passport> passportObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> TabView;

    @FXML
    private TableColumn<Person, Integer> PersonId;

    @FXML
    private TableColumn<Person, String> PersonFLP;

    @FXML
    private TableColumn<Person,Integer> PersonAge;

    @FXML
    private TableColumn<Person, String> PassSeries;

    @FXML
    private TableColumn<Person, Integer> PassNumber;

    @FXML
    private TextField pathField;

    @FXML
    private Label pathStatus;

    @FXML
    private TextField fileName;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private Button dl;


    @FXML
    void ButtonAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Person");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }


    @FXML
    void ButtonDelete(ActionEvent event) {
        Person person = TabView.getSelectionModel().getSelectedItem();
        PersonDAO personDAO = new PersonDAOImpl(factory);
        if (person == null){
            System.out.println("selected object is empty");
        }else {
            TabView.getItems().remove(person);
            personDAO.delete(person);
        }
    }

    @FXML
    void path(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/images/icon.png"));
        File dir = directoryChooser.showDialog(stage);
        if (dir == null){
            pathStatus.setText("Directory path is empty");
        }else {
            String path = dir.getAbsolutePath();
            pathField.setText(path);
        }

    }


    @FXML
    void ToPDF(ActionEvent event) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathField.getText().trim()+"\\"+fileName.getText().trim()+".pdf"));

        document.open();
        if(selectedItem == null){
            System.out.println("selected item is empty");
        }else {
            PlaceObject(writer,"ID: "+selectedItem.getId(),50,750);
            PlaceObject(writer,"Full name: "+selectedItem.getFlp(),85,750);
            PlaceObject(writer,"Age: "+selectedItem.getAge(),300,750);
            PlaceObject(writer,"Passport series: "+selectedItem.getPassport().getSeries(),350,750);
            PlaceObject(writer,"Passport number: "+selectedItem.getPassport().getNumber(),450,750);
        }
        document.close();
    }

    @FXML
    void backToSignIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SignInWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign In");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
        back.getScene().getWindow().hide();
    }


    public void initDateBase(){
        PassportDAO passportDAO = new PassportDAOImpl(factory);
        List<Passport> passportList = passportDAO.findByAll();
        PersonDAO personDAO = new PersonDAOImpl(factory);
        List<Person> personList = personDAO.findByAll();
        personObservableList.addAll(personList);
        passportObservableList.addAll(passportList);
    }

    public void initTableView(){
        PersonId.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getId()));
        PersonFLP.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getFlp()));
        PersonFLP.setCellFactory(TextFieldTableCell.forTableColumn());
        PersonFLP.setOnEditCommit(event ->{
            String newValue = event.getNewValue();
            Person person = event.getRowValue();
            person.setFlp(newValue);
            PersonDAO personDAO = new PersonDAOImpl(factory);
            personDAO.update(person);
        });
        PersonAge.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getAge()));
        PersonAge.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        PersonAge.setOnEditCommit(event ->{
            Integer newValue = event.getNewValue();
            Person person = event.getRowValue();
            person.setAge(newValue);
            PersonDAO personDAO = new PersonDAOImpl(factory);
            personDAO.update(person);
        });
        PassSeries.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getPassport().getSeries()));
        PassSeries.setCellFactory(TextFieldTableCell.forTableColumn());
        PassSeries.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            Person person = event.getRowValue();
            person.getPassport().setSeries(newValue);
            PersonDAO personDAO = new PersonDAOImpl(factory);
            personDAO.update(person);
        });
        PassNumber.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getPassport().getNumber()));
        PassNumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        PassNumber.setOnEditCommit(event -> {
            Integer number = event.getNewValue();
            Person person = event.getRowValue();
            person.getPassport().setNumber(number);
            PersonDAO personDAO = new PersonDAOImpl(factory);
            personDAO.update(person);
        });
        TabView.setItems(personObservableList);
        TabView.getSelectionModel().selectedItemProperty().addListener(
                (obj, oldValue, newValue) -> selectedItem = newValue);
        TabView.setEditable(true);
    }

    public void sign(){
        if (SignInController.role.equals("user")){
            add.setVisible(false);
            dl.setVisible(false);
        }
    }


    public void PlaceObject(PdfWriter writer, String object, int x, int y) throws IOException, DocumentException {
        BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts/Calibri.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);

        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf,11);
        cb.moveText(x,y);
        cb.showText(object);
        cb.endText();
        cb.restoreState();
    }

    @FXML
    void refresh(ActionEvent event) {
            ref();
    }

    public void ref(){
        personObservableList.clear();
        passportObservableList.clear();
        initialize();
    }

    @FXML
    void initialize() {
        sign();
        initDateBase();
        initTableView();
    }
}
