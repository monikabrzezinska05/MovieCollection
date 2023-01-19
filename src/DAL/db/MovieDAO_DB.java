package DAL.db;

import BE.Category;
import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.controlsfx.control.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB {
    private DatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new DatabaseConnector();
    }

    public List<Movie> getAllMovies(List<Category> categoriesList) throws Exception {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Movies;";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String title = resultSet.getString("Title");
                    String filepath = resultSet.getString("FilePath");
                    java.sql.Date lastwatched = resultSet.getDate("LastWatched");
                    int personalRating = resultSet.getInt("PersonalRating");
                    float IMDBRating = resultSet.getFloat("IMDBRating");

                    Movie movie = new Movie(id, title, filepath, lastwatched, personalRating, IMDBRating);

                    var categories = getMoviesCategories(movie, categoriesList);

                    movie.setCategoryList(categories);

                    allMovies.add(movie);
                }
            }
        }
        return allMovies;
    }



    public Movie getMovieById(int movieId, List<Category> categoryList) throws Exception {
        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Movies WHERE Id = ?;";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movieId);

            var resultSet = statement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("Id");
                String title = resultSet.getString("Title");
                String filepath = resultSet.getString("FilePath");
                java.sql.Date lastwatched = resultSet.getDate("LastWatched");
                int personalRating = resultSet.getInt("PersonalRating");
                float IMDBRating = resultSet.getFloat("IMDBRating");

                Movie movie = new Movie(id, title, filepath, lastwatched, personalRating, IMDBRating);

                var categories = getMoviesCategories(movie, categoryList);

                if(categories.size() > 0) {

                    StringBuilder sb = new StringBuilder();

                    for (Category c : categories) {
                        sb.append(c.getName()).append(", ");
                    }

                    movie.setCategories(sb.deleteCharAt(sb.length() - 2).toString());
                }

                return movie;

            }
        }
        return null;
    }

    public List<Category> getMoviesCategories(Movie movie, List<Category> categoryList) throws Exception {

        ArrayList<Category> categories = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT Category FROM MovieCategoryRelation WHERE Movie = ?;";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movie.getId());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                String category = resultSet.getString(1);
                var res = categoryList.stream().filter(c -> c.getName().equals(category)).findFirst();
                res.ifPresent(categoryToAdd -> categories.add(categoryToAdd));
            }
        }
        return categories;
    }

    public Movie createMovie(String title, String filepath, java.sql.Date lastWatched, int personalRating, float IMDBRating) throws Exception{
        String sql = "INSERT INTO Movies (Title, FilePath, LastWatched, PersonalRating, IMDBRating) VALUES (?,?,?,?,?);";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters.
            statement.setString(1, title);
            statement.setString(2, filepath);
            statement.setDate(3, lastWatched);
            statement.setInt(4, personalRating);
            statement.setFloat(5, IMDBRating);

            //Run the specified SQL statement.
            statement.executeUpdate();

            //Get the generated ID from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()) {
                id = rs.getInt(1);
            }

            //Create movie object and send up the layers.
            Movie movie = new Movie(id, title,  filepath, lastWatched, personalRating, IMDBRating);
            return movie;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create category", ex);
        }
    }

    public void deleteMovie(Movie movie) throws Exception {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM MovieCategoryRelation WHERE Movie = ?;DELETE FROM Movies WHERE Id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movie.getId());
            statement.setInt(2, movie.getId());

            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public void updateRating(Movie movie) throws Exception {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Movies SET PersonalRating = ? WHERE Id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movie.getPersonalRating());
            statement.setInt(2, movie.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public void addCategoryToMovie(Movie movie, Category c) throws Exception {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO MovieCategoryRelation (Category, Movie) VALUES (?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, c.getName());
            statement.setInt(2, movie.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }
}
