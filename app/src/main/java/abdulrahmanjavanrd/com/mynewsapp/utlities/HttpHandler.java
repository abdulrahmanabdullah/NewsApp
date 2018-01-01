package abdulrahmanjavanrd.com.mynewsapp.utlities;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by nfs05 on 02/01/2018.
 */

public class HttpHandler {

    private static final  String TAG = HttpHandler.class.getSimpleName();
    public HttpHandler(){

    }

    /**
     * This method i call when want to connect the uri.
     * @param theUri webSite uri .
     * @return stream data for this url .
     */
    public String callHttpConnection(String theUri) {
       String response ="";
       HttpURLConnection connection = null ;
       InputStream inputStream = null ;
        try{
            //Get the URL as Param .
            URL url = new URL(theUri);
            // Open Connection with param passing
            connection = (HttpURLConnection) url.openConnection();
            // set Connection GET
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();
            inputStream = connection.getInputStream();
            /** Call the {@link #convertStream(InputStream)} to Convert the Binary data to string Data*/
            response = convertStream(inputStream);
        } catch (MalformedURLException e) {
            Log.e(TAG,"No Legal Protocol found in  "+ e.getMessage());
        } catch (IOException e) {
            Log.e(TAG,"Input Stream Error : "+ e.getMessage());
        }
       finally {
            if (connection !=null){
                connection.disconnect();
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response ;
    }

    /**
     * @param inputStream to convert uri.getStream to String.
     * @return String Builder content all data in inputStream.
     */
    private String convertStream(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line ;
        if (inputStream != null ){
            InputStreamReader mInputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(mInputStreamReader);
            while ((line = reader.readLine()) !=null){
               sb.append(line).append('\n');
            }
        }else{
            Log.e(TAG,"Empty InputStream ");
        }
        // AutoClosable
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
//           while ((line = reader.readLine()) !=null){
//               sb.append(line).append("\n");
//           }
//        }catch (IOException e){
//            Log.e(TAG," Can't Convert Data something wrong "+ e.getMessage());
//        }
        return sb.toString();
    }
}
