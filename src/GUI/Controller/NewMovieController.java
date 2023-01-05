package GUI.Controller;

import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    }

    public void handleCancelMovie(ActionEvent actionEvent) {
    }

    public void handleSaveMovie(ActionEvent actionEvent) {
    }
}
