package com.neumontmc.stats_app.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.neumontmc.api.Models.ustats.Ustats;
import com.neumontmc.stats_app.R;

import java.util.ArrayList;

public class StatAdapter extends RecyclerView.Adapter<StatAdapter.ViewHolder>{

    ArrayList<Ustats> ustast;
    Context context;

    public StatAdapter(Context ct, ArrayList<Ustats> ustast) {
        context = ct;
        this.ustast = ustast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.key.setText(ustast.get(position).getUUID());
        holder.value.setText(ustast.get(position).getContent().getDeaths().toString());
    }

    @Override
    public int getItemCount() {
        return ustast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView key, value;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            key = itemView.findViewById(R.id.statKey);
            value = itemView.findViewById(R.id.statAttr);
        }
    }
}
