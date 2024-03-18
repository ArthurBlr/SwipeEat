package com.example.tests.ui.eat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
                282 // Hauteur fixe de 282dp
        ));
        newLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button)); // Définir la couleur de fond

        // Créer le bouton "Supprimer Jour"
        AppCompatButton buttonSupprJour = new AppCompatButton(requireContext());
        buttonSupprJour.setId(View.generateViewId());
        buttonSupprJour.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                0 // Hauteur à 0dp pour un dimensionnement automatique
        ));
        buttonSupprJour.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.close_button)); // Définir le fond du bouton
        newLayout.addView(buttonSupprJour);

        // Créer le bouton "Matin"
        AppCompatButton buttonMatin = new AppCompatButton(requireContext());
        buttonMatin.setId(View.generateViewId());
        buttonMatin.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                60 // Hauteur fixe de 60dp
        ));
        buttonMatin.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_dashed_button)); // Définir le fond du bouton
        buttonMatin.setText(getString(R.string.txt_butt_add)); // Définir le texte du bouton
        newLayout.addView(buttonMatin);

        // Créer le bouton "Midi"
        AppCompatButton buttonMidi = new AppCompatButton(requireContext());
        buttonMidi.setId(View.generateViewId());
        buttonMidi.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                60 // Hauteur fixe de 60dp
        ));
        buttonMidi.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_dashed_button)); // Définir le fond du bouton
        buttonMidi.setText(getString(R.string.txt_butt_add)); // Définir le texte du bouton
        newLayout.addView(buttonMidi);

        // Créer le bouton "Soir"
        AppCompatButton buttonSoir = new AppCompatButton(requireContext());
        buttonSoir.setId(View.generateViewId());
        buttonSoir.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                60 // Hauteur fixe de 60dp
        ));
        buttonSoir.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_dashed_button)); // Définir le fond du bouton
        buttonSoir.setText(getString(R.string.txt_butt_add)); // Définir le texte du bouton
        newLayout.addView(buttonSoir);

        // Création du bouton de suppression du matin
        AppCompatButton buttonSupprMatin = new AppCompatButton(requireContext());
        buttonSupprMatin.setId(View.generateViewId());
        buttonSupprMatin.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                0 // Hauteur à 0dp pour un dimensionnement automatique
        ));
        buttonSupprMatin.setBackgroundResource(R.drawable.close_button);
        newLayout.addView(buttonSupprMatin);

        // Création du bouton de suppression du midi
        AppCompatButton buttonSupprMidi = new AppCompatButton(requireContext());
        buttonSupprMidi.setId(View.generateViewId());
        buttonSupprMidi.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                0 // Hauteur à 0dp pour un dimensionnement automatique
        ));
        buttonSupprMidi.setBackgroundResource(R.drawable.close_button);
        newLayout.addView(buttonSupprMidi);

        // Création du bouton de suppression du soir
        AppCompatButton buttonSupprSoir = new AppCompatButton(requireContext());
        buttonSupprSoir.setId(View.generateViewId());
        buttonSupprSoir.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur à 0dp pour un dimensionnement automatique
                0 // Hauteur à 0dp pour un dimensionnement automatique
        ));

        buttonSupprSoir.setBackgroundResource(R.drawable.close_button);
        newLayout.addView(buttonSupprSoir);

        // Création du TextView
        TextView textView = new TextView(requireContext());
        textView.setId(R.id.textView);
        textView.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
        textView.setGravity(Gravity.CENTER);
        textView.setText(String.format("%d %d", R.string.name_day, semaineCounter));
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.background));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

        // Ajouter les contraintes pour les boutons
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(newLayout);
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(40));
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dpToPx(10));
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(48));
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.BOTTOM, R.id.textView, ConstraintSet.BOTTOM); // Référence au TextView

        constraintSet.connect(buttonMatin.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(5));
        constraintSet.connect(buttonMatin.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dpToPx(60));
        constraintSet.connect(buttonMatin.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(5));
        constraintSet.constrainPercentWidth(buttonMatin.getId(), 0.80f);

        constraintSet.connect(buttonMidi.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(5));
        constraintSet.connect(buttonMidi.getId(), ConstraintSet.TOP, buttonMatin.getId(), ConstraintSet.BOTTOM, dpToPx(5));
        constraintSet.connect(buttonMidi.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(5));
        constraintSet.constrainPercentWidth(buttonMidi.getId(), 0.80f);

        constraintSet.connect(buttonSoir.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(5));
        constraintSet.connect(buttonSoir.getId(), ConstraintSet.TOP, buttonMidi.getId(), ConstraintSet.BOTTOM, dpToPx(5));
        constraintSet.connect(buttonSoir.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(5));
        constraintSet.constrainPercentWidth(buttonSoir.getId(), 0.80f);

        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.START, R.id.button_matin, ConstraintSet.END);
        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.TOP, R.id.button_matin, ConstraintSet.TOP);
        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.BOTTOM, R.id.button_matin, ConstraintSet.BOTTOM);

        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.START, R.id.button_midi, ConstraintSet.END);
        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.TOP, R.id.button_midi, ConstraintSet.TOP);
        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.BOTTOM, R.id.button_midi, ConstraintSet.BOTTOM);

        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.START, R.id.button_soir, ConstraintSet.END);
        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.TOP, R.id.button_soir, ConstraintSet.TOP);
        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.BOTTOM, R.id.button_soir, ConstraintSet.BOTTOM);

        constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, R.id.button_matin, ConstraintSet.TOP);
        constraintSet.constrainPercentWidth(textView.getId(), 0.3f);

        constraintSet.applyTo(newLayout);

        semaineCounter++;

        return newLayout;
    }

    // Méthode pour convertir des dp en pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
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
