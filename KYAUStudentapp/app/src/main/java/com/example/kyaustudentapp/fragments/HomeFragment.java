package com.example.kyaustudentapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.activities.AdmitCardActivity;
import com.example.kyaustudentapp.activities.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    private TextView tvStudentName, tvStudentId, tvDepartment,
            tvBatch, tvSemester, tvCgpa;
    private MaterialButton btnAdmitCard, btnLogout;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        tvStudentName = view.findViewById(R.id.tvStudentName);
        tvStudentId = view.findViewById(R.id.tvStudentId);
        tvDepartment = view.findViewById(R.id.tvDepartment);
        tvBatch = view.findViewById(R.id.tvBatch);
        tvSemester = view.findViewById(R.id.tvSemester);
        tvCgpa = view.findViewById(R.id.tvCgpa);
        btnAdmitCard = view.findViewById(R.id.btnAdmitCard);
        btnLogout = view.findViewById(R.id.btnLogout);

        loadStudentData();

        btnAdmitCard.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), AdmitCardActivity.class)));

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        return view;
    }

    private void loadStudentData() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(getActivity(), "Not logged in",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = mAuth.getCurrentUser().getUid();
        db.collection("students").document(uid)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        tvStudentName.setText(document.getString("name"));
                        tvStudentId.setText("ID: " + document.getString("studentId"));
                        tvDepartment.setText(document.getString("department"));
                        tvBatch.setText("Batch: " + document.getString("batch"));
                        tvSemester.setText("Semester: " + document.getString("semester"));

                        Object cgpaObj = document.get("cgpa");
                        String cgpaText = cgpaObj != null ? String.valueOf(cgpaObj) : "N/A";
                        tvCgpa.setText("CGPA: " + cgpaText);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Failed: " +
                                e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}