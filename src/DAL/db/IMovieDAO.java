package DAL.db;

import BE.Category;
import BE.Movie;

import java.sql.Date;
import java.util.List;

public interface IMovieDAO {

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, float IMDBRating) throws Exception;

    List<Movie> getAllMovies(List<Category> categoriesList) throws Exception;

    void deleteMovie(Movie movie) throws Exception;

    public void updateRating(Movie movie) throws Exception;

    public List<Category> getMoviesCategories(Movie movie, List<Category> categoryList) throws Exception;

    public Movie getMovieById(int movieId, List<Category> categoryList) throws Exception;

    public void addCategoryToMovie(Movie movie, Category c) throws Exception;

    public void setLastTimeWatched(Movie movie, Date date) throws Exception;
    }
