package BLL;

import BE.Category;
import BE.Movie;
import DAL.db.MovieDAO_DB;

import java.util.List;

public class MovieManager {

    private MovieDAO_DB movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception{
        return movieDAO.createMovie(title, filepath, lastWatched, personalRating, IMDBRating);
    }

    public void deleteMovie(Movie movie) throws Exception{
        movieDAO.deleteMovie(movie);
    }

}
