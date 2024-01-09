package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Car;

// Déclaration de la classe AdapterCar qui étend RecyclerView.Adapter

public class AdapterCar extends RecyclerView.Adapter<AdapterCar.MyViewHolder> {

    // Déclaration des variables

    Context context;
    ArrayList<Car>list;

    // Constructeur de la classe AdapterCar
    public AdapterCar(Context context, ArrayList<Car> list) {
        this.context = context;
        this.list = list;
    }

    // Méthode appelée lorsqu'une nouvelle vue est créée
    @NonNull
    @Override
    public AdapterCar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.carmodel,parent,false);
        return new MyViewHolder(v);
    }

    // Méthode appelée pour lier les données aux éléments de la vue
    @Override
    public void onBindViewHolder(@NonNull AdapterCar.MyViewHolder holder, int position) {
        // Récupération de l'objet à la position donnée dans la liste
        Car car=list.get(position);
        // Attribution des valeurs aux TextViews de la vue
        holder.name.setText(car.getName());
        holder.nb_places.setText(car.getNb_places().toString());
        holder.price.setText(car.getPrice().toString());
        holder.state.setText(car.getState().toString());

    }

    // Méthode pour obtenir le nombre total d'éléments dans la liste
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Classe interne MyViewHolder pour contenir les références aux éléments de la vue
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,nb_places,price,state;

        // Constructeur de la classe MyViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialisation des TextViews avec les références de la vue
            name=itemView.findViewById(R.id.name);
            nb_places=itemView.findViewById(R.id.nb_places);
            price=itemView.findViewById(R.id.price);
            state=itemView.findViewById(R.id.state);
        }
    }
}
