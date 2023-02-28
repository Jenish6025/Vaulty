package com.example.datavaulty;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;


import cz.msebera.android.httpclient.Header;

public class primium extends AppCompatActivity {

    DiscreteScrollView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AsyncHttpClient asyncHttpClient;
    private ProgressDialog progressDialog;
   InfiniteScrollAdapter infiniteAdapter;
    ImageView img_back;
    AVLoadingIndicatorView avi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primium);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView=findViewById(R.id.recycle);
        img_back=(ImageView)findViewById(R.id.img_back);
        avi=findViewById(R.id.avi);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);


        //progressDialog.show();
            avi.show();
        if (asyncHttpClient!=null)
        {
            asyncHttpClient.cancelRequests(primium.this,true);
        }
        asyncHttpClient=new AsyncHttpClient();
        asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_showpackage.php",null,new response_handler());
    }
    class response_handler extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            primium_pojo_response wb = new Gson().fromJson(data, primium_pojo_response.class);
            try {
               /* layoutManager=new LinearLayoutManager(primium.this,LinearLayoutManager.HORIZONTAL,false);
                //layoutManager = new GridLayoutManager(primium.this, 1);
                recyclerView.setLayoutManager(layoutManager);
                primium_adp adp = new primium_adp(primium.this,wb.packageData);
                recyclerView.setAdapter(adp);*/

                infiniteAdapter = InfiniteScrollAdapter.wrap(new primium_adp(primium.this, wb.packageData));
                // gridAdapter = new home_screen_adp(MainActivity.this, hr.appStoryitem);
                recyclerView.setItemTransformer(new ScaleTransformer.Builder()
                        .setMinScale(0.8f)
                        .build());
                recyclerView.setAdapter(infiniteAdapter);
                //progressDialog.hide();
                avi.hide();
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }
}
