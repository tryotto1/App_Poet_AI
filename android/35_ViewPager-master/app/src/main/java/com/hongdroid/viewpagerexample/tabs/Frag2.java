package com.hongdroid.viewpagerexample.tabs;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hongdroid.viewpagerexample.R;
import com.hongdroid.viewpagerexample.tab3_recycler.Tab3Data;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class Frag2 extends Fragment {
    private View view;

    public static Frag2 newInstance() {
        Frag2 frag2 = new Frag2();
        return frag2;
    }

    // keyword 시를 채워준다
    TextView tv_title, tv_content, tv_writer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        // xml이랑 연결하기
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_writer = (TextView) view.findViewById(R.id.tv_writer);

        // 키워드 가져오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("keyword", getActivity().MODE_PRIVATE);
        String keyword = sharedPreferences.getString("keyword", "키워드");

        // 키워드 가져오기
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("keyword_poem", getActivity().MODE_PRIVATE);
        String keyword_poem = sharedPreferences2.getString("keyword_poem", "키워드");

        // 키워드 가져오기
        SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences("keyword_poet", getActivity().MODE_PRIVATE);
        String keyword_poet = sharedPreferences3.getString("keyword_poet", "키워드");
        Log.d("frag2 로그  :  ", "  "+keyword + "  "+ keyword_poem + "  "+keyword_poet);

        tv_title.setText(keyword);
        tv_content.setText(keyword_poem);
        tv_writer.setText(keyword_poet);

        return view;
    }
}
