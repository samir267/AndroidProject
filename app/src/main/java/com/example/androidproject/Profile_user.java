package com.example.androidproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.User;

public class Profile_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);
        // Recherche et référencement des éléments TextView dans le layout
        TextView txt1,txt2,txt3,txt4,txt5;
        txt1=findViewById(R.id.username);
        txt2=findViewById(R.id.email);
        txt3=findViewById(R.id.adress);
        txt4=findViewById(R.id.password);
        txt5=findViewById(R.id.phone);

        // Récupération de l'email enregistré dans les préférences partagées
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", null);

        // Création d'une clé unique pour l'email
        String emailKey = savedEmail.replace(".", "_");
              // Initialisation de Firebase Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users");


        // récupérer les données de l'utilisateur à partir de la base de données Firebase
        myRef.child(emailKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Vérification si les données existent
                if (dataSnapshot.exists()) {
                    // Récupération des données de l'utilisateur
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String name = dataSnapshot.child("username").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    Integer phoneNumber = dataSnapshot.child("phone_number").getValue(Integer.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    // Affichage des données récupérées dans les TextView correspondants
                    txt1.setText(name);
                    txt2.setText(email);
                    txt3.setText(address);
                    txt4.setText(password);
                    txt5.setText(String.valueOf(phoneNumber));

                } else {

                }
            }
            // Gestion des erreurs lors de la récupération des données
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}