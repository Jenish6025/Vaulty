 package com.example.datavaulty;

 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;
 import android.provider.Settings;
 import android.util.Log;
 import android.view.View;
 import android.view.WindowManager;
 import android.widget.Button;

 import androidx.appcompat.app.AppCompatActivity;

 import com.google.gson.Gson;
 import com.loopj.android.http.AsyncHttpClient;
 import com.loopj.android.http.AsyncHttpResponseHandler;
 import com.loopj.android.http.RequestParams;

 import cz.msebera.android.httpclient.Header;

 public class instruction extends AppCompatActivity {
    Button startnow;
    SharedPreferences sharedPreferences,sharedPreferences1;
    SharedPreferences.Editor editor,editor1;
    AsyncHttpClient asyncHttpClient;
    String devicename,did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        devicename= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        sharedPreferences=getSharedPreferences("First",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putString("First","start");
        editor.commit();


        startnow=(Button)findViewById(R.id.startnow);

        startnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (asyncHttpClient!=null)
                {
                    asyncHttpClient.cancelRequests(instruction.this,true);
                }

                asyncHttpClient=new AsyncHttpClient();
                RequestParams requestParams=new RequestParams();

                requestParams.put("deviceid",devicename);

                asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_adddeviceid.php",requestParams,new response_handler());

            }
        });

    }
     class response_handler extends AsyncHttpResponseHandler
     {

         @Override
         public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
             String s=new String(responseBody);

             Log.e("--------------->",s);

             deviceid_api_response_pojo devicename=new Gson().fromJson(s,deviceid_api_response_pojo.class);


             did=devicename.deviceid;
             //Toast.makeText(getApplicationContext(),did,Toast.LENGTH_LONG).show();

             sharedPreferences1=getSharedPreferences("Deviceid",MODE_PRIVATE);
             editor1=sharedPreferences1.edit();

             editor1.putString("Deviceid",did);
             editor1.commit();


             startActivity(new Intent(instruction.this,question_answer.class));
             finish();
         }

         @Override
         public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

         }
     }
}
