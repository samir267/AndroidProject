package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.checkerframework.checker.units.qual.C;

import model.Car;

public class home_admin extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_admin);


        // Recherche et référencement des EditText pour la saisie des informations de la voiture
        EditText txt1, txt2, txt3, txt4;
        txt1 = findViewById(R.id.name);
        txt2 = findViewById(R.id.prix);
        txt3 = findViewById(R.id.nb_places);
        txt4 = findViewById(R.id.etat);

        // Recherche et référencement du bouton pour sauvegarder les informations de la voiture
        Button btn = findViewById(R.id.saveButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des valeurs saisies dans les EditText
                String name = txt1.getText().toString().trim();
                String price = txt2.getText().toString().trim();
                String nb_places = txt3.getText().toString().trim();
                String state = txt4.getText().toString().trim();
                // Vérification si tous les champs sont remplis
                if (name.isEmpty() || price.isEmpty() || nb_places.isEmpty() || state.isEmpty()) {
                    Toast.makeText(home_admin.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Création d'un objet Car avec les informations saisies
                    Car c = new Car(name, Integer.parseInt(price), Integer.parseInt(nb_places), Integer.parseInt(state),"");

                    // Initialisation de Firebase Database et enregistrement de la voiture dans une collection cars
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("cars");
                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(c);

                    // Affichage d'un message de succès
                    Toast.makeText(home_admin.this, "Voiture enregistrée avec succès.", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    // Affichage d'un message d'erreur
                    Toast.makeText(home_admin.this, "Veuillez entrer des nombres valides.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
