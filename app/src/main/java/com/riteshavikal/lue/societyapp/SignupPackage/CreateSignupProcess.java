package com.riteshavikal.lue.societyapp.SignupPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.riteshavikal.lue.societyapp.R;

public class CreateSignupProcess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_signup_process);

        SharedPreferences sharedPrefotp = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        boolean defaultValue = sharedPrefotp.getBoolean("otpstatus",true);

    if(defaultValue) {

    VerifyNumberFragment fragment = new VerifyNumberFragment();
    FragmentManager manager = getSupportFragmentManager();
    manager.beginTransaction().replace(R.id.createAccountFrame, fragment).addToBackStack("STORE LIST").commit();


    }else{

                        Fragment fragment = new RepresentativeFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.createAccountFrame, fragment);
                ft.commit();


    }

    }

    @Override
    public void onBackPressed() {

        CreateSignupProcess.this.finish();

        super.onBackPressed();
    }
}
