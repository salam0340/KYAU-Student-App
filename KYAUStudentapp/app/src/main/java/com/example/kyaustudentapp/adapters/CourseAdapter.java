package com.example.kyaustudentapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.models.Course;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courseList;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.tvCourseCode.setText(course.getCourseCode());
        holder.tvCourseName.setText(course.getCourseName());
        holder.tvTeacherName.setText("Teacher: " + course.getTeacherName());
        holder.tvCreditHours.setText(course.getCreditHours() + " Credits");
        holder.tvSchedule.setText("Schedule: " + course.getSchedule());
        holder.tvRoom.setText("Room: " + course.getRoom());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseCode, tvCourseName, tvTeacherName,
                tvCreditHours, tvSchedule, tvRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseCode = itemView.findViewById(R.id.tvCourseCode);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvCreditHours = itemView.findViewById(R.id.tvCreditHours);
            tvSchedule = itemView.findViewById(R.id.tvSchedule);
            tvRoom = itemView.findViewById(R.id.tvRoom);
        }
    }
}