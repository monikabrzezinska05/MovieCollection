package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import GUI.Model.MovieTableModel;
import javafx.beans.property.SimpleStringProperty;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
    TableView<MovieTableModel> movieTable;
    @FXML
    private TableColumn<MovieTableModel, String> movieTitle;
    @FXML
    private TableColumn<MovieTableModel, Integer> personalRating;
    @FXML
    private TableColumn<MovieTableModel, Integer> imdbRating;
    @FXML
    private TableColumn<MovieTableModel, String> categories;
    @FXML
    private TableColumn<MovieTableModel, java.sql.Date> lastTimeWatched;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
        categories.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString())); // TODO: Fix this pls
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
            lastTimeWatched.setCellValueFactory(new PropertyValueFactory<MovieTableModel, Date>("lastWatched"));

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
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/StarRating.fxml"));
        Parent root = loader.load();

        StarRatingController controller = loader.getController();
        controller.setMoviemodel(movieModel);
        controller.setup();

        stage.setScene(new Scene(root));
        stage.setTitle("Rate the Movie");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void handleAddMovie(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/NewMovieView.fxml"));
        Parent root = loader.load();

        NewMovieController controller = loader.getController();
        controller.setMoviemodel(movieModel);
        controller.setCategoryModel(categoryModel);
        controller.setup();


        stage.setScene(new Scene(root));
        stage.setTitle("New Movie");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void handleDeleteMovie() {
        var selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if(selectedMovie == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete movie");
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete the movie: " + selectedMovie.getTitle());
        ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                try {
                    movieModel.deleteMovie(selectedMovie);
                } catch (Exception e) {
                    System.out.println("Could not delete moveie!");
                    e.printStackTrace();
                }
            }
        });
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
