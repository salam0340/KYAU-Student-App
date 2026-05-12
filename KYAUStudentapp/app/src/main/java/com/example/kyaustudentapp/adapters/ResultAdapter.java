package com.example.kyaustudentapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.models.Result;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<Result> resultList;

    public ResultAdapter(List<Result> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = resultList.get(position);

        holder.tvSemester.setText(result.getSemester());
        holder.tvGpa.setText(String.format("GPA: %.2f", result.getGpa()));
        holder.tvCgpa.setText(String.format("CGPA: %.2f", result.getCgpa()));
        holder.tvCredits.setText("Credits: " + result.getCreditCompleted());
        holder.tvGrade.setText(result.getGrade());

        // GPA color
        double gpa = result.getGpa();
        if (gpa >= 3.5) {
            holder.tvGpa.setTextColor(Color.parseColor("#2E7D32"));
            holder.tvGrade.setTextColor(Color.parseColor("#2E7D32"));
            holder.tvGrade.setBackgroundColor(Color.parseColor("#E8F5E9"));
        } else if (gpa >= 2.5) {
            holder.tvGpa.setTextColor(Color.parseColor("#F57F17"));
            holder.tvGrade.setTextColor(Color.parseColor("#F57F17"));
            holder.tvGrade.setBackgroundColor(Color.parseColor("#FFFDE7"));
        } else {
            holder.tvGpa.setTextColor(Color.parseColor("#C62828"));
            holder.tvGrade.setTextColor(Color.parseColor("#C62828"));
            holder.tvGrade.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemester, tvGpa, tvCgpa, tvCredits, tvGrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemester = itemView.findViewById(R.id.tvSemester);
            tvGpa = itemView.findViewById(R.id.tvGpa);
            tvCgpa = itemView.findViewById(R.id.tvCgpa);
            tvCredits = itemView.findViewById(R.id.tvCredits);
            tvGrade = itemView.findViewById(R.id.tvGrade);
        }
    }
}