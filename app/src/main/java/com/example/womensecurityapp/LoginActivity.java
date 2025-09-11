//////
//////
//////package com.example.womensecurityapp;
//////
//////import android.content.Intent;
//////import android.os.Bundle;
//////import android.widget.Toast;
//////
//////import androidx.appcompat.app.AppCompatActivity;
//////
//////import com.google.android.material.button.MaterialButton;
//////import com.google.android.material.textfield.TextInputEditText;
//////import com.google.android.material.textview.MaterialTextView;
//////import com.google.firebase.auth.FirebaseAuth;
//////import com.google.firebase.auth.FirebaseUser;
//////
//////public class LoginActivity extends AppCompatActivity {
//////
//////    TextInputEditText etEmail, etPassword, etPhone;
//////    MaterialButton btnLoginEmail, btnLoginPhone, btnLoginGoogle;
//////    MaterialTextView tvRegisterLink;
//////    FirebaseAuth mAuth;
//////
//////    @Override
//////    protected void onCreate(Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////        setContentView(R.layout.activity_login);
//////
//////        // Initialize Views
//////        etEmail = findViewById(R.id.etEmail);
//////        etPassword = findViewById(R.id.etPassword);
//////        etPhone = findViewById(R.id.etPhone);
//////
//////        btnLoginEmail = findViewById(R.id.btnLoginEmail);
//////        btnLoginPhone = findViewById(R.id.btnLoginPhone);
//////        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
//////
//////        tvRegisterLink = findViewById(R.id.tvRegisterLink);
//////
//////        // Firebase Auth
//////        mAuth = FirebaseAuth.getInstance();
//////
//////        // Email Login
//////        btnLoginEmail.setOnClickListener(v -> loginWithEmail());
//////
//////        // Phone Login (You need to implement OTP verification separately)
//////        btnLoginPhone.setOnClickListener(v ->
//////                Toast.makeText(this, "Phone login not implemented yet", Toast.LENGTH_SHORT).show()
//////        );
//////
//////        // Google Login (You need to implement Google Sign-In separately)
//////        btnLoginGoogle.setOnClickListener(v ->
//////                Toast.makeText(this, "Google login not implemented yet", Toast.LENGTH_SHORT).show()
//////        );
//////
//////        // Register Link
//////        tvRegisterLink.setOnClickListener(v ->
//////                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
//////        );
//////    }
//////
//////    private void loginWithEmail() {
//////        String email = etEmail.getText().toString().trim();
//////        String password = etPassword.getText().toString().trim();
//////
//////        if (email.isEmpty() || password.isEmpty()) {
//////            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//////            return;
//////        }
//////
//////        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//////            if (task.isSuccessful()) {
//////                FirebaseUser user = mAuth.getCurrentUser();
//////                if (user != null && user.isEmailVerified()) {
//////                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//////                    finish();
//////                } else {
//////                    Toast.makeText(this, "Please verify your email first", Toast.LENGTH_LONG).show();
//////                }
//////            } else {
//////                Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//////            }
//////        });
//////    }
//////}
//////
////
////package com.example.womensecurityapp;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.util.Log;
////import android.widget.Toast;
////
////import androidx.activity.result.ActivityResultLauncher;
////import androidx.activity.result.contract.ActivityResultContracts;
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.google.android.gms.auth.api.signin.GoogleSignIn;
////import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
////import com.google.android.gms.auth.api.signin.GoogleSignInClient;
////import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
////import com.google.android.gms.common.api.ApiException;
////import com.google.android.material.button.MaterialButton;
////import com.google.android.material.textfield.TextInputEditText;
////import com.google.android.material.textview.MaterialTextView;
////import com.google.firebase.FirebaseException;
////import com.google.firebase.auth.AuthCredential;
////import com.google.firebase.auth.FirebaseAuth;
////import com.google.firebase.auth.FirebaseUser;
////import com.google.firebase.auth.GoogleAuthProvider;
////import com.google.firebase.auth.PhoneAuthCredential;
////import com.google.firebase.auth.PhoneAuthOptions;
////import com.google.firebase.auth.PhoneAuthProvider;
////
////import java.util.concurrent.TimeUnit;
////
////public class LoginActivity extends AppCompatActivity {
////
////
////
////    private TextInputEditText etEmail, etPassword, etPhone;
////    private MaterialButton btnLoginEmail, btnLoginPhone, btnLoginGoogle;
////    private MaterialTextView tvRegisterLink;
////    private FirebaseAuth mAuth;
////
////    // Google Sign-In
////    private GoogleSignInClient mGoogleSignInClient;
////
////    // Phone Auth
////    private String verificationId;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_login);
////
////        // Initialize Views
////        etEmail = findViewById(R.id.etEmail);
////        etPassword = findViewById(R.id.etPassword);
////        etPhone = findViewById(R.id.etPhone);
////
////        btnLoginEmail = findViewById(R.id.btnLoginEmail);
////        btnLoginPhone = findViewById(R.id.btnLoginPhone);
////        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
////
////        tvRegisterLink = findViewById(R.id.tvRegisterLink);
////
////        // Firebase Auth
////        mAuth = FirebaseAuth.getInstance();
////
////        // Email Login
////        btnLoginEmail.setOnClickListener(v -> loginWithEmail());
////
////        // Phone Login
////        btnLoginPhone.setOnClickListener(v -> loginWithPhone());
////
////        // Google Login
////        configureGoogleSignIn();
////        btnLoginGoogle.setOnClickListener(v -> loginWithGoogle());
////
////        // Register Link
////        tvRegisterLink.setOnClickListener(v ->
////                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
////        );
////    }
////
////    // ------------------ EMAIL LOGIN ------------------
////    private void loginWithEmail() {
////        String email = etEmail.getText().toString().trim();
////        String password = etPassword.getText().toString().trim();
////
////        if(email.isEmpty() || password.isEmpty()){
////            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        mAuth.signInWithEmailAndPassword(email, password)
////                .addOnCompleteListener(task -> {
////                    if(task.isSuccessful()){
////                        FirebaseUser user = mAuth.getCurrentUser();
////                        if(user != null && user.isEmailVerified()){
////                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                            finish();
////                        } else {
////                            Toast.makeText(this, "Please verify your email first", Toast.LENGTH_LONG).show();
////                        }
////                    } else {
////                        Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
////                    }
////                });
////    }
////
////    // ------------------ PHONE LOGIN WITH OTP ------------------
////    private void loginWithPhone() {
////        String phone = etPhone.getText().toString().trim();
////        if(phone.isEmpty()){
////            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        PhoneAuthOptions options =
////                PhoneAuthOptions.newBuilder(mAuth)
////                        .setPhoneNumber(phone)
////                        .setTimeout(60L, TimeUnit.SECONDS)
////                        .setActivity(this)
////                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
////                            @Override
////                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
////                                // Auto sign in
////                                signInWithPhoneCredential(credential);
////                            }
////
////                            @Override
////                            public void onVerificationFailed(@NonNull FirebaseException e) {
////                                Toast.makeText(LoginActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////                            }
////
////                            @Override
////                            public void onCodeSent(@NonNull String verifId,
////                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
////                                verificationId = verifId;
////                                // Open OTP Activity
////                                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
////                                intent.putExtra("phone", phone);
////                                intent.putExtra("verificationId", verificationId);
////                                startActivity(intent);
////                            }
////                        })
////                        .build();
////
////        PhoneAuthProvider.verifyPhoneNumber(options);
////    }
////
////    private void signInWithPhoneCredential(PhoneAuthCredential credential){
////        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
////            if(task.isSuccessful()){
////                startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                finish();
////            } else {
////                Toast.makeText(this, "Phone login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
////            }
////        });
////    }
////
////    // ------------------ GOOGLE LOGIN ------------------
////    private void configureGoogleSignIn(){
////        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////                .requestIdToken(getString(R.string.default_web_client_id))
////                .requestEmail()
////                .build();
////        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
////    }
////
////    private final ActivityResultLauncher<Intent> googleSignInLauncher =
////            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
////                try {
////                    GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData())
////                            .getResult(ApiException.class);
////                    firebaseAuthWithGoogle(account.getIdToken());
////                } catch (ApiException e) {
////                    Toast.makeText(this, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////                }
////            });
////
////    private void loginWithGoogle(){
////        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
////        googleSignInLauncher.launch(signInIntent);
////    }
////
////    private void firebaseAuthWithGoogle(String idToken){
////        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
////        mAuth.signInWithCredential(credential)
////                .addOnCompleteListener(task -> {
////                    if(task.isSuccessful()){
////                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                        finish();
////                    } else {
////                        Toast.makeText(this, "Google login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
////                    }
////                });
////    }
////}
////
////
//
//
//package com.example.womensecurityapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textview.MaterialTextView;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private TextInputEditText etEmail, etPassword;
//    private MaterialButton btnLoginEmail, btnLoginGoogle;
//    private MaterialTextView tvRegisterLink;
//    private FirebaseAuth mAuth;
//
//    // Google Sign-In
//    private GoogleSignInClient mGoogleSignInClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // Initialize Views
//        etEmail = findViewById(R.id.etEmail);
//        etPassword = findViewById(R.id.etPassword);
//
//        btnLoginEmail = findViewById(R.id.btnLoginEmail);
//        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
//
//        tvRegisterLink = findViewById(R.id.tvRegisterLink);
//
//        // Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Email Login
//        btnLoginEmail.setOnClickListener(v -> loginWithEmail());
//
//        // Google Login
//        configureGoogleSignIn();
//        btnLoginGoogle.setOnClickListener(v -> loginWithGoogle());
//
//        // Register Link
//        tvRegisterLink.setOnClickListener(v ->
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
//        );
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // ✅ Auto-login if user already logged in
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }
//    }
//
//
//    // ------------------ EMAIL LOGIN ------------------
//    private void loginWithEmail() {
//        String email = etEmail.getText().toString().trim();
//        String password = etPassword.getText().toString().trim();
//
//        if(email.isEmpty() || password.isEmpty()){
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        if(user != null && user.isEmailVerified()){
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            finish();
//                        } else {
//                            Toast.makeText(this, "Please verify your email first", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//
//    // ------------------ GOOGLE LOGIN ------------------
//    private void configureGoogleSignIn(){
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//    }
//
//    private final ActivityResultLauncher<Intent> googleSignInLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                try {
//                    GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData())
//                            .getResult(ApiException.class);
//                    firebaseAuthWithGoogle(account.getIdToken());
//                } catch (ApiException e) {
//                    String msg = task.getException() != null ? task.getException().getMessage() : "Unknown error";
//                    Toast.makeText(this, "Login failed: " + msg, Toast.LENGTH_LONG).show();
//                }
//            });
//
//    private void loginWithGoogle(){
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        googleSignInLauncher.launch(signInIntent);
//    }
//
//    private void firebaseAuthWithGoogle(String idToken){
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        finish();
//                    } else {
//                        String msg = task.getException() != null ? task.getException().getMessage() : "Unknown error";
//                        Toast.makeText(this, "Login failed: " + msg, Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//}


package com.example.womensecurityapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLoginEmail, btnLoginGoogle;
    private MaterialTextView tvRegisterLink;
    private FirebaseAuth mAuth;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLoginEmail = findViewById(R.id.btnLoginEmail);
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);

        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Email Login
        btnLoginEmail.setOnClickListener(v -> loginWithEmail());

        // Google Login
        configureGoogleSignIn();
        btnLoginGoogle.setOnClickListener(v -> loginWithGoogle());

        // Register Link
        tvRegisterLink.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ✅ Auto-login if user already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    // ------------------ EMAIL LOGIN ------------------
    private void loginWithEmail() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Please verify your email first", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String msg = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(this, "Login failed: " + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    // ------------------ GOOGLE LOGIN ------------------
    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                try {
                    GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                            .getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Toast.makeText(this, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

    private void loginWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        String msg = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(this, "Google login failed: " + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
