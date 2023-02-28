package com.example.datavaulty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class History extends AppCompatActivity {

    RecyclerView recycle;
    RecyclerView.LayoutManager layoutManager;
    AsyncHttpClient asyncHttpClient;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView img_back_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        img_back_history=(ImageView)findViewById(R.id.img_back_history);
        sharedPreferences=getSharedPreferences("Deviceid",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        recycle=(RecyclerView)findViewById(R.id.recyclehistory);
        String deviceid=sharedPreferences.getString("Deviceid",null);

        img_back_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (asyncHttpClient!=null)
        {
            asyncHttpClient.cancelRequests(History.this,true);
        }
        asyncHttpClient=new AsyncHttpClient();
        RequestParams requestParams=new RequestParams();
        requestParams.put("deviceid",deviceid);
        asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_show_history.php",requestParams
                ,new Historyhandler());

    }
    public class Historyhandler extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);
            show_history_pojo_class wb = new Gson().fromJson(data, show_history_pojo_class.class);

            try {
                   layoutManager=new GridLayoutManager(History.this,1);
                //layoutManager = new GridLayoutManager(primium.this, 1);
                recycle.setLayoutManager(layoutManager);
                show_history_adp adp = new show_history_adp(History.this,wb.hidehistoryData);
                recycle.setAdapter(adp);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }
}
