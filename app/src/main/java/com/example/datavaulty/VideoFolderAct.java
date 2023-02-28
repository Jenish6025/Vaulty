package com.example.datavaulty;

import android.app.ProgressDialog;
import android.database.Cursor;
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
import java.util.HashSet;
import java.util.List;

public class VideoFolderAct extends AppCompatActivity {

    List<Model_Files_1> VideoList = new ArrayList<>();
    ArrayList<String> TotalFolder = new ArrayList<>();
    private VideoFolderAdapter videoFolderAdapter;
    private RecyclerView recyclerView;
    ImageView img_back;
    private ProgressDialog progressDialog;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_folder);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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

        getVideos();
    }
    public void getVideos() {
        try {
            Cursor cursor;
            HashSet<String> videoItemHashSet = new HashSet<>();
            String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
            cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
            try {
                cursor.moveToFirst();
                do {
                    videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
                } while (cursor.moveToNext());

                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
            VideoList.clear();
            if (downloadedList.size() != 0)
                for (int i = 0; i < downloadedList.size(); i++) {
//                    if (!Utils.getPrivateVideos(MainVideoFolderActivity.this).contains(downloadedList.get(i)))
                    VideoList.add(new Model_Files_1(new File(downloadedList.get(i)), false));
                }
            if (VideoList.size() != 0) {
                TotalFolder = getTotalFolder(VideoList);
                populateRecyclerView();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateRecyclerView() {
        try {
            if (VideoList.size() != 0) {
                videoFolderAdapter = new VideoFolderAdapter(VideoFolderAct.this, VideoList, TotalFolder);
                GridLayoutManager mLayoutManager = new GridLayoutManager(VideoFolderAct.this, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(videoFolderAdapter);
            //    progressDialog.hide();
                avi.hide();
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
