package com.riteshavikal.lue.societyapp.SignupPackage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.riteshavikal.lue.societyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.iwgang.countdownview.CountdownView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyNumberFragment extends Fragment {


    public VerifyNumberFragment() {
        // Required empty public constructor
    }

    Button mnextbtn,mnxtbtn,mresendbtn;
    EditText mmobileno;
    private ProgressDialog pDialog;
    EditText motptxt;
    String otp;

    RelativeLayout mtimertxt;

    CountdownView mflatclocl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verify_number, container, false);



        mmobileno = (EditText) view.findViewById(R.id.mobilenew);
        motptxt = (EditText) view.findViewById(R.id.textotp);

        mnextbtn = (Button) view.findViewById(R.id.next_btn);
        mresendbtn = (Button) view.findViewById(R.id.resend_btn);

        mresendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyotp();
                mnxtbtn.setVisibility(View.VISIBLE);
            }
        });

        mnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyotp();

            }
        });

        mnxtbtn = (Button) view.findViewById(R.id.submit_btn);
        mnxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String otptxt = motptxt.getText().toString().trim();
                                                    if(otp.equals(otptxt)) {

                                        Fragment fragment = new GeneralDetailFragment();
                                        FragmentManager fm = getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.createAccountFrame, fragment);
                                        ft.commit();
                                    }else{

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("Your Otp Is Incorrect")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }

            }
        });


        motptxt.setVisibility(View.INVISIBLE);
        mtimertxt = (RelativeLayout) view.findViewById(R.id.relativecount);
        mflatclocl = (CountdownView) view.findViewById(R.id.flatClock);
        mtimertxt.setVisibility(View.INVISIBLE);
        mresendbtn.setVisibility(View.INVISIBLE);

        mflatclocl.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {

                mresendbtn.setVisibility(View.VISIBLE);
                mnxtbtn.setVisibility(View.INVISIBLE);
            }
        });


        return  view ;
    }

    private void verifyotp(){


        final String macid = mmobileno.getText().toString().trim();

        SharedPreferences sharedPrefmobile = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editormobile = sharedPrefmobile.edit();
        editormobile.putString("regmobile",macid);
        editormobile.commit();

        if (macid.length() == 0) {

            mmobileno.requestFocus();
            mmobileno.setError("This Field Is Mandatory");

        }
        else
        {
            pDialog = new ProgressDialog(getContext());
            // Showing progress dialog before making http request
            pDialog.setMessage("Please Wait...");
            pDialog.show();

            final String key = "vsS1o3C5i7E9t0Y8am";
            final String opid = "1";

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
            String REGISTER_URL = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/smsservice";

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
                                JSONObject jsonresponse = new JSONObject(response);
                                String success = jsonresponse.getString("status");

                                if (success.equals("success")) {

                                    motptxt.setVisibility(View.VISIBLE);
                                    mnextbtn.setVisibility(View.INVISIBLE);
                                    mtimertxt.setVisibility(View.VISIBLE);
                                    mflatclocl.start(120000);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Verification Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //   Log.d("jabavererr00", usernumbr);
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(getContext(), "You Have Some Connectivity Issue..", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();


                    params.put(KEY_mobile, key);
                    params.put(KEY_mac, opid);
                    params.put(KEY_mobilenumber, message);

                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
