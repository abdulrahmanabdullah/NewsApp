package abdulrahmanjavanrd.com.mynewsapp.app;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.adapter.MyAdapter;
import abdulrahmanjavanrd.com.mynewsapp.model.News;
import loader.NewsLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    // TAG
    private static final String TAG = MainActivity.class.getSimpleName();
    // Fetch 50 pages,And get last news,Then get some data for each articles like title,section,date,img,web url.
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?q=/tags&page-size=50&order-by=newest&show-fields=shortUrl,thumbnail&show-blocks=body&api-key=test";
    // Loader ID
    private static final int PROCESS_ID = 1;
    // ListView
    ListView listView;
    // Adapter object .
    MyAdapter adapter;
    // ProgressBar .
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        //Declare ListView .
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        getLoaderManager().initLoader(PROCESS_ID, null, this);
        Log.i(TAG, "initLoader and get the process id = " + PROCESS_ID);
    }

    /**
     * @param news = list of {@link News}
     */
    private void updateUi(final List<News> news) {
        adapter = new MyAdapter(MainActivity.this, news);
        listView.setAdapter(adapter);
        // create onClick item Listener .
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentObject = news.get(position);
                Uri uri = Uri.parse(currentObject.getWebUrl());
                Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mIntent);
            }
        });
    }

    /**
     * @param id   the process ID,
     * @param args argument in this app set null .
     * @return New Loader if @param id ==  {@link #PROCESS_ID}, Else return null
     */
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader");
        if (id == PROCESS_ID) {
            Log.i(TAG, "Hi We receive this id = " + id);
            return new NewsLoader(this, REQUEST_URL);
        }
        return null;
    }


    // When process finished.
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        if (data != null && !data.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            updateUi(data);
        }
        Log.i(TAG, "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        /** set Empty list in {@link adapter.listOfNews} */
        adapter.setListOfNews(new ArrayList<News>());
        Log.i(TAG, "onLoaderRest");

    }
}
