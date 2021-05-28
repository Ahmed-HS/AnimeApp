package com.team.animeapp.ui.searchfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.iammert.library.ui.multisearchviewlib.MultiSearchView;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.models.network.Genre;
import com.team.animeapp.data.sources.local.AnimeDatabase;
import com.team.animeapp.databinding.FragmentSearchBinding;
import com.team.animeapp.databinding.ItemAnimeBinding;
import com.team.animeapp.databinding.ItemFilterBinding;
import com.team.animeapp.domain.Result;
import com.team.animeapp.util.AnimationHelper;
import com.team.animeapp.util.recyclerview.BaseListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;

    SearchViewModel viewModel;

    List<Integer> filters;

    String lastSearch = "";

    @Inject
    AnimeDatabase animeDatabase;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        filters = new ArrayList<>();
        setupFilters();
        setupSearch();
        setupAnimeList();
    }

    private void setupAnimeList()
    {
        BaseListAdapter<Anime, ItemAnimeBinding> animeListAdapter = new BaseListAdapter<>(R.layout.item_anime,(binding, anime, position) -> {

            Glide.with(binding.image)
                    .load(anime.imageUrl)
                    .into(binding.image);

            binding.title.setText(anime.title);

            binding.image.setOnClickListener(v -> {
                if (animeDatabase.isInWatchLater(anime)) {
                    animeDatabase.removeFromWatchLater(anime);
                    Snackbar.make(binding.getRoot(), "Removed " + anime.title + " from watch later list", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    animeDatabase.addToWatchLater(anime);
                    Snackbar.make(binding.getRoot(), "Added " + anime.title + " to watch later list", Snackbar.LENGTH_LONG)
                            .show();
                }

            });
        });

        binding.animeList.setAdapter(animeListAdapter);

        viewModel.searchResults.observe(getViewLifecycleOwner(), new Observer<Result<List<Anime>>>() {
            @Override
            public void onChanged(Result<List<Anime>> listResult) {
                if(listResult instanceof Result.Loading)
                {
                    binding.loadingIndicator.setRefreshing(true);
                }
                else if(listResult instanceof Result.Success)
                {
                    binding.loadingIndicator.setRefreshing(false);
                    animeListAdapter.submitList(((Result.Success<List<Anime>>) listResult).data);
                }
            }
        });
    }


    private void setupSearch()
    {
        binding.searchBar.setSearchViewListener(new MultiSearchView.MultiSearchViewListener() {
            @Override
            public void onTextChanged(int i, CharSequence charSequence) {

            }

            @Override
            public void onSearchComplete(int i, CharSequence charSequence) {

                viewModel.searchAnime(charSequence.toString(),filters);
                lastSearch = charSequence.toString();
            }

            @Override
            public void onSearchItemRemoved(int i) {
                viewModel.searchAnime("",null);
                lastSearch = "";
            }

            @Override
            public void onItemSelected(int i, CharSequence charSequence) {

            }
        });
    }

    private void setupFilters()
    {

        binding.filterBar.getRoot().setVisibility(View.INVISIBLE);

        binding.filterBtn.setOnClickListener (view ->{
            int visibility = binding.filterBar.getRoot().getVisibility();
            if(visibility == View.INVISIBLE)
            {
                AnimationHelper.playAnimation(binding.filterBar.getRoot(),R.anim.slide_up_fade_in);
                binding.filterBar.getRoot().setVisibility(View.VISIBLE);
            }
            else if (visibility == View.VISIBLE)
            {
                AnimationHelper.playAnimation(binding.filterBar.getRoot(),R.anim.slide_down_fade_out);
                binding.filterBar.getRoot().setVisibility(View.INVISIBLE);
            }
        });



        BaseListAdapter<Genre, ItemFilterBinding> filtersAdapter = new TagsAdapter(R.layout.item_filter,
        (genre,isChecked,checkedFilters) ->{
            filters = checkedFilters.stream().map(filter -> filter.malId).collect(Collectors.toList());
            viewModel.searchAnime(lastSearch,filters);
        });

        filtersAdapter.submitList(Genre.allGenres);

        binding.filterBar.filterChips.setAdapter(filtersAdapter);
    }
}
