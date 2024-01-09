package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Button btn = findViewById(R.id.loginButton2);

        // Création et démarrage d'une activité (signup)
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signin.this, signup.class);
                startActivity(i);
            }
        });

        // Recherche et référencement des champs de saisie d'email et de mot de passe et le bouton
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button btn2 = findViewById(R.id.loginButton);

        // click du bouton login crée une authentification
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pswd = password.getText().toString();

                // Validation des entrées (mail non vide, email valide, mot de passe non vide)
                // Si une validation échoue, un message d'erreur est affiché
                if (mail.isEmpty()) {
                    email.setError("mail is required");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("please enter a valid email");
                    email.requestFocus();
                    return;
                }
                if (pswd.isEmpty()) {
                    password.setError("password is required");
                    password.requestFocus();
                    return;
                }

                // Initialisation de Firebase Auth
                FirebaseAuth auth = FirebaseAuth.getInstance();

                // Tentative de connexion avec l'email et le mot de passe fournis
                auth.signInWithEmailAndPassword(mail, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Vérification si l'email est celui de l'administrateur  car j'ai un seul admin dans la base
                            if (mail.equals("samirchemkhi09@gmail.com")) {
                                Toast.makeText(getApplicationContext(), "admin logged successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(signin.this, home_admin.class);
                                // Enregistrement de l'email dans les préférences partagées
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", mail);
                                editor.apply();
                                startActivity(i);
                                finish();
                            } else {
                                // Si l'utilisateur n'est pas un administrateur
                                Toast.makeText(getApplicationContext(), "user logged successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(signin.this, home.class);
                                // Enregistrement de l'email dans les préférences partagées
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", mail);
                                editor.apply();
                                startActivity(i);
                                finish();
                            }
                        } else {
                            // Si la connexion échoue, affichage du message d'erreur
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}