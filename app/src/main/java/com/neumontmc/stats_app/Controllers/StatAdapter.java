package com.neumontmc.stats_app.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.neumontmc.stats_app.R;

public class  StatAdapter extends RecyclerView.Adapter<StatAdapter.ViewHolder>{

    String[] availableAttributes;
    Context context;

    public StatAdapter(Context ct, String[] avaStat) {
        context = ct;
        this.availableAttributes = avaStat;
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
        holder.value.setText(availableAttributes[position]);
    }

    @Override
    public int getItemCount() {
        return availableAttributes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView value;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            value = itemView.findViewById(R.id.statAttr);
        }
    }
}
