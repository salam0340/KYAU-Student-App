package com.example.kyaustudentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.adapters.CourseAdapter;
import com.example.kyaustudentapp.models.Course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courseList;
    private ProgressBar progressBar;
    private TextView tvTotalCourses, tvTotalCredits;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerViewCourse);
        progressBar = view.findViewById(R.id.progressBar);
        tvTotalCourses = view.findViewById(R.id.tvTotalCourses);
        tvTotalCredits = view.findViewById(R.id.tvTotalCredits);

        courseList = new ArrayList<>();
        adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadCourseData();

        return view;
    }

    private void loadCourseData() {
        progressBar.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("students").document(uid)
                .collection("courses")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    courseList.clear();

                    int totalCredits = 0;

                    for (var doc : queryDocumentSnapshots) {
                        Course course = doc.toObject(Course.class);
                        courseList.add(course);
                        totalCredits += course.getCreditHours();
                    }

                    tvTotalCourses.setText("Total Courses: " + courseList.size());
                    tvTotalCredits.setText("Total Credits: " + totalCredits);

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),
                            "Failed to load courses: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}