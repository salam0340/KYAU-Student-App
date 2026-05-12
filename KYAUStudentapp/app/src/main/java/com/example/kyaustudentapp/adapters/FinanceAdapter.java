package com.example.kyaustudentapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kyaustudentapp.R;
import com.example.kyaustudentapp.models.Finance;
import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder> {

    private List<Finance> financeList;

    public FinanceAdapter(List<Finance> financeList) {
        this.financeList = financeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_finance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Finance finance = financeList.get(position);

        holder.tvSemester.setText(finance.getSemester());
        holder.tvTotalFee.setText(String.format("Total: ৳ %.0f", finance.getTotalFee()));
        holder.tvPaidAmount.setText(String.format("Paid: ৳ %.0f", finance.getPaidAmount()));
        holder.tvDueAmount.setText(String.format("Due: ৳ %.0f", finance.getDueAmount()));
        holder.tvPaymentDate.setText("Last Payment: " + finance.getPaymentDate());
        holder.tvStatus.setText(finance.getStatus());

        // Status color
        if (finance.getStatus().equalsIgnoreCase("Paid")) {
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
        } else if (finance.getStatus().equalsIgnoreCase("Partial")) {
            holder.tvStatus.setTextColor(Color.parseColor("#F57F17"));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFFDE7"));
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#C62828"));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }

        // Due amount color
        if (finance.getDueAmount() > 0) {
            holder.tvDueAmount.setTextColor(Color.parseColor("#C62828"));
        } else {
            holder.tvDueAmount.setTextColor(Color.parseColor("#2E7D32"));
        }
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemester, tvTotalFee, tvPaidAmount,
                tvDueAmount, tvPaymentDate, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemester = itemView.findViewById(R.id.tvSemester);
            tvTotalFee = itemView.findViewById(R.id.tvTotalFee);
            tvPaidAmount = itemView.findViewById(R.id.tvPaidAmount);
            tvDueAmount = itemView.findViewById(R.id.tvDueAmount);
            tvPaymentDate = itemView.findViewById(R.id.tvPaymentDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}