package com.example.ladyrei.gameloggery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GameDetailsActivity extends Activity {

    private String URL = "http://demo8276673.mockable.io/games/";
    private String imageURL;
    private String charURL;
    private ImageView imageView;
    private GameItem item;

    private ProgressBar progressBar;

    private TextView titleTV, pubTV, platTV, yearTV, devTV, hoursTV, rateTV;
    private CheckBox beatenCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        progressBar = (ProgressBar) findViewById(R.id.imageProgressBar);
        imageView = (ImageView) findViewById(R.id.gameDetailImageView);

        titleTV = (TextView) findViewById(R.id.gameDetailTitleShow);
        pubTV = (TextView) findViewById(R.id.gameDetailPublisherShow);
        platTV = (TextView) findViewById(R.id.gameDetailPlatformShow);
        yearTV = (TextView) findViewById(R.id.gameDetailYearShow);
        devTV = (TextView) findViewById(R.id.gameDetailDeveloperShow);
        hoursTV = (TextView) findViewById(R.id.gameDetailHoursPlayedShow);
        beatenCB = (CheckBox) findViewById(R.id.gameDetailBeatenCheckBox);
        rateTV = (TextView) findViewById(R.id.gameDetailRatingShow);


        String id = getIntent().getExtras().getString("id");

        URL += id;

        new HTTPGameDetailsRequestHandler().execute();
    }

    void populateView()    {
        titleTV.setText(item.getName());
        pubTV.setText(item.getPublisher());
        yearTV.setText(item.getYear()+"");
        devTV.setText(item.getDeveloper());
        hoursTV.setText(item.getHours()+"");
        rateTV.setText(item.getRating()+"");

        switch (item.getPlatform())
        {
            case PS1: platTV.setText("Playstation"); break;
            case PS3: platTV.setText("Playstation 3"); break;
            case PS4: platTV.setText("Playstation 4"); break;
            case XBOX360: platTV.setText("XBox 360"); break;
        }

        if(item.isBeaten())
            beatenCB.setChecked(true);
        else
            beatenCB.setChecked(false);

        charURL = item.getCharUrl();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetailsActivity.this, ImageViewerActivity.class);
                intent.putExtra("imageUrl", charURL);

                startActivity(intent);
            }
        });
    }


    /**
     * DetailsLoader.
     */

    private class HTTPGameDetailsRequestHandler extends AsyncTask<URL, Void, GameItem> {

        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected GameItem doInBackground(URL... params) {
            HttpGet request = new HttpGet(URL);
            JSONDetailResponseHandler responseHandler = new JSONDetailResponseHandler();

            try {
                return httpClient.execute(request, responseHandler);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(GameItem result){
            //progressBar.setVisibility(ProgressBar.INVISIBLE);

            if(httpClient != null)
                httpClient.close();

            if (result != null)
            {
                item = result;

                populateView();
            }
        }
    }

    private class JSONDetailResponseHandler implements ResponseHandler<GameItem> {

        private static final String IMAGE_TAG = "imageurl";
        private static final String CHAR_TAG = "charurl";
        private static final String TITLE_TAG = "title";
        private static final String PLATFORM_TAG = "platform";
        private static final String PUBLISHER_TAG = "publisher";
        private static final String YEAR_TAG = "year";
        private static final String DEVELOPER_TAG = "developer";
        private static final String HOURS_TAG = "hoursplayed";
        private static final String BEATEN_TAG = "beaten";
        private static final String RATING_TAG = "rating";

        @Override
        public GameItem handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

            GameItem r_item;
            String JSONResponse = new BasicResponseHandler().handleResponse(response);
            try
            {
                JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

                String image = responseObject.getString(IMAGE_TAG);

                imageURL = image;
                new HTTPImageRequestHandler().execute();

                String title = responseObject.getString(TITLE_TAG);
                String chars = responseObject.getString(CHAR_TAG);
                String pub = responseObject.getString(PUBLISHER_TAG);
                String plat = responseObject.getString(PLATFORM_TAG);
                String year = responseObject.getString(YEAR_TAG);
                String dev = responseObject.getString(DEVELOPER_TAG);
                String hours = responseObject.getString(HOURS_TAG);
                String beaten = responseObject.getString(BEATEN_TAG);
                String rate = responseObject.getString(RATING_TAG);

                r_item = new GameItem(title, image, chars, pub, plat, dev, year, hours, rate, beaten);

                return r_item;

            } catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }


        }
    }


    /**
     * ImageLoader.
     */

    private class HTTPImageRequestHandler extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap image = null;

            try
            {
                URL urln = new URL(imageURL);
                HttpURLConnection connection = (HttpURLConnection)urln.openConnection();
                InputStream is = connection.getInputStream();
                image = BitmapFactory.decodeStream(is);

                if (image != null)
                    return image;

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap map) {
            if(map != null)
            {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                imageView.setImageBitmap(map);
            }
        }
    }

}