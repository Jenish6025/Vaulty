package com.example.datavaulty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class sound_unhide_act extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView img_back,img_lock;
    ArrayList<privateimagemodelunhide> files;
    List<File> Selectedfiles;
    TextView txt_main_remove_all_effect;
    AsyncHttpClient asyncHttpClient;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_unhide_act);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences=getSharedPreferences("Deviceid",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        img_back=(ImageView)findViewById(R.id.img_back);
        img_lock=(ImageView)findViewById(R.id.img_lock_sound_unhide);

        files=new ArrayList<>();
        Selectedfiles =new ArrayList<>();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        final Dialog dialog=new Dialog(sound_unhide_act.this);
                        dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.lock_dialog);
                        dialog.setCancelable(false);

                        Button btn_finish=(Button)dialog.findViewById(R.id.btn_finish);

                        txt_main_remove_all_effect=dialog.findViewById(R.id.dialog_count);
                        int cnt=0;
                        for (File temp:Selectedfiles)
                        {
                            cnt++;
                        }

                        txt_main_remove_all_effect.setText(cnt+" Sounds Added to Back");

                        dialog.show();
                        int count=0;

                        Log.e("----select",""+Selectedfiles);

                        for (File temp:Selectedfiles)
                        {
                            Log.e("Path--",temp.toString());

                            String sdCard = Environment.getExternalStorageDirectory().toString();
                            // the file to be moved or copied
                            File sourceLocation = new File (""+temp);
                            // make sure your target location folder exists!
                            File vault = new File (sdCard + "/Vaulty");
                            File sound = new File (sdCard + "/Vaulty/Vaulty_sound");

                            if (!vault.exists())
                            {
                                vault.mkdir();
                            }
                            if(!sound.exists())
                            {
                                sound.mkdir();
                            }


                            //sourcelocation
                            String sub2=sourceLocation.toString();

                            String s1=sub2.substring(1,sub2.length());
                            File sourcelocation1=new File(s1);
                            Log.e("----s1",""+sourcelocation1);


                            //targetlocation
                            String sub1=null;
                            sub1=temp.toString();

                            String s=sub1.substring(1,sub1.length());

                            Log.e("s--",""+s);

                            String[] substr1=s.split("/");

                            Log.e("substr----",""+substr1);

                            int len=substr1.length-1;

                            String name=substr1[len];

                            Log.e("split",""+name);

                            File targetLocation=new File(sound+"/"+name);

                            Log.e("video locations",""+sourcelocation1);
                            Log.e("video locationt",""+targetLocation);
                            // moving the file to another directory
                            sourcelocation1.renameTo(targetLocation);
                            count++;
                        }
                        String deviceid=sharedPreferences.getString("Deviceid",null);

                        Log.e("---->count",""+count);
                        Log.e("---->device",""+deviceid);


                        if (asyncHttpClient!=null)
                        {
                            asyncHttpClient.cancelRequests(sound_unhide_act.this,true);
                        }

                        asyncHttpClient=new AsyncHttpClient();
                        RequestParams requestParams=new RequestParams();
                        requestParams.put("deviceid",deviceid);
                        requestParams.put("totalitem",count);
                        requestParams.put("type","Sound UnHide");

                        asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_addhistory.php",requestParams,new soundunhidhandler());


                        btn_finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                startActivity(new Intent(sound_unhide_act.this,homescreen.class));
                                finish();

                            }
                        });

                    }
                },3);



            }
        });

        String path = Environment.getExternalStorageDirectory().toString()+"/Android/data/Vaulty/files/audio";
        File[] file;
        File directory = new File(path);
        file = directory.listFiles();

        for(File temp:file)
        {
            files.add(new privateimagemodelunhide(temp.getPath(),temp.getName()));
        }

        recyclerView=(RecyclerView) findViewById(R.id.recycle);
        layoutManager=new GridLayoutManager(sound_unhide_act.this,2);
        recyclerView.setLayoutManager(layoutManager);
        sound_act_hide_adp adp=new sound_act_hide_adp(sound_unhide_act.this,files);
        recyclerView.setAdapter(adp);
    }

    public class soundunhidhandler extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            Log.e("---->",data);

            history_pojo_class history=new Gson().fromJson(data,history_pojo_class.class);



        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }

    public class sound_act_hide_adp extends RecyclerView.Adapter<sound_act_hide_adp.Myview> {


        Context context;
        ArrayList<privateimagemodelunhide> data;

        public sound_act_hide_adp(Context context, ArrayList<privateimagemodelunhide> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public sound_act_hide_adp.Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(context).inflate(R.layout.sound_unhide_data_row,null);
            return new sound_act_hide_adp.Myview(view);

        }

        @Override
        public void onBindViewHolder(@NonNull final sound_act_hide_adp.Myview holder, final int position) {

            holder.sound_name.setText(data.get(position).getImagename());
            Glide.with(context).load(data.get(position).getImagePath()).into(holder.iv_sound_unhide);

            if(data.get(position).isSelected())
            {
                holder.sound_select.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.sound_select.setVisibility(View.GONE);
            }
            holder.iv_sound_unhide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.get(position).isSelected())
                    {
                        data.get(position).setSelected(false);
                        holder.sound_select.setVisibility(View.GONE);
                        Selectedfiles.remove(data.get(position).getImagePath());

                        Log.e("---------->remove", "" + data.get(position).getImagePath());
                    }
                    else
                    {

                        data.get(position).setSelected(true);
                        holder.sound_select.setVisibility(View.VISIBLE);
                        Selectedfiles.add(new File(data.get(position).getImagePath()));

                        Log.e("---------->add", "" + data.get(position).getImagePath());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Myview extends RecyclerView.ViewHolder {

            ImageView iv_sound_unhide;
            RelativeLayout sound_select;
            TextView sound_name;
            public Myview(@NonNull View itemView) {
                super(itemView);
                iv_sound_unhide=(ImageView)itemView.findViewById(R.id.iv_sound_unhide);
                sound_name=(TextView)itemView.findViewById(R.id.sound_name);
                sound_select=(RelativeLayout) itemView.findViewById(R.id.sound_select);
            }
        }
    }

}
