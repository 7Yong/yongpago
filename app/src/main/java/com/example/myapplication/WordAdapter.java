package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.CustomViewHolder> {

    private ArrayList<WordData> mList = null;
    private Activity context = null;


    public WordAdapter(Activity context, ArrayList<WordData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView idProgress;
        protected TextView Word;
        protected TextView Inputplayer;


        public CustomViewHolder(View view) {
            super(view);
            this.idProgress = (TextView) view.findViewById(R.id.textView_list_idProgress);
            this.Word = (TextView) view.findViewById(R.id.textView_list_Word);
            this.Inputplayer = (TextView) view.findViewById(R.id.textView_list_Inputplayer);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.idProgress.setText(mList.get(position).getMember_id());
        viewholder.Word.setText(mList.get(position).getMember_name());
        viewholder.Inputplayer.setText(mList.get(position).getMember_country());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}