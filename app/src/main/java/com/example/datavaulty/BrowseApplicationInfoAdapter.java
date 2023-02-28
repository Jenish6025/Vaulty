package com.example.datavaulty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class BrowseApplicationInfoAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    LayoutInflater infater = null;
    private List<AppInfo> mlistAppInfo = new ArrayList<>();
    private Context mContext;
    private OnStatusChangedListener mOnstatusChangedListener;

    public BrowseApplicationInfoAdapter(Context context, List<AppInfo> apps, OnStatusChangedListener onStatusChangedListener) {
        mContext = context;
        infater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlistAppInfo = apps;
        this.mOnstatusChangedListener = onStatusChangedListener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//		System.out.println("size" + mlistAppInfo.size());
        return mlistAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mlistAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = infater.inflate(R.layout.item_app_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppInfo appInfo = (AppInfo) getItem(position);
        if (appInfo.getType() == AppInfo.SECTION) {
            holder.appIcon.setVisibility(View.GONE);
            holder.tvAppLabel.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.llRoot.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.appIcon.setVisibility(View.VISIBLE);
            holder.llRoot.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        holder.appIcon.setImageDrawable(appInfo.getAppIcon());
        holder.tvAppLabel.setText(appInfo.getAppLabel());

        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == AppInfo.SECTION;
    }

    @Override
    public int getItemViewType(int position) {
        return ((AppInfo) getItem(position)).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public interface OnStatusChangedListener {
        void onStatusChange(AppInfo appInfo);
    }

    class ViewHolder {
        ImageView appIcon;
        TextView tvAppLabel;
        LinearLayout llRoot;

        public ViewHolder(View view) {
            this.appIcon = (ImageView) view.findViewById(R.id.imgApp);
            this.tvAppLabel = (TextView) view.findViewById(R.id.tvAppLabel);
            this.llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
        }
    }
}