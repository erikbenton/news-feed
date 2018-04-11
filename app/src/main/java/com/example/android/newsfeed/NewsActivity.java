package com.example.android.newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity
{
    // All the views that need to be grabbed
    private ListView mListView;

    // Adapter for the News Articles
    private NewsAdapter mAdapter;

    // Hardcoded JSON response
    private String JSON_RESPONSE = "{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":24644,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2465,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"science/occams-corner/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2017-11-06T11:14:55Z\",\"webTitle\":\"Universities are part of the solution to dysfunctional Brexit debates\",\"webUrl\":\"https://www.theguardian.com/science/occams-corner/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates\",\"apiUrl\":\"https://content.guardianapis.com/science/occams-corner/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"society/2017/oct/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law\",\"type\":\"article\",\"sectionId\":\"society\",\"sectionName\":\"Society\",\"webPublicationDate\":\"2017-10-19T05:30:45Z\",\"webTitle\":\"Paul Keating says assisted dying 'unacceptable' as Victoria debates law\",\"webUrl\":\"https://www.theguardian.com/society/2017/oct/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law\",\"apiUrl\":\"https://content.guardianapis.com/society/2017/oct/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/blog/live/2018/jan/09/reshuffle-government-tory-cabinet-theresa-may-not-quite-says-new-tory-chair-when-asked-about-party-being-in-a-mess-politics-live\",\"type\":\"liveblog\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-01-09T17:58:13Z\",\"webTitle\":\"Brexit department announces concessions over EU withdrawal bill ahead of key debates next week - Politics live\",\"webUrl\":\"https://www.theguardian.com/politics/blog/live/2018/jan/09/reshuffle-government-tory-cabinet-theresa-may-not-quite-says-new-tory-chair-when-asked-about-party-being-in-a-mess-politics-live\",\"apiUrl\":\"https://content.guardianapis.com/politics/blog/live/2018/jan/09/reshuffle-government-tory-cabinet-theresa-may-not-quite-says-new-tory-chair-when-asked-about-party-being-in-a-mess-politics-live\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"music/2018/mar/09/debate-over-nmes-heyday\",\"type\":\"article\",\"sectionId\":\"music\",\"sectionName\":\"Music\",\"webPublicationDate\":\"2018-03-09T17:05:42Z\",\"webTitle\":\"Debate over NME’s heyday | Letters\",\"webUrl\":\"https://www.theguardian.com/music/2018/mar/09/debate-over-nmes-heyday\",\"apiUrl\":\"https://content.guardianapis.com/music/2018/mar/09/debate-over-nmes-heyday\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"technology/2018/apr/09/killer-robots-pressure-builds-for-ban-as-governments-meet\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-04-09T10:35:50Z\",\"webTitle\":\"Killer robots: pressure builds for ban as governments meet\",\"webUrl\":\"https://www.theguardian.com/technology/2018/apr/09/killer-robots-pressure-builds-for-ban-as-governments-meet\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/apr/09/killer-robots-pressure-builds-for-ban-as-governments-meet\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"teacher-network/2017/jul/04/is-technology-delivering-in-schools-our-panel-debates\",\"type\":\"article\",\"sectionId\":\"teacher-network\",\"sectionName\":\"Teacher Network\",\"webPublicationDate\":\"2017-07-04T11:05:54Z\",\"webTitle\":\"Is technology delivering in schools? Our panel debates\",\"webUrl\":\"https://www.theguardian.com/teacher-network/2017/jul/04/is-technology-delivering-in-schools-our-panel-debates\",\"apiUrl\":\"https://content.guardianapis.com/teacher-network/2017/jul/04/is-technology-delivering-in-schools-our-panel-debates\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/mar/30/transgender-acceptance-media-international-day-visibility\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-03-30T07:59:15Z\",\"webTitle\":\"Trans visibility is greater than ever – but that's a double-edged sword | Shon Faye\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/mar/30/transgender-acceptance-media-international-day-visibility\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/mar/30/transgender-acceptance-media-international-day-visibility\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"education/2018/feb/05/removed-painting-is-subtly-but-unmistakably-paedophilic\",\"type\":\"article\",\"sectionId\":\"education\",\"sectionName\":\"Education\",\"webPublicationDate\":\"2018-02-05T18:03:48Z\",\"webTitle\":\"Art’s removal prompted debate | Letters\",\"webUrl\":\"https://www.theguardian.com/education/2018/feb/05/removed-painting-is-subtly-but-unmistakably-paedophilic\",\"apiUrl\":\"https://content.guardianapis.com/education/2018/feb/05/removed-painting-is-subtly-but-unmistakably-paedophilic\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"society/2017/oct/20/victorias-parliament-debates-voluntary-assisted-dying-bill-for-24-hours-straight\",\"type\":\"article\",\"sectionId\":\"society\",\"sectionName\":\"Society\",\"webPublicationDate\":\"2017-10-19T23:14:54Z\",\"webTitle\":\"Victoria's parliament debates voluntary assisted dying bill for 24 hours straight\",\"webUrl\":\"https://www.theguardian.com/society/2017/oct/20/victorias-parliament-debates-voluntary-assisted-dying-bill-for-24-hours-straight\",\"apiUrl\":\"https://content.guardianapis.com/society/2017/oct/20/victorias-parliament-debates-voluntary-assisted-dying-bill-for-24-hours-straight\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2018/mar/13/childcare-voucher-changes-delayed-after-commons-debate\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-03-13T19:53:59Z\",\"webTitle\":\"Childcare voucher changes delayed after Commons debate\",\"webUrl\":\"https://www.theguardian.com/politics/2018/mar/13/childcare-voucher-changes-delayed-after-commons-debate\",\"apiUrl\":\"https://content.guardianapis.com/politics/2018/mar/13/childcare-voucher-changes-delayed-after-commons-debate\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Get the ListView
        mListView = findViewById(R.id.list);

        // Create the adapter
        mAdapter = new NewsAdapter(this, new ArrayList<NewsArticle>());

        mAdapter.addAll(QueryUtils.extractArticles(JSON_RESPONSE));

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
    }
}
