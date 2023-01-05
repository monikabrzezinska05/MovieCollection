package GUI.Controller;

import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewMovieController extends BaseController implements Initializable {

    public Button chooseFileButton;
    public Button cancelMovieButton;
    public Button saveMovieButton;
    public TextField fileTextField;
    public TextField movieTitleTextField;
    public TextField imdbRatingTextField;
    MovieModel movieModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieModel = getMoviemodel();

    }

    public void handleChooseFileButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("*.mp4", "*.mpeg4"));
        fileChooser.setTitle("Choose MovieFile");

        File file = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());

    }

    public void handleCancelMovie(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelMovieButton.getScene().getWindow();
        stage.close();
    }

    public void handleSaveMovie(ActionEvent actionEvent) {
    }
}
