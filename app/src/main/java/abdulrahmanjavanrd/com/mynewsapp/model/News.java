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
    // web url
    private String webUrl ;
    // web publisher .
    private String webPublisher ;

    public News(String title,String summary , String section, String image, String date,String webUrl,String webPublisher) {
        this.title = title;
        this.summary = summary;
        this.section = section;
        this.image = image;
        this.date = date;
        this.webUrl = webUrl ;
        this.webPublisher = webPublisher ;
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

    public String getWebUrl() {
        return webUrl;
    }

    public String getWebPublisher() {
        return webPublisher;
    }
}
