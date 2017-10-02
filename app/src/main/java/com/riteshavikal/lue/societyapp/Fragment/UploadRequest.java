package com.riteshavikal.lue.societyapp.Fragment;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.riteshavikal.lue.societyapp.CameraPackage.CropImage;
import com.riteshavikal.lue.societyapp.R;
import com.riteshavikal.lue.societyapp.SaveUserId;
import com.riteshavikal.lue.societyapp.tabdesign;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadRequest extends Fragment implements View.OnClickListener {


    public UploadRequest() {
        // Required empty public constructor
    }

    EditText mrequesttitle,mrequestdewscrition;
    Button mchooseimagebtn,muploadrequstbtn;

    ImageView mrequstimage,mbackomage;
    private Bitmap bitmap;
    AlertDialog dialog;
    String strtim;

    private int PICK_IMAGE_REQUEST = 1;
    private int CAMERA_REQUEST = 2;

    public static final String KEY_ID= "user_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "content";
    public static final String KEY_IMAGE = "image";

    Uri imageUri;
    private static final int PICK_CROPIMAGE = 4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_request, container, false);

        mrequesttitle = (EditText) view.findViewById(R.id.request_title);
        mrequestdewscrition = (EditText) view.findViewById(R.id.request_description);

        mchooseimagebtn = (Button) view.findViewById(R.id.chooseimage_btn);
        muploadrequstbtn = (Button) view.findViewById(R.id.uploadrequst_btn);

        mrequstimage = (ImageView) view.findViewById(R.id.requst_Image);

        mchooseimagebtn.setOnClickListener(this);
        muploadrequstbtn.setOnClickListener(this);



        return  view;
    }

    @Override
    public void onClick(View v) {

        if(v == mchooseimagebtn){

            AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
            View mview = getActivity().getLayoutInflater().inflate(R.layout.chooseimage, null);
            Button mtakephoto = (Button) mview.findViewById(R.id.imagebycamera);
            mtakephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_REQUEST);


                }
            });

            Button mtakegallery = (Button) mview.findViewById(R.id.imagebygallery);
            mtakegallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showFileChooser();

                }
            });
            mbuilder.setView(mview);
            dialog = mbuilder.create();
            dialog.show();
        }

        if(v == muploadrequstbtn){
            uploadImage();
        }

    }

    private void showFileChooser() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 23) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            } else {

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, ""), PICK_IMAGE_REQUEST);

            }
        }catch (Exception e){

            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bmp==null){

        }else {
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        }
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage() {


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        Log.e("Activity name", "time date "+formattedDate);

        final String userid = String.valueOf(SaveUserId.getInstance(getContext()).getUserId());
        final String title = mrequesttitle.getText().toString().trim();
        final String description = mrequestdewscrition.getText().toString().trim();
        final String image = getStringImage(bitmap);


        if(title.isEmpty()){

            mrequesttitle.requestFocus();
            mrequesttitle.setError("This Field Is Mandatory");
        }else if(description.isEmpty()){

            mrequestdewscrition.requestFocus();
            mrequestdewscrition.setError("This Field Is Mandatory");
        }
        else {

            final String key = "vsS1o3C5i7E9t0Y8am";
            final String keyopid = "1";

            Random rnd = new Random();
            int n = 100000 + rnd.nextInt(900000);

            final String message;
            final JSONObject json = new JSONObject();
            try {
                json.put("type", "1");
                json.put("userid", userid);
                json.put("timestamp", formattedDate);
                json.put("title", title);
                json.put("description", description);
                json.put("img", image);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            message = json.toString();

            Log.d("hbibiibib", "" + message);
            final String KEY_mobile = "key";
            final String KEY_mac = "opid";
            final String KEY_mobilenumber = "jsondata";


            String url = null;
            String REGISTER_URL = "http://societycommunication.ap-south-1.elasticbeanstalk.com/webresources/messageservice";

            REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
            try {
                URL sourceUrl = new URL(REGISTER_URL);
                url = sourceUrl.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            final ProgressDialog loading = ProgressDialog.show(getContext(), "Uploading...", "Please wait...", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("jaba", userid);
                            try {
                                JSONObject jsonresponse = new JSONObject(response);
                                String  success = jsonresponse.getString("status");

                                if (success.equals("success")) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Upoading Success")
                                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    tabdesign fragment = new tabdesign();
                                                    FragmentManager manager = getFragmentManager();
                                                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Society App").commit();

                                                }
                                            })
                                            .create()
                                            .show();


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Upoading Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //  Log.d("jabadi", headline);
                            loading.dismiss();
                            Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("bada123", userid);

                            loading.dismiss();
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.d("error1234", error.toString());

                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(KEY_mobile, key);
                    params.put(KEY_mac, keyopid);
                    params.put(KEY_mobilenumber, message);
                    return params;

                }

            };
            // stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            stringRequest.setRetryPolicy(
                    new DefaultRetryPolicy(
                            500000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
            );


            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            // Log.d("try4",str);
            super.onActivityResult(requestCode, resultCode, data);}catch (Exception e) {
            Log.d("try8", e.toString());
            //   Toast.makeText(getContext(), "On super " + e.toString(), Toast.LENGTH_LONG).show();

        }


