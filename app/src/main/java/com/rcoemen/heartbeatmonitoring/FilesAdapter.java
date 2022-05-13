package com.rcoemen.heartbeatmonitoring;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MyViewHolder> {
    ArrayList<String> fileList;
    Context context;

    public FilesAdapter(ArrayList<String> fileList, Context context) {
        this.fileList = fileList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilesAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(fileList.get(position));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,PlayActivity.class).putExtra("name",fileList.get(position)));

            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    public  interface  OnNoteListerner{
        void  onNoteClick(int position);


    }
}
