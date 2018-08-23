package com.example.duongtainhan555.youtubebyme.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.duongtainhan555.youtubebyme.Adapter.CustomAdapter;
import com.example.duongtainhan555.youtubebyme.Model.VideoTrending;
import com.example.duongtainhan555.youtubebyme.R;
import com.example.duongtainhan555.youtubebyme.YoutubePlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.CoreProtocolPNames;
import cz.msebera.android.httpclient.util.EntityUtils;

public class TrendingFragment extends Fragment {

    String key="AIzaSyAsaI7Evp_fk_R_G6LJCBA5I-EBJXA7zIY";
    CustomAdapter customAdapter;
    private View view;
    ListView listView;
    ArrayList<VideoTrending> arrayVideoItems;


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.trending_fragment, container, false);
        listView = view.findViewById(R.id.listView);
        arrayVideoItems = new ArrayList<>();
        new TheTask().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), YoutubePlayer.class);
                intent.putExtra("video ID",arrayVideoItems.get(position).getId());
                startActivity(intent);
            }
        });
        return view;
    }
    private void GetData()
    {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        String url ="https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=20&type=video&regionCode=VN&key=AIzaSyAsaI7Evp_fk_R_G6LJCBA5I-EBJXA7zIY";
        HttpGet request = new HttpGet(url);
        // HttpGet request = new HttpGet("https://www.googleapis.com/youtube/v3/search?part=snippet&q=Mr.Tecas&maxResults=50&type=video_row&key=AIzaSyBxSE61XG4570uPVo5YfQkf_13z88RKMIk");
        //
        try {
            HttpResponse response = httpclient.execute(request);
            HttpEntity resEntity = response.getEntity();

            String _response = EntityUtils.toString(resEntity); // content will be consume only once
            JSONObject json = new JSONObject(_response);
            JSONArray jsonArray = json.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                VideoTrending videoTrending = new VideoTrending();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                videoTrending.setTitle(jsonObject.getJSONObject("snippet").getString("title"));
                videoTrending.setId(jsonObject.getString("id"));
                String thumbUrl = jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                videoTrending.setThumbnails(thumbUrl);
                arrayVideoItems.add(videoTrending);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        httpclient.getConnectionManager().shutdown();
    }
    class TheTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            GetData();
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            customAdapter = new CustomAdapter(getActivity(),R.layout.layout_item_video,arrayVideoItems);
            listView.setAdapter(customAdapter);
        }
    }
}
