package com.example.tests.ui.eat;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.lifecycle.ViewModelProvider; // Import manquant pour ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tests.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EatFragment extends Fragment {

    private DayMealViewModel dayMealViewModel;
    private DayMealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat, container, false);

        // Initialisation du ViewModel
        dayMealViewModel = new ViewModelProvider(this).get(DayMealViewModel.class);

        // Initialisation de RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.listeRepas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialisation de l'adaptateur avec une liste vide. La liste sera mise à jour via LiveData.
        adapter = new DayMealAdapter(new ArrayList<>(), position -> {
            dayMealViewModel.removeDayMeal(position); // Implémentation modifiée pour utiliser la méthode dans ViewModel
        }, getContext());
        recyclerView.setAdapter(adapter);

        // Observer LiveData dans le ViewModel
        dayMealViewModel.getDayMeals().observe(getViewLifecycleOwner(), newMeals -> {
            // Met à jour l'adaptateur avec les nouvelles données
            adapter.setDayMeals(newMeals);
        });

        // Gestion du clic sur le bouton ajouter
        Button addButton = view.findViewById(R.id.button_ajouter);
        addButton.setOnClickListener(v -> {
            // Calcul du nouveau numéro de jour basé sur la taille actuelle de la liste dans le ViewModel
            int newDayNumber = dayMealViewModel.getDayMeals().getValue() != null ? dayMealViewModel.getDayMeals().getValue().size() + 1 : 1;
            String dayTitle = "Jour " + newDayNumber;
            DayMeal newDayMeal = new DayMeal(dayTitle);
            dayMealViewModel.addDayMeal(newDayMeal); // Ajoute le nouveau jour via le ViewModel
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void saveData() {
        String json = adapter.toJson();
        FileOutputStream fos;
        try {
            fos = getContext().openFileOutput("dayMeals.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Save", json);
    }

    private void loadData() {
        FileInputStream fis;
        try {
            fis = getContext().openFileInput("dayMeals.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ArrayList<DayMeal> dayMeals = adapter.fromJson(sb.toString());
            Log.e("Load", sb.toString());

            dayMealViewModel.setDayMeals(dayMeals); // Assurez-vous d'avoir une méthode dans votre ViewModel pour mettre à jour la liste
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
