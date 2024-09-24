package com.example.dishdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dishdash.homepage.view.HomePageActivity;

import java.lang.annotation.Annotation;

public class MainActivity extends AppCompatActivity {
    Animation animation;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        animation= AnimationUtils.loadAnimation(this,R.anim.anima);
        txtView=findViewById(R.id.txtVieew);
        txtView.startAnimation(animation);
        txtView.postOnAnimationDelayed(new Runnable() {
            @Override
            public void run() {
                Intent outIntent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(outIntent);
                finish();
            }
        },3000);




    }
}