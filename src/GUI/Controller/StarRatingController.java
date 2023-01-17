package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

public class StarRatingController extends BaseController implements Initializable {

    public Button cancelRating;
    public Button saveRating;
    @FXML
    private Rating movieRating;

    @Override
    public void setup() {
        movieModel = getMovieModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleCancelRating(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelRating.getScene().getWindow();
        stage.close();
    }

    public void handleSaveRating(ActionEvent actionEvent) throws Exception {
       movieRating.getRating();
       Movie ratingToBeUpdated = movieModel.getSelectedMovie();

       ratingToBeUpdated.setPersonalRating((int)movieRating.getRating());

       movieModel.updateMovie(ratingToBeUpdated, getCategoryModel().getCategoryObservableList());

       Stage stage = (Stage) cancelRating.getScene().getWindow();
       stage.close();
    }

}
