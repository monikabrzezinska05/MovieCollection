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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovieCollectionController extends BaseController implements Initializable {


    CategoryModel categoryModel;
    MovieModel movieModel;

    ObservableList<Category> categoryObservableList;

    public Button addCategory;
    public Button deleteCategory;
    public Button rateMovie;
    public Button addMovie;
    public Button deleteMovie;
    public Button searchButton;
    public ListView<Category> allCategories;

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

    public void handleDeleteCategory() {
        var selectedCategory = allCategories.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete category");
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete the category: " + selectedCategory.getName());
        ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                try {
                    categoryModel.deleteCategory(selectedCategory);
                } catch (Exception e) {
                    e.printStackTrace();
                    showWarningDialog("Warning!", "Could not delete category: " + selectedCategory.getName());
                }
                categoryObservableList.remove(selectedCategory);
            }
        });
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

    public void handleSearchButton(ActionEvent actionEvent) {
    }

    private void showWarningDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

}
