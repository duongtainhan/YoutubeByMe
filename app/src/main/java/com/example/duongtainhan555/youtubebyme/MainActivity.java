package com.example.duongtainhan555.youtubebyme;

import android.os.Handler;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView imgYoutube;
    private int SPLASH_TIME_OUT = 4500;
    private LinearLayout linearLayout;
    private TabLayout tabLayout;
    private TabItem tabSearchName, tabSearchPlaylist;
    private ViewPager viewPager;
    private PageAdapter pagerAdapter;
    private EditText edInput;
    private ImageView imgSearch;
    String key="AIzaSyAsaI7Evp_fk_R_G6LJCBA5I-EBJXA7zIY";

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
                if(edInput.getText().toString().equals(""))
                {
                    imgSearch.setImageResource(R.drawable.ic_micro);
                }
                else
                {
                    imgSearch.setImageResource(R.drawable.ic_delete);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void Init()
    {
        linearLayout = findViewById(R.id.linerLayout);
        imgYoutube = findViewById(R.id.imgYoutbe);
        tabLayout = findViewById(R.id.tablayout);
        tabSearchName = findViewById(R.id.tabSearchName);
        tabSearchPlaylist = findViewById(R.id.tabSearchPlaylist);
        viewPager = findViewById(R.id.viewPager);
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
        pagerAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1)
                {
                    edInput.setHint(R.string.hint_search_playlist);
                } else {
                    edInput.setHint(R.string.hint_search_name);
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
}
