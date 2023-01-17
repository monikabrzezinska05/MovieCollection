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

    public List<Movie> getMovies(List<Category> categories) throws Exception {
        return movieDAO.getAllMovies(categories);
    }

    public Movie getMovieById(int id, List<Category> categories) throws Exception {
        return movieDAO.getMovieById(id, categories);
    }

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception{
        return movieDAO.createMovie(title, filepath, lastWatched, personalRating, IMDBRating);
    }

    public void deleteMovie(Movie movie) throws Exception{
        movieDAO.deleteMovie(movie);
    }

    public void updateRating(Movie updatedRating) throws Exception{
        movieDAO.updateRating(updatedRating);
    }


    public void addCategoryToMovie(Movie newMovie, Category c) throws Exception {
        movieDAO.addCategoryToMovie(newMovie, c);
    }
}
