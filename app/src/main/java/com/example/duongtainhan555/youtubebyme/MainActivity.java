package com.example.duongtainhan555.youtubebyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView imgYoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init
        Init();
        //StartAnimation
        StartAnimation();
    }
    private void Init()
    {
        imgYoutube = findViewById(R.id.imgYoutbe);
    }
    private void StartAnimation()
    {
        Animation animation_alpha = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        imgYoutube.startAnimation(animation_alpha);
        animation_alpha.reset();
    }
}
