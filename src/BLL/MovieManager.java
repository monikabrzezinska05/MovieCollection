package BLL;

import DAL.db.MovieDAOV_DB;

public class MovieManager {

    private MovieDAOV_DB movieDAOV;

    public MovieManager() { movieDAOV = new MovieDAOV_DB(); }
}
