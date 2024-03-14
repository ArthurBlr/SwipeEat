package com.example.tests.ui.likedislike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private ViewGroup layout;
    private int semaineCounter = 2; // Utilisé pour générer un nouvel ID pour chaque semaine

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LikeDislikeViewModel likeDislikeViewModel =
                new ViewModelProvider(this).get(LikeDislikeViewModel.class);

        binding = FragmentLikedislikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtenez une référence au conteneur parent
        layout = root.findViewById(R.id.LikeDislike);

        // Obtenez une référence au bouton qui ajoute de nouveaux éléments
        Button addButton = root.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez un nouvel élément ConstraintLayout
                ConstraintLayout newLayout = createNewConstraintLayout();
                // Ajoutez le nouvel élément ConstraintLayout au conteneur parent
                layout.addView(newLayout);
            }
        });

        return root;
    }

    // Méthode pour créer un nouvel élément ConstraintLayout
    private ConstraintLayout createNewConstraintLayout() {
        // Créer le nouvel élément ConstraintLayout
        ConstraintLayout newLayout = new ConstraintLayout(requireContext());
        newLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));

        // Créer le bouton
        Button button = new Button(requireContext());
        button.setId(View.generateViewId()); // Générez un ID unique pour chaque bouton
        button.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur 0 pour correspondre aux contraintes
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        button.setText("Semaine " + semaineCounter);
        button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.button));
        newLayout.addView(button);

        // Créer le SwitchCompat
        SwitchCompat switchCompat = new SwitchCompat(requireContext());
        switchCompat.setLayoutParams(new ConstraintLayout.LayoutParams(
                0, // Largeur 0 pour correspondre aux contraintes
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        switchCompat.setId(View.generateViewId()); // Générez un ID unique pour chaque SwitchCompat
        switchCompat.setButtonTintList(ContextCompat.getColorStateList(requireContext(), R.color.button));
        newLayout.addView(switchCompat);

        // Définir les contraintes pour le bouton
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(newLayout);
        constraintSet.connect(button.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(button.getId(), ConstraintSet.END, switchCompat.getId(), ConstraintSet.START);
        constraintSet.applyTo(newLayout);

        // Définir les contraintes pour le SwitchCompat
        constraintSet.connect(switchCompat.getId(), ConstraintSet.START, button.getId(), ConstraintSet.END);
        constraintSet.connect(switchCompat.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(switchCompat.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.applyTo(newLayout);

        // Incrémenter le compteur de semaine pour le prochain bouton
        semaineCounter++;

        return newLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
