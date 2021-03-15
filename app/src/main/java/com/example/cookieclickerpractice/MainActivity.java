package com.example.cookieclickerpractice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.concurrent.atomic.AtomicInteger;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.AnimationSet;
import android.util.Log;
import android.view.animation.ScaleAnimation;
import android.widget.RadioGroup;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;

import android.view.animation.AlphaAnimation;
import android.os.Bundle;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity
{
    int RunTime = 0;
    Button daButton;
    public static TextView thecookieS;
    TextView cookiePerSecondText;
    ImageView cookie;
    Toast xToast;
    RadioGroup theRadioGroup;
    ImageView daPlusOne;
    String radioGroupChecker = "Not Checked";
    TranslateAnimation thedaPlusOneFadingIn;
    AlphaAnimation thedaPlusOneFadingOut;
    AnimationSet animationSet;
    backroundincome1 backroundincome1;
    String yourChoice="";
    AnimationDrawable animationDrawable;
    Animation rotateAnimation;
    public static ImageView cookieUpgrade;
    public static AtomicInteger cookieCount;
    int amountneededToBuy = 20;
    int count;
    int perSecond = 0;
    ConstraintLayout daLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thecookieS = findViewById(R.id.id_thecookieS);
        theRadioGroup = findViewById(R.id.radioGroup_id);
        daButton = findViewById(R.id.buttonForUpgrade_id);
        thecookieS.setText("0 Cookies");
        cookiePerSecondText = findViewById(R.id.id_perSecond);
        cookiePerSecondText.setText("Per Second: " + perSecond);
        cookie = findViewById(R.id.imageForClicking_id);
        cookie.setImageResource(R.drawable.cookie);
        daLayout = findViewById(R.id.theLayout);
        cookieCount = new AtomicInteger(0);
        backroundincome1 = new backroundincome1();
        backroundincome1.start();

        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f,1.0f, 2.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,.5f);
        final ScaleAnimation scaleanimation2 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(400);
        scaleanimation2.setDuration(400);

        animationDrawable = (AnimationDrawable) daLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        theRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int radioGroupCheckerID) {
                if(radioGroupCheckerID == R.id.radioButton_buy){
                    radioGroupChecker = "Checked!";
                    yourChoice = "Buy";
                }
                else if(radioGroupCheckerID == R.id.radioButton2_sell){
                    radioGroupChecker = "Checked!";
                    yourChoice = "Sell";
                }
            }
        });
