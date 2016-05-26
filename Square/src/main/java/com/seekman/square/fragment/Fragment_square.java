package com.seekman.square.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.seekman.square.R;

public class Fragment_square extends Fragment {
//广场

    private View root = null;
    private Button mBtnTest = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.square_main,null);

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
        mBtnTest.setOnClickListener (new View.OnClickListener (){

            @Override
            public void onClick(View v) {
                Toast.makeText (getContext (),"广场",Toast.LENGTH_LONG).show ();
            }
        });
    }

    private void init() {
        mBtnTest = (Button) root.findViewById (R.id.bt_test);
    }

}
