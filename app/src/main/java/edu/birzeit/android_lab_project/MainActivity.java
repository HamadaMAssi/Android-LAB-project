package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final LinearLayout c1 = (LinearLayout) findViewById(R.id.c1);
        final LinearLayout c2 = (LinearLayout) findViewById(R.id.c2);
        final LinearLayout c3 = (LinearLayout) findViewById(R.id.c3);
        final LinearLayout c4 = (LinearLayout) findViewById(R.id.c4);
        final LinearLayout c5 = (LinearLayout) findViewById(R.id.c5);
        c1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.c1animation));
        c2.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.c2animation));
        c3.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.c3animation));
        c4.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.c4animation));
        c5.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.c5animation));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}