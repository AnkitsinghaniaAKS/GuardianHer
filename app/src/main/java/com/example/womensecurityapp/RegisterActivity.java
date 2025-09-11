package com.example.womensecurityapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import com.example.womensecurityapp.models.User;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etEmail, etPassword, etPhone;
    Button btnRegister;
    TextView tvLoginLink;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> registerUser());

        tvLoginLink.setOnClickListener(v ->
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class))
        );
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(firebaseUser != null){
                    firebaseUser.sendEmailVerification();

                    User user = new User(name, email, phone);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(firebaseUser.getUid())
                            .setValue(user);

                    Toast.makeText(this, "Registered! Verify email.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
            } else {
                Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
