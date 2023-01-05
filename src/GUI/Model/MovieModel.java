package GUI.Model;

import BE.Movie;
import BLL.MovieManager;

import java.util.List;

public class MovieModel {
   MovieManager movieManager;

    public MovieModel() {
        movieManager = new MovieManager();
    }

    public void createMovie(int id, String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception {
        movieManager.createMovie(id, title, filepath, lastWatched, personalRating, IMDBRating);
    }

    public void deleteMovie(Movie deletedMovie) throws Exception {
        movieManager.deleteMovie(deletedMovie);
    }
    public List<Movie> getMovies() {
        return getMovies();
    }

}
