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
import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MovieCollectionController extends BaseController implements Initializable {

    private ObservableList<Category> categoryObservableList;
    CategoryModel categoryModel;
    MovieModel movieModel;

    private ObservableList<MovieTableModel> movieObservableList;

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

        categoryModel = new CategoryModel();
        movieModel = new MovieModel();

        movieTitle.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        personalRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        categories.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString())); // TODO: Fix this pls
        lastTimeWatched.setCellValueFactory(new PropertyValueFactory<>("lastWatched"));

        try {
            categoryObservableList = FXCollections.observableArrayList(categoryModel.getCategoryManager().getCategories());
            allCategories.setItems(categoryObservableList);
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

            movies.forEach(movie -> {
                movieObservableList = FXCollections.observableArrayList();
                movieObservableList.add(new MovieTableModel(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getFilepath(),
                        movie.getLastWatched(),
                        movie.getPersonalRating(),
                        movie.getIMDBRating()
                ));
            });

            movieTable.setItems(movieObservableList);
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

    public void handleRateMovie() {
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

    public void handleDeleteMovie() {
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

    private File getMovieFile() {
        JFileChooser jfc = new JFileChooser();
        return jfc.getSelectedFile();
    }

    private void mouseClicked(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
            Desktop.getDesktop().open(getMovieFile());
        }
    }

}
