package GUI.Model;

import BE.Category;
import BLL.CategoryManager;

public class CategoryModel {
    CategoryManager categoryManager;

    public CategoryModel() {
        categoryManager = new CategoryManager();
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
