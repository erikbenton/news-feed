package com.example.android.newsfeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils
{
    // Used in error logs
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Constructor for class
     */
    private QueryUtils(){}

    /**
     * Extracts the data from the JSON response and creates a list of News Articles
     * @param articlesJson - JSON data for all the News Articles
     * @return List of all the News Articles
     */
    public static List<NewsArticle> extractArticles(String articlesJson)
    {
        // Check for nothing in the JSON
        if(TextUtils.isEmpty(articlesJson))
        {
            return null;
        }

        // Create List for storing News Articles
        List<NewsArticle> articles = new ArrayList<>();

        try
        {
            // Turn JSON string in JSON object
            JSONObject articlesJsonObject = new JSONObject(articlesJson);

            // Then get array of articles from the object
            JSONArray articleJsonArray = articlesJsonObject.getJSONArray("results");

            // Extract whats needed from each entry in array
            for(int i = 0; i < articleJsonArray.length(); i++)
            {
                // Get the current News Article
                JSONObject currentArticle = articleJsonArray.getJSONObject(i);

                // All the variables for a News Article
                String title;
                String date;
                String sectionName;
                String url;

                // Get data from the current News Article
                title = currentArticle.getString("webTitle");
                date = currentArticle.getString("webPublicationDate");
                sectionName = currentArticle.getString("sectionName");
                url = currentArticle.getString("webUrl");

                // Store it all into the News Article List
                articles.add(new NewsArticle(title, date, sectionName, url));
            }
        }
        catch(JSONException e)
        {
            Log.e(LOG_TAG, "Problem creating JSON object", e);
        }
        return articles;
    }
}
