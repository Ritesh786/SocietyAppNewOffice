package com.riteshavikal.lue.societyapp.SignupPackage;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralDetailFragment extends Fragment implements AdapterView.OnItemClickListener  {


    public GeneralDetailFragment() {
        // Required empty public constructor
    }

    Button mnextbtn,mbackbtn;

    EditText mfname,mlname,mhousno,mstrcolo,mlandmark,mwardno,mpincode;

    AutoCompleteTextView mstate,mdistrict,mcittvilag,mblock;

    private ProgressDialog pDialog;

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyD7_gkB6R8Tn2SVAgis-rrYJnB2KZtWbbQ";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_detail, container, false);

        mfname = (EditText) view.findViewById(R.id.nameed_txt);
        mlname = (EditText) view.findViewById(R.id.lastnameed_txt);
        mhousno = (EditText) view.findViewById(R.id.houseno_txt);
        mstrcolo = (EditText) view.findViewById(R.id.street_txt);
        mlandmark = (EditText) view.findViewById(R.id.landmark_txt);
        mwardno = (EditText) view.findViewById(R.id.ward_txt);
        mcittvilag = (AutoCompleteTextView) view.findViewById(R.id.city_txt);
        mpincode = (EditText) view.findViewById(R.id.pincode_txt);
        mblock = (AutoCompleteTextView) view.findViewById(R.id.block_txt);
        mdistrict = (AutoCompleteTextView) view.findViewById(R.id.district_txt);
        mstate = (AutoCompleteTextView) view.findViewById(R.id.state_txt);


        mnextbtn = (Button) view.findViewById(R.id.next_btn);
        mnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendGeneraiDetail();

            }
        });


        mbackbtn = (Button) view.findViewById(R.id.back_btn);
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new VerifyNumberFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.createAccountFrame, fragment);
                ft.commit();
            }
        });


        mcittvilag.setAdapter(new GooglePlacesAutocompleteAdapter(getContext(), R.layout.autocomplete));
        mstate.setAdapter(new GooglePlacesAutocompleteAdapter(getContext(), R.layout.autocomplete));
        mdistrict.setAdapter(new GooglePlacesAutocompleteAdapter(getContext(), R.layout.autocomplete));
        mblock.setAdapter(new GooglePlacesAutocompleteAdapter(getContext(), R.layout.autocomplete));
        mblock.setOnItemClickListener(this);
        mcittvilag.setOnItemClickListener(this);
        mstate.setOnItemClickListener(this);
        mdistrict.setOnItemClickListener(this);


        return view;
    }

    private void SendGeneraiDetail() {


        SharedPreferences sharedPrefmobile = getActivity().getPreferences(Context.MODE_PRIVATE);
        String mobile = sharedPrefmobile.getString("regmobile",null);


        final String fname = mfname.getText().toString().trim();
        final String lname = mlname.getText().toString().trim();
        final String houseno = mhousno.getText().toString().trim();
        final String stret = mstrcolo.getText().toString().trim();
        final String landmark = mlandmark.getText().toString().trim();
        final String wardno = mwardno.getText().toString().trim();
        final String ctyvlg = mcittvilag.getText().toString().trim();
        final String pincode = mpincode.getText().toString().trim();
        final String block = mblock.getText().toString().trim();
        final String distrct = mdistrict.getText().toString().trim();
        final String state = mstate.getText().toString().trim();


        if (TextUtils.isEmpty(fname)) {
            mfname.requestFocus();
            mfname.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(lname)) {
            mlname.requestFocus();
            mlname.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(houseno)) {
            mhousno.requestFocus();
            mhousno.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(stret)) {
            mstrcolo.requestFocus();
            mstrcolo.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(landmark)) {
            mlandmark.requestFocus();
            mlandmark.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(wardno)) {
            mwardno.requestFocus();
            mwardno.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(ctyvlg)) {
            mcittvilag.requestFocus();
            mcittvilag.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(pincode)) {
            mpincode.requestFocus();
            mpincode.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(block)) {
            mblock.requestFocus();
            mblock.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(distrct)) {
            mdistrict.requestFocus();
            mdistrict.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(state)) {
            mstate.requestFocus();
            mstate.setError("This Field Is Mandatory");
        } else {

            pDialog = new ProgressDialog(getContext());
            // Showing progress dialog before making http request
            pDialog.setMessage("Please Wait...");
            pDialog.show();

            final String key = "vsS1o3C5i7E9t0Y8am";
            final String opid = "1";

            final String message;
            JSONObject json = new JSONObject();
            try {

                json.put("fname", fname);
                json.put("lname", lname);
                json.put("houseno", houseno);
                json.put("colony", stret);
                json.put("landmark", landmark);
                json.put("wardno", wardno);
                json.put("city", ctyvlg);
                json.put("pincode", pincode);
                json.put("block", block);
                json.put("district", distrct);
                json.put("state", state);
                json.put("mobno", mobile);

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
                            Log.d("jabaver", fname);
                            hidePDialog();
                            try {
                                JSONObject jsonresponse = new JSONObject(response);
                                String success = jsonresponse.getString("status");

                                if (success.equals("success")) {

                                    Fragment fragment = new RepresentativeFragment();
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.createAccountFrame, fragment);
                                    ft.commit();

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Registration Failed")
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


    public static ArrayList autocomplete(String input) {

        Log.d("chala00","chal");
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {

            return resultList;

        } catch (IOException e) {

            return resultList;
        } finally {
            if (conn != null) {

                conn.disconnect();
            }
        }
        try {

            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            resultList = new ArrayList(predsJsonArray.length());
            Log.d("arrjs00", String.valueOf(predsJsonArray));
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch(JSONException e){

        }
        return resultList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements android.widget.Filterable {
        private ArrayList resultList;
        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }
        @Override
        public int getCount() {
            return resultList.size();
        }
        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }
        @Override
        public android.widget.Filter getFilter() {
            android.widget.Filter filter = new android.widget.Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {

                        resultList = autocomplete(constraint.toString());

                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }


}
