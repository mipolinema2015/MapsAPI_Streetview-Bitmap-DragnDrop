package com.pastelstudio.hilal13.streetview;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap resizedbitmap1;
    ImageButton ton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        Toast.makeText(CaptureActivity.this, "Captured!",
                Toast.LENGTH_SHORT).show();
        imageView = (ImageView) findViewById(R.id.imageView1);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        resizedbitmap1=Bitmap.createBitmap(bmp, 0,0,720,720);
//        Bitmap bitmap = BitmapFactory.decodeStream(StreetviewActivity.java.openFileInput("myImage"));//here context can be anything like getActivity() for fragment, this or MainActivity.this
        imageView.setImageBitmap(bmp);
        imageView.setImageBitmap(resizedbitmap1);

        ton1 = (ImageButton) findViewById(R.id.imageButton1);
        ton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

//                saveFile(CaptureActivity.this,resizedbitmap1,"Image");
                    saveImage(resizedbitmap1);
                /*
                ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
                File file = wrapper.getDir("Images",MODE_PRIVATE);
                file = new File(file, "UniqueFileName"+".jpg");
                try {
                    OutputStream stream = null;
                    stream = new FileOutputStream(file);

                    resizedbitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.flush();
                    stream.close();
                    Toast.makeText(CaptureActivity.this, "ISO COK!",
                            Toast.LENGTH_SHORT).show();
                }
                    catch (IOException e) // Catch the exception
                    {
                        e.printStackTrace();
                    }
                Uri savedImageURI = Uri.parse(file.getAbsolutePath()); */
                Intent inte = new Intent(CaptureActivity.this, MapsActivity.class);
                startActivity(inte);
            }
        });
    }

    public static void saveFile(Context context, Bitmap b, String picName){
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        catch (FileNotFoundException e) {
//            Log.d(TAG, "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
//            Log.d(TAG, "io exception");
            e.printStackTrace();
        } finally {
//            fos.close();
        }
    }
    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Shutta_"+ timeStamp +".png";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
