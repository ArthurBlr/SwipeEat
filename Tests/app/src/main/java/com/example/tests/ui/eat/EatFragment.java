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


        // Obtenez une référence au bouton qui ajoute de nouveaux éléments
        Button addButton = root.findViewById(R.id.button_ajout_jour);
        addButton.setOnClickListener(new View.OnClickListener() { //FragmentManager{3d88acc in HostCallbacks{3457115}}}
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                addConstraintLayout(createNewConstraintLayout());
            }
        });

        Button deleteButton = root.findViewById(R.id.button_suppr_jour);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                deleteLayout();
            }
        });

        return root;
    }

    private ConstraintLayout createNewConstraintLayout() {
        ConstraintLayout newLayout = new ConstraintLayout(requireContext());
        newLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                dpToPx(282)
        ));
        newLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button));

        AppCompatButton buttonSupprJour = createButtonSuppr(R.id.button_suppr_jour, newLayout);
        buttonSupprJour.setOnClickListener(new View.OnClickListener() { //FragmentManager{3d88acc in HostCallbacks{3457115}}}
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                newLayout.removeAllViews();
                linearLayout.removeView(newLayout);
                // Effacez également les données stockées si nécessaire
                SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                preferences.edit().remove("constraint_layout_" + getConstraintLayoutCount()).apply();
            }
        });
        newLayout.addView(buttonSupprJour);
        AppCompatButton buttonMatin = createButtonAdd(R.id.button_matin, R.drawable.rounded_dashed_button, newLayout);
        AppCompatButton buttonMidi = createButtonAdd(R.id.button_midi, R.drawable.rounded_dashed_button, newLayout);
        AppCompatButton buttonSoir = createButtonAdd(R.id.button_soir, R.drawable.rounded_dashed_button, newLayout);
        AppCompatButton buttonSupprMatin = createButtonSuppr(R.id.button_suppr_matin, newLayout);
        buttonSupprMatin.setOnClickListener(new View.OnClickListener() { //FragmentManager{3d88acc in HostCallbacks{3457115}}}
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                buttonMatin.setText(R.string.txt_butt_add);
                buttonMatin.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_dashed_button));
            }
        });
        newLayout.addView(buttonSupprMatin);
        AppCompatButton buttonSupprMidi = createButtonSuppr(R.id.button_suppr_midi, newLayout);
        buttonSupprMidi.setOnClickListener(new View.OnClickListener() { //FragmentManager{3d88acc in HostCallbacks{3457115}}}
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                buttonMidi.setText(R.string.txt_butt_add);
                buttonMidi.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_dashed_button));
            }
        });
        newLayout.addView(buttonSupprMidi);
        AppCompatButton buttonSupprSoir = createButtonSuppr(R.id.button_suppr_soir, newLayout);
        buttonSupprSoir.setOnClickListener(new View.OnClickListener() { //FragmentManager{3d88acc in HostCallbacks{3457115}}}
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                buttonSoir.setText(R.string.txt_butt_add);
                buttonSoir.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_dashed_button));
            }
        });
        newLayout.addView(buttonSupprSoir);
        TextView textView = new TextView(requireContext());
        textView.setId(R.id.textView);
        textView.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
        textView.setGravity(Gravity.CENTER);
        textView.setText("Jour " + semaineCounter);
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.background));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        newLayout.addView(textView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(newLayout);

        constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, R.id.button_matin, ConstraintSet.TOP);
        constraintSet.constrainPercentWidth(textView.getId(), 0.3f);


        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.START, R.id.textView, ConstraintSet.END, dpToPx(40));
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.TOP, R.id.textView, ConstraintSet.TOP, dpToPx(10));
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(48));
        constraintSet.connect(buttonSupprJour.getId(), ConstraintSet.BOTTOM, R.id.textView, ConstraintSet.BOTTOM, dpToPx(10));

        constraintSet.connect(buttonMatin.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(5));
        constraintSet.connect(buttonMatin.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dpToPx(60));
        constraintSet.constrainPercentWidth(buttonMatin.getId(), 0.80f);



        constraintSet.connect(buttonMidi.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(5));
        constraintSet.connect(buttonMidi.getId(), ConstraintSet.TOP, buttonMatin.getId(), ConstraintSet.BOTTOM, dpToPx(5));
        constraintSet.constrainPercentWidth(buttonMidi.getId(), 0.80f);


        constraintSet.connect(buttonSoir.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(5));
        constraintSet.connect(buttonSoir.getId(), ConstraintSet.TOP, buttonMidi.getId(), ConstraintSet.BOTTOM, dpToPx(5));
        constraintSet.constrainPercentWidth(buttonSoir.getId(), 0.80f);


        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.START, R.id.button_matin, ConstraintSet.END , dpToPx(15));
        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.TOP, R.id.button_matin, ConstraintSet.TOP, dpToPx(10));
        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(13));
        constraintSet.connect(buttonSupprMatin.getId(), ConstraintSet.BOTTOM, R.id.button_matin, ConstraintSet.BOTTOM, dpToPx(10));


        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.START, R.id.button_midi, ConstraintSet.END , dpToPx(15));
        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.TOP, R.id.button_midi, ConstraintSet.TOP, dpToPx(10));
        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(13));
        constraintSet.connect(buttonSupprMidi.getId(), ConstraintSet.BOTTOM, R.id.button_midi, ConstraintSet.BOTTOM, dpToPx(10));


        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.START, R.id.button_soir, ConstraintSet.END , dpToPx(15));
        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.TOP, R.id.button_soir, ConstraintSet.TOP, dpToPx(10));
        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(13));
        constraintSet.connect(buttonSupprSoir.getId(), ConstraintSet.BOTTOM, R.id.button_soir, ConstraintSet.BOTTOM, dpToPx(10));


        constraintSet.applyTo(newLayout);

        semaineCounter++;

        return newLayout;
    }

    private AppCompatButton createButtonAdd(int id, int backgroundResource, ConstraintLayout layout) {
        AppCompatButton button = new AppCompatButton(requireContext());
        button.setId(id);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(0, dpToPx(60)));
        button.setText(getString(R.string.txt_butt_add));
        button.setBackground(ContextCompat.getDrawable(requireContext(), backgroundResource));
        //event listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                button.setText("Bouton cliqué");
                button.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_lined_button));
            }
        });
        layout.addView(button);
        return button;
    }

    private AppCompatButton createButtonSuppr(int id, ConstraintLayout layout) {
        AppCompatButton button = new AppCompatButton(requireContext());
        button.setId(id);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
        button.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.close_button));
        return button;
    }


    // Méthode pour convertir des dp en pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


    private static final String CONSTRAINT_LAYOUT_COUNT_KEY = "constraint_layout_count";

    private int getConstraintLayoutCount() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getInt(CONSTRAINT_LAYOUT_COUNT_KEY, 0);
    }

    private void incrementConstraintLayoutCount() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int count = getConstraintLayoutCount() + 1;
        preferences.edit().putInt(CONSTRAINT_LAYOUT_COUNT_KEY, count).apply();
    }

    private void saveConstraintLayout(ConstraintLayout layout, int index) {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("constraint_layout_" + index,  ConstraintLayoutSerializer.serializeConstraintLayout(layout)).apply();
    }

    private String getConstraintLayout(int index) {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getString("constraint_layout_" + index, "");
    }

    private void loadConstraintLayouts() {
        int count = getConstraintLayoutCount();
        for (int i = 0; i < count; i++) {
            String serializedLayout = getConstraintLayout(i);
            if (!serializedLayout.isEmpty()) {
                ConstraintLayout layout =  ConstraintLayoutSerializer.deserializeConstraintLayout(serializedLayout, requireContext());
                if (layout != null) {
                    linearLayout.addView(layout);
                }
            }
        }
    }

    private void addConstraintLayout(ConstraintLayout layout) {
        // Enregistrer le layout
        saveConstraintLayout(layout, getConstraintLayoutCount());
        // Incrémenter le compteur de layouts
        incrementConstraintLayoutCount();
        // Ajouter le layout à l'interface utilisateur
        linearLayout.addView(layout);
    }

    public void deleteLayout() {
        linearLayout.removeAllViews();
        // Effacez également les données stockées si nécessaire
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        semaineCounter = 1;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadConstraintLayouts();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
