package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.User;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        // click du bouton pour la redirection a l'interface du login
        Button btn=findViewById(R.id.loginButton2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(signup.this,signin.class);
                startActivity(i);
            }
        });
        // Extraction des données saisies dans le formulaire d'inscription
        EditText username,address,email,password,tele;
        username=findViewById(R.id.username);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        tele=findViewById(R.id.tele);








        Button btnreg=findViewById(R.id.loginButton);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String adr=address.getText().toString();
                String mail=email.getText().toString();
                String pswd=password.getText().toString();
                String tel=tele.getText().toString();

                // Validation des entrées (nom, adresse, email, mot de passe, numéro de téléphone)
                // Si une validation échoue, un message d'erreur est affiché
                if (name.isEmpty()) {
                    username.setError("name is required");
                    username.requestFocus();
                    return;
                }
                if (adr.isEmpty()) {
                    address.setError("address is required");
                    address.requestFocus();
                    return;
                }

                if (tel.isEmpty()) {
                    tele.setError("Phone_number is required");
                    tele.requestFocus();
                    return;
                }
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
                if (pswd.length() < 8) {
                    password.setError("Minimum length should be 8");
                    password.requestFocus();
                    return;
                }

                if (tel.length()!=8) {
                    tele.setError("Phone_number length should be 8");
                    tele.requestFocus();
                    return;
                }

                // Initialisation de Firebase Auth et de la base de données Firebase
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                // Création d'un utilisateur dans la collection users
                auth.createUserWithEmailAndPassword(mail,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Si les données sont réussi
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"user registered successfully",Toast.LENGTH_SHORT).show();

                            // Création d'un nouvel objet User
                            User us = new User(name, adr, mail, pswd,"user",Integer.parseInt(tel));
                            // Création d'une clé unique pour l'email et la sauvegarde des données
                            String emailKey = mail.replace(".", "_");
                            myRef.child(emailKey).setValue(us);


                        }else {
                            // Si l'email est déjà utilisé, affichage d'un message d'erreur
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"You are not registered",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });





            }
        });
    }
}