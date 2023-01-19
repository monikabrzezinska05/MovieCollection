package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
        movieModel = getMovieModel();
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

        float ImdbRating;

        try {
            ImdbRating = Float.parseFloat(imdbRatingTextField.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ImdbRating error!");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.setContentText("The IMDB rating must be a number with one or less decimals!");
            ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait();
            return;
        }

        Movie newMovie = null;

        try {
            newMovie = movieModel.createMovie(
                movieTitleTextField.getText(),
                selectedFile.getAbsolutePath(),
                new Date(System.currentTimeMillis()),
                0,
                ImdbRating
            );
        } catch (Exception e) {
            System.out.println("Could not create movie!");
            e.printStackTrace();
        }

        var selectedCategories = categoryComboBox.getCheckModel().getCheckedItems();

        for(Category c : selectedCategories) {
            try {
                movieModel.addCategoryToMovie(newMovie, c);
            } catch (Exception e) {
                System.out.printf("Could not add category %s to movie %s%n", c.getName(), newMovie.getTitle());
                e.printStackTrace();
            }
        }

        try {
            movieModel.getMoviesObservableList().add(movieModel.getMovieById(newMovie.getId(), categoryModel.getCategoryObservableList()));
        } catch (Exception e) {
            System.out.println("Could not get newly created movie!");
            e.printStackTrace();
        }

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
