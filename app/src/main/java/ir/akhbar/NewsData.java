package ir.akhbar;

public class NewsData {

    private String title;
    private String description;
    private String urlToImage;
    private String url;

    public NewsData(String title, String description, String urlToImage, String url) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public String getNewsTitle() {
        return title;
    }

    public String getNewsDescription() {
        return description;
    }

    public String getNewsImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }
}
