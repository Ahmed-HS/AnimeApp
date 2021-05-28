package com.team.animeapp.ui.watchlaterfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.sources.local.AnimeDatabase;
import com.team.animeapp.databinding.FragmentSearchBinding;
import com.team.animeapp.databinding.FragmentWatchLaterBinding;
import com.team.animeapp.databinding.ItemAnimeBinding;
import com.team.animeapp.util.recyclerview.BaseListAdapter;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WatchLaterFragment extends Fragment {

    @Inject
    AnimeDatabase animeDatabase;

    FragmentWatchLaterBinding binding;

    public WatchLaterFragment() {
        // Required empty public constructor
        super(R.layout.fragment_watch_later);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentWatchLaterBinding.bind(view);

        BaseListAdapter<Anime, ItemAnimeBinding> animeListAdapter = new BaseListAdapter<>(R.layout.item_anime,(binding, anime, position) ->{

            Glide.with(binding.image)
                    .load(anime.imageUrl)
                    .into(binding.image);

            binding.title.setText(anime.title);

            binding.image.setOnClickListener(v ->{
            });

        });

        animeListAdapter.submitList(animeDatabase.getWatchLaterList());

        binding.animeList.setAdapter(animeListAdapter);

    }
}