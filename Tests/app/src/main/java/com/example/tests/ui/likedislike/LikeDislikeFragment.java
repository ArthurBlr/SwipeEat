package com.example.tests.ui.likedislike;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tests.R;
import com.example.tests.ui.swiper.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LikeDislikeFragment extends Fragment {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView ingredientsList;
    private IngredientsAdapter adapter;

    // Simuler une liste d'ingrédients
    private Map<String, Boolean> allIngredients = new HashMap<>();
    private Map<String, Boolean> filteredIngredients = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likedislike, container, false);
        searchInput = view.findViewById(R.id.search_input);
        ingredientsList = view.findViewById(R.id.ingredients_list);

        // Configurer le RecyclerView
        ingredientsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientsAdapter(this.filteredIngredients, (ingredient, isChecked) -> {
            updateIngredient(ingredient, isChecked);
        });
        ingredientsList.setAdapter(adapter);

        // Charger la liste initiale d'ingrédients
        loadIngredients();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filterIngredients(s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterIngredients(s.toString());
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        // Effacer le contenu du champ de texte à chaque fois que le fragment revient au premier plan
        if (searchInput != null) {
            searchInput.setText("");
        }
    }

    private void loadIngredients() {
        // Charger la liste d'ingrédients (à implémenter)
        this.allIngredients = new HashMap<>();
        this.filteredIngredients = new HashMap<>();

        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        String url = "http://10.0.2.2:80/ingredients/evaluated";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray likedIngredients = response.getJSONArray("liked_ingredients");
                    for (int i = 0; i < likedIngredients.length(); i++) {
                        String ingredient = likedIngredients.getString(i);
                        allIngredients.put(ingredient, true);
                    }

                    JSONArray dislikedIngredients = response.getJSONArray("disliked_ingredients");
                    for (int i = 0; i < dislikedIngredients.length(); i++) {
                        String ingredient = dislikedIngredients.getString(i);
                        allIngredients.put(ingredient, false);
                    }

                    // Mise à jour après le chargement des données
                    filteredIngredients.putAll(allIngredients);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.updateIngredients(filteredIngredients);
                            adapter.notifyDataSetChanged();

                            Log.e("API Response", "Mise à jour des ingrédients");
                            Log.e("Ingrédients chargés", allIngredients.toString());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Gérer l'erreur
                Log.e("Volley", "Erreur: " + error.toString());
            }
        });

        // Ajouter la requête à la RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void filterIngredients(String query) {
        // Vérifier si la chaîne de recherche est vide
        if (query == null || query.trim().isEmpty() || query.length() == 0) {
            // Si la chaîne de recherche est vide, mettre à jour l'adapter avec la liste complète
            adapter.updateIngredients(this.allIngredients);
        } else {
            // Sinon, créer une nouvelle map pour stocker les résultats filtrés
            Map<String, Boolean> filteredIngredients = new HashMap<>();

            // Convertir la chaîne de recherche en minuscules pour une recherche insensible à la casse
            String lowerCaseQuery = query.toLowerCase();

            for (Map.Entry<String, Boolean> entry : allIngredients.entrySet()) {
                // Vérifier si le nom de l'ingrédient contient la chaîne de recherche
                if (entry.getKey().toLowerCase().contains(lowerCaseQuery)) {
                    // Ajouter à la map filtrée
                    filteredIngredients.put(entry.getKey(), entry.getValue());
                }
            }

            // Mettre à jour l'adapter avec les ingrédients filtrés
            adapter.updateIngredients(filteredIngredients);
        }
    }

    private void updateIngredient(String ingredientName, boolean isLiked) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url;
        if (isLiked) {
            url = "http://10.0.2.2:80/ingredients/like"; // Ajustez avec votre URL
        } else {
            url = "http://10.0.2.2:80/ingredients/dislike"; // Ajustez avec votre URL
        }

        Map<String, String> params = new HashMap<>();
        params.put("ingredient", ingredientName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    Log.d("Volley", "Réponse de l'API: " + response.toString());
                    // Traitez ici la réponse, si nécessaire
                },
                error -> {
                    Log.e("Volley", "Erreur lors de la mise à jour de l'ingrédient: " + error.toString());
                    // Gérez ici l'erreur
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                // Ajoutez ici d'autres headers si nécessaire, par exemple un token d'authentification
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }
}
