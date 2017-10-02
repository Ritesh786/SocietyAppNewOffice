package com.riteshavikal.lue.societyapp.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.riteshavikal.lue.societyapp.Adapter.AppController;
import com.riteshavikal.lue.societyapp.Adapter.RecycleAdapter;
import com.riteshavikal.lue.societyapp.R;
import com.riteshavikal.lue.societyapp.SaveUserId;
import com.riteshavikal.lue.societyapp.Utils.MainSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocietyMainNews extends Fragment {

    private static final String TAG = SocietyMainNews.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<MainSingleton> movieList = new ArrayList<MainSingleton>();

    private RecyclerView recyclerView;

    private RecycleAdapter adapter;
    String strtext;

    MainSingleton movie;

    JSONObject jsonresponse;



    public SocietyMainNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new RecycleAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addOnItemTouchListener(
//                new RecyclerTouchListener(getContext(), new RecyclerTouchListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//
//                        MainSingleton mo123 = movieList.get(position);
//
//                        Intent newsdetailintnt = new Intent(getContext(),RequestDetailActivity.class);
//                        //   newsdetailintnt.putExtra("type",mo123.getYear());
//                        newsdetailintnt.putExtra("title",mo123.getTitle());
//                        newsdetailintnt.putExtra("content",mo123.getRating());
//                        newsdetailintnt.putExtra("image",mo123.getThumbnailUrl());
//                        newsdetailintnt.putExtra("id",mo123.getId());
//                        newsdetailintnt.putExtra("post","Pending Story");
//                        //    newsdetailintnt.putExtra("caption",mo123.getCaption());
//                        startActivity(newsdetailintnt);
//
//
//                        // TODO Handle item click
//                    }
//                })
//        );


        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();



        populatedata();


        return  view;
    }


    public void populatedata(){

        final String url = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/messageservice";
        strtext =  SaveUserId.getInstance(getContext()).getUserId();
        //   String newurl = url+strtext;


        final String KEY_mobile = "key";
        final String KEY_mac = "opid";
        final String KEY_mobilenumber = "jsondata";

        final String key = "vsS1o3C5i7E9t0Y8am";
        final String keyopid = "2";



        final String message;
        JSONObject json = new JSONObject();
        try {

            json.put("type", "2");
            json.put("status", "1");
            json.put("userid", "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        message = json.toString();

        Log.d("msfgh",""+message);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try {

                            jsonresponse = new JSONObject(response);
                            JSONArray jsonArray = jsonresponse.getJSONArray("msgs");

                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject obj = jsonArray.getJSONObject(i);

                                MainSingleton movie = new MainSingleton();

                                String imagestr = obj.getString("postid");
                                String imagrurl = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/fileservice?key=vsS1o3C5i7E9t0Y8am&opid=1&postid=";
                                String imageurlfull = imagrurl+imagestr;

                                movie.setTitle(obj.getString("title"));
                                movie.setImage(imageurlfull);
                                movie.setDescriptionn(obj.getString("fname"));

                                movie.setTimestamp(obj.getString("timestamp"));

                                movieList.add(movie);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);

        if(movieList!=null) movieList.clear();

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
