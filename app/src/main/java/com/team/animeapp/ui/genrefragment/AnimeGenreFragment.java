package com.team.animeapp.ui.genrefragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.models.network.Genre;
import com.team.animeapp.data.sources.local.AnimeDatabase;
import com.team.animeapp.databinding.FragmentTopAnimeBinding;
import com.team.animeapp.databinding.ItemAnimeBinding;
import com.team.animeapp.ui.topanimefragment.TopAnimeViewModel;
import com.team.animeapp.util.recyclerview.BaseListAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AnimeGenreFragment extends Fragment {

    private static final String TAG  = AnimeGenreFragment.class.getSimpleName();

    Genre genreToDisplay;

    FragmentTopAnimeBinding binding;

    GenreViewModel viewModel;

    public AnimeGenreFragment(Genre genre) {
        // Required empty public constructor
        super(R.layout.fragment_top_anime);

        genreToDisplay = genre;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentTopAnimeBinding.bind(view);

        viewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        viewModel.fetchAnimeInGenre(genreToDisplay.malId);

        BaseListAdapter<Anime, ItemAnimeBinding> topAnimeListAdapter = new BaseListAdapter<>(R.layout.item_anime,(binding,anime,position) ->{

            binding.rank.setVisibility(View.INVISIBLE);

            binding.setAnime(anime);

            binding.getRoot().setOnClickListener(v ->{

                Bundle bundle = new Bundle();
                bundle.putInt("anime",anime.malId);
                Navigation.findNavController(getView()).navigate(R.id.detailsFragment,bundle);

            });


        });

        binding.topAnimeList.setAdapter(topAnimeListAdapter);

        viewModel.genreResults.observe(getViewLifecycleOwner(), new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                binding.progressIndicator.setVisibility(View.GONE);
                topAnimeListAdapter.submitList(anime);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}