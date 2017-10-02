package com.riteshavikal.lue.societyapp.SignupPackage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.riteshavikal.lue.societyapp.MainActivity;
import com.riteshavikal.lue.societyapp.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepresentativeFragment extends Fragment {


    public RepresentativeFragment() {
        // Required empty public constructor
    }

   List<String>rlst = new ArrayList<>();
    AutoCompleteTextView mrepresentativename;
    private ProgressDialog pDialog;
    private ArrayList<String>Representive_id = new ArrayList<String>();
    String pid,rid;

    Button msendrepstivebtn,mrotpbtn;

    EditText mreppotp;
    String part1;
    String otp;

    SharedPreferences sharedPrefotp;

    TextInputLayout mlinearonerep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_representative, container, false);
        mrepresentativename = (AutoCompleteTextView) view.findViewById(R.id.selectreprestve_edtxt);

        msendrepstivebtn = (Button) view.findViewById(R.id.sendrepstive_btn);
        mrotpbtn = (Button) view.findViewById(R.id.sotp_btn);
        mreppotp = (EditText) view.findViewById(R.id.textotp);

        mlinearonerep = (TextInputLayout) view.findViewById(R.id.representive_type);

        sharedPrefotp = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        mrepresentativename.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getAdapter().getItem(position);
                String[] parts = item.split("-");
                 part1 = parts[0]; // 004

                SharedPreferences.Editor editor123 = sharedPrefotp.edit();
                editor123.putBoolean("otpstatus",false);
                editor123.commit();

               Log.d("rrriid",""+part1+""+item);
            }
        });

        msendrepstivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendRepresentiveinfo();

            }
        });

        mrotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

                String defaultValue = sharedPref.getString("rstve",null);
                final String otptxt = mreppotp.getText().toString().trim();



                if(defaultValue.equals(otptxt)) {


                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Conngratulation")
                            .setMessage("Your Registraion Completed")

                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    startActivity(new Intent(getContext(), MainActivity.class));
                                    SharedPreferences.Editor editor123 = sharedPrefotp.edit();
                                    editor123.putBoolean("otpstatus",true);
                                    editor123.commit();
                                    getActivity().finish();

                                }
                            })
                            .show();

                }else{

                    SharedPreferences.Editor editor123 = sharedPrefotp.edit();
                    editor123.putBoolean("otpstatus",false);
                    editor123.commit();
                }

            }
        });

       // mreppotp.setVisibility(View.INVISIBLE);
//        mrotpbtn.setVisibility(View.INVISIBLE);

        RepresentiveInfo();

        SharedPreferences sharedPrefvisible = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean defaultValue = sharedPrefotp.getBoolean("otpstatus",true);

        if(defaultValue){

            msendrepstivebtn.setVisibility(View.VISIBLE);
            mrepresentativename.setVisibility(View.VISIBLE);
            mlinearonerep.setVisibility(View.VISIBLE);

            mreppotp.setVisibility(View.INVISIBLE);
            mrotpbtn.setVisibility(View.INVISIBLE);


        }else{

            msendrepstivebtn.setVisibility(View.INVISIBLE);
            mrepresentativename.setVisibility(View.INVISIBLE);
            mlinearonerep.setVisibility(View.INVISIBLE);
            mreppotp.setVisibility(View.VISIBLE);
            mrotpbtn.setVisibility(View.VISIBLE);

        }

        return view;

    }

    private void RepresentiveInfo(){

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please Wait...");
            pDialog.show();

            final String key = "vsS1o3C5i7E9t0Y8am";
            final String opid = "1";


            final String KEY_mobile = "key";
            final String KEY_mac = "opid";
            final String KEY_mobilenumber = "jsondata";


            String url = null;
            String REGISTER_URL = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/representativeservice";

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
                            Log.d("jabaver", key);
                            hidePDialog();
                            try {
                                JSONObject jsonresponse = new JSONObject(response);
                                JSONArray jsonArray = jsonresponse.getJSONArray("representatives_list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String name = obj.getString("name");
                                    String id  = obj.getString("id");

                                    Representive_id.add(id);

                                    String conct = id+"-"+name;
                                    rlst.add(conct);


                                }

                                ArrayAdapter<String> gendadapter =
                                        new ArrayAdapter<String>(getContext(), R.layout.autocomplete, rlst);

                                mrepresentativename.setAdapter(gendadapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                       //     Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
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

                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }


    private void SendRepresentiveinfo(){

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please Wait...");
        pDialog.show();

        final String key = "vsS1o3C5i7E9t0Y8am";
        final String opid = "2";


        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        otp = String.valueOf(n);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("rstve",otp);
        editor.commit();

        final String KEY_mobile = "key";
        final String KEY_mac = "opid";
        final String KEY_mobilenumber = "jsondata";


        final String message;
        JSONObject json = new JSONObject();
        try {
            json.put("id", part1);
            json.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        message = json.toString();
        Log.d("nnbjhvjvjv",""+message);


        String url = null;
        String REGISTER_URL = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/representativeservice";

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
                        Log.d("jabaver", key);
                        hidePDialog();
                        try {

                            JSONObject jsonresponse = new JSONObject(response);
                            String success = jsonresponse.getString("status");

                            SharedPreferences sharedPrefvisible = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorvisi = sharedPrefvisible.edit();

                            if (success.equals("success")) {

                                mreppotp.setVisibility(View.VISIBLE);
                                mrotpbtn.setVisibility(View.VISIBLE);
                                msendrepstivebtn.setVisibility(View.INVISIBLE);
                                mrepresentativename.setVisibility(View.INVISIBLE);
                                mlinearonerep.setVisibility(View.INVISIBLE);

                                editorvisi.putBoolean("repvis",false);
                                editorvisi.commit();

                            }
                            else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Sending Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //     Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
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
