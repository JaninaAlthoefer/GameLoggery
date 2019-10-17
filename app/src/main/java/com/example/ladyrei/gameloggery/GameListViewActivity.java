package com.example.ladyrei.gameloggery;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class GameListViewActivity extends ListActivity {

    ProgressBar progressBar;
    List<GameID> mResult;
    ListView lv;
    View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game_list_view);

        lv = getListView();

        //lv.setFooterDividersEnabled(true);
        //footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
        //                          .inflate(R.layout.activity_game_list_footer, null, false);
        //lv.addFooterView(footerView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GameListViewActivity.this, GameDetailsActivity.class);
                intent.putExtra("id", mResult.get(position).getId());

                startActivity(intent);
            }
        });

        //progressBar = (ProgressBar) footerView.findViewById(R.id.listProgressBar);

        new HTTPGameListRequestHandler().execute();
    }

class GameAdapter extends BaseAdapter {

    private final Context mContext;
    //private final List<GameID> mItems = new ArrayList<GameID>();

    GameAdapter(Context context)
    {
        mContext = context;
        //super(GameListViewActivity.this, R.layout.activity_game_list_view); //, R.id.label, items);
    }

    @Override
    public int getCount() {
        return mResult.size();
    }

    @Override
    public Object getItem(int position) {
        return mResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        GameID game = mResult.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.activity_game_list_view, null);

        ImageView icon = (ImageView) convertView.findViewById(R.id.platformImageView);
        TextView label = (TextView) convertView.findViewById(R.id.gameTitleTextView_List);

        label.setText(game.getGameTitle());

        switch(game.getPlatform())
        {
            case PS1:icon.setImageResource(R.drawable.ps1); break;
            case PS3:icon.setImageResource(R.drawable.ps3); break;
            case PS4:icon.setImageResource(R.drawable.ps4); break;
            case XBOX360:icon.setImageResource(R.drawable.xbox); break;
        }

        return convertView;
    }
}

/**
 * ListLoader.
 */

private class HTTPGameListRequestHandler extends AsyncTask<URL, Void, List<GameID>> {

    private static final String URL = "http://demo8276673.mockable.io/games";

    AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

    @Override
    protected void onPreExecute(){
        //progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected List<GameID> doInBackground(URL... params) {
        HttpGet request = new HttpGet(URL);
        JSONListResponseHandler responseHandler = new JSONListResponseHandler();

        try{
            return httpClient.execute(request, responseHandler);
        }
        catch (ClientProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<GameID> result){
        //progressBar.setVisibility(ProgressBar.INVISIBLE);

        if(httpClient != null)
            httpClient.close();

        //lv.removeFooterView(footerView);

        mResult = result;

        setListAdapter(new GameAdapter(GameListViewActivity.this));
    }
}

private class JSONListResponseHandler implements ResponseHandler<List<GameID>> {

    private static final String ID_TAG = "id";
    private static final String TITLE_TAG = "title";
    private static final String PLATFORM_TAG = "platform";

    @Override
    public List<GameID> handleResponse(HttpResponse response) throws ClientProtocolException, IOException
    {
        List<GameID> result = new ArrayList<GameID>();
        String JSONResponse = new BasicResponseHandler()
                                      .handleResponse(response);
        try
        {
            JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

            JSONArray games = responseObject.getJSONArray("games");

            for (int idx = 0; idx < games.length(); idx++)
            {
                JSONObject game = (JSONObject) games.get(idx);

                String id = game.getString(ID_TAG);
                String title = game.getString(TITLE_TAG);
                String platform = game.getString(PLATFORM_TAG);

                GameID toAdd = new GameID(id, title, platform);

                result.add(toAdd);

            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        //for (int i = 1; i < 11; i++) {
            //sleep();
        //}

        return result;
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
    }
}
}