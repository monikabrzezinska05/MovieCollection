package DAL.db;

import BE.Category;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO_DB implements ICategoryDAO {
    private DatabaseConnector databaseConnector;

    public CategoryDAO_DB() {
        databaseConnector = new DatabaseConnector();
    }

    public Category createCategory(String name) throws Exception{
        String sql = "INSERT INTO Categories (Name) VALUES (?);";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            //Bind parameters.
            statement.setString(1, name);

            //Run the specified SQL statement.
            statement.executeUpdate();

            //Get the generated ID from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()) {
                id = rs.getInt(1);
            }

            //Create movie object and send up the layers.
            Category category = new Category(name);
            return category;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create category", ex);
        }
    }

    @Override
    public List<Category> getCategories() throws Exception {

        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Categories;";

            Statement statement = connection.createStatement();

            var resultSet = statement.executeQuery(sql);

            var resultList = new ArrayList<Category>();

            while(resultSet.next()) {
                resultList.add(new Category(
                    resultSet.getString("Name")
                ));
            }

            return resultList;
        }

    }

    public void deleteCategory(Category category) throws Exception {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Categories WHERE Name = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, category.getName());

            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

}
