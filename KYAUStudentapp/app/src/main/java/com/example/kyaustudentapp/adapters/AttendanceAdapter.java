package com.example.kyaustudentapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.models.Attendance;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<Attendance> attendanceList;

    public AttendanceAdapter(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);

        holder.tvCourseCode.setText(attendance.getCourseCode());
        holder.tvCourseName.setText(attendance.getCourseName());
        holder.tvAttendedClasses.setText(
                attendance.getAttendedClasses() + "/" +
                        attendance.getTotalClasses() + " classes attended");

        double percentage = attendance.getPercentage();
        holder.tvPercentage.setText(String.format("%.1f%%", percentage));
        holder.progressBar.setProgress((int) percentage);

        if (percentage >= 75) {
            holder.tvPercentage.setTextColor(Color.parseColor("#1B5E20"));
            holder.tvPercentage.setBackgroundResource(R.drawable.circle_percentage_bg);
            holder.progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#2E7D32")));
            holder.progressBar.setProgressBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#E8EAF6")));
        } else if (percentage >= 60) {
            holder.tvPercentage.setTextColor(Color.parseColor("#E65100"));
            holder.tvPercentage.setBackgroundResource(R.drawable.circle_percentage_bg);
            holder.progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#F57F17")));
            holder.progressBar.setProgressBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#E8EAF6")));
        } else {
            holder.tvPercentage.setTextColor(Color.parseColor("#B71C1C"));
            holder.tvPercentage.setBackgroundResource(R.drawable.circle_percentage_bg);
            holder.progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#C62828")));
            holder.progressBar.setProgressBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#E8EAF6")));
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseCode, tvCourseName, tvAttendedClasses, tvPercentage;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseCode = itemView.findViewById(R.id.tvCourseCode);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvAttendedClasses = itemView.findViewById(R.id.tvAttendedClasses);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}