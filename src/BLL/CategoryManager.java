package BLL;

import BE.Category;
import DAL.db.CategoryDAO_DB;

import java.util.List;

public class CategoryManager {

    private CategoryDAO_DB categoryDAO;

    public CategoryManager() { categoryDAO = new CategoryDAO_DB(); }

    public List<Category> getCategories() throws Exception{
        return categoryDAO.getCategories();
    }

    public Category createCategory(String name) throws Exception{
        return categoryDAO.createCategory(name);
    }

    public void deleteCategory(Category deletedCategory) throws Exception{
        categoryDAO.deleteCategory(deletedCategory);
    }

}
