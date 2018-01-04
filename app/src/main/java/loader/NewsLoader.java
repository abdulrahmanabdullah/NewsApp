package loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import abdulrahmanjavanrd.com.mynewsapp.model.News;
import utilties.QueryUtils;

/**
 * @author Abdulrahman.A on 04/01/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String TAG = NewsLoader.class.getSimpleName();
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }


    @Override
    protected void onStartLoading() {
        Log.i(TAG, "Here,onStartLoading");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        Log.i(TAG, "Here, Start Download Files .");
        if (url == null)
            return null;
        List<News> newsList = QueryUtils.fetchNewsData(url);
        return newsList;
    }

}
