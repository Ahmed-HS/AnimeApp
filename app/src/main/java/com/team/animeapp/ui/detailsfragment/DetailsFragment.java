package com.team.animeapp.ui.detailsfragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.sources.remote.JikanApi;
import com.team.animeapp.databinding.FragmentDetailsBinding;
import com.team.animeapp.databinding.FragmentSearchBinding;
import com.team.animeapp.domain.Result;
import com.team.animeapp.domain.repositories.AnimeRepository;
import com.team.animeapp.ui.searchfragment.SearchViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class DetailsFragment extends Fragment {

  FragmentDetailsBinding binding;
  Anime anime;
  @Inject
  JikanApi jikanApi;
  int counter;

    public DetailsFragment() {
        // Required empty public constructor
        super(R.layout.fragment_details);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.bind(view);
        counter = 0;
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            int  currAnimeID = bundle.getInt("anime");

            jikanApi.getAnimeById(currAnimeID).enqueue(new Callback<Anime>() {
                @Override
                public void onResponse(Call<Anime> call, Response<Anime> response) {
                    if(response!=null) {
                        anime = response.body();
                        System.out.println("DataAnime"+anime);
                        binding.movieImg.post(() -> {
                            Glide.with(binding.movieImg)
                                    .load(anime.imageUrl)
                                    .into(binding.movieImg);

                            binding.movieName.setText(anime.title);

                            WebSettings webSettings = binding.webview.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webSettings.getBuiltInZoomControls();
                            webSettings.getCacheMode();
                            binding.webview.loadUrl(anime.trailerUrl);

                            binding.tag.setText(anime.type);

                            binding.simpleRatingBar.setRating(anime.rating%5);

                            binding.movieDesc.setText(anime.synopsis);

                            System.out.println("hello"+anime.genres.get(0).name);
                            System.out.println("hello"+anime.studios.get(0).name);
                            System.out.println("hello"+anime.endDate);

                        });

                    }
                }

                @Override
                public void onFailure(Call<Anime> call, Throwable t) {

                }
            });




        }
    }

}