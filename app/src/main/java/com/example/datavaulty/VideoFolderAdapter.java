package com.example.datavaulty;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class VideoFolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<Object> TotalFolder = new ArrayList<>();
    private List<Model_Files_1> DocumentList;
    private static final int RECIPE = 0;

    public VideoFolderAdapter(Context context, List<Model_Files_1> moviesList, ArrayList<String> TotalFolder) {
        this.mContext = context;
        this.DocumentList = moviesList;
        this.TotalFolder.addAll(TotalFolder);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == RECIPE) {
            View contactView = inflater.inflate(R.layout.video_folder_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType == RECIPE) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final String privateImageModel = (String) TotalFolder.get(position);
            viewHolder.tv_folder.setText(privateImageModel);
            ArrayList<String> FilesList = new ArrayList<>();
            String FolderName = privateImageModel;
            for (int i = 0; i < DocumentList.size(); i++) {
                if (TextUtils.equals(FolderName, DocumentList.get(i).getFile().getParentFile().getName())) {
                    FilesList.add(DocumentList.get(i).getFile().getAbsolutePath());
                }
            }
            viewHolder.tv_folder2.setText("" + FilesList.size());
            Glide.with(mContext).load("file://" + FilesList.get(0))
                    .placeholder(R.drawable.ic_folder)
                    .into(viewHolder.iv_image);
            FilesList.clear();

            viewHolder.rl_main_images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ArrayList<String> FilesList = new ArrayList<>();
                        String FolderName = privateImageModel;
                        for (int i = 0; i < DocumentList.size(); i++) {
                            if (TextUtils.equals(FolderName, DocumentList.get(i).getFile().getParentFile().getName())) {
                                FilesList.add(DocumentList.get(i).getFile().getAbsolutePath());
                            }
                        }
                        if (FilesList.size() != 0) {
                            Intent intent = new Intent(mContext, VideoListAct.class);
                            intent.putExtra("title", privateImageModel);
                            intent.putStringArrayListExtra("array", FilesList);
                            mContext.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        Object item = TotalFolder.get(position);
        if (item instanceof String) {
            return RECIPE;
        }else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return TotalFolder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_folder, tv_folder2;
        ImageView iv_image;
        RelativeLayout rl_main_images;

        public ViewHolder(View view) {
            super(view);
            rl_main_images = (RelativeLayout) view.findViewById(R.id.rl_main_images);
            tv_folder = (TextView) view.findViewById(R.id.tv_folder);
            tv_folder2 = (TextView) view.findViewById(R.id.tv_folder2);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }

}