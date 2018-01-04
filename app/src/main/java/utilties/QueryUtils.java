package utilties;

import android.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import abdulrahmanjavanrd.com.mynewsapp.error.CheckError;
import abdulrahmanjavanrd.com.mynewsapp.model.News;

/**
 * Created by nfs05 on 04/01/2018.
 */

public class QueryUtils {

    // Magic Numbers .
    private static final  int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT =15000 ;
    //TAG value.
//    private static final String TAG = QueryUtils.class.getSimpleName();
    private QueryUtils(){}


    public static List<News> fetchNewsData(String thrUrl){
        URL url = createUrl(thrUrl);
        String jsonResponse = null ;
        try{
            jsonResponse = makeHttpRequest(url);

        }catch (IOException e){
//           Log.e(CheckError.TAG,"Error with fetch data method "+e.getMessage());
        }
        List<News> news = extractJsonData(jsonResponse);
        return news;
    }
    // create url.
    private static URL createUrl(String theUrl){
       URL url = null ;
       try
       {
           url = new URL(theUrl);
       } catch (MalformedURLException e) {
//           Log.e(CheckError.TAG,"Please check your Link URL "+ e.getMessage());
       }
       return url ;
    }


    private static String makeHttpRequest(URL url) throws IOException{
        String response  =null ;
        HttpURLConnection connection = null ;
        InputStream inputStream =null ;
        try{
            connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");
           connection.setReadTimeout(READ_TIMEOUT);
           connection.setConnectTimeout(CONNECT_TIMEOUT);
           // check if connection success .
            if (connection.getResponseCode() == 200){
                 inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToData(inputStream);
            }else {
//                Log.i(CheckError.TAG,"Bad Connection Please check url");
            }
        }catch (IOException e){
//            Log.e(CheckError.TAG,e.getMessage());
        }
        finally {
            if (connection !=null)
                 connection.disconnect();
            if (inputStream !=null)
                inputStream.close();
        }
        return response ;
    }


    private static String convertStreamToData(InputStream ins){
       StringBuilder sb = new StringBuilder();
       String line ;
       try(BufferedReader reader = new BufferedReader(new InputStreamReader(ins))){
           while ((line = reader.readLine()) !=null){
              sb.append(line).append('\n');
           }
       }catch (IOException e){
//          Log.e(CheckError.TAG,"Failed to Convert Stream to String "+ e.getMessage()) ;
       }
       return sb.toString();
    }

    private static List<News> extractJsonData(String jsonUrl){
        List<News> newsList = new ArrayList<>();
        if (TextUtils.isEmpty(jsonUrl)){
            return null ;
        }
            try {
                JSONObject baseKey = new JSONObject(jsonUrl);
                JSONObject root = baseKey.getJSONObject("response");
                JSONArray firstArray = root.getJSONArray("results");
                // check if JsonArray contain any elements before looping.
                if (firstArray.length() > 0){
                   for (int i = 0 ; i < firstArray.length();i++){
                    JSONObject index = firstArray.getJSONObject(i);
                     //First get Title , Section , Date, webUrl .
                     String webTitle = index.getString("webTitle");
                     String sectionName = index.getString("sectionName");
                     String date = index.getString("webPublicationDate");
                     String webUrl = index.getString("webUrl");
                     // Now fetch image and Article summary
                     JSONObject fields = index.getJSONObject("fields");
                     String thumbnail = fields.getString("thumbnail");
                     // Now fetch summary text from blocks
                     JSONObject blocks = index.getJSONObject("blocks");
                     // Now get all array inside this blocks .
                     JSONArray bodyArray = blocks.getJSONArray("body");
                     // Now fetch data in bodyArray .
                     JSONObject secondIndex = bodyArray.getJSONObject(0);
                     // Now i can access the all elements inside this array .
                     String articleSummary = secondIndex.getString("bodyTextSummary");
                     // Now save this values in News class .
                       newsList.add(new News(webTitle,articleSummary,sectionName,thumbnail,date,webUrl));
                   }
                }else{
//                   Log.e(CheckError.TAG," JsonArray is empty,Please check syntax name. ");
                }
            } catch (JSONException e) {
//                Log.e(CheckError.TAG,"Failed JsonObject "+ e.getMessage());
            }
            return newsList;
    }
}
