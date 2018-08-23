package com.example.duongtainhan555.youtubebyme.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duongtainhan555.youtubebyme.Adapter.VideoAdapter;
import com.example.duongtainhan555.youtubebyme.Model.Item;
import com.example.duongtainhan555.youtubebyme.Model.VideoItems2;
import com.example.duongtainhan555.youtubebyme.R;
import com.example.duongtainhan555.youtubebyme.Retrofit2_RxJava.RequestInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private EditText edKeySearch;
    ListView listView;
    VideoAdapter videoAdapter;
    private VideoItems2 videoItems2;
    private ArrayList<Item> arrayItems;
    private static final String base_url="https://www.googleapis.com/youtube/v3/";
    private static final String key="AIzaSyAsaI7Evp_fk_R_G6LJCBA5I-EBJXA7zIY";
    private CompositeDisposable compositeDisposable;
    private String key_search;
    private boolean status_delete = false;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private ImageView imgSearchMain2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        key_search = intent.getStringExtra("key_search");
        //Init
        Init();
        edKeySearch.setText(intent.getStringExtra("key_search_src"));
        Toast.makeText(this, key_search, Toast.LENGTH_LONG).show();
        compositeDisposable = new CompositeDisposable();
        videoItems2 = new VideoItems2();
        arrayItems = new ArrayList<>();
        loadJSON(base_url);
        //Event
        edKeySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SetImageSearch();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edKeySearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SetImageSearch();
            }
        });
    }
    private void Init()
    {
        edKeySearch = findViewById(R.id.edKeySearch);
        listView = findViewById(R.id.listView);
        imgSearchMain2 = findViewById(R.id.imgSearchMain2);
    }
    private void loadJSON(String url)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();
        RequestInterface requestInterface = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);
        Disposable disposable = requestInterface.register(key_search,"50","video",key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
        compositeDisposable.add(disposable);
    }
    private void handleResponse(VideoItems2 items2) {
        videoItems2 = items2;
        for(int i=0;i<videoItems2.getItems().size();i++)
        {
            Item item = new Item();
            item.setSnippet(videoItems2.getItems().get(i).getSnippet());
            item.setId(videoItems2.getItems().get(i).getId());
            arrayItems.add(item);
            //Log.d("BBB",videoItems2.getItems().get(i).getSnippet().getThumbnails().getMedium().getUrl());
        }
        //Log.d("CCC",arrayItems.get(1).getId().getVideoId());
        videoAdapter = new VideoAdapter(SearchActivity.this,R.layout.layout_item_video,arrayItems);
        listView.setAdapter(videoAdapter);
    }
    private void handleError(Throwable error) {

        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleSuccess() {
        Toast.makeText(this, "Ket noi thanh cong! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void EventClickSearch(View view) {
        if (status_delete == true) {
            edKeySearch.getText().clear();
        }
        else
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edKeySearch.setText(result.get(0));
                }
                break;
            }

        }
    }
    private void SetImageSearch()
    {
        if(edKeySearch.getText().toString().equals(""))
        {
            imgSearchMain2.setImageResource(R.drawable.ic_micro);
            status_delete = false;
        }
        else
        {
            imgSearchMain2.setImageResource(R.drawable.ic_delete);
            status_delete = true;
        }
    }
}
