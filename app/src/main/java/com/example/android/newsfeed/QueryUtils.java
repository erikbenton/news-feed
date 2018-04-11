package com.example.android.newsfeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
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

            JSONObject responseJsonObject = articlesJsonObject.getJSONObject("response");

            // Then get array of articles from the object
            JSONArray articleJsonArray = responseJsonObject.getJSONArray("results");

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

    /**
     * Gets the News Article data from the desired request url
     * @param requestUrl - URL string to make request to
     * @return List of News Articles in the request url
     */
    public static List<NewsArticle> fetchArticleData(String requestUrl)
    {
        // Create a URL from the requestUrl
        URL url = createUrl(requestUrl);

        // Init the JSON Response
        String jsonResponse = null;

        // Try to make a request for the jsonData
        try
        {
            jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e)
        {
            // Log that the HTTP request didn't work
            Log.e(LOG_TAG, "Unable to make HTTP request", e);
        }

        // Get the extracted articles from the json response and return
        return extractArticles(jsonResponse);
    }

    /**
     * Creates a URL from the given string
     * @param stringUrl - String of the desired URL
     * @return URL object from given string
     */
    private static URL createUrl(String stringUrl)
    {
        // Init url object
        URL url = null;

        // Try making the string into a URL
        try
        {
            url = new URL(stringUrl);
        }
        catch(MalformedURLException e)
        {
            Log.e(LOG_TAG, "Unable to create URL from string", e);
        }

        return url;
    }

    /**
     * Makes the http request and gets the JSON response
     * @param url - URL to make the request to
     * @return JSON response as a String
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException
    {
        // Init the JSON Response String
        String jsonResponse = "";

        // Check if the url is null
        if(url == null)
        {
            return jsonResponse; //Return empty String
        }

        // Init the urlConnection and inputStream
        HttpURLConnection urlConnection = null;
        InputStream       inputStream   = null;

        // Try to make a connection
        try
        {
            // Open the connection
            urlConnection = (HttpURLConnection) url.openConnection();

            // Set the Read and Connect Timeouts
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            // Set up the method of request
            urlConnection.setRequestMethod("GET");

            // Get the response code
            int responseCode = urlConnection.getResponseCode();

            // Make sure we're good to go
            if(responseCode == 200)
            {
                // Get and input stream and read from it
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else // Log the response code
            {
                Log.e(LOG_TAG, "Error making connection, response code: " + responseCode);
            }
        }
        catch(IOException e)
        {
            // Log error if connection can't be made
            Log.e(LOG_TAG, "Error retrieving JSON results", e);
        }
        finally
        {
            // If the URL Connection is null, disconnect
            if(urlConnection == null)
            {
                urlConnection.disconnect();
            }

            // If the input stream isn't null
            // then we got stuff and need to close it
            if(inputStream != null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Reads the InputStream from the HTTP request and returns the response as a String
     * @param inputStream - Input Stream to read from
     * @return String of the data read from the Input Stream
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        // Create StringBuilder for making string from stream
        StringBuilder output = new StringBuilder();

        // Check if inputStream is null
        if(inputStream != null)
        {
            // Create input stream reader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            // Create a BufferedReader to parse input stream more steadily
            BufferedReader reader = new BufferedReader(inputStreamReader);

            // Create String for reading lines from the BufferedReader
            String line = reader.readLine();

            // As long as there are lines to read from the BufferedReader
            // Then keep reading them
            while(line != null)
            {
                // Add the line to the output
                output.append(line);

                // Read new line
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
