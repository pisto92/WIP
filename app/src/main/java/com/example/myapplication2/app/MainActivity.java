package com.example.myapplication2.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        runReadThread();
    }

    private void runReadThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(300);
                        if (editText.getText().length() > 2) {
                            String query = "" + editText.getText();
                            query = query.replace(" ", "%20");
                            new DownloadWebpageTask().execute("" + query);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        new Thread(runnable).start();
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... query) {

            JSONArray jsonItems = null;
            try {
                String queryUri = String.format("https://api.7pixel.it/it/autocomplete?q=%s&wt=json", query);

                HttpClient httpClient = new com.example.myapplication2.app.HttpClient();
                JSONObject response = httpClient.getJSONObject(queryUri);

                jsonItems = response.getJSONObject("suggestions").getJSONArray("suggestion");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonItems;
        }

        @Override
        protected void onPostExecute(JSONArray jsonItems) {
            TextView textview = (TextView) findViewById(R.id.response);
            textview.setText("");
            try {
                for (int arrayIndex = 0; arrayIndex < jsonItems.length(); arrayIndex++) {
                    String suggestion = jsonItems.getJSONObject(arrayIndex).getString("search");
                    textview.append(suggestion + "\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
