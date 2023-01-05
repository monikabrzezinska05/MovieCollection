package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovieCollectionController implements Initializable {

    CategoryModel categoryModel;

    ObservableList<Category> categoryObservableList;

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

    public void handleAddMovie(ActionEvent actionEvent) {
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
