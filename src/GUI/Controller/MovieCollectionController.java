package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import javafx.beans.property.SimpleStringProperty;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovieCollectionController extends BaseController implements Initializable {

    CategoryModel categoryModel;
    MovieModel movieModel;

    public Button addCategory;
    public Button deleteCategory;
    public Button rateMovie;
    public Button addMovie;
    public Button deleteMovie;
    public Button searchButton;
    public ListView<Category> allCategories;

    @FXML
    TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> movieTitle;
    @FXML
    private TableColumn<Movie, Integer> personalRating;
    @FXML
    private TableColumn<Movie, Integer> imdbRating;
    @FXML
    private TableColumn<Movie, String> categories;
    @FXML
    private TableColumn<Movie, java.sql.Date> lastTimeWatched;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var selectedMovie = movieTable.getSelectionModel().getSelectedItem();
                File movieToOpen = new File(selectedMovie.getFilepath());
                try {
                    Desktop.getDesktop().open(movieToOpen);
                } catch (IOException e) {
                    showWarningDialog("Error", "Couldn't open the selected Movie");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setup() {
        try {
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            System.out.println("Could not create CategoryModel");
            e.printStackTrace();
        }

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            System.out.println("Could not create MovieModel");
            e.printStackTrace();
        }

        movieTitle.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        personalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        categories.setCellValueFactory(new PropertyValueFactory<>("categories")); // TODO: Fix this pls
        lastTimeWatched.setCellValueFactory(new PropertyValueFactory<>("lastWatched"));

        try {
            allCategories.setItems(categoryModel.getCategoryObservableList());
        } catch (Exception e) {
            e.printStackTrace();
            showWarningDialog("Warning!", "Could not get categories!");
        }

        try {
            personalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
            imdbRating.setCellValueFactory(new PropertyValueFactory<>("IMDBRating"));
            // TODO: Categories
            movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            lastTimeWatched.setCellValueFactory(new PropertyValueFactory<>("lastWatched"));

            List<Movie> movies = movieModel.getAllMovies();


            movieTable.setItems(movieModel.getMoviesObservableList());
        } catch (Exception e) {
            e.printStackTrace();
            showWarningDialog("Warning!", "Could not get movies!");
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
                categoryModel.getCategoryObservableList().add(category);
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
                categoryModel.getCategoryObservableList().remove(selectedCategory);
            }
        });
    }

    public void handleRateMovie(ActionEvent actionEvent) throws IOException {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieModel.setSelectedMovie(selectedMovie);

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/StarRating.fxml"));
            Parent root = loader.load();

            StarRatingController controller = loader.getController();
            controller.setMovieModel(movieModel);
            controller.setup();

            stage.setScene(new Scene(root));
            stage.setTitle("Rate the Movie!");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }
    }

    public void handleAddMovie(ActionEvent actionEvent) throws IOException {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/NewMovieView.fxml"));
            Parent root = loader.load();

            NewMovieController controller = loader.getController();
            controller.setMovieModel(movieModel);
            controller.setCategoryModel(categoryModel);
            controller.setup();

            Stage dialogWindow = new Stage();
            dialogWindow.setTitle("New Movie");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }


    public void handleDeleteMovie(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Movie");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to delete the movie?: " + selectedMovie.getTitle());
            ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    try {
                        movieModel.deleteMovie(selectedMovie);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showWarningDialog("Warning!", "Could not delete Movie: " + selectedMovie.getTitle());
                    }
                    movieModel.getMoviesObservableList().remove(selectedMovie);
                }
            });
        }
    }

    public void handleSearchButton() {

    }

    private void showWarningDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

}
