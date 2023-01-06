package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
   private MovieManager movieManager;

    private ObservableList<MovieTableModel> moviesObservableList;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        var movies = getAllMovies();

        moviesObservableList = FXCollections.observableArrayList();

        movies.forEach(movie -> moviesObservableList.add(new MovieTableModel(
            movie.getId(),
            movie.getTitle(),
            movie.getFilepath(),
            movie.getLastWatched(),
            movie.getPersonalRating(),
            movie.getIMDBRating()
        )));
    }

    public ObservableList<MovieTableModel> getMoviesObservableList() {
        return moviesObservableList;
    }

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception {
        return movieManager.createMovie(title, filepath, lastWatched, personalRating, IMDBRating);
    }

    public void deleteMovie(Movie deletedMovie) throws Exception {
        movieManager.deleteMovie(deletedMovie);
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieManager.getMovies();
    }

}
