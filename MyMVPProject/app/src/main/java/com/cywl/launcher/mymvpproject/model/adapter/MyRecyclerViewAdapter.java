package com.cywl.launcher.mymvpproject.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cywl.launcher.mymvpproject.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public MyRecyclerViewAdapter(Context context, List<String> mList) {
        this.mContext = context;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(mInflater.inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
