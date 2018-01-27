package com.pastelstudio.hilal13.streetview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class StreetviewActivity extends AppCompatActivity {
    ImageView imageView,sticker1,sticker2,sticker3;
    String latitude,longitude;
//    private ImageView img;
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    Button ton1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetview);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        sticker1 = (ImageView) rootLayout.findViewById(R.id.sticker1);
        sticker2 = (ImageView) rootLayout.findViewById(R.id.sticker2);
        sticker3 = (ImageView) rootLayout.findViewById(R.id.sticker3);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        sticker1.setLayoutParams(layoutParams);
        sticker1.setOnTouchListener(new ChoiceTouchListener());
        sticker2.setLayoutParams(layoutParams);
        sticker2.setOnTouchListener(new ChoiceTouchListener());
        sticker3.setLayoutParams(layoutParams);
        sticker3.setOnTouchListener(new ChoiceTouchListener());
//        imageUrl=

        Bundle extras = getIntent().getExtras();
        latitude=extras.getString("langitude");
        longitude=extras.getString("longitude");

        Toast.makeText(StreetviewActivity.this, "after! latitude "+latitude+" and longitude "+longitude,
                Toast.LENGTH_SHORT).show();

        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load("https://maps.googleapis.com/maps/api/streetview?size=1000x1000&location="+latitude+","+longitude+"&fov=90&heading=235&pitch=10&key=AIzaSyAL0DOengQWZ-RU7m5VbIXShCa67XUgvyc").into(imageView);


        Button ton1 = (Button) findViewById(R.id.button1);
        ton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Bitmap bmp = takeScreenShot(rootLayout);
//                createImageFromBitmap(bmp);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();



                Intent inte = new Intent(StreetviewActivity.this, CaptureActivity.class);
                inte.putExtra("image",byteArray);

                startActivity(inte);
            }
        });
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    Toast.makeText(StreetviewActivity.this, "xDelta :"+X+" and yDelta :"+Y,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = 10;
                    layoutParams.bottomMargin = 10;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }


    public Bitmap takeScreenShot(View view) {
        // configuramos para que la view almacene la cache en una imagen
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        view.buildDrawingCache();

        if(view.getDrawingCache() == null) return null; // Verificamos antes de que no sea null

        // utilizamos esa cache, para crear el bitmap que tendra la imagen de la view actual
        Bitmap snapshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return snapshot;
    }

}
