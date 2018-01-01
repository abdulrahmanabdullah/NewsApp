package abdulrahmanjavanrd.com.mynewsapp.app;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.adapter.MainAdapter;
import abdulrahmanjavanrd.com.mynewsapp.model.News;
import abdulrahmanjavanrd.com.mynewsapp.utlities.HttpHandler;

public class MainActivity extends AppCompatActivity {

    private static final String REQUEST_URL = "https://content.guardianapis.com/search?q=/tags&page-size=20&order-date=published&show-fields=shortUrl,thumbnail&show-blocks=body&api-key=test";
    ArrayList<News> mList ;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        //Declare ListView .
         listView = findViewById(R.id.listView);
        /** Here Call {@link MyTask} */
        new MyTask().execute();
    }

    /**
     * Inner Class to do long operation,Fetch json Data from {@link #REQUEST_URL}
     */
    private class MyTask extends AsyncTask<Void,Void,Void>{

        private final String TAG = MyTask.class.getSimpleName();
        @Override
        protected void onPreExecute() {
           //TODO: Create ProgressDialog .
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /** First declare {@link mList} then  Create object of {@link HttpHandler} and then set {@link #REQUEST_URL},
             *                          into {@link #callHttpConnection} ;
             * */
            mList = new ArrayList<>();
            HttpHandler handler = new HttpHandler();
            String uri = handler.callHttpConnection(REQUEST_URL);
            if (uri != null) {
                try {
                    JSONObject baseJson = new JSONObject(uri);
                    JSONObject root = baseJson.getJSONObject("response");
                    JSONArray mainArray = root.getJSONArray("results");
                    // for loop to fetch all data inside this array .
                    for(int i = 0 ; i< mainArray.length();i++){
                        // Get every element inside mainArray .
                        JSONObject index = mainArray.getJSONObject(i);
                        //First get Title , Section , Date
                        String webTitle = index.getString("webTitle");
                        String sectionName = index.getString("sectionName");
                        String date = index.getString("webPublicationDate");
                        // Now fetch image and Article summary
                        JSONObject fieldes = index.getJSONObject("fields");
                        String thumbanil = fieldes.getString("thumbnail");
                        // Now fetch summary text from blocks
                        JSONObject blocks = index.getJSONObject("blocks");
                        // Now get all array inside this blocks .
                        JSONArray bodyArray = blocks.getJSONArray("body");
                        // Now fetch data in bodyArray .
                        JSONObject secondIndex = bodyArray.getJSONObject(0);
                        // Now i can access the all elements inside this array .
                        String articleSummary =secondIndex.getString("bodyTextSummary");
                        // Now save this values in News class .
                        News mNews = new News(webTitle,articleSummary,sectionName,thumbanil,date,"Abdulrahman");
                        mList.add(mNews);

                    }

                }catch (JSONException e)
                {
                    Log.e(TAG, "Create JS Failed,Please check the URI and Connection " + e.getMessage());
                }
            }else{
                Log.e(TAG,"Wrong Uri ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Wrong URL ",Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            //TODO: create object of {@link MainAdapter} and passing into {@link #listView} as Adapter .
            MainAdapter adapter = new MainAdapter(MainActivity.this,mList);
            listView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }
}
