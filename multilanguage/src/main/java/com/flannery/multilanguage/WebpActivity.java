package com.flannery.multilanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ResourceManagerInternal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webp);
        ImageView iv2 = findViewById(R.id.mIv2);
        iv2.setImageResource(R.drawable.ic_purchase_history_blank);

//        String s = "<p>The history </p><p>1111</p><p><img src=\"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/sites/test/7974197355424055296/jpeg/src%3Dhttp___c-ssl.duitang.com_uploads_item_202002_27_20200227162050_cpbbr.thumb.400_0.jpg%26refer%3Dhttp___c-ssl.duitang_1626421754515_531a4983.jpeg\"></p><p>1111</p>";
        String s = "<p>The history</p><p>1111</p><p>加一个 双引号\\\" \\\"</p><p>\\\"\\\"</p><p>\\'\\'</p><p><br></p><p><img src=\\\"https://teachee-site.s3.ap-south-1.amazonaws.com/frontend/sites/test/7974197355424055296/jpeg/src%3Dhttp___c-ssl.duitang.com_uploads_item_202002_27_20200227162050_cpbbr.thumb.400_0.jpg%26refer%3Dhttp___c-ssl.duitang_1626421754515_531a4983.jpeg\\\"></p><p>1111</p>";
        Document parse = Jsoup.parse(s);
        String s1 = parse.toString();
        Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();
        Log.i("TAG", s1);
        Log.i("TAG", "=======================");
        Log.i("TAG", parse.body().toString());
        Log.i("TAG", "=======================");
        Log.i("TAG", parse.body().data());

        WebView mWebView = findViewById(R.id.mWebView);
        mWebView.loadData(s1, "text/html; charset=UTF-8", null);
    }
}