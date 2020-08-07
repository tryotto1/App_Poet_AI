package com.hongdroid.viewpagerexample.tabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hongdroid.viewpagerexample.ExtraTabs.WriteActivity;
import com.hongdroid.viewpagerexample.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Frag1 extends Fragment {
    private View view;
    private FloatingActionButton btn_write;

    public static Frag1 newInstance() {
        Frag1 frag1 = new Frag1();
        return frag1;
    }

    // keyword 시를 채워준다
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);

        btn_write = view.findViewById(R.id.btn_write);
        textView = (TextView) view.findViewById(R.id.textView);

        // 키워드 가져오기
        SharedPreferences sharedPreferences_keyword = getActivity().getSharedPreferences("keyword", getActivity().MODE_PRIVATE);
        String keyword = sharedPreferences_keyword.getString("keyword", "키워드");

        textView.setText(keyword);

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 글 쓰는 activity로 넘겨준다
                Intent intent = new Intent(getActivity(), WriteActivity.class);



                startActivity(intent);
            }
        });

        return view;
    }
}
