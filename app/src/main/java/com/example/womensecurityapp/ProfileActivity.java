package com.example.womensecurityapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText etProfileName, etProfilePhone;
    private TextView tvProfileEmail;
    private ImageView ivProfilePic;
    private MaterialButton btnEditProfile, btnSaveProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // view binding
        etProfileName = findViewById(R.id.etProfileName);
        etProfilePhone = findViewById(R.id.etProfilePhone);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        loadCurrentUserData();

        btnEditProfile.setOnClickListener(v -> {
            etProfileName.setEnabled(true);
            etProfilePhone.setEnabled(true);
            etProfileName.requestFocus();
            btnSaveProfile.setVisibility(View.VISIBLE);
            btnEditProfile.setVisibility(View.GONE);
        });

        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void loadCurrentUserData() {
        FirebaseUser fbUser = mAuth.getCurrentUser();
        if (fbUser == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // set email from auth
        tvProfileEmail.setText(fbUser.getEmail() != null ? fbUser.getEmail() : "");

        String uid = fbUser.getUid();
        // try to fetch Name/Phone from Realtime DB (if saved earlier)
        usersRef.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DataSnapshot snap = task.getResult();
                // Assuming database fields are "name" and "phone"
                String name = snap.child("name").getValue(String.class);
                String phone = snap.child("phone").getValue(String.class);

                if (!TextUtils.isEmpty(name)) etProfileName.setText(name);
                // If displayName present in FirebaseAuth but not DB, prefer it
                if (TextUtils.isEmpty(name) && fbUser.getDisplayName() != null) {
                    etProfileName.setText(fbUser.getDisplayName());
                }
                if (!TextUtils.isEmpty(phone)) etProfilePhone.setText(phone);
            } else {
                // No DB entry — fill from auth if available
                if (fbUser.getDisplayName() != null) etProfileName.setText(fbUser.getDisplayName());
            }
        }).addOnFailureListener(e -> {
            // silent fallback
        });
    }

    private void saveProfile() {
        FirebaseUser fbUser = mAuth.getCurrentUser();
        if (fbUser == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = fbUser.getUid();
        String name = etProfileName.getText().toString().trim();
        String phone = etProfilePhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etProfileName.setError("Enter name");
            etProfileName.requestFocus();
            return;
        }

        // We'll update only name & phone fields in Realtime DB (no model required)
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("phone", phone);
        updates.put("email", fbUser.getEmail());

        usersRef.child(uid).updateChildren(updates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        // disable editing
                        etProfileName.setEnabled(false);
                        etProfilePhone.setEnabled(false);
                        btnSaveProfile.setVisibility(View.GONE);
                        btnEditProfile.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to save: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
