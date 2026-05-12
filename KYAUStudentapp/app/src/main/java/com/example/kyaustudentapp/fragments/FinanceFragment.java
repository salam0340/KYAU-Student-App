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
import com.example.kyaustudentapp.adapters.FinanceAdapter;
import com.example.kyaustudentapp.models.Finance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class FinanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private FinanceAdapter adapter;
    private List<Finance> financeList;
    private ProgressBar progressBar;
    private TextView tvTotalFee, tvTotalPaid, tvTotalDue;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerViewFinance);
        progressBar = view.findViewById(R.id.progressBar);
        tvTotalFee = view.findViewById(R.id.tvTotalFee);
        tvTotalPaid = view.findViewById(R.id.tvTotalPaid);
        tvTotalDue = view.findViewById(R.id.tvTotalDue);

        financeList = new ArrayList<>();
        adapter = new FinanceAdapter(financeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadFinanceData();

        return view;
    }

    private void loadFinanceData() {
        if (mAuth.getCurrentUser() == null) return;

        progressBar.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("students").document(uid)
                .collection("finance")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    financeList.clear();

                    double totalFee = 0;
                    double totalPaid = 0;
                    double totalDue = 0;

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Finance finance = doc.toObject(Finance.class);
                        if (finance != null) {
                            financeList.add(finance);
                            totalFee += finance.getTotalFee();
                            totalPaid += finance.getPaidAmount();
                            totalDue += finance.getDueAmount();
                        }
                    }

                    tvTotalFee.setText(String.format("৳ %.0f", totalFee));
                    tvTotalPaid.setText(String.format("৳ %.0f", totalPaid));
                    tvTotalDue.setText(String.format("৳ %.0f", totalDue));

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    if (getActivity() != null) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),
                                "Failed: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}