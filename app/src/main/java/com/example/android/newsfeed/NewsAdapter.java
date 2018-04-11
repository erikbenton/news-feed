package com.example.android.newsfeed;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsArticle>
{
    // Variables for the class
    private Context mContext;
    private List<NewsArticle> newsList;

    /**
     * Constructor for the adapter
     * @param context - Context the adapter is used in
     * @param list - List of news articles for the adapter
     */
    public NewsAdapter(@NonNull Activity context, ArrayList<NewsArticle> list)
    {
        super(context, 0, list);
        mContext = context;
        newsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // Get the current view
        View listItem = convertView;

        // Get the current Article
        NewsArticle currentArticle = newsList.get(position);

        // Get the display variables form the current Article
        String title       = currentArticle.getTitle();
        String sectionName = currentArticle.getSectionName();

        // Inflate the ListView item for filling with display values
        if(listItem == null)
        {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.news_list, parent, false);
        }

        // Get the Views for display
        TextView titleView   = listItem.findViewById(R.id.title_view);
        TextView sectionView = listItem.findViewById(R.id.section_view);

        // Set appropriate values for display
        titleView.setText(title);
        sectionView.setText(sectionName);

        return listItem;
    }
}
