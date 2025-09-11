////////package com.example.womensecurityapp;
////////
////////import android.Manifest;
////////import android.content.Context;
////////import android.content.Intent;
////////import android.content.SharedPreferences;
////////import android.content.pm.PackageManager;
////////import android.location.Location;
////////import android.location.LocationListener;
////////import android.location.LocationManager;
////////import android.os.Bundle;
////////import android.telephony.SmsManager;
////////import android.widget.Toast;
////////
////////import androidx.annotation.NonNull;
////////import androidx.appcompat.app.AppCompatActivity;
////////import androidx.core.app.ActivityCompat;
////////
////////import com.google.android.material.button.MaterialButton;
////////import com.google.gson.Gson;
////////import com.google.gson.reflect.TypeToken;
////////
////////import java.lang.reflect.Type;
////////import java.util.ArrayList;
////////import java.util.List;
////////
////////public class MainActivity extends AppCompatActivity {
////////
////////    private MaterialButton btnSOS, btnContacts, btnRecording, btnLogout;
////////    private List<Contact> contactsList = new ArrayList<>();
////////    private SharedPreferences sharedPreferences;
////////    private LocationManager locationManager;
////////    private String locationText = "Location not available";
////////
////////    private static final int PERMISSION_SMS = 101;
////////    private static final int PERMISSION_LOCATION = 102;
////////
////////    @Override
////////    protected void onCreate(Bundle savedInstanceState) {
////////        super.onCreate(savedInstanceState);
////////        setContentView(R.layout.activity_main);
////////
////////        btnSOS = findViewById(R.id.btnSOS);
////////        btnContacts = findViewById(R.id.btnContacts);
////////        btnRecording = findViewById(R.id.btnRecording);
////////        btnLogout = findViewById(R.id.btnLogout);
////////
////////        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE); // ✅ fixed name
////////        loadContacts();
////////
////////        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////////
////////        // SOS Button
////////        btnSOS.setOnClickListener(v -> getLocationAndSendSOS());
////////
////////        // Preferred Contacts Button
////////        btnContacts.setOnClickListener(v -> {
////////            startActivity(new Intent(MainActivity.this, ContactsActivity.class));
////////        });
////////
////////        // Recording Button
////////        btnRecording.setOnClickListener(v -> {
////////            Toast.makeText(this, "Recording feature coming soon!", Toast.LENGTH_SHORT).show();
////////        });
////////
////////        // Logout Button
////////        btnLogout.setOnClickListener(v -> {
////////            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
////////            // Implement your logout logic here
////////        });
////////    }
////////
////////    private void getLocationAndSendSOS() {
////////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////////            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
////////            return;
////////        }
////////
////////        // Try GPS first, then fallback to Network provider
////////        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
////////            @Override
////////            public void onLocationChanged(@NonNull Location location) {
////////                locationText = "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude();
////////                sendSOS();
////////            }
////////        }, null);
////////
////////        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
////////            @Override
////////            public void onLocationChanged(@NonNull Location location) {
////////                locationText = "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude();
////////                sendSOS();
////////            }
////////        }, null);
////////    }
////////
////////    private void sendSOS() {
////////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
////////            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SMS);
////////            return;
////////        }
////////
////////        if (contactsList.isEmpty()) {
////////            Toast.makeText(this, "No preferred contacts found!", Toast.LENGTH_SHORT).show();
////////            return;
////////        }
////////
////////        for (Contact contact : contactsList) {
////////            if (contact.getNumber() == null || contact.getNumber().isEmpty()) continue;
////////
////////            // ✅ message improved
////////            String message = "🚨 SOS! I need help! My location: " + locationText;
////////            try {
////////                SmsManager smsManager = SmsManager.getDefault();
////////                smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
////////            } catch (Exception e) {
////////                Toast.makeText(this, "Failed to send to " + contact.getName(), Toast.LENGTH_SHORT).show();
////////            }
////////        }
////////
////////        Toast.makeText(this, "SOS sent!", Toast.LENGTH_SHORT).show();
////////    }
////////
////////    private void loadContacts() {
////////        String contactsJson = sharedPreferences.getString("contacts_json", ""); // ✅ fixed key
////////        if (!contactsJson.isEmpty()) {
////////            Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
////////            contactsList = new Gson().fromJson(contactsJson, type);
////////        }
////////    }
////////
////////    @Override
////////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////////
////////        if (requestCode == PERMISSION_SMS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////////            sendSOS();
////////        }
////////
////////        if (requestCode == PERMISSION_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////////            getLocationAndSendSOS();
////////        }
////////    }
////////}
//////
//////
//////package com.example.womensecurityapp;
//////
//////import android.Manifest;
//////import android.content.Context;
//////import android.content.Intent;
//////import android.content.SharedPreferences;
//////import android.content.pm.PackageManager;
//////import android.location.Location;
//////import android.location.LocationListener;
//////import android.location.LocationManager;
//////import android.media.MediaRecorder;
//////import android.os.Bundle;
//////import android.telephony.SmsManager;
//////import android.widget.Toast;
//////
//////import androidx.annotation.NonNull;
//////import androidx.appcompat.app.AppCompatActivity;
//////import androidx.core.app.ActivityCompat;
//////
//////import com.google.android.material.button.MaterialButton;
//////import com.google.gson.Gson;
//////import com.google.gson.reflect.TypeToken;
//////import com.google.firebase.auth.FirebaseAuth;
//////
//////import java.lang.reflect.Type;
//////import java.util.ArrayList;
//////import java.util.List;
//////
//////public class MainActivity extends AppCompatActivity {
//////
//////    private MaterialButton btnSOS, btnContacts, btnRecording, btnLogout;
//////    private List<Contact> contactsList = new ArrayList<>();
//////    private SharedPreferences sharedPreferences;
//////    private LocationManager locationManager;
//////    private String locationText = "Location not available";
//////
//////    private static final int PERMISSION_SMS = 101;
//////    private static final int PERMISSION_LOCATION = 102;
//////    private static final int PERMISSION_RECORD_AUDIO = 103;
//////
//////    // 🎤 Recording
//////    private MediaRecorder mediaRecorder;
//////    private boolean isRecording = false;
//////    private String audioFilePath;
//////
//////    @Override
//////    protected void onCreate(Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////        setContentView(R.layout.activity_main);
//////
//////        btnSOS = findViewById(R.id.btnSOS);
//////        btnContacts = findViewById(R.id.btnContacts);
//////        btnRecording = findViewById(R.id.btnRecording);
//////        btnLogout = findViewById(R.id.btnLogout);
//////
//////        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
//////        loadContacts();
//////
//////        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//////
//////        // 🚨 SOS Button
//////        btnSOS.setOnClickListener(v -> getLocationAndSendSOS());
//////
//////        // 📒 Preferred Contacts Button
//////        btnContacts.setOnClickListener(v -> {
//////            startActivity(new Intent(MainActivity.this, ContactsActivity.class));
//////        });
//////
//////        // 🎤 Recording Button
//////        btnRecording.setOnClickListener(v -> {
//////            if (isRecording) {
//////                stopRecording();
//////            } else {
//////                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//////                        != PackageManager.PERMISSION_GRANTED) {
//////                    ActivityCompat.requestPermissions(this,
//////                            new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
//////                } else {
//////                    startRecording();
//////                }
//////            }
//////        });
//////
//////        // 🔐 Logout Button
//////        btnLogout.setOnClickListener(v -> {
//////            FirebaseAuth.getInstance().signOut();
//////            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
//////            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//////            finish();
//////        });
//////    }
//////
//////    // ==============================
//////    // 🚨 SOS FEATURE
//////    // ==============================
//////    private void getLocationAndSendSOS() {
//////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//////                != PackageManager.PERMISSION_GRANTED) {
//////            ActivityCompat.requestPermissions(this,
//////                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
//////            return;
//////        }
//////
//////        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
//////            @Override
//////            public void onLocationChanged(@NonNull Location location) {
//////                locationText = "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude();
//////                sendSOS();
//////            }
//////        }, null);
//////    }
//////
//////    private void sendSOS() {
//////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
//////                != PackageManager.PERMISSION_GRANTED) {
//////            ActivityCompat.requestPermissions(this,
//////                    new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SMS);
//////            return;
//////        }
//////
//////        if (contactsList.isEmpty()) {
//////            Toast.makeText(this, "No preferred contacts found!", Toast.LENGTH_SHORT).show();
//////            return;
//////        }
//////
//////        for (Contact contact : contactsList) {
//////            String message = "SOS! Help! My location: " + locationText;
//////            try {
//////                SmsManager smsManager = SmsManager.getDefault();
//////                smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
//////            } catch (Exception e) {
//////                Toast.makeText(this, "Failed to send to " + contact.getName(), Toast.LENGTH_SHORT).show();
//////            }
//////        }
//////
//////        Toast.makeText(this, "SOS sent!", Toast.LENGTH_SHORT).show();
//////    }
//////
//////    // ==============================
//////    // 🎤 RECORDING FEATURE
//////    // ==============================
//////    private void startRecording() {
//////        try {
//////            audioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recording.3gp";
//////            mediaRecorder = new MediaRecorder();
//////            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//////            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//////            mediaRecorder.setOutputFile(audioFilePath);
//////            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//////            mediaRecorder.prepare();
//////            mediaRecorder.start();
//////
//////            isRecording = true;
//////            btnRecording.setText("Stop Recording");
//////            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
//////        } catch (Exception e) {
//////            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//////        }
//////    }
//////
//////    private void stopRecording() {
//////        try {
//////            mediaRecorder.stop();
//////            mediaRecorder.release();
//////            mediaRecorder = null;
//////
//////            isRecording = false;
//////            btnRecording.setText("Start Recording");
//////            Toast.makeText(this, "Recording saved: " + audioFilePath, Toast.LENGTH_LONG).show();
//////        } catch (Exception e) {
//////            Toast.makeText(this, "Stop failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//////        }
//////    }
//////
//////    // ==============================
//////    // 📒 CONTACTS FEATURE
//////    // ==============================
//////    private void loadContacts() {
//////        String contactsJson = sharedPreferences.getString("contacts", "");
//////        if (!contactsJson.isEmpty()) {
//////            Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
//////            contactsList = new Gson().fromJson(contactsJson, type);
//////        }
//////    }
//////
//////    // ==============================
//////    // ⚡ PERMISSIONS HANDLER
//////    // ==============================
//////    @Override
//////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//////                                           @NonNull int[] grantResults) {
//////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//////
//////        if (requestCode == PERMISSION_SMS && grantResults.length > 0
//////                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//////            sendSOS();
//////        }
//////
//////        if (requestCode == PERMISSION_LOCATION && grantResults.length > 0
//////                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//////            getLocationAndSendSOS();
//////        }
//////
//////        if (requestCode == PERMISSION_RECORD_AUDIO && grantResults.length > 0
//////                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//////            startRecording();
//////        }
//////    }
//////}
////
////
////
////package com.example.womensecurityapp;
////
////import android.Manifest;
////import android.content.Context;
////import android.content.Intent;
////import android.content.SharedPreferences;
////import android.content.pm.PackageManager;
////import android.location.Location;
////import android.location.LocationManager;
////import android.media.MediaRecorder;
////import android.os.BatteryManager;
////import android.os.Bundle;
////import android.telephony.SmsManager;
////import android.widget.Toast;
////
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.core.app.ActivityCompat;
////
////import com.google.firebase.auth.FirebaseAuth;
////
////
////import com.google.android.material.button.MaterialButton;
////import com.google.gson.Gson;
////
////public class MainActivity extends AppCompatActivity {
////
////    private MaterialButton btnSOS, btnContacts, btnRecording, btnLogout;
////    private SharedPreferences sharedPreferences;
////
////    private static final int PERMISSION_SMS_LOCATION = 101;
////    private static final int PERMISSION_RECORD_AUDIO = 102;
////
////    // Recording
////    private MediaRecorder mediaRecorder;
////    private boolean isRecording = false;
////    private String audioFilePath;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////
////        btnSOS = findViewById(R.id.btnSOS);
////        btnContacts = findViewById(R.id.btnContacts);
////        btnRecording = findViewById(R.id.btnRecording);
////        btnLogout = findViewById(R.id.btnLogout);
////
////        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
////
////        // SOS button
////        btnSOS.setOnClickListener(v -> getLocationAndSendSOS());
////
////        // Preferred contacts button
////        btnContacts.setOnClickListener(v ->
////                startActivity(new Intent(MainActivity.this, ContactsActivity.class))
////        );
////
////        // Recording button
////        btnRecording.setOnClickListener(v -> {
////            if (isRecording) {
////                stopRecording();
////            } else {
////                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
////                        != PackageManager.PERMISSION_GRANTED) {
////                    ActivityCompat.requestPermissions(this,
////                            new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
////                } else {
////                    startRecording();
////                }
////            }
////        });
////
////        // Logout button
////        btnLogout.setOnClickListener(v -> {
////            // Firebase logout
////            FirebaseAuth.getInstance().signOut();
////            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
////            startActivity(new Intent(MainActivity.this, LoginActivity.class));
////            finish();
////        });
////    }
////
////    // =========================
////    // SOS FEATURE
////    // =========================
////    private void getLocationAndSendSOS() {
////        // Get preferred contact from SharedPreferences
////        String json = sharedPreferences.getString("preferred_contact", null);
////        if (json == null) {
////            Toast.makeText(this, "No preferred contact set!", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        Contact contact = new Gson().fromJson(json, Contact.class);
////        if (contact == null || contact.getNumber() == null || contact.getNumber().isEmpty()) {
////            Toast.makeText(this, "Invalid preferred contact!", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        // Request permissions if not granted
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
////                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this,
////                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION},
////                    PERMISSION_SMS_LOCATION);
////            return;
////        }
////
////        sendSOS(contact);
////    }
////
////    private void sendSOS(Contact contact) {
////        // Get location
////        String locationText = "Location not available";
////        try {
////            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////            Location location = null;
////            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////                if (location == null) {
////                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
////                }
////            }
////
////
////            if (location != null) {
////                double lat = location.getLatitude();
////                double lon = location.getLongitude();
////                locationText = "https://maps.google.com/?q=" + lat + "," + lon;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        // Get battery percentage
////        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
////        int batteryLevel = bm != null ? bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : -1;
////
////        // Create message
////        String message = "🚨 SOS! This is " + contact.getName() +
////                ".\n📍 Location: " + locationText +
////                "\n🔋 Battery: " + batteryLevel + "%" +
////                "\n❗ I need help!";
////
////        // Send SMS
////        try {
////            SmsManager smsManager = SmsManager.getDefault();
////            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
////            Toast.makeText(this, "SOS sent to " + contact.getName(), Toast.LENGTH_LONG).show();
////        } catch (Exception e) {
////            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
////
////    // =========================
////    // RECORDING FEATURE
////    // =========================
////    private void startRecording() {
////        try {
////            audioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recording.3gp";
////            mediaRecorder = new MediaRecorder();
////            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
////            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
////            mediaRecorder.setOutputFile(audioFilePath);
////            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
////            mediaRecorder.prepare();
////            mediaRecorder.start();
////
////            isRecording = true;
////            btnRecording.setText("Stop Recording");
////            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
////        } catch (Exception e) {
////            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
////
////    private void stopRecording() {
////        try {
////            mediaRecorder.stop();
////            mediaRecorder.release();
////            mediaRecorder = null;
////
////            isRecording = false;
////            btnRecording.setText("Start Recording");
////            Toast.makeText(this, "Recording saved: " + audioFilePath, Toast.LENGTH_LONG).show();
////        } catch (Exception e) {
////            Toast.makeText(this, "Stop failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
////
////    // =========================
////    // PERMISSIONS HANDLER
////    // =========================
////    @Override
////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
////                                           @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////
////        if (requestCode == PERMISSION_SMS_LOCATION) {
////            getLocationAndSendSOS(); // Retry SOS after permissions granted
////        }
////
////        if (requestCode == PERMISSION_RECORD_AUDIO && grantResults.length > 0
////                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////            startRecording();
////        }
////    }
////}
////
////
//
//package com.example.womensecurityapp;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//import android.media.MediaRecorder;
//import android.net.Uri;
//import android.os.BatteryManager;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.material.button.MaterialButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.gson.Gson;
//
//public class MainActivity extends AppCompatActivity {
//
//    private MaterialButton btnSOS, btnContacts, btnRecording, btnLogout;
//    private SharedPreferences sharedPreferences;
//
//    private static final int PERMISSION_SMS_LOCATION = 101;
//    private static final int PERMISSION_RECORD_AUDIO = 102;
//
//    // Recording
//    private MediaRecorder mediaRecorder;
//    private boolean isRecording = false;
//    private String audioFilePath;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnSOS = findViewById(R.id.btnSOS);
//        btnContacts = findViewById(R.id.btnContacts);
//        btnRecording = findViewById(R.id.btnRecording);
//        btnLogout = findViewById(R.id.btnLogout);
//
//        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
//
//        // SOS button
//        btnSOS.setOnClickListener(v -> getLocationAndSendSOS());
//
//        // Preferred contacts button
//        btnContacts.setOnClickListener(v ->
//                startActivity(new Intent(MainActivity.this, ContactsActivity.class))
//        );
//
//        // Recording button
//        btnRecording.setOnClickListener(v -> {
//            if (isRecording) {
//                stopRecording();
//            } else {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
//                } else {
//                    startRecording();
//                }
//            }
//        });
//
//        // Logout button
//        btnLogout.setOnClickListener(v -> {
//            FirebaseAuth.getInstance().signOut();
//            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        });
//    }
//
//    // =========================
//    // SOS FEATURE
//    // =========================
//    private void getLocationAndSendSOS() {
//        String json = sharedPreferences.getString("preferred_contact", null);
//        if (json == null) {
//            Toast.makeText(this, "No preferred contact set!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Contact contact = new Gson().fromJson(json, Contact.class);
//        if (contact == null || contact.getNumber() == null || contact.getNumber().isEmpty()) {
//            Toast.makeText(this, "Invalid preferred contact!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSION_SMS_LOCATION);
//            return;
//        }
//
//        sendSOS(contact);
//    }
//
//    private void sendSOS(Contact contact) {
//        String locationText = "Location not available";
//        try {
//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Location location = null;
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                if (location == null) {
//                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                }
//            }
//
//            if (location != null) {
//                double lat = location.getLatitude();
//                double lon = location.getLongitude();
//                locationText = "https://maps.google.com/?q=" + lat + "," + lon;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
//        int batteryLevel = bm != null ? bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : -1;
//
//        String message = "🚨 SOS! I need help!" +
//                "\n📍 Location: " + locationText +
//                "\n🔋 Battery: " + batteryLevel + "%";
//
//        // Instead of sending silently, open SMS app like WhatsApp/Truecaller
//        try {
//            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//            smsIntent.setData(Uri.parse("smsto:" + contact.getNumber())); // "smsto:" ensures SMS app opens
//            smsIntent.putExtra("sms_body", message);
//            startActivity(smsIntent);
//
//            Toast.makeText(this, "Opening SMS app for " + contact.getName(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    // =========================
//    // RECORDING FEATURE
//    // =========================
////    private void startRecording() {
////        try {
////            audioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recording.3gp";
////            mediaRecorder = new MediaRecorder();
////            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
////            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
////            mediaRecorder.setOutputFile(audioFilePath);
////            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
////            mediaRecorder.prepare();
////            mediaRecorder.start();
////
////            isRecording = true;
////            btnRecording.setText("Stop Recording");
////            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
////        } catch (Exception e) {
////            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
////
////    private void stopRecording() {
////        try {
////            mediaRecorder.stop();
////            mediaRecorder.release();
////            mediaRecorder = null;
////
////            isRecording = false;
////            btnRecording.setText("Start Recording");
////            Toast.makeText(this, "Recording saved: " + audioFilePath, Toast.LENGTH_LONG).show();
////        } catch (Exception e) {
////            Toast.makeText(this, "Stop failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////        }
////    }
//
//
//    // =========================
//// 🎤 RECORDING FEATURE
//// =========================
//    private void startRecording() {
//        try {
//            // ✅ Save in user-accessible Music/Recordings folder with timestamp
//            String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
//                    .format(new java.util.Date());
//
//            String fileName = "Recording_" + timeStamp + ".3gp";
//
//            // Use app-specific external storage (accessible via File Manager too)
//            audioFilePath = getExternalFilesDir(android.os.Environment.DIRECTORY_MUSIC)
//                    .getAbsolutePath() + "/" + fileName;
//
//            mediaRecorder = new MediaRecorder();
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//            mediaRecorder.setOutputFile(audioFilePath);
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mediaRecorder.prepare();
//            mediaRecorder.start();
//
//            isRecording = true;
//            btnRecording.setText("Stop Recording");
//            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void stopRecording() {
//        try {
//            mediaRecorder.stop();
//            mediaRecorder.release();
//            mediaRecorder = null;
//
//            isRecording = false;
//            btnRecording.setText("Start Recording");
//
//            Toast.makeText(this, "Recording saved:\n" + audioFilePath, Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Stop failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//
//    // =========================
//    // PERMISSIONS HANDLER
//    // =========================
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_SMS_LOCATION && grantResults.length > 0) {
//            boolean granted = true;
//            for (int result : grantResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    granted = false;
//                    break;
//                }
//            }
//            if (granted) getLocationAndSendSOS();
//        }
//
//        if (requestCode == PERMISSION_RECORD_AUDIO && grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startRecording();
//        }
//    }
//}
//
//


package com.example.womensecurityapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.widget.Toast;


import android.content.ContentValues;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnSOS, btnContacts, btnRecording, btnLogout;
    private SharedPreferences sharedPreferences;

    private static final int PERMISSION_SMS_LOCATION = 101;
    private static final int PERMISSION_RECORD_AUDIO = 102;

    // Recording
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private String audioFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSOS = findViewById(R.id.btnSOS);
        btnContacts = findViewById(R.id.btnContacts);
        btnRecording = findViewById(R.id.btnRecording);
        btnLogout = findViewById(R.id.btnLogout);

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        // SOS button
        btnSOS.setOnClickListener(v -> getLocationAndSendSOS());

        // Preferred contacts button
        btnContacts.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ContactsActivity.class))
        );

        // Recording button
        btnRecording.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
                } else {
                    startRecording();
                }
            }
        });

        // Logout button
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }

    // =========================
    // SOS FEATURE
    // =========================
    private void getLocationAndSendSOS() {
        String json = sharedPreferences.getString("preferred_contact", null);
        if (json == null) {
            Toast.makeText(this, "No preferred contact set!", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Gson().fromJson(json, Contact.class);
        if (contact == null || contact.getNumber() == null || contact.getNumber().isEmpty()) {
            Toast.makeText(this, "Invalid preferred contact!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_SMS_LOCATION);
            return;
        }

        sendSOS(contact);
    }

    private void sendSOS(Contact contact) {
        String locationText = "Location not available";
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = null;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }

            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                locationText = "https://maps.google.com/?q=" + lat + "," + lon;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        int batteryLevel = bm != null ? bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : -1;

        String message = "🚨 SOS! I need help!" +
                "\n📍 Location: " + locationText +
                "\n🔋 Battery: " + batteryLevel + "%";

        try {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:" + contact.getNumber()));
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);

            Toast.makeText(this, "Opening SMS app for " + contact.getName(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // =========================
    // 🎤 RECORDING FEATURE (New: Timestamp + Music folder)
    // =========================
//    private void startRecording() {
//        try {
//            String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
//                    .format(new java.util.Date());
//            String fileName = "Recording_" + timeStamp + ".3gp";
//
//            audioFilePath = getExternalFilesDir(android.os.Environment.DIRECTORY_MUSIC)
//                    .getAbsolutePath() + "/" + fileName;
//
//            mediaRecorder = new MediaRecorder();
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//            mediaRecorder.setOutputFile(audioFilePath);
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mediaRecorder.prepare();
//            mediaRecorder.start();
//
//            isRecording = true;
//            btnRecording.setText("Stop Recording");
//            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void stopRecording() {
//        try {
//            mediaRecorder.stop();
//            mediaRecorder.release();
//            mediaRecorder = null;
//
//            isRecording = false;
//            btnRecording.setText("Start Recording");
//
//            Toast.makeText(this, "Recording saved:\n" + audioFilePath, Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Stop failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }



    // =========================
// 🎤 RECORDING FEATURE (Safe, Android 10+)
// =========================
    private Uri audioFileUri; // store globally

    private void startRecording() {
        try {
            String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "Recording_" + timeStamp + ".3gp";

            // Check permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD_AUDIO);
                return;
            }

            // Prepare file in Music/WomenSecurityApp folder
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/3gpp");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC + "/WomenSecurityApp");

            audioFileUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            if (audioFileUri == null) {
                Toast.makeText(this, "Failed to create recording file!", Toast.LENGTH_SHORT).show();
                return;
            }

            ParcelFileDescriptor pfd = null;
            try {
                pfd = getContentResolver().openFileDescriptor(audioFileUri, "w");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Cannot access file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return; // stop execution if failed
            }

            if (pfd == null) return;

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(pfd.getFileDescriptor());
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            btnRecording.setText("Stop Recording");
            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void stopRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;

                isRecording = false;
                btnRecording.setText("Start Recording");

                Toast.makeText(this, "Recording saved in Music/WomenSecurityApp folder", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Stop failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }




    // =========================
    // PERMISSIONS HANDLER
    // =========================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_SMS_LOCATION && grantResults.length > 0) {
            boolean granted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) getLocationAndSendSOS();
        }

        if (requestCode == PERMISSION_RECORD_AUDIO && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording();
        }
    }
}
