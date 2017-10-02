package com.riteshavikal.lue.societyapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.riteshavikal.lue.societyapp.SignupPackage.CreateSignupProcess;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button signup;
    private ViewPager viewPager;
    private ImageView[] pageIndicator;
    private static final int REQUEST_PERMISSION = 10;
    private SparseIntArray mErrorString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initDataUI();
        setListeners();

    }

    private void initComponents() {
       login = (Button) findViewById(R.id.login);
       /* new SimpleTooltip.Builder(this)
                .anchorView(login)
                .text("")
                .gravity(Gravity.TOP)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();*/
       signup = (Button) findViewById(R.id.signup);
//        new SimpleTooltip.Builder(this)
//                .anchorView(signup)
//                .text("Sign up with ur Details,Fb or GOOGLE")
//                .gravity(Gravity.TOP)
//                .animated(true)
//                .transparentOverlay(false)
//                .build()
//                .show();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageIndicator = new ImageView[4];
        pageIndicator[0] = (ImageView) findViewById(R.id.indicator1);
        pageIndicator[1] = (ImageView) findViewById(R.id.indicator2);
        pageIndicator[2] = (ImageView) findViewById(R.id.indicator3);
        pageIndicator[3] = (ImageView) findViewById(R.id.indicator4);
    }

    private void initDataUI() {
        viewPager.setAdapter(new ScrollPagerAdapter(getSupportFragmentManager()));
    }

    private void setListeners() {
       login.setOnClickListener(loginListener);
       signup.setOnClickListener(signupListener);
       viewPager.setOnPageChangeListener(pageChangeListener);
    }
  private View.OnClickListener loginListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    };

    private View.OnClickListener signupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signUpIntent = new Intent(MainActivity.this, CreateSignupProcess.class);
            startActivity(signUpIntent);
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            for(int i = 0; i < 4; i++) {
                if(position == i)
                    pageIndicator[i].setImageResource(R.drawable.circle_fill);
                else
                    pageIndicator[i].setImageResource(R.drawable.circle);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
