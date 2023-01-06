package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import GUI.Model.MovieTableModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewMovieController extends BaseController implements Initializable {

    public Button chooseFileButton;
    public Button cancelMovieButton;
    public Button saveMovieButton;
    public TextField fileTextField;
    public TextField movieTitleTextField;
    public TextField imdbRatingTextField;

    public CheckComboBox<Category> categoryComboBox;

    private MovieModel movieModel;

    private CategoryModel categoryModel;

    private File selectedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedFile = null;

        checkSaveButtonDisable();

        movieTitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkSaveButtonDisable();
        });

        imdbRatingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkSaveButtonDisable();
        });
    }

    @Override
    public void setup() {
        movieModel = getMoviemodel();
        categoryModel = getCategoryModel();

        try {
            var categories = categoryModel.getCategoryManager().getCategories();
            categoryComboBox.getItems().setAll(categories);
        } catch (Exception e) {
            System.out.println("Could not get categories.");
            e.printStackTrace();
        }
    }

    public void handleChooseFileButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Allowed extensions", "*.mp4", "*.mpeg4"));
        fileChooser.setTitle("Choose MovieFile");

        selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());
        fileTextField.setText(selectedFile.getAbsolutePath());
        movieTitleTextField.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf('.')));
        checkSaveButtonDisable();
    }

    public void handleCancelMovie(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelMovieButton.getScene().getWindow();
        stage.close();
    }

    public void handleSaveMovie(ActionEvent actionEvent) {
        if(selectedFile == null) return;
        if(movieTitleTextField.getText().isEmpty()) return;
        if(imdbRatingTextField.getText().isEmpty()) return;

        Movie newMovie = null;

        try {
            newMovie = movieModel.createMovie(
                movieTitleTextField.getText(),
                selectedFile.getAbsolutePath(),
                new Date(System.currentTimeMillis()),
                0,
                42
            );
        } catch (Exception e) {
            System.out.println("Could not create movie!");
            e.printStackTrace();
        }

        if(newMovie != null) movieModel.getMoviesObservableList().add(new MovieTableModel(
                newMovie.getId(),
                newMovie.getTitle(),
                newMovie.getFilepath(),
                newMovie.getLastWatched(),
                newMovie.getPersonalRating(),
                newMovie.getIMDBRating()
        ));

        Stage stage = (Stage) cancelMovieButton.getScene().getWindow();
        stage.close();

    }

    private void checkSaveButtonDisable() {
        boolean isFileChosen = selectedFile != null;
        boolean isTitleGiven = movieTitleTextField.getText().length() > 0;
        boolean isImdbRatingGiven = imdbRatingTextField.getText().length() > 0;

        saveMovieButton.setDisable(!(isFileChosen && isTitleGiven && isImdbRatingGiven));
    }


}
