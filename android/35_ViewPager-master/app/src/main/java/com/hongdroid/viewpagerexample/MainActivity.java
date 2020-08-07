package com.hongdroid.viewpagerexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.hongdroid.viewpagerexample.ExtraTabs.FollowingActivity;
import com.hongdroid.viewpagerexample.ExtraTabs.LikeActivity;
import com.hongdroid.viewpagerexample.ExtraTabs.LoginActivity;
import com.hongdroid.viewpagerexample.ExtraTabs.MyWritingsActivity;
import com.hongdroid.viewpagerexample.tabs.Frag2;
import com.hongdroid.viewpagerexample.tabs.ViewPagerAdapter;

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

public class MainActivity extends AppCompatActivity {

    //splash에 사용
    private static int SPLASH_TIME = 1;

    // viewpager에 사용
    private FragmentPagerAdapter fragmentPagerAdapter;

    // navigation menu에 사용
    private DrawerLayout drawerLayout;
    private View drawerView;

    String writer_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        // 현재 로그인 된 이메일을 가져온다
        SharedPreferences sharedPreferences = getSharedPreferences("cur_email",MODE_PRIVATE);
        writer_email = sharedPreferences.getString("cur_email", "여기서도 안 됐음");

        // 키워드 관련 시를 가져온다
        new keyword_poem_get().execute("http://192.168.0.14:8000/keyword_poem_get/");

        // navigation menu 여는 코드
        ImageButton btn_open = (ImageButton)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        LinearLayout my_writing = (LinearLayout) findViewById(R.id.my_writing);
        LinearLayout my_like = (LinearLayout) findViewById(R.id.my_like);
        LinearLayout my_following = (LinearLayout) findViewById(R.id.my_following);
        LinearLayout exit = (LinearLayout) findViewById(R.id.exit);

        my_writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyWritingsActivity.class);
                intent.putExtra("writer", writer_email);
                startActivity(intent);
            }
        });

        my_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LikeActivity.class);
                startActivity(intent);
            }
        });

        my_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FollowingActivity.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });


        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        // viewpager 세팅
        ViewPager viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    // 키워드에 맞는 시를 하나 가져온다
    public class keyword_poem_get extends AsyncTask<String, String, String> {
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

                    writer.write("{}");
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

                    Log.d("받은 내용 main activity >", "doInBackground: " + buffer.toString());

                    JSONObject tmp_json = new JSONObject(buffer.toString());

                    Log.d("json 어레이 ", "doInBackground: >>>>>>>>>" + tmp_json);

                    // 키워드를 가져온다 - 시 + 키워드
                    String _keyword = tmp_json.getString("keyword");
                    String _keyword_poem = tmp_json.getString("keyword_poem");
                    String _keyword_poet = tmp_json.getString("keyword_poet");

                    Log.d("키워드 시 관련 : ",""+_keyword+"  "+_keyword_poem + "  "+_keyword_poet);

                    // 키워드를 저장해준다
                    SharedPreferences sharedPreferences = getSharedPreferences("keyword",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("keyword", _keyword);
                    editor.commit();

                    // 키워드를 저장해준다
                    SharedPreferences sharedPreferences2 = getSharedPreferences("keyword_poem",MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString("keyword_poem", _keyword_poem);
                    editor2.commit();

                    // 키워드를 저장해준다
                    SharedPreferences sharedPreferences3 = getSharedPreferences("keyword_poet",MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                    editor3.putString("keyword_poet", _keyword_poet);
                    editor3.commit();

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

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        // slide 했을때 호출됨.
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        // 열었을때 호출됨.
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        // 닫혔을 때 호출됨.
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };


}
