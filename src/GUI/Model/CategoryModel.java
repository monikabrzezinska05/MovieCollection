package GUI.Model;

import BE.Category;
import BLL.CategoryManager;

public class CategoryModel {
    CategoryManager categoryManager;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
    }

    public void createCategory(String name) throws Exception {
        Category category = categoryManager.createCategory(name);

    }

}
