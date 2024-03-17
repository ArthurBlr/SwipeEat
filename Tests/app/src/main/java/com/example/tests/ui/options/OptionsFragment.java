package com.example.tests.ui.options;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tests.R;
import com.example.tests.databinding.FragmentOptionsBinding;
import com.example.tests.ui.eat.EatFragment;
import com.example.tests.ui.likedislike.LikeDislikeFragment;

import org.json.JSONObject;

public class OptionsFragment extends Fragment {

    private FragmentOptionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addButton = root.findViewById(R.id.button_supprimer);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter le bouton en appelant la méthode addButton
                deleteDatas();

            }
        });
        return root;
    }

    private void deleteDatas() {
        // Créer une boîte de dialogue AlertDialog pour demander confirmation à l'utilisateur
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmation de suppression");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer les données ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = "http://10.0.2.2:80/reset";

                JSONObject postData = new JSONObject();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                        response -> Log.d("API", "Réponse: " + response),
                        error -> Log.e("API", "Erreur: " + error)
                );

                queue.add(jsonObjectRequest);





                // Obtenir une référence au gestionnaire de fragments de l'activité parente
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Supprimer les boutons stockés dans EatFragment
                EatFragment eatFragment = (EatFragment) fragmentManager.findFragmentById(R.id.navigation_eat);
                if (eatFragment != null) {
                    eatFragment.deleteButtons();
                }

                // Supprimer les boutons stockés dans LikeDislikeFragment
                LikeDislikeFragment likeDislikeFragment = (LikeDislikeFragment) fragmentManager.findFragmentById(R.id.navigation_likedislike);
                if (likeDislikeFragment != null) {
                    likeDislikeFragment.deleteButtons();
                }
            }
        });
        builder.setNegativeButton("Non", null); // Si l'utilisateur annule

        // Afficher la boîte de dialogue
        builder.show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}