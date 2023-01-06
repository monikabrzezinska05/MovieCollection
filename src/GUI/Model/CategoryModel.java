package GUI.Model;

import BE.Category;
import BLL.CategoryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel {

    private ObservableList<Category> categoryObservableList;

    private CategoryManager categoryManager;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
        categoryObservableList = FXCollections.observableArrayList(categoryManager.getCategories());
    }

    public ObservableList<Category> getCategoryObservableList() {
        return categoryObservableList;
    }

    public Category createCategory(String name) throws Exception {
        Category category = categoryManager.createCategory(name);
        return category;
    }

    public void deleteCategory(Category category) throws Exception {
        categoryManager.deleteCategory(category);
    }

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }
}
