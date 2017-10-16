package customclasses;

import android.util.Log;
import android.util.Pair;

import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shery on 12/6/2016.
 */

public class PhpConnectivity {
    private static final String TAG = PhpConnectivity.class.getSimpleName();
    private static HttpURLConnection urlConnection;
    private static AsyncHttpClient mClient;

    public static AsyncHttpClient getClient() {
        if (mClient == null) {
            mClient = new AsyncHttpClient();
            mClient.setConnectTimeout(100000);
            mClient.setTimeout(100000);
            mClient.setResponseTimeout(100000);

        }
        return mClient;
    }

    public static String    getStringFromUrl(List<NameValuePair> params) {
        StringBuilder result = new StringBuilder();
        String queryString = "https://mms.safcosupport.org/employees/tabData/syncdown.php";
        Log.e(TAG, "URL" + queryString);
        try {
            URL url = new URL(queryString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);


            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            Log.e(TAG, "--get data response code = " + responseCode);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {

                result.append(line);

            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();


            return null;

        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }


    public static String getStringFromUrl(String Url, List<NameValuePair> params) {
        StringBuilder result = new StringBuilder();
        String queryString = Url;
        Log.e(TAG, "URL" + queryString);
        try {
            URL url = new URL(queryString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(45000);
            urlConnection.setConnectTimeout(45000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);


            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            Log.e(TAG, "--get data response code = " + responseCode);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {

                result.append(line);

            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getStringFromUrl: " + e.getMessage());

            return null;

        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }


    private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair: params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    public static List<NameValuePair> getParams(String data) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", data));
        return params;
    }

    /*public static List<NameValuePair> getParamsFP(String data) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(data, cnic));
        return params;

    }*/



}
