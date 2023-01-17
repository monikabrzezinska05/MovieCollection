package BE;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private int id;
    private String title;
    private String filepath;
    private java.sql.Date lastWatched;
    private int personalRating;
    private int IMDBRating;

    private String categories = "";

    private List<Category> categoryList;

    public Movie(int id, String title, String filepath, java.sql.Date lastWatched, int personalRating, int IMDBRating) {
        setId(id);
        setTitle(title);
        setFilepath(filepath);
        setLastWatched(lastWatched);
        setPersonalRating(personalRating);
        setIMDBRating(IMDBRating);

        this.categoryList = new ArrayList<>();
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

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;

        updateCategoryString();
    }

    private void updateCategoryString() {
        if(categoryList.size() > 0) {

            StringBuilder sb = new StringBuilder();

            for (Category c : categoryList) {
                sb.append(c.getName()).append(", ");
            }

            this.setCategories(sb.deleteCharAt(sb.length() - 2).toString());
        }
    }
}
