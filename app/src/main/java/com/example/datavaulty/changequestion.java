package com.example.datavaulty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class changequestion extends AppCompatActivity {

    ImageView img_back_question;
    Button update;
    Spinner spinner;
    EditText answer;
    AsyncHttpClient asyncHttpClient,asyncHttpClient1;
    String ans,question,answershard,deviceid,qid,position;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor1;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;
    change_question_pojo_class pojo_class;
    question_api_response_pojo questionn;
    List<String> stringList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changequestion);

        img_back_question=(ImageView)findViewById(R.id.img_back_question);
        spinner=(Spinner)findViewById(R.id.spinner);
        answer=(EditText)findViewById(R.id.answer);
        update=(Button)findViewById(R.id.update);

        stringList.add("What is your birth place?");
        stringList.add("What is your favourite hill station?");
        stringList.add("What is your favourite food?");

        Log.e("StringList",""+stringList);

        sharedPreferences=getSharedPreferences("Deviceid",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        sharedPreferences1=getSharedPreferences("question",MODE_PRIVATE);
        editor1=sharedPreferences1.edit();
        sharedPreferences2=getSharedPreferences("answer",MODE_PRIVATE);
        editor2=sharedPreferences2.edit();
        sharedPreferences3=getSharedPreferences("qid",MODE_PRIVATE);
        editor3=sharedPreferences3.edit();

        //LastClick=sharedPreferences4.getInt("questiopn",0);

        question=sharedPreferences1.getString("question",null);
        answershard=sharedPreferences2.getString("answer",null);
        deviceid=sharedPreferences.getString("Deviceid",null);
        qid=sharedPreferences1.getString("qid",null);
        Log.e("--->qid",""+qid);


        img_back_question.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ans=answer.getText().toString();
        if (asyncHttpClient!=null)
        {
            asyncHttpClient.cancelRequests(changequestion.this,true);
        }
        asyncHttpClient=new AsyncHttpClient();
        RequestParams requestParams=new RequestParams();
        requestParams.put("questionid",question);
        requestParams.put("answer",answershard);
        requestParams.put("deviceid",deviceid);


        asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_showsequrityquestion.php",requestParams,new questionanswer() );

        answer.setText(answershard);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.getText().length()==0)
                {
                    answer.setError("Please enter your new answer");
                }
                else
                {
                    editor1.clear();
                    editor1.commit();
                    editor2.clear();
                    editor2.commit();

                    editor2.putString("answer",answer.getText().toString());
                    editor2.commit();

                    if (asyncHttpClient1!=null)
                    {
                        asyncHttpClient1.cancelRequests(changequestion.this,true);
                    }
                    asyncHttpClient1=new AsyncHttpClient();
                    RequestParams requestParams=new RequestParams();
                    requestParams.put("questionid",question);
                    requestParams.put("answer",answershard);
                    requestParams.put("deviceid",deviceid);
                    asyncHttpClient1.post("http://skysparrow.in/project_api/vault/api_showsequrityquestion.php",requestParams,new changequestionanswer());

                }

            }
        });

    }
    public class questionanswer extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            questionn=new Gson().fromJson(data, question_api_response_pojo.class);
            question_List_view adp=new question_List_view(changequestion.this,R.layout.question_data_row,questionn.sequrityquestionData);
            Log.e("--->qid",""+qid);
            spinner.setAdapter(adp);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("-------->question",questionn.sequrityquestionData.get(position).questionid);
                    qid=questionn.sequrityquestionData.get(position).questionid;

                    if (spinner.equals(stringList.get(position)))
                    {
                        spinner.setSelection(Integer.parseInt(qid));
                    }

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
    public class changequestionanswer extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            Log.e("---->changquestion",""+data);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }
}
