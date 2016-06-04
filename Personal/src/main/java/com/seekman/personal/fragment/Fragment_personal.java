package com.seekman.personal.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seekman.personal.R;


/**
 * Created by alice on 2016/5/19.
 */
public class Fragment_personal extends Fragment {
//个人

    private View root = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.personal_main,null);
        init();
        data();
        setting();
        lister();

        return root;
    }

    private void data() {
    }

    private void setting() {
    }

    private void lister() {

    }

    private void init() {
    }

}
