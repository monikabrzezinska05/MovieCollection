package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
   private MovieManager movieManager;

    private ObservableList<Movie> moviesObservableList;
    private Movie selectedMovie;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        var movies = getAllMovies();

        moviesObservableList = FXCollections.observableArrayList();
        moviesObservableList.addAll(movies);
    }

    public ObservableList<Movie> getMoviesObservableList() {
        return moviesObservableList;
    }

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception {
        return movieManager.createMovie(title, filepath, lastWatched, personalRating, IMDBRating);
    }

    public void deleteMovie(Movie deletedMovie) throws Exception {
        movieManager.deleteMovie(deletedMovie);
        moviesObservableList.remove(deletedMovie);
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieManager.getMovies();
    }

    public void updateMovie(Movie updatedRating) throws Exception {
        movieManager.updateRating(updatedRating);
        moviesObservableList.clear();
        moviesObservableList.addAll(movieManager.getMovies());
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }
    
    public Movie getSelectedMovie(){
        return selectedMovie;
    }
    
}
