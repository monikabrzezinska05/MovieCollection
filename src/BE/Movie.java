package BE;

import java.util.Date;

public class Movie {

    private int id;
    private String title;
    private String filepath;
    private java.sql.Date lastWatched;
    private int personalRating;
    private int IMDBRating;


    public Movie(int id, String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) {
        setId(id);
        setTitle(title);
        setFilepath(filepath);
        setLastWatched(lastWatched);
        setPersonalRating(personalRating);
        setIMDBRating(IMDBRating);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public java.sql.Date getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(java.sql.Date lastWatched) {
        this.lastWatched = lastWatched;
    }

    public int getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(int personalRating) {
        this.personalRating = personalRating;
    }

    public int getIMDBRating() {
        return IMDBRating;
    }

    public void setIMDBRating(int IMDBRating) {
        this.IMDBRating = IMDBRating;
    }

}
