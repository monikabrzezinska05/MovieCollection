package DAL.db;

import BE.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB {
    private DatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new DatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {
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
                    int IMDBRating = resultSet.getInt("IMDBRating");

                    Movie movie = new Movie(id, title, filepath, lastwatched, personalRating, IMDBRating);
                    allMovies.add(movie);
                }
            }
        }
        return allMovies;
    }

    public Movie createMovie(int id, String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) throws Exception{
        String sql = "INSERT INTO Movies (Id, Title, FilePath, LastWatched, PersonalRating, IMDBRating) VALUES (?,?,?,?,?,?);";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            //Bind parameters.
            statement.setInt(1, id);
            statement.setString(2, title);
            statement.setString(3, filepath);
            statement.setDate(4, lastWatched);
            statement.setInt(5, personalRating);
            statement.setInt(6, IMDBRating);

            //Run the specified SQL statement.
            statement.executeUpdate();

            //Get the generated ID from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int Id = 0;

            if(rs.next()) {
                Id = rs.getInt(1);
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
            String sql = "DELETE FROM Categories WHERE Id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movie.getId());

            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

}
