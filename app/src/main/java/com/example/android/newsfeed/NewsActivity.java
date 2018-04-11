package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticle>>
{
    // All the views that need to be grabbed
    private ListView mListView;

    // Adapter for the News Articles
    private NewsAdapter mAdapter;

    // Loader for the News Articles
    private LoaderManager mLoaderManager;
    private static final int NEWS_ARTICLE_ID = 1;


    // Hardcoded JSON response
    private String NEWS_URL = "http://content.guardianapis.com/search?q=debates&api-key=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Get the ListView
        mListView = findViewById(R.id.list);

        // Create the adapter
        mAdapter = new NewsAdapter(this, new ArrayList<NewsArticle>());

        // Set adapter for the ListView
        mListView.setAdapter(mAdapter);

        // Set it so that a click on an item goes to that article
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // Get the article that was clicked on
                NewsArticle article = mAdapter.getItem(position);

                // Get the url from the article
                String url = article.getUrl();

                // Create Intent for opening the webpage
                Intent openWebpage = new Intent(Intent.ACTION_VIEW);
                openWebpage.setData(Uri.parse(url));
                startActivity(openWebpage);
            }
        });

        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(NEWS_ARTICLE_ID, null, this);
    }

    /**
     * Creates a News Loader
     * @param id - ID of the loader
     * @param args
     * @return Loader with the List of News Articles
     */
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle args)
    {
        return new NewsLoader(this, NEWS_URL + getString(R.string.API_KEY));
    }

    /**
     * When the Loader is finished, add all the data to the adapter
     * @param loader - Loader that finished
     * @param articles - List of News Articles to add
     */
    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> articles)
    {
        // Clear the adapter
        mAdapter.clear();

        if(articles != null && !articles.isEmpty())
        {
            // Add all the new stuff to the adapter
            mAdapter.addAll(articles);
        }
    }

    /**
     * If the loader resets then clear the adapter
     * @param loader - Loader that reset
     */
    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader)
    {
        mAdapter.clear();
    }
}
