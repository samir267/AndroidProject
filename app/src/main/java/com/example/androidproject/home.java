package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Car;

public class home extends AppCompatActivity {

    // Déclaration des éléments de la navigation du drawer layout
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    // Déclaration de la RecyclerView et de la liste de voitures
    RecyclerView recyclerView;
    ArrayList<Car>ls;
    AdapterCar adapterCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        // Initialisation de la RecyclerView et configuration de l'adaptateur
        recyclerView=findViewById(R.id.recycler);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("cars");
        ls=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterCar=new AdapterCar(this,ls);
        recyclerView.setAdapter(adapterCar);

        // Écouteur pour récupérer les données de la base de données Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Parcours des données et ajout à la liste
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Car car=dataSnapshot.getValue(Car.class);
                    ls.add(car);
                }
                adapterCar.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        // Configuration de la navigation du drawer layout
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navview);

        View headerView = navigationView.getHeaderView(0);

        TextView mail = headerView.findViewById(R.id.headermail);
        // Récupération de l'email enregistré dans les préférences partagées et affichage dans le header
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", null);

        mail.setText(savedEmail);









        //Affichage des éléments de la navigation latérale du drawer layout
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Gestion des actions en fonction de l'élément sélectionné par l'id de l'élement selectionné
                int id = item.getItemId();
                if (id==R.id.profile){
                    Intent i=new Intent(home.this,Profile_user.class);
                    startActivity(i);
                }if (id==R.id.logout){
                   logout(home.this);
                }
                return  true;
            }
        });

        // Configuration de la barre d'outils
        Toolbar toolbar = findViewById(R.id.toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    //gérer la déconnexion de l'utilisateur
    private void logout(home home){
        AlertDialog.Builder builder=new AlertDialog.Builder(home);
        builder.setTitle("Logout");
        builder.setMessage("Sure to logout ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirection vers l'activité login et fermeture de l'activité actuelle
                Intent i=new Intent(home.this,signin.class);
                startActivity(i);
                home.finish();

            }

        });
        builder.show();
    }
}