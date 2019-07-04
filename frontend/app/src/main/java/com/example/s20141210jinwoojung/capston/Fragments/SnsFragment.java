package com.example.s20141210jinwoojung.capston.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.s20141210jinwoojung.capston.R;

//import xebia.ismail.e_learning.R;

/**
 * Created by Admin on 3/13/2017.
 */
public class SnsFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_maps, container, false);
        return v;
    }
}

