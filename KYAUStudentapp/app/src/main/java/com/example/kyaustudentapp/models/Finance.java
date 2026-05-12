package com.example.kyaustudentapp.models;

import com.google.firebase.firestore.PropertyName;

public class Finance {
    @PropertyName("Semester")
    private String semester;
    @PropertyName("TotalFee")
    private long totalFee;
    @PropertyName("PaidAmount")
    private long paidAmount;
    @PropertyName("DueAmount")
    private long dueAmount;
    @PropertyName("PaymentDate")
    private String paymentDate;
    @PropertyName("Status")
    private String status;

    public Finance() {}

    @PropertyName("Semester")
    public String getSemester() { return semester; }
    @PropertyName("Semester")
    public void setSemester(String semester) { this.semester = semester; }

    @PropertyName("TotalFee")
    public double getTotalFee() { return (double) totalFee; }
    @PropertyName("TotalFee")
    public void setTotalFee(long totalFee) { this.totalFee = totalFee; }

    @PropertyName("PaidAmount")
    public double getPaidAmount() { return (double) paidAmount; }
    @PropertyName("PaidAmount")
    public void setPaidAmount(long paidAmount) { this.paidAmount = paidAmount; }

    @PropertyName("DueAmount")
    public double getDueAmount() { return (double) dueAmount; }
    @PropertyName("DueAmount")
    public void setDueAmount(long dueAmount) { this.dueAmount = dueAmount; }

    @PropertyName("PaymentDate")
    public String getPaymentDate() { return paymentDate; }
    @PropertyName("PaymentDate")
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    @PropertyName("Status")
    public String getStatus() { return status; }
    @PropertyName("Status")
    public void setStatus(String status) { this.status = status; }
}