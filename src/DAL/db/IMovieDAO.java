package DAL.db;

import BE.Movie;

import java.util.List;

public interface IMovieDAO {

    Movie createMovie(int id, String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception;
    
    List<Movie> getMovies() throws Exception;

    void deleteMovie(Movie movie) throws Exception;

}
