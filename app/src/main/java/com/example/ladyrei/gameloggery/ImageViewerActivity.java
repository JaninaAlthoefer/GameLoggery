package com.example.ladyrei.gameloggery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageViewerActivity extends Activity {

    private String imageURL;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.imageViewerProgressBar);

        imageURL = getIntent().getExtras().getString("imageUrl");

        new ImageRequestHandler().execute();

    }

    private class ImageRequestHandler extends AsyncTask<String, Void, Bitmap> {

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
