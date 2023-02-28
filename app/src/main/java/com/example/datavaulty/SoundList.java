package com.example.datavaulty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SoundList extends AppCompatActivity {


    private RecyclerView recyclerView;
    List<Model_Files_1> SoundList = new ArrayList<>();
    SoundsListAdapter soundsListAdapter;
    ArrayList<String> FilesList;
    List<File> Selectedfiles;
    ImageView img_back,img_lock_sound;
    TextView txt_title;
    String videofoldername;
    TextView txt_main_remove_all_effect;
    AsyncHttpClient asyncHttpClient;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_list);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences=getSharedPreferences("Deviceid",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        img_back=(ImageView)findViewById(R.id.img_back);
        txt_title=(TextView)findViewById(R.id.txt_title);
        img_lock_sound=(ImageView)findViewById(R.id.img_lock_sound);

        Intent intent=getIntent();
        videofoldername=intent.getStringExtra("videofoldertitle");
        txt_title.setText(videofoldername);

        Selectedfiles=new ArrayList<>();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_lock_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {

                       final Dialog dialog=new Dialog(SoundList.this);
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

                       txt_main_remove_all_effect.setText(cnt+" Sounds Added to Private");

                       dialog.show();
                       int count=0;

                       Log.e("------select",""+Selectedfiles);

                       for (File temp:Selectedfiles)
                       {
                           String sdCard = Environment.getExternalStorageDirectory().toString();
                           // the file to be moved or copied
                           File sourceLocation = new File (""+temp);
                           // make sure your target location folder exists!
                           File vault = new File (sdCard + "/Android/data/Vaulty");
                           File file = new File (sdCard + "/Android/data/Vaulty/files");
                           File audio = new File (sdCard + "/Android/data/Vaulty/files/audio");


                           if (!vault.exists())
                           {
                               vault.mkdir();
                           }
                           if(!file.exists())
                           {
                               file.mkdir();
                           }
                           if(!audio.exists())
                           {
                               audio.mkdir();
                           }

                           //sourcelocation
                           String sub2=sourceLocation.toString();

                           String s1=sub2.substring(1,sub2.length());
                           File sourcelocation1=new File(s1);

                           //targetlocation
                           String sub1=temp.toString();

                           String s=sub1.substring(1,sub1.length());

                           String[] substr1=s.split("/");

                           int len=substr1.length-1;

                           String name=substr1[len];
                           for (String temp1:substr1)
                           {
                               Log.e("splittttttttt", "" + temp1);

                           }

                           Log.e("split",""+name);

                           File targetLocation=new File(audio+"/"+name);

                           Log.e("--->",""+sourcelocation1);
                           Log.e("-->",""+targetLocation);
                           // moving the file to another directory
                           sourcelocation1.renameTo(targetLocation);
                           count++;
                       }
                       String deviceid=sharedPreferences.getString("Deviceid",null);

                       Log.e("---->count",""+count);
                       Log.e("---->device",""+deviceid);


                       if (asyncHttpClient!=null)
                       {
                           asyncHttpClient.cancelRequests(SoundList.this,true);
                       }

                       asyncHttpClient=new AsyncHttpClient();
                       RequestParams requestParams=new RequestParams();
                       requestParams.put("deviceid",deviceid);
                       requestParams.put("totalitem",count);
                       requestParams.put("type","Sound Hide");

                       asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_addhistory.php",requestParams,new Soundhandler());

                       btn_finish.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               dialog.cancel();
                               startActivity(new Intent(SoundList.this,homescreen.class));
                               finish();
                           }
                       });

                   }
               },3);


            }
        });

        FilesList = getIntent().getStringArrayListExtra("array");
        try {
            if (FilesList.size() != 0) {
                for (int i = 0; i < FilesList.size(); i++) {
                    SoundList.add(new Model_Files_1(new File(FilesList.get(i)), false));
                }
                soundsListAdapter = new SoundsListAdapter(SoundList.this, SoundList);
                GridLayoutManager mLayoutManager = new GridLayoutManager(SoundList.this, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(soundsListAdapter);
                soundsListAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Soundhandler extends AsyncHttpResponseHandler
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



    public class SoundsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context mContext;
        private List<Object> DocumentList = new ArrayList<>();
        int positionn;
        private static final int RECIPE = 0;

        public SoundsListAdapter(Context context, List<Model_Files_1> moviesList) {
            this.mContext = context;
            this.DocumentList.addAll(moviesList);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (viewType == RECIPE) {
                View contactView = inflater.inflate(R.layout.sound_list_item, parent, false);
                SoundsListAdapter.ViewHolder viewHolder = new SoundsListAdapter.ViewHolder(contactView);
                return viewHolder;
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            int itemType = getItemViewType(position);
            if (itemType == RECIPE) {

                SoundsListAdapter.ViewHolder viewHolder = (SoundsListAdapter.ViewHolder) holder;
                final Model_Files_1 privateImageModel = (Model_Files_1) DocumentList.get(position);

                String filename = privateImageModel.getFile().getAbsolutePath().substring(privateImageModel.getFile().getAbsolutePath().lastIndexOf("/") + 1);
                viewHolder.txt_folder_name.setText(filename);
                try {
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    byte[] rawArt;
                    Bitmap art = null;
                    BitmapFactory.Options bfo = new BitmapFactory.Options();
                    mmr.setDataSource(privateImageModel.getFile().getAbsolutePath());
                    rawArt = mmr.getEmbeddedPicture();
                    if (null != rawArt)
                        art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
                    if (art != null)
                        viewHolder.img_folder.setImageBitmap(art);
                    else
                        viewHolder.img_folder.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_folder));
                } catch (IllegalArgumentException e) {
                    viewHolder.img_folder.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_folder));
                }

                Utils utils=new Utils();
                utils.NotifySDCard(SoundList.this, privateImageModel.getFile().getAbsolutePath());


                positionn=position;

                if(((Model_Files_1) DocumentList.get(position)).IsSelected)
                {
                    ((SoundsListAdapter.ViewHolder) holder).sound_upper.setVisibility(View.VISIBLE);
                }
                else
                {
                    ((SoundsListAdapter.ViewHolder) holder).sound_upper.setVisibility(View.GONE);
                }

                ((SoundsListAdapter.ViewHolder) holder).img_folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(((Model_Files_1) DocumentList.get(position)).IsSelected)
                        {
                            ((Model_Files_1) DocumentList.get(position)).setSelected(false);
                            Selectedfiles.remove(privateImageModel.getFile());
                            Log.e("soundr",""+privateImageModel.getFile());
                        }
                        else
                        {
                            ((Model_Files_1) DocumentList.get(position)).setSelected(true);
                            Selectedfiles.add(privateImageModel.getFile());
                            Log.e("sounda",""+privateImageModel.getFile());
                        }
                        // ((Model_Files_1) DocumentList.get(position)).setSelected(true);

                        notifyDataSetChanged();

                    }
                });

            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            Object item = DocumentList.get(position);
            if (item instanceof Model_Files_1) {
                return RECIPE;
            } else {
                return -1;
            }
        }

        @Override
        public int getItemCount() {
            return DocumentList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txt_folder_name;
            ImageView img_folder, iv_image1;
            RelativeLayout ll_main,sound_upper;

            public ViewHolder(View view) {
                super(view);
                ll_main = (RelativeLayout) view.findViewById(R.id.rl_main_private);
                txt_folder_name = (TextView) view.findViewById(R.id.tv_folder);
                img_folder = (ImageView) view.findViewById(R.id.iv_image);
                iv_image1 = (ImageView) view.findViewById(R.id.iv_image1);
                sound_upper=(RelativeLayout)view.findViewById(R.id.sound_upper);
            }
        }
    }
}
