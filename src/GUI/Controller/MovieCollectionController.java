package GUI.Controller;

import BE.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieCollectionController implements Initializable {

    public Button addCategory;
    public Button deleteCategory;
    public Button rateMovie;
    public Button addMovie;
    public Button deleteMovie;
    public ListView allCategories;

    @FXML
    private TableColumn<Movie, String> movieTitle;
    @FXML
    private TableColumn<Movie, Integer> personalRating;
    @FXML
    private TableColumn<Movie, Integer> imdbRating;
    @FXML
    private TableColumn<Movie, String> categories;
    @FXML
    private TableColumn<Movie, Integer> lastTimeWatched;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieTitle"));
        personalRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("personalRating"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("imdbRating"));
        categories.setCellValueFactory(new PropertyValueFactory<Movie, String>("categories"));
        lastTimeWatched.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("lastTimeWatched"));

    }

    public void handleAddCategory(ActionEvent actionEvent) {
    }

    public void handleDeleteCategory(ActionEvent actionEvent) {
    }

    public void handleRateMovie(ActionEvent actionEvent) {
    }

    public void handleAddMovie(ActionEvent actionEvent) {
    }

    public void handleDeleteMovie(ActionEvent actionEvent) {
    }
}
