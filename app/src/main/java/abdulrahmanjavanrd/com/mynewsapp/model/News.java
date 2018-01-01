package abdulrahmanjavanrd.com.mynewsapp.model;

/**
 * @author  Abdulrahman.A on 01/01/2018.
 */

public class News {
    //Article Title .
    private String title ;
    // Article Summary .
    private String summary ;
    //Type of  Section .
    private String section ;
    //img for each articles.
    private String image ;
    // date
    private String date ;
    // author name .
    private String author ;

    public News(String title,String summary , String section, String image, String date, String author) {
        this.title = title;
        this.summary = summary;
        this.section = section;
        this.image = image;
        this.date = date;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getSection() {
        return section;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }
}