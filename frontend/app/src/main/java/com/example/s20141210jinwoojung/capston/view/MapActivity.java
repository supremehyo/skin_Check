package com.example.s20141210jinwoojung.capston.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.s20141210jinwoojung.capston.R;
import com.naver.maps.map.NaverMapSdk;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("r0r28ed6ou"));
    }
}
