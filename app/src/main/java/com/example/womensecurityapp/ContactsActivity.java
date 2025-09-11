////package com.example.womensecurityapp;
////
////import android.content.SharedPreferences;
////import android.os.Bundle;
////import android.widget.ArrayAdapter;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.ListView;
////import android.widget.LinearLayout;
////import android.widget.Toast;
////
////import androidx.appcompat.app.AlertDialog;
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.google.gson.Gson;
////import com.google.gson.reflect.TypeToken;
////
////import java.lang.reflect.Type;
////import java.util.ArrayList;
////
////public class ContactsActivity extends AppCompatActivity {
////
////    private EditText etName, etNumber;
////    private Button btnAddContact;
////    private ListView lvContacts;
////
////    private ArrayList<Contact> contactsList;
////    private ArrayAdapter<String> adapter;
////    private ArrayList<String> contactDisplayList;
////
////    private SharedPreferences preferences;
////    private Gson gson;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_contacts);
////
////        etName = findViewById(R.id.etName);
////        etNumber = findViewById(R.id.etNumber);
////        btnAddContact = findViewById(R.id.btnAddContact);
////        lvContacts = findViewById(R.id.lvContacts);
////
////        gson = new Gson();
////        preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
////
////        // Load saved contacts
////        String json = preferences.getString("contacts_json", null);
////        if (json != null) {
////            Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
////            contactsList = gson.fromJson(json, type);
////        } else {
////            contactsList = new ArrayList<>();
////        }
////
////        // Display list
////        contactDisplayList = new ArrayList<>();
////        for (Contact c : contactsList) contactDisplayList.add(c.toString());
////
////        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactDisplayList);
////        lvContacts.setAdapter(adapter);
////
////        btnAddContact.setOnClickListener(v -> addContact());
////
////        lvContacts.setOnItemClickListener((parent, view, position, id) -> showEditDeleteDialog(position));
////    }
////
////    private void addContact() {
////        String name = etName.getText().toString().trim();
////        String number = etNumber.getText().toString().trim();
////
////        if (name.isEmpty() || number.isEmpty()) {
////            Toast.makeText(this, "Enter name and number", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        Contact contact = new Contact(name, number);
////        contactsList.add(contact);
////        contactDisplayList.add(contact.toString());
////        adapter.notifyDataSetChanged();
////
////        saveContacts();
////
////        etName.setText("");
////        etNumber.setText("");
////    }
////
////    private void showEditDeleteDialog(int position) {
////        Contact contact = contactsList.get(position);
////
////        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////        builder.setTitle(contact.getName())
////                .setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
////                    if (which == 0) editContact(position);
////                    else deleteContact(position);
////                })
////                .show();
////    }
////
////    private void editContact(int position) {
////        Contact contact = contactsList.get(position);
////
////        EditText editName = new EditText(this);
////        editName.setText(contact.getName());
////        EditText editNumber = new EditText(this);
////        editNumber.setText(contact.getNumber());
////
////        LinearLayout layout = new LinearLayout(this);
////        layout.setOrientation(LinearLayout.VERTICAL);
////        layout.addView(editName);
////        layout.addView(editNumber);
////
////        new AlertDialog.Builder(this)
////                .setTitle("Edit Contact")
////                .setView(layout)
////                .setPositiveButton("Save", (dialog, which) -> {
////                    // Use a final variable for lambda
////                    Contact updatedContact = new Contact(editName.getText().toString(), editNumber.getText().toString());
////                    contactsList.set(position, updatedContact);
////                    contactDisplayList.set(position, updatedContact.toString());
////                    adapter.notifyDataSetChanged();
////                    saveContacts();
////                })
////                .setNegativeButton("Cancel", null)
////                .show();
////    }
////
////    private void deleteContact(int position) {
////        contactsList.remove(position);
////        contactDisplayList.remove(position);
////        adapter.notifyDataSetChanged();
////        saveContacts();
////    }
////
////    private void saveContacts() {
////        SharedPreferences.Editor editor = preferences.edit();
////        String json = gson.toJson(contactsList);
////        editor.putString("contacts_json", json);
////        editor.apply();
////    }
////}
//
//package com.example.womensecurityapp;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//public class ContactsActivity extends AppCompatActivity {
//
//    private EditText etName, etNumber;
//    private Button btnAddContact;
//    private ListView lvContacts;
//
//    private ArrayList<Contact> contactsList;
//    private ArrayAdapter<String> adapter;
//    private ArrayList<String> contactDisplayList;
//
//    private SharedPreferences preferences;
//    private Gson gson;
//
//    private String preferredName = "";
//    private String preferredNumber = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contacts);
//
//        etName = findViewById(R.id.etName);
//        etNumber = findViewById(R.id.etNumber);
//        btnAddContact = findViewById(R.id.btnAddContact);
//        lvContacts = findViewById(R.id.lvContacts);
//
//        gson = new Gson();
//        preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
//
//        // Load preferred
//        preferredName = preferences.getString("preferred_contact_name", "");
//        preferredNumber = preferences.getString("preferred_contact_number", "");
//
//        // Load saved contacts
//        String json = preferences.getString("contacts_json", null);
//        if (json != null) {
//            Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
//            contactsList = gson.fromJson(json, type);
//        } else {
//            contactsList = new ArrayList<>();
//        }
//
//        // Display list with ⭐ for preferred
//        contactDisplayList = new ArrayList<>();
//        refreshContactList();
//
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactDisplayList);
//        lvContacts.setAdapter(adapter);
//
//        btnAddContact.setOnClickListener(v -> addContact());
//
//        lvContacts.setOnItemClickListener((parent, view, position, id) -> showEditDeleteDialog(position));
//    }
//
//    private void addContact() {
//        String name = etName.getText().toString().trim();
//        String number = etNumber.getText().toString().trim();
//
//        if (name.isEmpty() || number.isEmpty()) {
//            Toast.makeText(this, "Enter name and number", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Contact contact = new Contact(name, number);
//        contactsList.add(contact);
//        saveContacts();
//
//        etName.setText("");
//        etNumber.setText("");
//
//        refreshContactList();
//        adapter.notifyDataSetChanged();
//    }
//
//    private void showEditDeleteDialog(int position) {
//        Contact contact = contactsList.get(position);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(contact.getName())
//                .setItems(new CharSequence[]{"Edit", "Delete", "Set as Preferred"}, (dialog, which) -> {
//                    if (which == 0) editContact(position);
//                    else if (which == 1) deleteContact(position);
//                    else setAsPreferred(contact);
//                })
//                .show();
//    }
//
//    private void editContact(int position) {
//        Contact contact = contactsList.get(position);
//
//        EditText editName = new EditText(this);
//        editName.setText(contact.getName());
//        EditText editNumber = new EditText(this);
//        editNumber.setText(contact.getNumber());
//
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        layout.addView(editName);
//        layout.addView(editNumber);
//
//        new AlertDialog.Builder(this)
//                .setTitle("Edit Contact")
//                .setView(layout)
//                .setPositiveButton("Save", (dialog, which) -> {
//                    Contact updatedContact = new Contact(editName.getText().toString(), editNumber.getText().toString());
//                    contactsList.set(position, updatedContact);
//                    saveContacts();
//                    refreshContactList();
//                    adapter.notifyDataSetChanged();
//                })
//                .setNegativeButton("Cancel", null)
//                .show();
//    }
//
//    private void deleteContact(int position) {
//        Contact contact = contactsList.get(position);
//        if (contact.getName().equals(preferredName) && contact.getNumber().equals(preferredNumber)) {
//            // clear preferred if deleted
//            preferredName = "";
//            preferredNumber = "";
//            preferences.edit().remove("preferred_contact_name").remove("preferred_contact_number").apply();
//        }
//
//        contactsList.remove(position);
//        saveContacts();
//        refreshContactList();
//        adapter.notifyDataSetChanged();
//    }
//
////    private void setAsPreferred(Contact contact) {
////        preferredName = contact.getName();
////        preferredNumber = contact.getNumber();
////
////        SharedPreferences.Editor editor = preferences.edit();
////        editor.putString("preferred_contact_name", preferredName);
////        editor.putString("preferred_contact_number", preferredNumber);
////        editor.apply();
////
////        refreshContactList();
////        adapter.notifyDataSetChanged();
////
////        Toast.makeText(this, preferredName + " set as preferred contact!", Toast.LENGTH_SHORT).show();
////    }
//
//
//    private void setAsPreferred(Contact contact) {
//        String json = gson.toJson(contact);
//        preferences.edit().putString("preferred_contact", json).apply();
//
//        Toast.makeText(this, contact.getName() + " set as preferred contact!", Toast.LENGTH_SHORT).show();
//        refreshContactList();
//        adapter.notifyDataSetChanged();
//    }
//
//
//
//    private void saveContacts() {
//        SharedPreferences.Editor editor = preferences.edit();
//        String json = gson.toJson(contactsList);
//        editor.putString("contacts_json", json);
//        editor.apply();
//    }
//
//    private void refreshContactList() {
//        contactDisplayList.clear();
//        for (Contact c : contactsList) {
//            if (c.getName().equals(preferredName) && c.getNumber().equals(preferredNumber)) {
//                contactDisplayList.add("⭐ " + c.getName() + " - " + c.getNumber());
//            } else {
//                contactDisplayList.add(c.getName() + " - " + c.getNumber());
//            }
//        }
//    }
//}



