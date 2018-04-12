package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticle>>
{
    // All the views that need to be grabbed
    private ListView mListView;

    // Adapter for the News Articles
    private NewsAdapter mAdapter;

    // Query input
    private EditText mQueryInput;

    // Query Button
    private Button mQueryButton;

    // Loader for the News Articles
    private LoaderManager mLoaderManager;
    private static final int NEWS_ARTICLE_ID = 1;

    // Hardcoded JSON response
    private static final String NEWS_URL = "http://content.guardianapis.com/search?";

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

        // Query views
        mQueryInput = findViewById(R.id.query_field);
        mQueryButton = findViewById(R.id.query_button);

        mQueryInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    performQuery();
                    handled = true;
                }
                return handled;
            }
        });

        // Set a click listener for when "Search" is clicked
        mQueryButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                performQuery();
            }
        });

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
     * Performs the query for the JSON data
     */
    private void performQuery()
    {
        // Hide the EmptyView
        //mEmptyView.setVisibility(View.GONE);

        //Show ProgressBar
        //mProgressBar.setVisibility(View.VISIBLE);

        // Clears focus from the EditText View
        mQueryInput.clearFocus();

        // Manager for handling the input
        InputMethodManager in = (InputMethodManager)NewsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

        // Hides the keyboard
        in.hideSoftInputFromWindow(mQueryInput.getWindowToken(), 0);

        // Init the loader
        mLoaderManager.restartLoader(NEWS_ARTICLE_ID, null, NewsActivity.this);
    }

    /**
     * When options menu is created, the menu is inflated
     * and ready to be filled
     * @param menu - Menu with search options
     * @return Boolean for when it completes
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflates the main menu layout
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * When an item in the options area of the app bar is selected -> choose what to do
     * @param item - Item that is clicked on
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Get the ID of the selected options item
        int id = item.getItemId();

        // If the id is that for the action_settings
        // then start that activity
        if(id == R.id.action_settings)
        {
            // Make intent for opening the settings in the app
            Intent settingsIntent = new Intent(this, SettingsActivity.class);

            // Start that activity
            startActivity(settingsIntent);

            //return true to mark done
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates the URL for the query and creates a News Loader
     * @param id - ID of the loader
     * @param args
     * @return Loader with the List of News Articles
     */
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle args)
    {
        // First, fill the shared preferences with the defaults
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Get the search settings
        String searchSection = sharedPrefs.getString
                (
                    getString(R.string.settings_section_key),
                    getString(R.string.settings_section_default)
                );

        // Get the search settings
        String searchTag = sharedPrefs.getString
                (
                        getString(R.string.settings_tag_key),
                        getString(R.string.settings_tag_default)
                );

        // Build URI to search with
        //Create base URI
        Uri baseUri = Uri.parse(NEWS_URL);

        // Create Uri builder
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // If there is something entered for Search Section
        if(!searchSection.equals(""))
        {
            // Add that to the query
            uriBuilder.appendQueryParameter("section", searchSection.trim());
        }

        // If there is something entered for Search Section
        if(!searchTag.equals(""))
        {
            searchTag = TextUtils.join("/", searchTag.trim().split("/"));
            // Add that to the query
            uriBuilder.appendQueryParameter("tag", searchTag.trim());
        }

        // Get query
        String query = mQueryInput.getText().toString();

        // Add the query to the search
        uriBuilder.appendQueryParameter("q", query);

        // Add the API Key
        uriBuilder.appendQueryParameter("api-key", getString(R.string.API_KEY));

        // Log the query URI
        Log.v("QUERY URL", uriBuilder.toString());

        return new NewsLoader(this, uriBuilder.toString());
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
