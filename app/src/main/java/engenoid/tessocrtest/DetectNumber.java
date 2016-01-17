package engenoid.tessocrtest;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DetectNumber extends AsyncTask<Object, Void, String> {
    private String Content;
    private String Error = "Hata";
    private Context mContext;
    String number;
    String urls = "http://canercay.besaba.com/number.php?kod=";

    public DetectNumber(Context paramContext) {
        this.mContext = paramContext;
    }

    protected String doInBackground(Object[] urls) {
        BufferedReader reader = null;
        try {
            this.urls += urls[0];
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(this.urls);
            HttpResponse httpResponse = httpClient.execute(get);
            Content = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(Content);

        } catch (Exception ex) {
            Error = ex.getMessage();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        return Content;
    }

    protected void onPostExecute(String paramString) {

        if (this.Error != null)
            System.out.println(this.Error);
        System.out.println(paramString);
        JSONArray jsonMainNode = null;
        try {
            jsonMainNode = new JSONArray(paramString);
            JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
            String name = jsonChildNode.optString("karsilik").toString();
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}