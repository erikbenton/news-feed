package com.example.android.newsfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class NewsActivity extends AppCompatActivity
{
    // All the views that need to be grabbed
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Get the ListView
        mListView = findViewById(R.id.list);
    }
}
