package com.example.kyaustudentapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.kyaustudentapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvStudentId, tvDepartment,
            tvBatch, tvSemester, tvPhone;
    private EditText etName, etPhone;
    private MaterialButton btnUpdateProfile, btnChangePassword;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvStudentId = view.findViewById(R.id.tvStudentId);
        tvDepartment = view.findViewById(R.id.tvDepartment);
        tvBatch = view.findViewById(R.id.tvBatch);
        tvSemester = view.findViewById(R.id.tvSemester);
        tvPhone = view.findViewById(R.id.tvPhone);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        progressBar = view.findViewById(R.id.progressBar);

        loadProfileData();

        btnUpdateProfile.setOnClickListener(v -> updateProfile());
        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());

        return view;
    }

    private void loadProfileData() {
        progressBar.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("students").document(uid)
                .get()
                .addOnSuccessListener(document -> {
                    progressBar.setVisibility(View.GONE);
                    if (document.exists()) {
                        tvName.setText(document.getString("name"));
                        tvEmail.setText(document.getString("email"));
                        tvStudentId.setText("ID: " + document.getString("studentId"));
                        tvDepartment.setText(document.getString("department"));
                        tvBatch.setText("Batch: " + document.getString("batch"));
                        tvSemester.setText("Semester: " + document.getString("semester"));
                        tvPhone.setText(document.getString("phone"));

                        // Set editable fields
                        etName.setText(document.getString("name"));
                        etPhone.setText(document.getString("phone"));
                    }
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),
                            "Failed to load profile: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void updateProfile() {
        String newName = etName.getText().toString().trim();
        String newPhone = etPhone.getText().toString().trim();

        if (newName.isEmpty()) {
            etName.setError("Name is required");
            return;
        }
        if (newPhone.isEmpty()) {
            etPhone.setError("Phone is required");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnUpdateProfile.setEnabled(false);

        String uid = mAuth.getCurrentUser().getUid();
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", newName);
        updates.put("phone", newPhone);

        db.collection("students").document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.GONE);
                    btnUpdateProfile.setEnabled(true);
                    tvName.setText(newName);
                    tvPhone.setText(newPhone);
                    Toast.makeText(getActivity(),
                            "Profile updated successfully",
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    btnUpdateProfile.setEnabled(true);
                    Toast.makeText(getActivity(),
                            "Update failed: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void showChangePasswordDialog() {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_change_password, null);

        EditText etCurrentPassword = dialogView.findViewById(R.id.etCurrentPassword);
        EditText etNewPassword = dialogView.findViewById(R.id.etNewPassword);
        EditText etConfirmPassword = dialogView.findViewById(R.id.etConfirmPassword);

        new AlertDialog.Builder(getContext())
                .setTitle("Change Password")
                .setView(dialogView)
                .setPositiveButton("Change", (dialog, which) -> {
                    String currentPass = etCurrentPassword.getText().toString().trim();
                    String newPass = etNewPassword.getText().toString().trim();
                    String confirmPass = etConfirmPassword.getText().toString().trim();

                    if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                        Toast.makeText(getActivity(),
                                "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!newPass.equals(confirmPass)) {
                        Toast.makeText(getActivity(),
                                "Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newPass.length() < 6) {
                        Toast.makeText(getActivity(),
                                "Password must be at least 6 characters",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    changePassword(currentPass, newPass);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void changePassword(String currentPassword, String newPassword) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, currentPassword);

        user.reauthenticate(credential)
                .addOnSuccessListener(aVoid ->
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(aVoid2 -> {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(),
                                            "Password changed successfully",
                                            Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(),
                                            "Failed to change password: " + e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }))
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),
                            "Current password is incorrect",
                            Toast.LENGTH_SHORT).show();
                });
    }
}