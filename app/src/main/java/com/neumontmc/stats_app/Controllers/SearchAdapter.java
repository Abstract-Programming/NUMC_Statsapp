package com.neumontmc.stats_app.Controllers;

import com.neumontmc.stats_app.R;
import com.neumontmc.stats_app.View.UserStats;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    String[] nameData, uuidData;
    Context context;

    public SearchAdapter(Context ct, String[] name, String[] uuid) {
        context = ct;
        nameData = name;
        uuidData = uuid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTV.setText(nameData[position]);
        holder.uuidTV.setText(uuidData[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserStats.class);
                intent.putExtra("name", nameData[position]);
                intent.putExtra("uuid", uuidData[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTV, uuidTV;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nameTV = itemView.findViewById(R.id.cardNameTextView);
            uuidTV = itemView.findViewById(R.id.cardUuidTextView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}

