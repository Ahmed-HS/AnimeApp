package com.team.animeapp.ui.genrefragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.models.network.GenreResponse;
import com.team.animeapp.data.sources.remote.JikanApi;
import com.team.animeapp.domain.Result;
import com.team.animeapp.domain.repositories.AnimeRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class GenreViewModel extends ViewModel {

    private JikanApi jikanApi;

    public MutableLiveData<List<Anime>> genreResults;

    @Inject
    GenreViewModel(JikanApi jikanApi)
    {
        this.jikanApi = jikanApi;
        genreResults = new MutableLiveData<>();
    }

    public void fetchAnimeInGenre(int genreId)
    {
        Log.d("Fetch", "fetchAnimeInGenre: " + genreId);

        jikanApi.getAnimeInGenre(genreId).enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if(response.body() != null)
                {
                    genreResults.postValue(response.body().anime);
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
            }
        });

    }

}
