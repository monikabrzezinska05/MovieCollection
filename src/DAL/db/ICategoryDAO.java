package DAL.db;

import BE.Category;

import java.util.List;

public interface ICategoryDAO {

    Category createCategory(String name) throws Exception;

    List<Category> getCategories() throws Exception;

    void deleteCategory(Category category) throws Exception;

}
