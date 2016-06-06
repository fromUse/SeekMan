package com.seekman.square.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.seekman.square.R;

public class Fragment_square extends Fragment {
//广场
private static final String TAG = "Fragment_square";

    private View root = null;
    private Button mBtnTest = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            Log.i (TAG, "onCreateView: "  + savedInstanceState.toString ());
        }

        //Log.i (TAG, "onCreateView: ----------------------");
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


    @Override
    public void onResume() {

        super.onResume ();


        Log.i (TAG, "onResume: -----------------可见状态-----");
    }

    @Override
    public void onPause() {

        super.onPause ();
        Log.i (TAG, "onPause: ---------------不可见------------");

    }

    @Override
    public void onDestroy() {
        super.onDestroy ();


        Log.i (TAG, "onDestroy: ------------------销毁---------------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);


        if (savedInstanceState != null) {
            Log.i (TAG, "onCreate:     savedInstanceState   不为空             ");
        }
        Log.i (TAG, "onCreate: -------------------------------------");
    }


    @Override
    public void onDestroyView() {

        Log.i (TAG, "onDestroyView: ---------------销毁view--------");

        super.onDestroyView ();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.i (TAG, "onSaveInstanceState: -----------------保存状态-------------------");
        Bundle a =  new Bundle ();
        a.putString ("key"," value");
        super.onSaveInstanceState (a);

    }


}
