package com.example.android.newsfeed;

public class NewsArticle
{
    private String mTitle;
    private String mDate;
    private String mSectionName;
    private String mUrl;

    /**
     * Constructor for a News Article
     * @param title - Title of the Article
     * @param date - Date when Article was published
     * @param sectionName - Section that the Article belongs to
     * @param url - url of the Article for viewing
     */
    public NewsArticle(String title, String date, String sectionName, String url)
    {
        mTitle       = title;
        mDate        = date;
        mSectionName = sectionName;
        mUrl         = url;
    }

    /**
     * Gets the title of the Article
     * @return - String of the article title
     */
    public String getTitle()
    {
        return mTitle;
    }

    /**
     * Gets the date that the Article was published
     * @return - String of the date that the Article was published
     */
    public String getDate()
    {
        return mDate;
    }

    /**
     * Gets the name of the section that the Article belongs to
     * @return - String of the name of the section that the Article belongs to
     */
    public String getSectionName()
    {
        return mSectionName;
    }

    /**
     * Gets the url of the Article for viewing on the web
     * @return - String of the url for the Article
     */
    public String getUrl()
    {
        return mUrl;
    }
}
