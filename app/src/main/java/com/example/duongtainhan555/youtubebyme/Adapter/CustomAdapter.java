package com.example.duongtainhan555.youtubebyme.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duongtainhan555.youtubebyme.Model.VideoTrending;
import com.example.duongtainhan555.youtubebyme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<VideoTrending> {

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<VideoTrending> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        if(view==null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.layout_item_video,null);
        }
        VideoTrending videoTrending = getItem(position);
        if(videoTrending !=null)
        {
            ImageView imgThumbnails = view.findViewById(R.id.imgThumbnails);
            Picasso.get().load(videoTrending.getThumbnails()).into(imgThumbnails);
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            txtTitle.setText(videoTrending.getTitle());
            ImageView imgOption = view.findViewById(R.id.imgOption);
        }
        return view;
    }
}