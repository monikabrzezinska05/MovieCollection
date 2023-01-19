package GUI.Controller;

import BE.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReminderController extends BaseController implements Initializable {

    public ListView<Movie> movieListView;
    public Button deleteSelectedMovies;
    public Button closeWindow;

    private List<Movie> movieList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @Override
    public void setup() {

        movieListView.setItems(FXCollections.observableArrayList(movieList));
    }

    public void initData(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void handleDeleteSelectedMovies() {
        var moviesToDelete = movieListView.getSelectionModel().getSelectedItems();

        for(Movie m : moviesToDelete) {
            try {
                movieModel.deleteMovie(m);
            } catch (Exception e) {
                System.out.println("Could not delete movie: " + m.toString());
                e.printStackTrace();
            }
        }

        handleCloseWindow();
    }

    public void handleCloseWindow() {
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }
}
