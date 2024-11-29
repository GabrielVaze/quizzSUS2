package com.example.quizzsus.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizzsus.R;
import java.util.List;

public class CuriositiesAdapter extends RecyclerView.Adapter<CuriositiesAdapter.CuriositiesViewHolder> {

    private List<String> curiositiesList;

    public CuriositiesAdapter(List<String> curiositiesList) {
        this.curiositiesList = curiositiesList;
    }

    @NonNull
    @Override
    public CuriositiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curiosity, parent, false);
        return new CuriositiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuriositiesViewHolder holder, int position) {
        holder.textViewCuriosity.setText(curiositiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return curiositiesList.size();
    }

    public static class CuriositiesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCuriosity;

        public CuriositiesViewHolder(View itemView) {
            super(itemView);
            textViewCuriosity = itemView.findViewById(R.id.textViewCuriosity);
        }
    }
}
