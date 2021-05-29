package com.team.animeapp.ui.genrefragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Genre;
import com.team.animeapp.databinding.FragmentGenreBinding;

import java.util.List;


public class GenreFragment extends Fragment {

    FragmentGenreBinding binding;

    public GenreFragment()
    {
        super(R.layout.fragment_genre);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentGenreBinding.bind(view);
        GenrePagerAdapter adapter = new GenrePagerAdapter(this);
        binding.pager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.pager,
                (tab, position) -> tab.setText(Genre.allGenres.get(position).name)
        ).attach();

    }

}

 class GenrePagerAdapter extends FragmentStateAdapter {

    List<Genre> allGenre = Genre.allGenres;

    public GenrePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new AnimeGenreFragment(allGenre.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return allGenre.size();
    }
}