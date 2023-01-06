import GUI.Controller.MovieCollectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/View/MovieCollectionView.fxml"));

        Parent root = loader.load();

        MovieCollectionController controller = loader.getController();
        controller.setup();

        primaryStage.setTitle("MovieCollection");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}