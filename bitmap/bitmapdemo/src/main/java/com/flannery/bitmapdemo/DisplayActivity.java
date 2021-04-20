package com.flannery.bitmapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ImageView img = findViewById(R.id.img);
        TextView txt = findViewById(R.id.txt);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (type.equals("sd")) {
            //bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory())
        } else if (type.equals("assets")) {
            try {
                InputStream input = getAssets().open("model_assets.jpg");
                bitmap = BitmapFactory.decodeStream(input, new Rect(), options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model, options);
        } else if (type.equals("mdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model_mdpi, options);
        } else if (type.equals("hdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model_hdpi, options);
        } else if (type.equals("xhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model_xhdpi, options);
        } else if (type.equals("xxhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model_xxhdpi, options);
        } else if (type.equals("xxxhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model_xxxhdpi, options);
        } else if (type.equals("mhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.model_hdpi, options);
        } else if (type.equals("mxhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.model_xhdpi, options);
        } else if (type.equals("mxxhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.model_xxhdpi, options);
        } else if (type.equals("mxxxhdpi")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.model_xxxhdpi, options);
        }

        img.setImageBitmap(bitmap);

        StringBuilder builder = new StringBuilder();
        builder.append("内存占用" + bitmap.getByteCount() + " byte").append("\n");
        builder.append("width=" + bitmap.getWidth()).append("\n");
        builder.append("height=" + bitmap.getHeight()).append("\n");
        builder.append("inPreferedConfig=" + bitmap.getHeight()).append("\n");
        builder.append("inSampleSize=" + bitmap.getHeight()).append("\n");
        builder.append("inDensity=" + bitmap.getHeight()).append("\n");
        builder.append("inTargetDensity=" + bitmap.getHeight()).append("\n");
        builder.append("inScaled=" + bitmap.getHeight()).append("\n");

        txt.setText(builder.toString());
    }
}