    package com.example.quizzsus.ui;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;
    import com.example.quizzsus.R;
    import java.util.List;

    public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        private final List<QuizDatabaseHelper.ScoreRecord> scoreRecords;

        public HistoryAdapter(List<QuizDatabaseHelper.ScoreRecord> scoreRecords) {
            this.scoreRecords = scoreRecords;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score_record, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            QuizDatabaseHelper.ScoreRecord scoreRecord = scoreRecords.get(position);
            holder.textViewCorrect.setText("Corretas: " + scoreRecord.getCorrect());
            holder.textViewWrong.setText("Erradas: " + scoreRecord.getWrong());
            holder.textViewDate.setText("Data: " + scoreRecord.getDate());
        }

        @Override
        public int getItemCount() {
            return scoreRecords.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textViewCorrect, textViewWrong, textViewDate;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewCorrect = itemView.findViewById(R.id.textViewC);
                textViewWrong = itemView.findViewById(R.id.textViewW);
                textViewDate = itemView.findViewById(R.id.textViewD);
            }
        }
    }

