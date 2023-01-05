package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovieCollectionController extends BaseController implements Initializable {

    CategoryModel categoryModel;
    MovieModel movieModel;

    ObservableList<Category> categoryObservableList;

    public File file;

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

        categoryModel = new CategoryModel();
        movieModel = new MovieModel();

        movieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieTitle"));
        personalRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("personalRating"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("imdbRating"));
        categories.setCellValueFactory(new PropertyValueFactory<Movie, String>("categories"));
        lastTimeWatched.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("lastTimeWatched"));

        try {
            categoryObservableList = FXCollections.observableArrayList(categoryModel.getCategoryManager().getCategories());
            allCategories.setItems(categoryObservableList);
        } catch (Exception e) {
            e.printStackTrace();
            showWarningDialog("Warning!", "Could not get categories!");
        }

    }

    void initData (Movie movie) {
        movieTitle.setText(movie.getTitle());
    }

    public void handleAddCategory() {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("Add a category");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Category name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(s -> {
            try {
                var category = categoryModel.createCategory(s);
                categoryObservableList.add(category);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not create category!");
                showWarningDialog("Warning!", "Could not create category!");
            }
        });
    }

    public void handleDeleteCategory(ActionEvent actionEvent) {
    }

    public void handleRateMovie(ActionEvent actionEvent) {
    }

    public void handleAddMovie(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/NewMovieView.fxml"));
        Parent root = loader.load();

        NewMovieController controller = loader.getController();
        controller.setMoviemodel(movieModel);

        stage.setScene(new Scene(root));
        stage.setTitle("New Movie");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void handleDeleteMovie(ActionEvent actionEvent) {
    }

    private void showWarningDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

}
