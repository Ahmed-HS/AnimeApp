package com.team.animeapp.ui.topanimefragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.sources.local.AnimeDatabase;
import com.team.animeapp.databinding.FragmentTopAnimeBinding;
import com.team.animeapp.databinding.ItemAnimeBinding;
import com.team.animeapp.util.recyclerview.BaseListAdapter;
import com.team.animeapp.util.recyclerview.Bindable;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopAnimeFragment extends Fragment {

    private static final String TAG  = TopAnimeFragment.class.getSimpleName();

    @Inject
    AnimeDatabase animeDatabase;

    FragmentTopAnimeBinding binding;

    TopAnimeViewModel viewModel;

    public TopAnimeFragment() {
        // Required empty public constructor
        super(R.layout.fragment_top_anime);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentTopAnimeBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(TopAnimeViewModel.class);

        BaseListAdapter<Anime, ItemAnimeBinding> topAnimeListAdapter = new BaseListAdapter<>(R.layout.item_anime,(binding,anime,position) ->{

            Glide.with(binding.image)
                    .load(anime.imageUrl)
                    .into(binding.image);

            binding.title.setText(anime.title);

            binding.image.setOnClickListener(v ->{
                if(animeDatabase.isInWatchLater(anime))
                {
                    animeDatabase.removeFromWatchLater(anime);
                    Snackbar.make(binding.getRoot(),"Removed " + anime.title + " from watch later list",Snackbar.LENGTH_LONG)
                    .show();
                }
                else {
                    animeDatabase.addToWatchLater(anime);
                    Snackbar.make(binding.getRoot(),"Added " + anime.title + " to watch later list",Snackbar.LENGTH_LONG)
                            .show();
                }
            });

        });

        binding.topAnimeList.setAdapter(topAnimeListAdapter);

        viewModel.topAnime.observe(getViewLifecycleOwner(), new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                Log.d(TAG, "onChanged: " + anime.toString());
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