package com.example.womensecurityapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private EditText etName, etNumber;
    private Button btnAddContact;
    private ListView lvContacts;

    private ArrayList<Contact> contactsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> contactDisplayList;

    private SharedPreferences preferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        btnAddContact = findViewById(R.id.btnAddContact);
        lvContacts = findViewById(R.id.lvContacts);

        gson = new Gson();
        preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);

        // Load saved contacts
        String json = preferences.getString("contacts_json", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
            contactsList = gson.fromJson(json, type);
        } else {
            contactsList = new ArrayList<>();
        }

        // Initialize display list
        contactDisplayList = new ArrayList<>();
        refreshContactList();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactDisplayList);
        lvContacts.setAdapter(adapter);

        btnAddContact.setOnClickListener(v -> addContact());

        lvContacts.setOnItemClickListener((parent, view, position, id) -> showEditDeleteDialog(position));
    }

    private void addContact() {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Enter name and number", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact(name, number);
        contactsList.add(contact);
        saveContacts();

        etName.setText("");
        etNumber.setText("");

        refreshContactList();
        adapter.notifyDataSetChanged();
    }

    private void showEditDeleteDialog(int position) {
        Contact contact = contactsList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(contact.getName())
                .setItems(new CharSequence[]{"Edit", "Delete", "Set as Preferred"}, (dialog, which) -> {
                    if (which == 0) editContact(position);
                    else if (which == 1) deleteContact(position);
                    else setAsPreferred(contact);
                })
                .show();
    }

    private void editContact(int position) {
        Contact contact = contactsList.get(position);

        EditText editName = new EditText(this);
        editName.setText(contact.getName());
        EditText editNumber = new EditText(this);
        editNumber.setText(contact.getNumber());

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editName);
        layout.addView(editNumber);

        new AlertDialog.Builder(this)
                .setTitle("Edit Contact")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    Contact updatedContact = new Contact(editName.getText().toString(), editNumber.getText().toString());
                    contactsList.set(position, updatedContact);
                    saveContacts();
                    refreshContactList();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteContact(int position) {
        Contact contact = contactsList.get(position);

        // Clear preferred if deleted
        String preferredJson = preferences.getString("preferred_contact", "");
        if (!preferredJson.isEmpty()) {
            Contact preferredContact = gson.fromJson(preferredJson, Contact.class);
            if (contact.getName().equals(preferredContact.getName()) &&
                    contact.getNumber().equals(preferredContact.getNumber())) {
                preferences.edit().remove("preferred_contact").apply();
            }
        }

        contactsList.remove(position);
        saveContacts();
        refreshContactList();
        adapter.notifyDataSetChanged();
    }

    private void setAsPreferred(Contact contact) {
        String json = gson.toJson(contact);
        preferences.edit().putString("preferred_contact", json).apply();

        Toast.makeText(this, contact.getName() + " set as preferred contact!", Toast.LENGTH_SHORT).show();
        refreshContactList();
        adapter.notifyDataSetChanged();
    }

    private void saveContacts() {
        String json = gson.toJson(contactsList);
        preferences.edit().putString("contacts_json", json).apply();
    }

    private void refreshContactList() {
        contactDisplayList.clear();

        // Load preferred contact
        String preferredJson = preferences.getString("preferred_contact", "");
        Contact preferredContact = null;
        if (!preferredJson.isEmpty()) {
            preferredContact = gson.fromJson(preferredJson, Contact.class);
        }

        for (Contact c : contactsList) {
            if (preferredContact != null &&
                    c.getName().equals(preferredContact.getName()) &&
                    c.getNumber().equals(preferredContact.getNumber())) {
                contactDisplayList.add("⭐ " + c.getName() + " - " + c.getNumber());
            } else {
                contactDisplayList.add(c.getName() + " - " + c.getNumber());
            }
        }
    }
}
