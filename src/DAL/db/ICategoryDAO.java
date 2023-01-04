package DAL.db;

import BE.Category;

public interface ICategoryDAO {

    Category createCategory(String name) throws Exception;

    void deleteCategory(Category category) throws Exception;

}
