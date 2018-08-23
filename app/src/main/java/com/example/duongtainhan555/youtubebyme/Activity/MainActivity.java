package com.example.duongtainhan555.youtubebyme.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duongtainhan555.youtubebyme.R;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageView imgYoutube;
    private int SPLASH_TIME_OUT = 4500;
    private LinearLayout linearLayout;
    private TabLayout tabLayout;
    private TabItem tabTrending, tabFavourite;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private EditText edInput;
    private ImageView imgSearch;
    private boolean status_delete = false;
    private static final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        StartSplashScreen();
        HideSplashScreen();
        SetViewPager();
        //Event
        edInput.addTextChangedListener(new TextWatcher() {
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
        edInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(status_delete == true)
                    {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        String key_search = edInput.getText().toString();
                        key_search = key_search.replace(" ","%20");
                        intent.putExtra("key_search",key_search);
                        intent.putExtra("key_search_src",edInput.getText().toString());
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private void Init()
    {
        tabLayout = findViewById(R.id.tablayout);
        tabTrending = findViewById(R.id.tabTrending);
        tabTrending = findViewById(R.id.tabFavourite);
        viewPager = findViewById(R.id.viewPager);
        imgYoutube = findViewById(R.id.imgYoutbe);
        linearLayout = findViewById(R.id.linerLayout);
        edInput = findViewById(R.id.edInput);
        imgSearch = findViewById(R.id.imgSearch);
    }
    private void StartSplashScreen()
    {
        imgYoutube.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        Animation animation_alpha = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        imgYoutube.startAnimation(animation_alpha);
        Animation animation_scale = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
        imgYoutube.startAnimation(animation_scale);
    }
    private void HideSplashScreen()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha_dissapear);
                imgYoutube.startAnimation(animation);
                imgYoutube.clearAnimation();
            }
        },SPLASH_TIME_OUT);
        imgYoutube.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha_dissapear);
                linearLayout.startAnimation(animation);
            }
        },4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.INVISIBLE);
            }
        },5000);
    }
    private void SetViewPager()
    {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1)
                {

                } else {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
    private void SetImageSearch()
    {
        if(edInput.getText().toString().equals(""))
        {
            imgSearch.setImageResource(R.drawable.ic_micro);
            status_delete = false;
        }
        else
        {
            imgSearch.setImageResource(R.drawable.ic_delete);
            status_delete = true;
        }
    }

    public void EventClickSearch(View view) {
        if (status_delete == true) {
            edInput.getText().clear();
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
                    edInput.setText(result.get(0));
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    String key_search = edInput.getText().toString();
                    key_search = key_search.replace(" ","%20");
                    intent.putExtra("key_search",key_search);
                    intent.putExtra("key_search_src",edInput.getText().toString());
                    startActivity(intent);
                }
                break;
            }

        }
    }
}
