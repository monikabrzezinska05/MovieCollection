package GUI.Controller;

import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;

public abstract class BaseController {

    private MovieModel moviemodel;
    private CategoryModel categoryModel;

    public MovieModel getMoviemodel() {
        return moviemodel;
    }

    public void setMoviemodel(MovieModel moviemodel) {
        this.moviemodel = moviemodel;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

}
