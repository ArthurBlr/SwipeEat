package com.example.tests.ui.likedislike;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tests.R;
import com.example.tests.databinding.FragmentLikedislikeBinding;

public class LikeDislikeFragment extends Fragment {

    private FragmentLikedislikeBinding binding;
    private LinearLayout linearLayout;
    private int semaineCounter = 2; // Utilisé pour générer un nouvel ID pour chaque semaine

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLikedislikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtenez une référence au LinearLayout où vous ajouterez les nouveaux éléments
        linearLayout = root.findViewById(R.id.linearLayout);

        // Obtenez une référence au bouton qui ajoute de nouveaux éléments
        Button addButton = root.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                addButton("Semaine " + semaineCounter);
            }
        });

        return root;
    }

    private ConstraintLayout createNewConstraintLayout(String buttonText) {
        // Créer le nouvel élément ConstraintLayout
        ConstraintLayout newLayout = new ConstraintLayout(requireContext());
        newLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));

        // Créer le bouton
        Button button = new Button(requireContext());
        button.setId(View.generateViewId());
        button.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        button.setText(buttonText);
        button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.button));
        newLayout.addView(button);

        // Créer le SwitchCompat
        SwitchCompat switchCompat = new SwitchCompat(requireContext());
        switchCompat.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        switchCompat.setId(View.generateViewId());
        switchCompat.setButtonTintList(ContextCompat.getColorStateList(requireContext(), R.color.button));
        newLayout.addView(switchCompat);

        // Définir les contraintes pour le bouton
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(newLayout);
        constraintSet.connect(button.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.constrainWidth(button.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainPercentWidth(button.getId(), 0.85f);

        // Définir les contraintes pour le SwitchCompat
        constraintSet.constrainWidth(switchCompat.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainPercentWidth(switchCompat.getId(), 0.15f);
        constraintSet.connect(switchCompat.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(switchCompat.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        constraintSet.applyTo(newLayout);

        // Incrémenter le compteur de semaine pour le prochain bouton
        semaineCounter++;

        return newLayout;
    }

    private static final String BUTTON_COUNT_KEY = "button_count";

    private int getButtonCount() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getInt(BUTTON_COUNT_KEY, 0);
    }

    private void incrementButtonCount() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int count = getButtonCount() + 1;
        preferences.edit().putInt(BUTTON_COUNT_KEY, count).apply();
    }

    private void saveButton(String buttonText) {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int count = getButtonCount();
        preferences.edit().putString("button_" + count, buttonText).apply();
    }

    private String getButton(int index) {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getString("button_" + index, "");
    }

    private void loadButtons() {
        int count = getButtonCount();
        for (int i = 0; i < count; i++) {
            String buttonText = getButton(i);
            if (!buttonText.isEmpty()) {
                ConstraintLayout newLayout = createNewConstraintLayout(buttonText);
                linearLayout.addView(newLayout);
            }
        }
    }



    private void addButton(String buttonText) {
        // Enregistrer le texte du bouton
        saveButton(buttonText);
        // Incrémenter le compteur de boutons
        incrementButtonCount();
        // Créer et ajouter le bouton à l'interface utilisateur
        ConstraintLayout newLayout = createNewConstraintLayout(buttonText);
        linearLayout.addView(newLayout);
    }

    public void deleteButtons() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        linearLayout.removeAllViews();
        semaineCounter = 2;
        // Effacez également les données stockées si nécessaire
    }

    @Override
    public void onResume() {
        super.onResume();
        loadButtons();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
