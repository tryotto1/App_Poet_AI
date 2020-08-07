package com.hongdroid.viewpagerexample.MyWritings_recycler;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongdroid.viewpagerexample.ExtraTabs.ShowWritingActivity;
import com.hongdroid.viewpagerexample.R;

import java.util.ArrayList;

public class MyWritingsAdapter extends RecyclerView.Adapter<MyWritingsAdapter.CustomViewHolder> {

    private ArrayList<MyWritingsData> arrayList; // 서버에서 받는 정보

    public MyWritingsAdapter(ArrayList<MyWritingsData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyWritingsAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        MyWritingsAdapter.CustomViewHolder holder = new MyWritingsAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyWritingsAdapter.CustomViewHolder holder, int position) {
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_content.setText(arrayList.get(position).getContent());
        holder.tv_writer.setText(arrayList.get(position).getWriter());
        holder.tv_date.setText(arrayList.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                MyWritingsData clicked = arrayList.get(position);

                String writing = clicked.getContent();
                String title = clicked.getTitle();
                String writer = clicked.getWriter();
                String date = clicked.getDate();

                Intent intent = new Intent(view.getContext(), ShowWritingActivity.class);
                intent.putExtra("writing", writing);
                intent.putExtra("title", title);
                intent.putExtra("writer", writer);
                intent.putExtra("date", date);
                view.getContext().startActivity(intent);

            }
        });

//        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                remove(holder.getAdapterPosition());
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position); // 새로고침 해줌
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_title, tv_content, tv_writer, tv_date;
        protected LinearLayout layout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.tv_writer = (TextView) itemView.findViewById(R.id.tv_writer);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
