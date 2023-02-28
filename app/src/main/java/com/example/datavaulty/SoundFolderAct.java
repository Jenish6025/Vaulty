package com.example.datavaulty;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SoundFolderAct extends AppCompatActivity {

    ArrayList<String> TotalFolder = new ArrayList<>();
    List<Model_Files_1> SoundList = new ArrayList<>();
    private RecyclerView recycler_view;
    SoundListAdapter soundListAdapter;
    ImageView img_back;
    private ProgressDialog progressDialog;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_folder);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        img_back=(ImageView)findViewById(R.id.img_back);
        avi=findViewById(R.id.avi);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        //progressDialog.show();
        avi.show();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getAllFiles();
    }

    private void getAllFiles() {

        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;
        if (cur != null) {
            count = cur.getCount();
            if (count > 0) {
                SoundList.clear();
                while (cur.moveToNext()) {
                    SoundList.add(new Model_Files_1(new File(cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA))), false));
                }
                TotalFolder = getTotalFolder(SoundList);
                populateRecyclerView();
            }
        }
        cur.close();

    }


    private void populateRecyclerView() {
        try {
            if (SoundList.size() != 0) {

                soundListAdapter = new SoundListAdapter(SoundFolderAct.this, SoundList, TotalFolder);
                GridLayoutManager mLayoutManager = new GridLayoutManager(SoundFolderAct.this, 2);
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());

                recycler_view.setAdapter(soundListAdapter);
                //progressDialog.hide();
                avi.hide();
                soundListAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getTotalFolder(List<Model_Files_1> VideoLists) {
        ArrayList<String> Folders = new ArrayList<>();
        for (int i = 0; i < VideoLists.size(); i++) {
            String FolderName = VideoLists.get(i).getFile().getParentFile().getName();
            if (!Folders.contains(FolderName))
                Folders.add(FolderName);
        }
        return Folders;
    }

}
