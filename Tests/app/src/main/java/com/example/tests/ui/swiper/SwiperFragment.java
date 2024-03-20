package com.example.tests.ui.swiper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.tests.R;
import com.example.tests.databinding.FragmentSwiperBinding;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SwiperFragment extends Fragment {

    private FragmentSwiperBinding binding;

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swiper, container, false);

        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {}

            @Override
            public void onCardSwiped(Direction direction) {
                int position = manager.getTopPosition() - 1;
                Ingredient likedIngredient = adapter.getIngredientAt(position);
                if (direction == Direction.Right) {
                    envoyerNomIngredientSwiped(likedIngredient.getName(), true);
                } else if (direction == Direction.Left) {
                    envoyerNomIngredientSwiped(likedIngredient.getName(), false);
                }
            }

            @Override
            public void onCardRewound() {}

            @Override
            public void onCardCanceled() {}

            @Override
            public void onCardAppeared(View view, int position) {}

            @Override
            public void onCardDisappeared(View view, int position) {}
        });

        adapter = new CardStackAdapter(createIngredients());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);

        AppCompatButton buttonLike = view.findViewById(R.id.button_like);
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Right)
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });
        AppCompatButton buttonDislike = view.findViewById(R.id.button_dislike);
        buttonDislike.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .build();
            manager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });

        return view;
    }

    private void envoyerNomIngredientSwiped(String nomIngredient, boolean liked) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url;
        if (liked) {
            url = "http://10.0.2.2:80/ingredients/like";
        }
        else {
            url = "http://10.0.2.2:80/ingredients/dislike";
        }

        JSONObject postData = new JSONObject();
        try {
            postData.put("ingredient", nomIngredient);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                response -> Log.d("API", "Réponse: " + response),
                error -> Log.e("API", "Erreur: " + error)
        );

        queue.add(jsonObjectRequest);
    }


    private List<Ingredient> createIngredients() {
        List<Ingredient> Ingredients = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        String url = "http://10.0.2.2:80/ingredients/not_evaluated";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Traitement de la réponse
                        // Exemple : textView.setText("Réponse : " + response.toString());
                        try {
                            JSONArray ingredients = response.getJSONArray("ingredients");
                            for (int i = 0; i < ingredients.length(); i++) {
                                String ingredient = ingredients.getString(i);

                                // Affichage de l'ingrédient
                                //System.out.println("Ingrédient: " + ingredient);
                                Ingredients.add(new Ingredient(ingredient, R.drawable.ic_launcher_background));
                            }
                            adapter.notifyDataSetChanged(); // Informer l'adapter
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
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

        return Ingredients;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}