package com.example.datavaulty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class question_answer extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AsyncHttpClient asyncHttpClient;
    Spinner spinner;
    EditText answer;
    String ans,id,devid;
    String qid;
    AsyncHttpClient asyncHttpClient1;
    Button startnow;
    question_api_response_pojo question;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor1;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;

    List<question_api_response_pojo.SequrityquestionDatum> aa=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        spinner = (Spinner) findViewById(R.id.spinner);
        answer=(EditText)findViewById(R.id.answer);
        startnow=(Button)findViewById(R.id.startnow);

        spinner.setOnItemSelectedListener(this);

        sharedPreferences1=getSharedPreferences("question",MODE_PRIVATE);
        editor1=sharedPreferences1.edit();
        sharedPreferences2=getSharedPreferences("answer",MODE_PRIVATE);
        editor2=sharedPreferences2.edit();
        sharedPreferences3=getSharedPreferences("qid",MODE_PRIVATE);
        editor3=sharedPreferences3.edit();

        if (asyncHttpClient!=null)
        {
            asyncHttpClient.cancelRequests(question_answer.this,true);
        }

        asyncHttpClient=new AsyncHttpClient();
        asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_showsequrityquestion.php",null,new response_question() );

        startnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0) {
                    if (asyncHttpClient1 != null) {
                        asyncHttpClient1.cancelRequests(question_answer.this, true);
                    }

                    sharedPreferences = getSharedPreferences("Deviceid", MODE_PRIVATE);
                    devid = sharedPreferences.getString("Deviceid", null);
                    ans = answer.getText().toString();

                    editor2.putString("answer",ans);
                    editor2.commit();
                    //Toast.makeText(getApplicationContext(),devid,Toast.LENGTH_LONG).show();

                    asyncHttpClient1 = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("deviceid", id);
                    requestParams.put("questionid", qid);
                    requestParams.put("answear", ans);

                    editor3.putString("qid",qid);
                    editor3.commit();

                    asyncHttpClient1.post("http://skysparrow.in/project_api/vault/api_addanswer.php", requestParams, new response_answer());
                }
                else {
                    answer.setError("Enter Your Answer First");
                }
            }
        });
    }

    class response_answer extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);
            question_answer_response_pojo qarp=new Gson().fromJson(data, question_answer_response_pojo.class);

            startActivity(new Intent(question_answer.this,splashscreen.class));
            finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }

    class response_question extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            String s=new String(responseBody);

            question=new Gson().fromJson(s, question_api_response_pojo.class);
            question_List_view adp=new question_List_view(question_answer.this,R.layout.question_data_row,question.sequrityquestionData);
            spinner.setAdapter(adp);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("-------->",question.sequrityquestionData.get(position).questionid);
                    qid=question.sequrityquestionData.get(position).questionid;

                    editor1.putString("qid",qid);
                    editor1.commit();
                    Log.e("--->qidd",""+qid);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
