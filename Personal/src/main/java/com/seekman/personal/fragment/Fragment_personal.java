package com.seekman.personal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.seekman.personal.R;


/**
 * Created by alice on 2016/5/19.
 */
public class Fragment_personal extends Fragment {
//个人

    private View root = null;
    private Button mBtnTest = null;
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
        mBtnTest.setOnClickListener (new View.OnClickListener (){

            @Override
            public void onClick(View v) {
                startActivity (new Intent (getContext (),LoginActivity.class));
            }
        });
    }

    private void init() {
        mBtnTest = (Button) root.findViewById (R.id.bt_test);
    }

}
