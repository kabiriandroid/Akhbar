package ir.akhbar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class NewsTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String urlToImage;
    private String url;

    public NewsTable(String title, String description, String urlToImage, String url) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }
}
