package abdulrahmanjavanrd.com.mynewsapp.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.adapter.MyAdapter;
import abdulrahmanjavanrd.com.mynewsapp.model.News;

public class MainActivity extends AppCompatActivity {

    // Fetch 50 pages,And get last news,Then get same data for each articles like title,section,date,img.
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?q=/tags&page-size=50&order-by=newest&show-fields=shortUrl,thumbnail&show-blocks=body&api-key=test";
    ArrayList<News> mList;
    ListView listView;
    // Progress Dialog .
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        //Declare ListView .
        listView = findViewById(R.id.listView);
        // Declare ProgressDialog .
        progressDialog = new ProgressDialog(this);
        /** Here Call {@link MyTask} */
        new MyTask().execute();
    }

    /**
     * Inner Class to do long operation,Fetch json Data from {@link #REQUEST_URL}
     */
    private class MyTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = MyTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            /** call  {@link #showProgressDialog()} */
            showProgressDialog();
        }

        private void showProgressDialog() {
            progressDialog.setTitle("Connect to guardianApis");
            progressDialog.setMessage("Please Wait until fetch data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /** First declare {@link mList} then  Create object of {@link HttpHandler} and then set {@link #REQUEST_URL},
             *                          into {@link #callHttpConnection} ;
             * */
            mList = new ArrayList<>();
            URL theURL = createUrl(REQUEST_URL);
            String url;
            try {
                url = makeHttpRequest(theURL);
                JSONObject baseJson = new JSONObject(url);
                JSONObject root = baseJson.getJSONObject("response");
                JSONArray mainArray = root.getJSONArray("results");
                // for loop to fetch all data inside this array .
                for (int i = 0; i < mainArray.length(); i++) {
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
                    String articleSummary = secondIndex.getString("bodyTextSummary");
                    // Now save this values in News class .
                    News mNews = new News(webTitle, articleSummary, sectionName, thumbanil, date);
                    mList.add(mNews);
                }

            } catch (IOException e) {
                Log.e(TAG, "Can't connect the website " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Please Connect the Network", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException e) {
                Log.e(TAG, "Not Found JSON Object, Please check key name." + e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            /** create object of {@link MyAdapter} and passing into {@link #listView} as Adapter,
             check if {@link progressDialog} is showing */
            if (progressDialog.isShowing())
                progressDialog.cancel();
            MyAdapter adapter = new MyAdapter(MainActivity.this, mList);
            listView.setAdapter(adapter);
        }

        /**
         * @param strUrl webSite link .
         * @return url object .
         */
        private URL createUrl(String strUrl) {
            URL url = null;
            try {
                url = new URL(strUrl);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Error with create URL" + e.getMessage());
            }
            return url;
        }

        private String makeHttpRequest(URL theUrl) throws IOException {
            String response = null;
            HttpURLConnection connection;
            InputStream inputStream;
            try {
                connection = (HttpURLConnection) theUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    Log.e(TAG, "Connection successful .");
                    inputStream = connection.getInputStream();
                    response = convertStreamToString(inputStream);
                } else {
                    Log.e(TAG, "Please check your URL, Bad Connection ");
                }
            } catch (IOException e) {
                Log.e(TAG, "Please connect the network " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Please Connect the Network", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return response;
        }

        private String convertStreamToString(InputStream inputStream) throws IOException {
            StringBuilder sb = new StringBuilder();
            String line;
            // AutoClosable
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                Log.e(TAG, " Can't Convert Data something wrong " + e.getMessage());
            }
            return sb.toString();
        }
    }
}
