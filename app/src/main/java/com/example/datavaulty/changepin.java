package com.example.datavaulty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class changepin extends AppCompatActivity {

    EditText oldpin;
    Button cancel,updatepin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AsyncHttpClient asyncHttpClient;

    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepin);

        oldpin=(EditText)findViewById(R.id.oldpin);
        cancel=(Button)findViewById(R.id.cancel);
        updatepin=(Button)findViewById(R.id.updatepin);

        sharedPreferences= getSharedPreferences("password",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sharedPreferences1 = getSharedPreferences("Deviceid", MODE_PRIVATE);
        final String  devid = sharedPreferences1.getString("Deviceid", null);
        final String pin=sharedPreferences.getString("password",null);

        Log.e("---->pass",""+pin);
        Log.e("---->pas",""+devid);


        final String junopin=oldpin.getText().toString();
        Log.e("---->pass juno",""+junopin);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpin.setText("");
            }
        });

        updatepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("------>",""+oldpin.getText().toString());

                if (oldpin.getText().length() == 0 )
                {
                    oldpin.setError("Enter old pin First");
                }

                Log.e("---->pass pin",""+pin);
                Log.e("---->pass juno",""+junopin);


                if (pin.equals(oldpin.getText().toString()))
                {
//                            if (asyncHttpClient!=null)
//                            {
//                                asyncHttpClient.cancelRequests(changepin.this,true);
//                            }
//                            asyncHttpClient=new AsyncHttpClient();
//                            RequestParams requestParams=new RequestParams();
//                            requestParams.put("fourdigitpin",navopin);
//                            requestParams.put("deviceid",devid);
//                            asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_updatepin.php",requestParams,new changepinhandler());

                            Intent intent=new Intent(changepin.this,dialer.class);
                            intent.putExtra("changepin","yes");
                            intent.putExtra("newPin",oldpin.getText().toString());
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            oldpin.setError("Pin not Match!");
                        }
                    }
        });

    }

//    public  class changepinhandler extends AsyncHttpResponseHandler
//    {
//
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//            String data=new String(responseBody);
//
//            Log.e("----->new pin",""+data);
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//        }
//    }


}
