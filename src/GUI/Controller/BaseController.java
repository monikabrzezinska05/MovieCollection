package GUI.Controller;

import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;

public abstract class BaseController {

    public MovieModel movieModel;
    private CategoryModel categoryModel;

    public MovieModel getMovieModel() {
        return movieModel;
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public abstract void setup();

}
