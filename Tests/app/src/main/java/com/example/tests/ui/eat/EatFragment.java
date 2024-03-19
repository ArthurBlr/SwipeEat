package com.example.tests.ui.eat;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tests.R;
import com.example.tests.ui.eat.DayMeal;
import com.example.tests.ui.eat.DayMealAdapter;

import java.util.ArrayList;

public class EatFragment extends Fragment implements DayMealAdapter.OnDayRemovedListener {

    private ArrayList<DayMeal> dayMeals = new ArrayList<>();
    private DayMealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat, container, false);

        // Initialisation de RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.listeRepas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DayMealAdapter(dayMeals, this);
        recyclerView.setAdapter(adapter);

        // Gestion du clic sur le bouton ajouter
        Button addButton = view.findViewById(R.id.button_ajouter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newDayNumber = dayMeals.size() + 1; // Détermine le numéro du nouveau jour
                String dayTitle = "Jour " + newDayNumber;
                DayMeal newDayMeal = new DayMeal(dayTitle);
                adapter.addDayMeal(newDayMeal);
            }
        });

        return view;
    }

    @Override
    public void onDayRemoved(int position) {
        dayMeals.remove(position); // Supprime l'élément de la liste des données
        adapter.notifyItemRemoved(position); // Notifie que l'élément a été supprimé de l'adaptateur

        // Met à jour les titres des jours restants pour refléter la nouvelle séquence
        for (int i = position; i < dayMeals.size(); i++) {
            DayMeal dayMeal = dayMeals.get(i);
            String updatedDayTitle = "Jour " + (i + 1);
            dayMeal.setDayTitle(updatedDayTitle);
            adapter.notifyItemChanged(i); // Notifie que l'élément a été mis à jour dans l'adaptateur
        }
    }
}
