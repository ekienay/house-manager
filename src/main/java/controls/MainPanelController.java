package controls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.source.tree.IfTree;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.PersonDAOImpl;

public class MainPanelController {

    private Person selectedItem;
    private Stage stage;

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    ObservableList<Person> personObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> TabView;

    @FXML
    private TableColumn<Person, Integer> PersonId;

    @FXML
    private TableColumn<Person, String> PersonFLP;

    @FXML
    private TableColumn<Person, Integer> PersonAge;

    @FXML
    private TextField pathField;

    @FXML
    private Label pathStatus;

    @FXML
    private TextField fileName;

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
        PersonDAO personDAO = new PersonDAOImpl(factory);
        if (person == null){
            System.out.println("selected object is empty");
        }else {
            TabView.getItems().remove(person);
            personDAO.delete(person);
        }
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
    void path(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
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
            PlaceObject(writer, String.valueOf(selectedItem.getId()),50,750);
            PlaceObject(writer, String.valueOf(selectedItem.getFlp()),85,750);
            PlaceObject(writer,String.valueOf(selectedItem.getAge()),250,750);
        }
        document.close();
    }


    public void initDateBase(){
        PersonDAO personDAO = new PersonDAOImpl(factory);
        List<Person> personList = personDAO.findByAll();
        personObservableList.addAll(personList);
    }


    public void PlaceObject(PdfWriter writer, String object, int x, int y) throws IOException, DocumentException {
        BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts/Calibri.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font font = new Font(bf);

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
    void initialize() {
        PersonId.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getId()));
        PersonFLP.setCellValueFactory(p -> new SimpleObjectProperty<String>(p.getValue().getFlp()));
        PersonAge.setCellValueFactory(p -> new SimpleObjectProperty<Integer>(p.getValue().getAge()));
        TabView.setItems(personObservableList);
        TabView.getSelectionModel().selectedItemProperty().addListener(
                (obj, oldValue, newValue) -> selectedItem = newValue);
        TabView.setEditable(true);

        initDateBase();
    }
}
