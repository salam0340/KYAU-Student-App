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
import com.example.kyaustudentapp.adapters.AttendanceAdapter;
import com.example.kyaustudentapp.models.Attendance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<Attendance> attendanceList;
    private ProgressBar progressBar;
    private TextView tvOverallPercentage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerViewAttendance);
        progressBar = view.findViewById(R.id.progressBar);
        tvOverallPercentage = view.findViewById(R.id.tvOverallPercentage);

        attendanceList = new ArrayList<>();
        adapter = new AttendanceAdapter(attendanceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadAttendanceData();

        return view;
    }

    private void loadAttendanceData() {
        progressBar.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("students").document(uid)
                .collection("attendance")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    attendanceList.clear();

                    double totalPercentage = 0;
                    int count = 0;

                    for (var doc : queryDocumentSnapshots) {
                        Attendance attendance = doc.toObject(Attendance.class);
                        attendanceList.add(attendance);
                        totalPercentage += attendance.getPercentage();
                        count++;
                    }

                    if (count > 0) {
                        double overall = totalPercentage / count;
                        tvOverallPercentage.setText(
                                String.format("Overall Attendance: %.1f%%", overall));
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),
                            "Failed to load attendance: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}