//Upgrade Section
        daButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if (yourChoice.equals("Sell") && RunTime > 0){
                    count = cookieCount.get();
                    count+=8;
                    perSecond--;
                    RunTime--;
                    cookieUpgrade.startAnimation(scaleanimation2);
                    if(RunTime==0) {
                        daLayout.removeView(cookieUpgrade);
                    }
                    cookieCount.getAndSet(count);
                    thecookieS.setText(cookieCount.get() + " Cookies");
                    cookiePerSecondText.setText("Per Second Cookies: " + perSecond);
                    daButton.setTextColor(Color.BLUE);
                    daButton.setBackgroundColor(Color.RED);
                    daButton.setText("Sold!");
                }
                else if(cookieCount.get() >= amountneededToBuy && radioGroupChecker.equals("Checked!")){
                    count = cookieCount.get();
                    if(yourChoice.equals("Sell") && RunTime > 0){
                        count +=8;
                        RunTime--;
                        perSecond--;
                    }
                    else if(yourChoice.equals("Buy")) {
                        count -= amountneededToBuy;
                        RunTime++;
                        perSecond += 1;
                        daButton.setTextColor(Color.GREEN);
                        daButton.setBackgroundColor(Color.YELLOW);
                        daButton.setText("Bought!");
                    }
                    cookieCount.getAndSet(count);
                    thecookieS.setText(cookieCount.get() + " Cookies");
                    cookiePerSecondText.setText("Per Second Cookies: " + perSecond);
                    daLayout.removeView(cookieUpgrade);

                    cookieUpgrade = new ImageView(MainActivity.this);
                    cookieUpgrade.setId(View.generateViewId());
                    cookieUpgrade.setImageResource(R.drawable.download);
                    cookieUpgrade.startAnimation(scaleAnimation);

                    ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    cookieUpgrade.setLayoutParams(params2);
                    daLayout.addView(cookieUpgrade);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(daLayout);
                    constraintSet.connect(cookieUpgrade.getId(), ConstraintSet.TOP, daLayout.getId(), ConstraintSet.TOP);
                    constraintSet.connect(cookieUpgrade.getId(), ConstraintSet.BOTTOM, daLayout.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(cookieUpgrade.getId(), ConstraintSet.RIGHT, daLayout.getId(), ConstraintSet.RIGHT);
                    constraintSet.connect(cookieUpgrade.getId(), ConstraintSet.LEFT, daLayout.getId(), ConstraintSet.LEFT);
                    constraintSet.setHorizontalBias(cookieUpgrade.getId(),.6f);
                    constraintSet.setVerticalBias(cookieUpgrade.getId(), .4f);
                    constraintSet.applyTo(daLayout);
                    rotateAnimation(cookieUpgrade);
                }
                else
                {
                    xToast = Toast.makeText(MainActivity.this, "Error! Try Again!", Toast.LENGTH_SHORT);
                    xToast.show();
                }
            }
        });
        cookie.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                v.startAnimation(scaleAnimation);
                daPlusOne = new ImageView(MainActivity.this);
                cookieCount.getAndAdd(1);
                thecookieS.setText(cookieCount + " Cookies");

                daPlusOne.setId(View.generateViewId());
                daPlusOne.setImageResource(R.drawable.plusone);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                params.height = 300;
                params.width = 300;
                daPlusOne.setLayoutParams(params);

                daLayout.addView(daPlusOne);
                ConstraintSet constraintSet1 = new ConstraintSet();
                constraintSet1.clone(daLayout);
                constraintSet1.connect(daPlusOne.getId(), ConstraintSet.TOP, daLayout.getId(), ConstraintSet.TOP);
                constraintSet1.connect(daPlusOne.getId(), ConstraintSet.BOTTOM, daLayout.getId(), ConstraintSet.BOTTOM);
                constraintSet1.connect(daPlusOne.getId(), ConstraintSet.RIGHT, daLayout.getId(), ConstraintSet.RIGHT);
                constraintSet1.connect(daPlusOne.getId(), ConstraintSet.LEFT, daLayout.getId(), ConstraintSet.LEFT);
                constraintSet1.setHorizontalBias(daPlusOne.getId(), (float) ((Math.random() * .30) + .30));
                constraintSet1.setVerticalBias(daPlusOne.getId(), (float) ((Math.random() * .2) + .6));
                constraintSet1.applyTo(daLayout);


                thedaPlusOneFadingIn = new TranslateAnimation(0, 0, 0, -300);
                thedaPlusOneFadingIn.setDuration(200);
                thedaPlusOneFadingOut = new AlphaAnimation(1, 0);
                thedaPlusOneFadingOut.setDuration(200);
                animationSet = new AnimationSet(false);
                animationSet.addAnimation(thedaPlusOneFadingIn);
                animationSet.addAnimation(thedaPlusOneFadingOut);

                daPlusOne.startAnimation(animationSet);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        daLayout.removeView(daPlusOne);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Log.d("TAG", String.valueOf(daLayout.getChildCount()));
                //daPlusOne.setVisibility(View.INVISIBLE)
            }
        });
    }
    private void rotateAnimation(ImageView imageView)
    {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotateAnimation);
    }
    public class backroundincome1 extends Thread{
        @Override
        public void run()
        {
            while(true)
            {
                try
                {
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                cookieCount.getAndAdd(perSecond);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        thecookieS.setText(cookieCount + " Cookies");
                    }
                });
            }
        }
    }
}