package com.neumontmc.stats_app.Controllers;

import com.neumontmc.api.Models.User;
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

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    ArrayList<User> users;
    Context context;

    public SearchAdapter(Context ct, ArrayList<User> users) {
        context = ct;
        this.users = users;
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
//        holder.nameTV.setText(nameData[position]);
//        holder.uuidTV.setText(uuidData[position]);
          holder.nameTV.setText(users.get(position).getUsername());
            holder.uuidTV.setText(users.get(position).getUuid().toString());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserStats.class);
                intent.putExtra("name", users.get(position).getUsername());
                intent.putExtra("uuid", users.get(position).getUuid().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, uuidTV;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.cardNameTextView);
            uuidTV = itemView.findViewById(R.id.cardUuidTextView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}

