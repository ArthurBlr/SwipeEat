package com.example.tests.ui.eat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tests.R;
import com.example.tests.databinding.FragmentEatBinding;

public class EatFragment extends Fragment {

    private com.example.tests.databinding.FragmentEatBinding binding;

    private int semaineCounter = 2; // Utilisé pour générer un nouvel ID pour chaque semaine

    private ViewGroup linearLayout;
    // Dans votre activité principale



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EatViewModel eatViewModel = new ViewModelProvider(this).get(EatViewModel.class);

        binding = FragmentEatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtenez une référence au LinearLayout où vous ajouterez les nouveaux éléments
        linearLayout = root.findViewById(R.id.linearLayout);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        System.out.println("tag de ce fragment = " + this.getTag());

        // Obtenez une référence au bouton qui ajoute de nouveaux éléments
        Button addButton = root.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() { //FragmentManager{3d88acc in HostCallbacks{3457115}}}
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                addButton("Semaine " + semaineCounter);
            }
        });

        return root;
    }

    private ConstraintLayout createNewConstraintLayout() {
        // Créer le nouvel élément ConstraintLayout
        ConstraintLayout newLayout = new ConstraintLayout(requireContext());
        newLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));

        // Créer le bouton
        int buttonHeightInDp = 100;
        Button button = new Button(requireContext());
        button.setId(View.generateViewId());
        button.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                buttonHeightInDp
        ));
        button.setText("Semaine " + semaineCounter);
        button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.button));
        newLayout.addView(button);

        // Définir les contraintes pour le bouton
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(newLayout);
        constraintSet.connect(button.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.applyTo(newLayout);

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
                ConstraintLayout newLayout = createNewConstraintLayout();
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
        ConstraintLayout newLayout = createNewConstraintLayout();
        linearLayout.addView(newLayout);
    }

    public void deleteButtons() {
        linearLayout.removeAllViews();
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
