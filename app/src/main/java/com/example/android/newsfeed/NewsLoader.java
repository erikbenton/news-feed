package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsArticle>>
{
    // Variable for holding http request url
    private String mUrl;

    /**
     * Contructor for News Loader
     * @param context - Context for the Loader
     * @param url - Url for the HTTP request
     */
    public NewsLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
    }

    /**
     * Force the loader to go when starting
     */
    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    /**
     * Get the article data from the Url in the background
     * @return List of the articles to display
     */
    @Override
    public List<NewsArticle> loadInBackground()
    {
        // Check to make sure that the URL isn't null
        if(mUrl == null)
        {
            return null;
        }

        // Get the article data from the url
        List<NewsArticle> articles = QueryUtils.fetchArticleData(mUrl);

        return articles;
    }
}
