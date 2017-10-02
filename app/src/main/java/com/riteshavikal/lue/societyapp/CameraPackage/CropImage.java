package com.riteshavikal.lue.societyapp.CameraPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import com.riteshavikal.lue.societyapp.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.theartofdev.edmodo.cropper.CropImageView;


public class CropImage extends AppCompatActivity {

    CropImageView cropImageView;
    LinearLayout cropBtn;
    boolean cameraSource;
    String filename="";
    String photoUri;
    Bitmap bitmap;
    Bitmap cropped;
    public static final String PHOTO_URI="photouri";
    private static final int PICK_CROPIMAGE = 4;
    boolean checkcam;
    String camearaimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        cropImageView=(CropImageView)findViewById(R.id.cropImageView);
        cropBtn=(LinearLayout)findViewById(R.id.cropBtn);

       Intent data = getIntent();
       camearaimage = data.getStringExtra("cameraji");
        checkcam = data.getBooleanExtra("camerajiboolean",false);
        Log.d("camimg00","camimg "+camearaimage);
        if(checkcam){

            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), Uri.parse(camearaimage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch(Error e2) {

                bitmap  = getDownsampledBitmap(CropImage.this, Uri.parse(camearaimage),500,500);
                // Toast.makeText(getContext(),"Your Image Is Too Big",Toast.LENGTH_LONG).show();
            }

            Log.d("bitmaocam00","bitmapvam "+bitmap);
        }

//        cameraSource=getIntent().getBooleanExtra(GlobalVariables.CAMERA_SOURCE,false);
//       checkcam = getIntent().getBooleanExtra("camerajiboolean",false);
//        if(cameraSource) {
//            filename =GlobalVariables.profilepic_name;
//            bitmap=UtilityClass.getImage(filename);
//        }

else {
            photoUri = getIntent().getStringExtra("ramji");
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(photoUri));
            } catch (IOException e) {
                e.printStackTrace();
            }

            catch(Error e2) {

                bitmap  = getDownsampledBitmap(CropImage.this, Uri.parse(photoUri),500,500);
                // Toast.makeText(getContext(),"Your Image Is Too Big",Toast.LENGTH_LONG).show();
            }
        }
        cropImageView.setImageBitmap(bitmap);


        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage(bitmap);
            }
        });
    }

    public void cropImage(Bitmap bitmap)

    {
         cropped = cropImageView.getCroppedImage();

        if(checkcam) {
            try {

                Uri imguri = getImageUri(getApplicationContext(),cropped);
                Log.d("010101",""+imguri.toString());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("cropimageone",imguri.toString());
                setResult(PICK_CROPIMAGE, returnIntent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("010111101",""+e.toString());
            }
        }else {
            //  String bitimage = getStringImage(cropped);

            //   String bitimage = getStringImage(cropped);
            Uri imguri = getImageUri(getApplicationContext(),cropped);
            Log.d("010101uri",""+imguri.toString());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("cropimage",imguri.toString());
            setResult(6, returnIntent);
            finish();


        }
        }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getStringImage(Bitmap bmp){
        long lengthbmp = BitmapCompat.getAllocationByteCount(bmp);
        byte[] imageBytes = null;
        if(lengthbmp > 16000000){

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
            imageBytes = baos.toByteArray();
            Log.d("202kghg",""+lengthbmp);
        }
        else{

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            imageBytes = baos.toByteArray();
            Log.d("202kghg",""+lengthbmp);
        }


        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    @Override
    public void onBackPressed() {

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

        InputStream is= getContentResolver().openInputStream(uri);
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

        InputStream is = getContentResolver().openInputStream(uri);
        resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
        is.close();

        return resizedBitmap;
    }

}
