package GUI.Model;

import BE.Category;
import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
   private MovieManager movieManager;

    private ObservableList<Movie> moviesObservableList;
    private Movie selectedMovie;

    public MovieModel(List<Category> categories) throws Exception {
        movieManager = new MovieManager();
        var movies = getAllMovies(categories);

        moviesObservableList = FXCollections.observableArrayList();
        moviesObservableList.addAll(movies);
    }

    public ObservableList<Movie> getMoviesObservableList() {
        return moviesObservableList;
    }

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, float IMDBRating) throws Exception {
        return movieManager.createMovie(title, filepath, lastWatched, personalRating, IMDBRating);
    }

    public void deleteMovie(Movie deletedMovie) throws Exception {
        movieManager.deleteMovie(deletedMovie);
        moviesObservableList.remove(deletedMovie);
    }

    public List<Movie> getAllMovies(List<Category> categories) throws Exception {
        return movieManager.getMovies(categories);
    }

    public Movie getMovieById(int id, List<Category> categories) throws Exception {
        return movieManager.getMovieById(id, categories);
    }

    public void updateMovie(Movie updatedRating, List<Category> categories) throws Exception {
        movieManager.updateRating(updatedRating);
        moviesObservableList.clear();
        moviesObservableList.addAll(movieManager.getMovies(categories));
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }
    
    public Movie getSelectedMovie(){
        return selectedMovie;
    }

    public void addCategoryToMovie(Movie newMovie, Category c) throws Exception {
        movieManager.addCategoryToMovie(newMovie, c);
    }

    public void setLastTimeWatched(Movie movie, java.sql.Date date) throws Exception {
        movie.setLastWatched(date);
        movieManager.setLastTimeWatched(movie, date);
    }
}
