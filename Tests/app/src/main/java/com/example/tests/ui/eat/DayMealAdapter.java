package com.example.tests.ui.eat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tests.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DayMealAdapter extends RecyclerView.Adapter<DayMealAdapter.ViewHolder> {

    private ArrayList<DayMeal> dayMeals;
    private Consumer<Integer> onDayRemovedListener;
    private Context context;

    public DayMealAdapter(ArrayList<DayMeal> dayMeals, Consumer<Integer> onDayRemovedListener, Context context) {
        this.dayMeals = dayMeals;
        this.onDayRemovedListener = onDayRemovedListener;
        this.context = context;
    }

    @NonNull
    @Override
    public DayMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dayMeals.size();
    }

    public void setDayMeals(ArrayList<DayMeal> dayMeals) {
        this.dayMeals = dayMeals;
        notifyDataSetChanged(); // Notifie que les données ont changé
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayMeal dayMeal = dayMeals.get(position);
        holder.dayTitle.setText(dayMeal.getDayTitle());

        holder.removeDayButton.setOnClickListener(v -> onDayRemovedListener.accept(position));

        // Exemple d'écouteur de clic pour le bouton "Matin"
        holder.btnMatin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipeName(holder.btnMatin);
            }
        });

        holder.btnMidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipeName(holder.btnMidi);
            }
        });

        holder.btnSoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipeName(holder.btnSoir);
            }
        });

        // Ajout des écouteurs pour les boutons de suppression
        holder.removeButtonMatin.setOnClickListener(v -> {
            holder.btnMatin.setHint(R.string.matin); // Réinitialise le texte du bouton "Matin"
            holder.btnMatin.setText("");
        });

        // Faites de même pour les boutons Midi et Soir
        holder.removeButtonMidi.setOnClickListener(v -> {
            holder.btnMidi.setHint(R.string.midi); // Réinitialise le texte du bouton "Midi"
            holder.btnMidi.setText("");
        });

        holder.removeButtonSoir.setOnClickListener(v -> {
            holder.btnSoir.setHint(R.string.soir); // Réinitialise le texte du bouton "Soir"
            holder.btnSoir.setText("");
        });

        Log.e("List", dayMeals.toString());
    }

    private void fetchRecipeName(final Button button) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://10.0.2.2:80/recipes?count=1";

        // Utilisez JsonArrayRequest pour gérer une réponse qui est un tableau JSON
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                JSONObject firstRecipe = response.getJSONObject(0);
                                String recipeName = firstRecipe.getString("name"); // Obtient le nom de la recette
                                button.setText(recipeName); // Met à jour le texte du bouton avec le nom de la recette
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Gestion de l'erreur de parsing JSON
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Gestion de l'erreur de requête
                error.printStackTrace();
            }
        });

        // Ajouter la requête à la RequestQueue.
        queue.add(jsonArrayRequest);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayTitle;
        Button btnMatin;
        Button btnMidi;
        Button btnSoir;
        Button removeButtonMatin;
        Button removeButtonMidi;
        Button removeButtonSoir;
        View removeDayButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dayTitle = itemView.findViewById(R.id.dayTitle);
            removeDayButton = itemView.findViewById(R.id.removeDayButton);
            btnMatin = itemView.findViewById(R.id.btnMatin);
            btnMidi = itemView.findViewById(R.id.midi);
            btnSoir = itemView.findViewById(R.id.soir);
            removeButtonMatin = itemView.findViewById(R.id.removeButtonMatin);
            removeButtonMidi = itemView.findViewById(R.id.removeButtonMidi);
            removeButtonSoir = itemView.findViewById(R.id.removeButtonSoir);
        }
    }

    public void addDayMeal(DayMeal dayMeal) {
        dayMeals.add(dayMeal);
        notifyItemInserted(dayMeals.size() - 1);
    }

    public void removeDayMeal(int position) {
        if (position >= 0 && position < dayMeals.size()) {
            dayMeals.remove(position);
            notifyItemRemoved(position);
        }
    }
}
