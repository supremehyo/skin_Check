package com.example.s20141210jinwoojung.capston.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.s20141210jinwoojung.capston.R;

//import xebia.ismail.e_learning.R;
/**
 * Created by Admin on 5/25/2016.
 */
public class TabGeometry extends Fragment
{

    EditText side;
    TextView tv_vol, tv_luas, tv_kel;
    Button hasil;

    double vol, luas, kel;
    String value;
    String tf;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.geometry_cube, container, false);
        side = (EditText) v.findViewById(R.id.side);
        tv_vol = (TextView) v.findViewById(R.id.volume);
        tv_luas = (TextView) v.findViewById(R.id.area);
        tv_kel = (TextView)v.findViewById(R.id.kel);
      //  tv_vol.setText(value);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

