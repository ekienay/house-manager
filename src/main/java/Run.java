import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Run extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SignInWindow.fxml"));
        stage.setTitle("Sign In");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }
    public static void main(String[] args) {
            launch(args);
    }
}
