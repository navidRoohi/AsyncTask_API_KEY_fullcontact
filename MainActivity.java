package com.example.navidroohibroojeni.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText emailText;
    TextView responseView;
    ProgressBar progressBar;

     static final String API_KEY = "------";
     static final String API_URL = "https://api.fullcontact.com/v2/person.json?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseView = (TextView) findViewById(R.id.responseViewId);
        emailText = (EditText) findViewById(R.id.emailTextId);
        progressBar = (ProgressBar) findViewById(R.id.progressBarId);

    }

    public void goSearch(View view) {

        new RetrieveFeedTask().execute();

    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;
        String email;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
            email = emailText.getText().toString();

        }

        @Override
        protected String doInBackground(Void... params) {


            try {
                URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);


                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuilder stringBuilder = new StringBuilder();

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {

                        stringBuilder.append(line).append("\n");

                    }

                    bufferedReader.close();

                    return stringBuilder.toString();

                } finally {

                    urlConnection.disconnect();

                }

            } catch (Exception e) {

                Log.e("ERROR", e.getMessage(), e);

                return null;

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);

                responseView.setText(response.toString());



        }
    }

}
