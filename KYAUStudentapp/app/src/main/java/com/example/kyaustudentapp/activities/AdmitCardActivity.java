package com.example.kyaustudentapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import com.example.kyaustudentapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdmitCardActivity extends AppCompatActivity {

    private TextView tvStudentName, tvStudentId, tvDepartment,
            tvBatch, tvSemester, tvExamName,
            tvExamDate, tvExamCenter, tvIssueDate;
    private Button btnDownload, btnShare;
    private TextView btnBack;
    private ProgressBar progressBar;
    private CardView cardAdmitCard;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvDepartment = findViewById(R.id.tvDepartment);
        tvBatch = findViewById(R.id.tvBatch);
        tvSemester = findViewById(R.id.tvSemester);
        tvExamName = findViewById(R.id.tvExamName);
        tvExamDate = findViewById(R.id.tvExamDate);
        tvExamCenter = findViewById(R.id.tvExamCenter);
        tvIssueDate = findViewById(R.id.tvIssueDate);
        btnDownload = findViewById(R.id.btnDownload);
        btnShare = findViewById(R.id.btnShare);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        cardAdmitCard = findViewById(R.id.cardAdmitCard);

        // Set today's date as issue date
        String today = new SimpleDateFormat(
                "dd MMM yyyy", Locale.getDefault()).format(new Date());
        tvIssueDate.setText("Issue Date: " + today);

        loadAdmitCardData();

        btnDownload.setOnClickListener(v -> downloadAdmitCard());
        btnShare.setOnClickListener(v -> shareAdmitCard());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadAdmitCardData() {
        progressBar.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("students").document(uid)
                .get()
                .addOnSuccessListener(document -> {
                    progressBar.setVisibility(View.GONE);
                    if (document.exists()) {
                        tvStudentName.setText(document.getString("name"));
                        tvStudentId.setText(document.getString("studentId"));
                        tvDepartment.setText(document.getString("department"));
                        tvBatch.setText(document.getString("batch"));
                        tvSemester.setText(document.getString("semester") + " Semester");
                        tvExamName.setText(document.getString("semester")
                                + " Semester Final Examination");
                    }

                    // Load exam info from examInfo collection
                    db.collection("examInfo").document("current")
                            .get()
                            .addOnSuccessListener(examDoc -> {
                                if (examDoc.exists()) {
                                    tvExamDate.setText(examDoc.getString("examDate"));
                                    tvExamCenter.setText(examDoc.getString("examCenter"));
                                } else {
                                    tvExamDate.setText("To be announced");
                                    tvExamCenter.setText("KYAU Main Campus");
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this,
                            "Failed to load data: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private Bitmap getAdmitCardBitmap() {
        cardAdmitCard.setDrawingCacheEnabled(true);
        cardAdmitCard.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(
                cardAdmitCard.getWidth(),
                cardAdmitCard.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        cardAdmitCard.draw(canvas);
        cardAdmitCard.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        try {
            File directory = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), "AdmitCards");
            if (!directory.exists()) directory.mkdirs();

            String fileName = "AdmitCard_" + System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void downloadAdmitCard() {
        try {
            Bitmap bitmap = getAdmitCardBitmap();
            File file = saveBitmapToFile(bitmap);
            if (file != null) {
                Toast.makeText(this,
                        "Admit card saved to: " + file.getAbsolutePath(),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Failed to save admit card", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareAdmitCard() {
        try {
            Bitmap bitmap = getAdmitCardBitmap();
            File file = saveBitmapToFile(bitmap);
            if (file != null) {
                Uri uri = FileProvider.getUriForFile(
                        this,
                        getPackageName() + ".provider",
                        file
                );
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "KYAU Student Admit Card");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share Admit Card"));
            }
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}