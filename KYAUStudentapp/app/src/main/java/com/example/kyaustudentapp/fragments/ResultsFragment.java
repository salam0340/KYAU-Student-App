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
import com.example.kyaustudentapp.adapters.ResultAdapter;
import com.example.kyaustudentapp.models.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    private List<Result> resultList;
    private ProgressBar progressBar;
    private TextView tvCurrentCgpa, tvTotalCredits, tvCompletedSemesters;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerViewResults);
        progressBar = view.findViewById(R.id.progressBar);
        tvCurrentCgpa = view.findViewById(R.id.tvCurrentCgpa);
        tvTotalCredits = view.findViewById(R.id.tvTotalCredits);
        tvCompletedSemesters = view.findViewById(R.id.tvCompletedSemesters);

        resultList = new ArrayList<>();
        adapter = new ResultAdapter(resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadResultData();

        return view;
    }

    private void loadResultData() {
        progressBar.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("students").document(uid)
                .collection("results")
                .orderBy("semester", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    resultList.clear();

                    double totalCredits = 0;

                    for (var doc : queryDocumentSnapshots) {
                        Result result = doc.toObject(Result.class);
                        resultList.add(result);
                        totalCredits += result.getCreditCompleted();
                    }

                    if (!resultList.isEmpty()) {
                        // Latest semester CGPA
                        double latestCgpa = resultList.get(0).getCgpa();
                        tvCurrentCgpa.setText(String.format("%.2f", latestCgpa));
                        tvTotalCredits.setText("Credits: " + (int)totalCredits);
                        tvCompletedSemesters.setText(
                                "Semesters: " + resultList.size());
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),
                            "Failed to load results: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}