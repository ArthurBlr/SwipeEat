package com.example.tests.ui.eat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

        holder.btnMatin.setText(dayMeal.getBreakfastName());
        holder.btnMidi.setText(dayMeal.getLunchName());
        holder.btnSoir.setText(dayMeal.getDinnerName());

        holder.removeDayButton.setOnClickListener(v -> onDayRemovedListener.accept(position));

        holder.btnMatin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayMeal.getBreakfastId() != 0) {
                    openWebPage("https://www.food.com/recipe/" + dayMeal.getBreakfastName().replace("", "-")+ "-" + dayMeal.getBreakfastId(), context);
                } else {
                    fetchRecipeName(holder.btnMatin, position);
                }
            }
        });

        holder.btnMidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipeName(holder.btnMidi, position);
            }
        });

        holder.btnSoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipeName(holder.btnSoir, position);
            }
        });

        // Ajout des écouteurs pour les boutons de suppression
        holder.removeButtonMatin.setOnClickListener(v -> {
            dayMeals.get(position).setBreakfastName("Matin");
            dayMeals.get(position).setBreakfastId(0);
            holder.btnMatin.setText(dayMeals.get(position).getBreakfastName()); // Réinitialise le texte du bouton "Matin"
        });

        // Faites de même pour les boutons Midi et Soir
        holder.removeButtonMidi.setOnClickListener(v -> {
            dayMeals.get(position).setLunchName("Midi");
            dayMeals.get(position).setLunchId(0);
            holder.btnMidi.setText(R.string.midi); // Réinitialise le texte du bouton "Midi"
        });

        holder.removeButtonSoir.setOnClickListener(v -> {
            dayMeals.get(position).setDinnerName("Soir");
            dayMeals.get(position).setDinnerId(0);
            holder.btnSoir.setText(R.string.soir); // Réinitialise le texte du bouton "Soir"
        });

        Log.e("List", dayMeals.toString());

    }

    private void fetchRecipeName(final Button button, int position) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://10.0.2.2:80/recipes?count=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject firstRecipe = response.getJSONObject(0);
                            String recipeName = firstRecipe.getString("name");

                            // Met à jour le modèle de données
                            DayMeal dayMeal = dayMeals.get(position);
                            if (button.getId() == R.id.btnMatin) {
                                dayMeal.setBreakfastName(recipeName);
                                dayMeal.setBreakfastId(firstRecipe.getInt("recipe_id"));
                            } else if (button.getId() == R.id.midi) {
                                dayMeal.setLunchName(recipeName);
                                dayMeal.setLunchId(firstRecipe.getInt("recipe_id"));
                            } else if (button.getId() == R.id.soir) {
                                dayMeal.setDinnerName(recipeName);
                                dayMeal.setDinnerId(firstRecipe.getInt("recipe_id"));
                            }
                            button.setText(recipeName); // Met à jour l'UI
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            // Gestion de l'erreur de requête
            error.printStackTrace();
        });

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

    // Méthode pour ouvrir une page web
    private void openWebPage(String url, Context context) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        context.startActivity(intent);
        Log.e("Button", "Matin");
    }
}
