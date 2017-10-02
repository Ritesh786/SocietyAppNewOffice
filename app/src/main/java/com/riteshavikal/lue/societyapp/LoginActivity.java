package com.riteshavikal.lue.societyapp;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.riteshavikal.lue.societyapp.SignupPackage.GeneralDetailFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.iwgang.countdownview.CountdownView;

public class LoginActivity extends AppCompatActivity {

    Button mnextbtn,mnxtbtn,mresendbtn;
    EditText mmobileno;
    private ProgressDialog pDialog;
    EditText motptxt;
    String otp;

    RelativeLayout mtimertxt;

    CountdownView mflatclocl;
    JSONObject jsonresponse;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mmobileno = (EditText) findViewById(R.id.mobilenew);
        motptxt = (EditText) findViewById(R.id.textotp);

        mnextbtn = (Button) findViewById(R.id.next_btn);
        mresendbtn = (Button) findViewById(R.id.resend_btn);

        session = new UserSessionManager(getApplicationContext());

        mresendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
                mnxtbtn.setVisibility(View.VISIBLE);
            }
        });

        mnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();

            }
        });

        mnxtbtn = (Button) findViewById(R.id.submit_btn);
        mnxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String otptxt = motptxt.getText().toString().trim();
                if(otp.equals(otptxt)) {

                    Intent registerintent = new Intent(LoginActivity.this, DashboardActivity.class);
                    registerintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    registerintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    ComponentName cn = registerintent.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);

                    startActivity(mainIntent);
                    finish();

                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Your Otp Is Incorrect")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            }
        });


        motptxt.setVisibility(View.INVISIBLE);
        mtimertxt = (RelativeLayout) findViewById(R.id.relativecount);
        mflatclocl = (CountdownView) findViewById(R.id.flatClock);
        mtimertxt.setVisibility(View.INVISIBLE);
        mresendbtn.setVisibility(View.INVISIBLE);

        mflatclocl.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {

                mresendbtn.setVisibility(View.VISIBLE);
                mnxtbtn.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void LoginUser(){


        final String macid = mmobileno.getText().toString().trim();

        if (macid.length() == 0) {

            mmobileno.requestFocus();
            mmobileno.setError("This Field Is Mandatory");

        }
        else
        {
            pDialog = new ProgressDialog(LoginActivity.this);
            // Showing progress dialog before making http request
            pDialog.setMessage("Please Wait...");
            pDialog.show();

            final String key = "vsS1o3C5i7E9t0Y8am";
            final String keyopid = "2";

            Random rnd = new Random();
            int n = 100000 + rnd.nextInt(900000);

            otp = String.valueOf(n);
            Log.d("otpnew",""+otp);
            final String message;
            JSONObject json = new JSONObject();
            try {
                json.put("mobno", macid);
                json.put("otp", otp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            message = json.toString();

            Log.d("hbibiibib", "" + message);
            final String KEY_mobile = "key";
            final String KEY_mac = "opid";
            final String KEY_mobilenumber = "jsondata";


            String url = null;
            String REGISTER_URL = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/personservice";

            REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
            try {
                URL sourceUrl = new URL(REGISTER_URL);
                url = sourceUrl.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("jabaver", macid);
                            hidePDialog();
                            try {
                                 jsonresponse = new JSONObject(response);
                                String success = jsonresponse.getString("status");

                                if (success.equals("success")) {

                                    motptxt.setVisibility(View.VISIBLE);
                                    mnextbtn.setVisibility(View.INVISIBLE);
                                    mtimertxt.setVisibility(View.VISIBLE);
                                    mflatclocl.start(120000);

                                    String name = jsonresponse.getString("fname");
                                    String id = jsonresponse.getString("id");

                                    SaveUserId.getInstance(getApplicationContext()).saveuserId(id);

                                    session.createUserLoginSession(id);
                                    session.Savename(name);

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //   Log.d("jabavererr00", usernumbr);
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(LoginActivity.this, "You Have Some Connectivity Issue..", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();


                    params.put(KEY_mobile, key);
                    params.put(KEY_mac, keyopid);
                    params.put(KEY_mobilenumber, message);

                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(stringRequest);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
