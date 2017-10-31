package com.agile.ireality.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.agile.ireality.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5500;
    Runnable runThread = null;
    private Handler timeoutHandler = new Handler();
    ImageView zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen;
    TextView iReality;
    Animation spin, fadein, bounce, end;
    Animation blockAnimation1, blockAnimation2, blockAnimation3, blockAnimation4, blockAnimation5,
            blockAnimation6, blockAnimation7, blockAnimation8, blockAnimation9, blockAnimation10,
            blockAnimation11, blockAnimation12, blockAnimation13, blockAnimation14, blockAnimation15,
            blockAnimation16;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {

            spin = AnimationUtils.loadAnimation(Splash.this,
                    R.anim.ir_logo_animation_start);
            fadein = AnimationUtils.loadAnimation(Splash.this,
                    R.anim.fade_in_splash);
            bounce = AnimationUtils.loadAnimation(Splash.this,
                    R.anim.bounce);
            end = AnimationUtils.loadAnimation(Splash.this,
                    R.anim.end);

            initialiseViews();
            setSpinAnimation();

            iReality.setAnimation(fadein);
            zero.setAnimation(spin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private void setSpinAnimation() {
        fourteen.setAnimation(blockAnimation1);
        eight.setAnimation(blockAnimation2);
        ten.setAnimation(blockAnimation3);
        sixteen.setAnimation(blockAnimation4);
        two.setAnimation(blockAnimation5);
        four.setAnimation(blockAnimation6);
        twelve.setAnimation(blockAnimation7);
        five.setAnimation(blockAnimation8);
        seven.setAnimation(blockAnimation9);
        thirteen.setAnimation(blockAnimation10);
        eleven.setAnimation(blockAnimation11);
        fifteen.setAnimation(blockAnimation12);
        nine.setAnimation(blockAnimation13);
        three.setAnimation(blockAnimation14);
        one.setAnimation(blockAnimation15);
        six.setAnimation(blockAnimation16);

        end.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent gotoLoginPage = new Intent(Splash.this, Login.class);
                startActivity(gotoLoginPage);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        spin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                YoYo.with(Techniques.Tada).delay(1500).duration(300).playOn(zero);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        blockAnimation16.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                zero.setAnimation(end);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void initialiseViews() {

        iReality = (TextView) findViewById(R.id.tv_iReality);
        zero = (ImageView) findViewById(R.id.zero);

        one = (ImageView) findViewById(R.id.one);
        two = (ImageView) findViewById(R.id.two);
        three = (ImageView) findViewById(R.id.three);
        four = (ImageView) findViewById(R.id.four);
        five = (ImageView) findViewById(R.id.five);
        six = (ImageView) findViewById(R.id.six);
        seven = (ImageView) findViewById(R.id.seven);
        eight = (ImageView) findViewById(R.id.eight);
        nine = (ImageView) findViewById(R.id.nine);
        ten = (ImageView) findViewById(R.id.ten);
        eleven = (ImageView) findViewById(R.id.eleven);
        twelve = (ImageView) findViewById(R.id.twelve);
        thirteen = (ImageView) findViewById(R.id.thirteen);
        fourteen = (ImageView) findViewById(R.id.fourteen);
        fifteen = (ImageView) findViewById(R.id.fifteen);
        sixteen = (ImageView) findViewById(R.id.sixteen);

        blockAnimation1 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation1.setStartOffset(500);

        blockAnimation2 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation2.setStartOffset(600);

        blockAnimation3 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation3.setStartOffset(700);

        blockAnimation4 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation4.setStartOffset(800);

        blockAnimation5 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation5.setStartOffset(900);

        blockAnimation6 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation6.setStartOffset(1000);

        blockAnimation7 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation7.setStartOffset(1100);

        blockAnimation8 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation8.setStartOffset(1200);

        blockAnimation9 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation9.setStartOffset(1300);

        blockAnimation10 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation10.setStartOffset(1400);

        blockAnimation11 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation11.setStartOffset(1500);

        blockAnimation12 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation12.setStartOffset(1600);

        blockAnimation13 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation13.setStartOffset(1700);

        blockAnimation14 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation14.setStartOffset(1800);

        blockAnimation15 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation15.setStartOffset(1900);

        blockAnimation16 = AnimationUtils.loadAnimation(Splash.this,
                R.anim.block_animation);
        blockAnimation16.setStartOffset(2000);

    }

}