package com.riteshavikal.lue.societyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.riteshavikal.lue.societyapp.SignupPackage.CreateSignupProcess;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//        Thread splashthread = new Thread() {
//            public void run() {
//                try {
//                    sleep(2000);
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
//                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                        }
//                    });
//                    finish();
//                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//
//        splashthread.start();
//
//    }

    boolean defaultValue;

    @Override
    public void initSplash(ConfigSplash configSplash) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        configSplash.setBackgroundColor(R.color.css); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        configSplash.setLogoSplash(R.drawable.logo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);


        //Customize Title
        configSplash.setTitleSplash("Society App");
        configSplash.setTitleTextColor(android.R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.Landing);
     //   configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/


        SharedPreferences sharedPrefotp = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        defaultValue = sharedPrefotp.getBoolean("otpstatus",true);

    }

    @Override
    public void animationsFinished() {

        if(defaultValue) {

            startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        else{

            startActivity(new Intent(SplashScreen.this, CreateSignupProcess.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();

        }

    }

    @Override
    public void onBackPressed() {

    }

}
