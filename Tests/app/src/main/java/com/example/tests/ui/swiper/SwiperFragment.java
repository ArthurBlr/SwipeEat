package com.example.tests.ui.swiper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tests.R;
import com.example.tests.databinding.FragmentSwiperBinding;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;
import java.util.List;

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
                // Vous pouvez gérer les actions après un swipe ici
                System.out.println("Swiped: " + direction.toString());
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

        return view;
    }

    private List<Ingredient> createIngredients() {
        List<Ingredient> Ingredients = new ArrayList<>();
        // Ajoutez vos profils ici, exemple :
        Ingredients.add(new Ingredient("Nom jjd d d f df r e er r r rz zr ezr er zer es sr s es ersserersersser se ers ser ers sre ers ser serersers rs ers srers1", R.drawable.ic_launcher_background));
        Ingredients.add(new Ingredient("Nom 2", R.drawable.ic_launcher_background));
        // et ainsi de suite...
        return Ingredients;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}