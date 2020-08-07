package com.hongdroid.viewpagerexample.ExtraTabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hongdroid.viewpagerexample.Follwers_recycler.FollowersAdapter;
import com.hongdroid.viewpagerexample.Follwers_recycler.FollowersData;
import com.hongdroid.viewpagerexample.MyWritings_recycler.MyWritingsData;
import com.hongdroid.viewpagerexample.R;
import com.hongdroid.viewpagerexample.R;

import org.json.JSONArray;
import org.json.JSONException;
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

import java.util.ArrayList;

public class FollowingActivity extends AppCompatActivity {
    private View view;

    JSONArray my_follower_jsonArr, follower_name_jsonArr;
    String cur_email;


    // tab3 recyclerview에서 사용
    private ArrayList<FollowersData> arrayList;
    private FollowersAdapter FollowersAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ImageView iv_back;

    public static FollowingActivity newInstance() {
        FollowingActivity fragWednesday = new FollowingActivity();
        return fragWednesday;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        //tab3 recyclerview
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        FollowersAdapter = new FollowersAdapter(arrayList);
        recyclerView.setAdapter(FollowersAdapter);


        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 내 이메일 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("cur_email", MODE_PRIVATE);
        cur_email = sharedPreferences.getString("cur_email", "여기서도 안 됐음");

        // 내 팔로우 리스트 가져오기
        new get_my_follows().execute("http://192.168.0.14:8000/get_follower_list/");
    }

    // 내 모든 팔로우 목록을 가져온다
    public class get_my_follows extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);

                    // 연결을 해준다
                    con = (HttpURLConnection) url.openConnection();

                    // 연결 설정해주기
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "text/html");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    // 서버로 보낼 스트림 -> 이걸 이용한 버퍼 생성
                    OutputStream outStream = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                    String tmp = "{\"my_email\":\""+cur_email+"\"}";
                    writer.write(tmp);
                    Log.d("잘 되니22? tmp 값", "doInBackground: >>>>>>>>>>>>>>>" + tmp);
                    writer.flush();
                    writer.close();

                    // 서버로부터 데이터 받음 -> 이걸 이용한 버퍼 생성
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    // 결과로 리턴 될 버퍼
                    StringBuffer buffer = new StringBuffer();

                    // 버퍼 리더로부터 문자열을 받은걸 결과 버퍼에 담는다
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    JSONObject tmp_json = new JSONObject(buffer.toString());
                    my_follower_jsonArr = tmp_json.getJSONArray("every_follower");
                    follower_name_jsonArr = tmp_json.getJSONArray("every_name");

                    Log.d("json 어레이 ", "doInBackground: >>>>>>>>>" + my_follower_jsonArr);

                    // jsonArray 에 있는 모든 내용물들을 리사이클러 뷰에 담아준다
                    for (int i = 0; i < my_follower_jsonArr.length(); i++) {
                        try {
                            Log.d("팔로워 이메일, 팔로워 이름", "doInBackground: >>>>>>>>>>>>" + my_follower_jsonArr.getString(i) + "  " +  follower_name_jsonArr.getString(i));

                            FollowersData tab3Data = new FollowersData("", "", "", follower_name_jsonArr.getString(i), my_follower_jsonArr.getString(i));

                            arrayList.add(tab3Data);

                            FollowingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    FollowersAdapter.notifyDataSetChanged();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // 결과 버퍼 내용을 문자열로 바꿔서 리턴한다
                    return buffer.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally
                {
                    if (con != null) {
                        con.disconnect();
                    }
                    try
                    {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}

