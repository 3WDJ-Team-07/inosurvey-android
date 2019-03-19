package xyz.inosurvey.inosurvey;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class postToken {

    public String myJSON;
    public String cookie;
    static final String COOKIES_HEADER = "Set-Cookie";

    public void postData(String url) {
        class postDataToken extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                if (params == null || params.length < 1)
                    return null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();

                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                System.out.println(myJSON);
                //showList();
            }
        }
       postDataToken pdt = new postDataToken();
        pdt.execute(url);
    }
}