//
//        if (requestCode >= PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//
//            Uri filePath = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
//                mnewsimage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }

        if(requestCode==PICK_IMAGE_REQUEST) {

            if(data==null){

                Toast.makeText(getContext()," Please Select Image For Uploading.... ",Toast.LENGTH_LONG).show();

            }else {
                Uri filePath = data.getData();
                Intent intentcrop = new Intent(getContext(), CropImage.class);
                intentcrop.putExtra("ramji", filePath.toString());
                startActivityForResult(intentcrop, 6);
            }
        }


//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            bitmap = (Bitmap) data.getExtras().get("data");
//            mnewsimage.setImageBitmap(bitmap);
//        }

        if(requestCode == CAMERA_REQUEST ){
//            if(data.getExtras()==null){
//
//                Toast.makeText(getContext()," Please Take Image For Uploading.... ",Toast.LENGTH_LONG).show();
//
//            }else {
//                Bitmap bitmapcamear = (Bitmap) data.getExtras().get("data");
//                String bitstring = getStringImage(bitmapcamear);
            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(thumbnail == null){

                Toast.makeText(getContext()," Please Select Image For Uploading.... ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
            else {
                boolean checktr = true;
                Intent intentcrop = new Intent(getContext(), CropImage.class);
                intentcrop.putExtra("cameraji", imageUri.toString());
                intentcrop.putExtra("camerajiboolean", checktr);
                startActivityForResult(intentcrop, PICK_CROPIMAGE);
            }
//            }
//            bitmap =  UtilityClass.getImage(GlobalVariables.profilepic_name);
//            mnewsimage.setImageBitmap(bitmap);
//            dialog.dismiss();


        }

        if(requestCode==PICK_CROPIMAGE)
        {
            strtim = data.getStringExtra("cropimageone");

            Log.d("imageindash","imageindd "+strtim);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), Uri.parse(strtim));
            } catch (Exception e) {
                e.printStackTrace();
            }
            catch(Error e2) {

                bitmap  = getDownsampledBitmap(getContext(), Uri.parse(strtim),500,500);
                Toast.makeText(getContext(),"Your Image Is Too Big You May Found Some Problem",Toast.LENGTH_LONG).show();
            }
            Log.d("imageinbitmap","imageinbit "+bitmap);
            mrequstimage.setImageBitmap(bitmap);

            dialog.dismiss();

        }

        if(requestCode==6)
        {
            strtim = data.getStringExtra("cropimage");
            Log.d("imageindash","imageindd "+strtim);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), Uri.parse(strtim));
            }catch(Exception e){

                Toast.makeText(getContext(),"Your Image Is Too Big",Toast.LENGTH_LONG).show();
            }
            catch(Error e2) {

                bitmap  = getDownsampledBitmap(getContext(), Uri.parse(strtim),500,500);
                 Toast.makeText(getContext(),"Your Image Is Too Big You May Found Some Problem",Toast.LENGTH_LONG).show();
            }

            Log.d("imageinbitmap","imageinbit "+bitmap);
            mrequstimage.setImageBitmap(bitmap);

            dialog.dismiss();


        }

    }

    private Bitmap getDownsampledBitmap(Context ctx, Uri uri, int targetWidth, int targetHeight) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options outDimens = getBitmapDimensions(uri);

            int sampleSize = calculateSampleSize(outDimens.outWidth, outDimens.outHeight, targetWidth, targetHeight);

            bitmap = downsampleBitmap(uri, sampleSize);

        } catch (Exception e) {
            //handle the exception(s)
        }

        return bitmap;
    }

    private BitmapFactory.Options getBitmapDimensions(Uri uri) throws FileNotFoundException, IOException {
        BitmapFactory.Options outDimens = new BitmapFactory.Options();
        outDimens.inJustDecodeBounds = true; // the decoder will return null (no bitmap)

        InputStream is= getContext().getContentResolver().openInputStream(uri);
        // if Options requested only the size will be returned
        BitmapFactory.decodeStream(is, null, outDimens);
        is.close();

        return outDimens;
    }

    private int calculateSampleSize(int width, int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;

        if (height > targetHeight || width > targetWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) targetHeight);
            final int widthRatio = Math.round((float) width / (float) targetWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private Bitmap downsampleBitmap(Uri uri, int sampleSize) throws FileNotFoundException, IOException {
        Bitmap resizedBitmap;
        BitmapFactory.Options outBitmap = new BitmapFactory.Options();
        outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
        outBitmap.inSampleSize = sampleSize;

        InputStream is = getContext().getContentResolver().openInputStream(uri);
        resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
        is.close();

        return resizedBitmap;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }



}
