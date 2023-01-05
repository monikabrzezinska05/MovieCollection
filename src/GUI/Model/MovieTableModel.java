package GUI.Model;

import BE.Category;
import BE.Movie;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MovieTableModel extends Movie {

    List<Category> categories;

    public MovieTableModel(int id, String title, String filepath, Date lastWatched, int personalRating, int IMDBRating) {
        super(id, title, filepath, lastWatched, personalRating, IMDBRating);

        categories = new ArrayList<>();
    }


}
